package ru.quantum_emperor.disable_portals.mixin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.edenring.world.EdenPortal;
import ru.quantum_emperor.disable_portals.config.DisablePortalConfig;

@Mixin(EdenPortal.class)
public class EdenPortalMixin {
    @Inject(method = "buildPortal", at = @At("HEAD"), cancellable = true)
    private static void close_portal(World level, BlockPos center, CallbackInfo ci) {
        if (DisablePortalConfig.isDisableEden()) {
            ci.cancel();
        }
    }
}
