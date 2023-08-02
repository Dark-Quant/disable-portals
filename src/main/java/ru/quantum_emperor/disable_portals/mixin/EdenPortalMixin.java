package ru.quantum_emperor.disable_portals.mixin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.edenring.world.EdenPortal;
import ru.quantum_emperor.disable_portals.config.Settings;

@Mixin(EdenPortal.class)
public class EdenPortalMixin {
    @Inject(method = "checkNewPortal", at = @At("HEAD"), cancellable = true)
    private static void closeEdenPortal(World world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (Settings.isDisableEden()) {
            cir.setReturnValue(false);
        }
    }
}
