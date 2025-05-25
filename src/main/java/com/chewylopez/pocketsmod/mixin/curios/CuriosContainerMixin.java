package com.chewylopez.pocketsmod.mixin.curios;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(targets = "top.theillusivec4.curios.common.inventory.container.CuriosContainer")
public class CuriosContainerMixin {

    @ModifyConstant(method = {"setPage"}, constant = {@Constant(intValue = 3)})
    private int addRow(int original) {
        return 4;
    }

    @ModifyConstant(method = {"setPage"}, constant = {@Constant(intValue = 142)})
    private int changeHotbar(int original) {
        return 160;
    }

    @ModifyConstant(method = {"setPage"}, constant = {@Constant(intValue = 36)})
    private int changearmorslot(int original) {
        return original + 8;
    }
    @ModifyConstant(method = {"setPage"}, constant = {@Constant(intValue = 40)})
    private int offhandIndexChange(int original) {
        return original + 9;
    }

    @ModifyConstant(method = {"quickMoveStack"}, constant = {@Constant(intValue = 45, ordinal = 0)})
    private int inventoryReferenceChange(int original) {
        return original + 9;
    }
    @ModifyConstant(method = {"quickMoveStack"}, constant = {@Constant(intValue = 45, ordinal = 1)})
    private int inventoryReferenceChange2(int original) {
        return original + 9;
    }
    @ModifyConstant(method = {"quickMoveStack"}, constant = {@Constant(intValue = 45, ordinal = 2)})
    private int inventoryReferenceChange3(int original) {
        return original + 9;
    }
    @ModifyConstant(method = {"quickMoveStack"}, constant = {@Constant(intValue = 45, ordinal = 3)})
    private int inventoryReferenceChange4(int original) {
        return original + 9;
    }
    @ModifyConstant(method = {"quickMoveStack"}, constant = {@Constant(intValue = 45, ordinal = 4)})
    private int inventoryReferenceChange5(int original) {
        return original + 9;
    }
    @ModifyConstant(method = {"quickMoveStack"}, constant = {@Constant(intValue = 45, ordinal = 5)})
    private int inventoryReferenceChange6(int original) {
        return original + 9;
    }
    @ModifyConstant(method = {"quickMoveStack"}, constant = {@Constant(intValue = 45, ordinal = 6)})
    private int inventoryReferenceChange7(int original) {
        return original + 9;
    }
    @ModifyConstant(method = {"quickMoveStack"}, constant = {@Constant(intValue = 45, ordinal = 7)})
    private int inventoryReferenceChange8(int original) {
        return original + 9;
    }

    @ModifyConstant(method = {"quickMoveStack"}, constant = {@Constant(intValue = 36, ordinal = 0)})
    private int inventoryReferenceChange9(int original) {
        return original + 9;
    }
    @ModifyConstant(method = {"quickMoveStack"}, constant = {@Constant(intValue = 36, ordinal = 1)})
    private int inventoryReferenceChange10(int original) {
        return original + 9;
    }
    @ModifyConstant(method = {"quickMoveStack"}, constant = {@Constant(intValue = 36, ordinal = 2)})
    private int inventoryReferenceChange11(int original) {
        return original + 9;
    }

    @ModifyConstant(method = {"quickMoveStack"}, constant = {@Constant(intValue = 46, ordinal = 0)})
    private int inventoryReferenceChange12(int original) {
        return original + 9;
    }
    @ModifyConstant(method = {"quickMoveStack"}, constant = {@Constant(intValue = 46, ordinal = 1)})
    private int inventoryReferenceChange13(int original) {
        return original + 9;
    }
    @ModifyConstant(method = {"quickMoveStack"}, constant = {@Constant(intValue = 46, ordinal = 2)})
    private int inventoryReferenceChange14(int original) {
        return original + 9;
    }

}
