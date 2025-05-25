package com.chewylopez.pocketsmod.mixin.playerinventory;

import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({Inventory.class})
public class InventoryMixin {

    @ModifyArg(method = {"<init>"}, index = 0, at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/core/NonNullList;withSize(ILjava/lang/Object;)Lnet/minecraft/core/NonNullList;"))
    private int modifyInvSize(int size) {
        return 45;
    }

    @ModifyConstant(method = "getSlotWithRemainingSpace", constant = @Constant(intValue = 40, ordinal = 0))
    private int changeInventoryReferenceSize(int constant) {
        return constant + 9;
    }

    @ModifyConstant(method = "getSlotWithRemainingSpace", constant = @Constant(intValue = 40, ordinal = 1))
    private int changeInventoryReferenceSize2(int constant) {
        return constant + 9;
    }

}

