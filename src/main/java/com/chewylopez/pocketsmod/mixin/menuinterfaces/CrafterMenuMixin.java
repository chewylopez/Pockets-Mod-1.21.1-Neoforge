package com.chewylopez.pocketsmod.mixin.menuinterfaces;

import net.minecraft.world.inventory.CartographyTableMenu;
import net.minecraft.world.inventory.CrafterMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(CrafterMenu.class)
public class CrafterMenuMixin {

    @ModifyConstant(method = {"addSlots"}, constant = {@Constant(intValue = 3, ordinal = 3)})
    private int rowAdd(int original) {
        return 4;
    }

    @ModifyConstant(method = {"addSlots"}, constant = {@Constant(intValue = 142)})
    private int changeHotbarHeight(int original) {
        return original + 18;
    }

}
