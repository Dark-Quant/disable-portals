package ru.quantum_emperor.disable_portals.events;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.dimension.AreaHelper;
import net.minecraft.world.explosion.Explosion;
import paulevs.edenring.EdenRing;
import paulevs.edenring.world.EdenPortal;
import ru.quantum_emperor.disable_portals.config.Settings;

import javax.swing.*;
import java.util.HashSet;
import java.util.Set;

public class PortalActivationEvent implements UseBlockCallback {
    private static final Set<Item> igniting = new HashSet<>();

    static {
        igniting.add(Items.FLINT_AND_STEEL);
        igniting.add(Items.FIRE_CHARGE);
    }

    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        if (Settings.isDisabledNether()) {
            ActionResult result = disableNether(player, world, hand, hitResult);
            if (result != ActionResult.PASS)
                return result;
        }

        if (Settings.isDisableEden()) {
            ActionResult result = disableEden(player, world, hand, hitResult);
            if (result != ActionResult.PASS)
                return result;
        }
        return ActionResult.PASS;
    }

    private ActionResult disableEden(PlayerEntity player, World level, Hand hand, BlockHitResult hitResult) {
        if (level.isClient || hitResult.getSide() != Direction.UP) return ActionResult.PASS;
        BlockPos pos = hitResult.getBlockPos();
        boolean correctDimension = level.getRegistryKey().equals(World.OVERWORLD) || level.getRegistryKey() == EdenRing.EDEN_RING_KEY;
        boolean isIgnitingItem = player.getStackInHand(hand).getItem() == Items.FLINT_AND_STEEL;
        if (correctDimension && isIgnitingItem
                && level.getBlockState(pos).isOf(Blocks.GOLD_BLOCK) && EdenPortal.checkNewPortal(level, pos.up())) {
            level.createExplosion(null, (double) pos.getX() + 0.5, (double) pos.getY() + 1.5, (double) pos.getZ() + 0.5, 2.0f, false, Explosion.DestructionType.NONE);
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, Settings.getDarknessDuration()));
            player.sendMessage(Text.translatable("disable_portal.cancel_activation", "§b§leden"));
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    private ActionResult disableNether(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        if (world.isClient)
            return ActionResult.PASS;
        ItemStack itemStack = player.getStackInHand(hand);
        BlockPos pos = hitResult.getBlockPos();
        BlockPos pos2 = pos.offset(hitResult.getSide());
        boolean canPlaced = AbstractFireBlock.canPlaceAt(world, pos2, player.getHorizontalFacing());
        boolean canActivated = world.getRegistryKey() == World.OVERWORLD || world.getRegistryKey() == World.NETHER;
        if (igniting.contains(itemStack.getItem()) && canPlaced && canActivated
                && AreaHelper.getNewPortal(world, pos2, Direction.Axis.X).isPresent()) {
            world.createExplosion(null, (double) pos2.getX() + 0.5, (double) pos2.getY() + 0.5, (double) pos2.getZ() + 0.5, 2.0f, false, Explosion.DestructionType.NONE);
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, Settings.getDarknessDuration()));
            player.sendMessage(Text.translatable("disable_portal.cancel_activation", "§4§lnether"));
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

}
