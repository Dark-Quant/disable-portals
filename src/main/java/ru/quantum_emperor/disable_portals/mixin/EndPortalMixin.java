package ru.quantum_emperor.disable_portals.mixin;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.EnderEyeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.quantum_emperor.disable_portals.config.Settings;

import java.util.Objects;

@Mixin(EnderEyeItem.class)
public class EndPortalMixin {
    @Inject(method = "useOnBlock", cancellable = true, at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;with(Lnet/minecraft/state/property/Property;Ljava/lang/Comparable;)Ljava/lang/Object;"))
    private void closePortal(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        if (Settings.isDisableEnd()) {
            BlockPos pos = context.getBlockPos();
            context.getWorld().createExplosion(null, (double)pos.getX() + 0.5, (double)pos.getY() + 1.5, (double)pos.getZ() + 0.5, 2.0f, false, Explosion.DestructionType.NONE);
            Objects.requireNonNull(context.getPlayer()).addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, Settings.getDarknessDuration()));
            Objects.requireNonNull(context.getPlayer()).sendMessage(Text.translatable("disable_portal.cancel_activation", "§5§lend"));
            cir.setReturnValue(ActionResult.success(context.getWorld().isClient()));
        }
    }
}
