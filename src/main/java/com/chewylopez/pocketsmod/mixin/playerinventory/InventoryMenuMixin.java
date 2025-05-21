package com.chewylopez.pocketsmod.mixin.playerinventory;

import net.minecraft.world.inventory.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;

import javax.annotation.Nullable;

@Mixin(InventoryMenu.class)
public abstract class InventoryMenuMixin extends AbstractContainerMenu {

    @Shadow @Final private ResultContainer resultSlots;

    protected InventoryMenuMixin(@Nullable MenuType<?> type, int syncId) {
        super(type, syncId);
    }

    @ModifyConstant(method = {"<init>"}, constant = {@Constant(intValue = 39)})
    private int armorIndexChange(int original) {
        return original + 9;
    }

    @ModifyConstant(method = {"<init>"}, constant = {@Constant(intValue = 40)})
    private int offhandIndexChange(int original) {
        return original + 9;
    }

    @ModifyConstant(method = {"<init>"}, constant = {@Constant(intValue = 3)})
    private int addrow(int original) {
        return 4;
    }

    @ModifyConstant(method = {"<init>"}, constant = {@Constant(intValue = 142)})
    private int changeHotbarHeight(int original) {
        return 160;
    }
}