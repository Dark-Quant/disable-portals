package ru.quantum_emperor.disable_portals.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.quantum_emperor.disable_portals.config.Settings;

import javax.swing.text.html.parser.Entity;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    @Inject(method = "moveToWorld", at = @At("HEAD"), cancellable = true)
    private void cancelMoveToWorld(ServerWorld world, CallbackInfoReturnable<Entity> cir) {
        if (Settings.getInstance().isDisableEnd() && world.getRegistryKey() == World.END)
            cir.setReturnValue(null);
        if (Settings.getInstance().isDisabledNether() && world.getRegistryKey() == World.NETHER)
            cir.setReturnValue(null);
    }
}
