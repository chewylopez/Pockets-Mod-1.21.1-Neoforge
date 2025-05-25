package com.chewylopez.pocketsmod.mixin.playerinventory;

import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(CreativeModeInventoryScreen.ItemPickerMenu.class)
public class ItemPickerMenuMixin {

    @ModifyConstant(method = {"<init>"}, constant = {@Constant(intValue = 5, ordinal = 0)})
    private int rowAdd(int original) {
        return 6;
    }

    @ModifyConstant(method = {"<init>"}, constant = {@Constant(intValue = 112)})
    private int changeHotbarHeight(int original) {
        return original + 18;
    }

    @ModifyConstant(method = {"scrollTo"}, constant = {@Constant(intValue = 5)})
    private int rowAdd2(int original) {
        return 6;
    }
    @ModifyConstant(method = {"canScroll"}, constant = {@Constant(intValue = 45)})
    private int rowAdd3(int original) {
        return 54;
    }


}
