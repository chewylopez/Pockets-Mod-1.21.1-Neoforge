package com.chewylopez.pocketsmod.mixin.menuinterfaces;

import net.minecraft.world.inventory.AbstractFurnaceMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AbstractFurnaceMenu.class)
public class AbstractFurnaceMenuMixin {

    @ModifyConstant(method = {"<init>(Lnet/minecraft/world/inventory/MenuType;Lnet/minecraft/world/item/crafting/RecipeType;Lnet/minecraft/world/inventory/RecipeBookType;ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/Container;Lnet/minecraft/world/inventory/ContainerData;)V"}, constant = {@Constant(intValue = 3, ordinal = 1)})
    private int rowAdd(int original) {
        return 4;
    }

    @ModifyConstant(method = {"<init>(Lnet/minecraft/world/inventory/MenuType;Lnet/minecraft/world/item/crafting/RecipeType;Lnet/minecraft/world/inventory/RecipeBookType;ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/Container;Lnet/minecraft/world/inventory/ContainerData;)V"}, constant = {@Constant(intValue = 142)})
    private int changeHotbarHeight(int original) {
        return original + 18;
    }

}
