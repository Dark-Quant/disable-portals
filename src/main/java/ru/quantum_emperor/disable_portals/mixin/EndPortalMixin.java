package ru.quantum_emperor.disable_portals.mixin;

import net.minecraft.item.EnderEyeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.quantum_emperor.disable_portals.config.Settings;

@Mixin(EnderEyeItem.class)
public class EndPortalMixin {
    @Inject(method = "useOnBlock", cancellable = true, at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;with(Lnet/minecraft/state/property/Property;Ljava/lang/Comparable;)Ljava/lang/Object;"))
    private void closePortal(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        if (Settings.getInstance().isDisableEnd()) {
            cir.setReturnValue(ActionResult.SUCCESS);
            cir.cancel();
        }
    }
}
