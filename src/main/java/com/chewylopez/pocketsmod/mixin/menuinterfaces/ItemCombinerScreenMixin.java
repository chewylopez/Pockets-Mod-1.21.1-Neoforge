package com.chewylopez.pocketsmod.mixin.menuinterfaces;

import net.minecraft.world.inventory.ItemCombinerMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ItemCombinerMenu.class)
public class ItemCombinerScreenMixin {

    @ModifyConstant(method = {"createInventorySlots"}, constant = {@Constant(intValue = 3)})
    private int rowAdd(int original) {
        return 4;
    }

    @ModifyConstant(method = {"createInventorySlots"}, constant = {@Constant(intValue = 142)})
    private int changeHotbarHeight(int original) {
        return original + 18;
    }

}
