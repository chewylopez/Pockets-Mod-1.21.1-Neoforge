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

}
