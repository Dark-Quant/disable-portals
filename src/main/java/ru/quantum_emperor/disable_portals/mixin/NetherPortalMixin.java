package ru.quantum_emperor.disable_portals.mixin;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.dimension.AreaHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.quantum_emperor.disable_portals.config.Settings;

import java.util.Optional;

@Mixin(AbstractFireBlock.class)
public class NetherPortalMixin {


    @Redirect(method = "onBlockAdded", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/dimension/AreaHelper;getNewPortal(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction$Axis;)Ljava/util/Optional;"))
    private Optional<AreaHelper> closePortal(WorldAccess world, BlockPos pos, Direction.Axis axis) {
        if (Settings.getInstance().isDisabledNether()) {
            return Optional.empty();
        } else {
            return AreaHelper.getNewPortal(world, pos, axis);
        }
    }

}
