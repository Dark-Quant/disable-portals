package ru.quantum_emperor.disable_portals.events;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
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
import ru.quantum_emperor.disable_portals.config.Settings;

import javax.swing.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class PortalActivationEvent implements UseBlockCallback {
    private static final Set<Item> igniting = new HashSet<>();

    static {
        igniting.add(Items.FLINT_AND_STEEL);
        igniting.add(Items.FIRE_CHARGE);
    }

    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        if (!Settings.isDisabledNether())
            return ActionResult.SUCCESS;
        if (world.isClient)
            return ActionResult.PASS;
        ItemStack itemStack;
        if (hand == Hand.MAIN_HAND)
            itemStack = player.getInventory().main.get(player.getInventory().selectedSlot);
        else
            itemStack = player.getInventory().offHand.get(0);
        BlockPos pos = hitResult.getBlockPos();
        BlockPos pos2 = pos.offset(hitResult.getSide());
        boolean canPlaced = AbstractFireBlock.canPlaceAt(world, pos2, player.getHorizontalFacing());
        boolean canActivated = world.getRegistryKey() == World.OVERWORLD || world.getRegistryKey() == World.NETHER;
        Optional<AreaHelper> optional;
        System.out.println("itemStack");
        if (igniting.contains(itemStack.getItem())  && canPlaced && canActivated
                && (optional = AreaHelper.getNewPortal(world, pos2, Direction.Axis.X)).isPresent()) {
            player.sendMessage(Text.of("You cannot activate portal"));
        }

        return ActionResult.PASS;
    }
}
