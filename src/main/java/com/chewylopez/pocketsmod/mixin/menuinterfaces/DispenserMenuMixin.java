package com.chewylopez.pocketsmod.mixin.menuinterfaces;

import net.minecraft.world.inventory.DispenserMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(DispenserMenu.class)
public class DispenserMenuMixin {

    @ModifyConstant(method = {"<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/Container;)V"}, constant = {@Constant(intValue = 3, ordinal = 3)})
    private int rowAdd(int original) {
        return 4;
    }

    @ModifyConstant(method = {"<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/Container;)V"}, constant = {@Constant(intValue = 142)})
    private int changeHotbarHeight(int original) {
        return original + 18;
    }

}
