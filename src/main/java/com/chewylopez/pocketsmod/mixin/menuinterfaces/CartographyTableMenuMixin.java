package com.chewylopez.pocketsmod.mixin.menuinterfaces;

import net.minecraft.world.inventory.CartographyTableMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(CartographyTableMenu.class)
public class CartographyTableMenuMixin {

    @ModifyConstant(method = {"<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V"}, constant = {@Constant(intValue = 3, ordinal = 0)})
    private int rowAdd(int original) {
        return 4;
    }

    @ModifyConstant(method = {"<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V"}, constant = {@Constant(intValue = 142)})
    private int changeHotbarHeight(int original) {
        return original + 18;
    }

}
