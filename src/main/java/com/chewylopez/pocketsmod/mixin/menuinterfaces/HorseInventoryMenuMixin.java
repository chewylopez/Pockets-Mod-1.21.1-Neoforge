package com.chewylopez.pocketsmod.mixin.menuinterfaces;

import net.minecraft.world.inventory.HorseInventoryMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(HorseInventoryMenu.class)
public class HorseInventoryMenuMixin {

    @ModifyConstant(method = {"<init>"}, constant = {@Constant(intValue = 3, ordinal = 2)})
    private int rowAdd(int original) {
        return 4;
    }

    @ModifyConstant(method = {"<init>"}, constant = {@Constant(intValue = 142)})
    private int changeHotbarHeight(int original) {
        return original + 18;
    }

}
