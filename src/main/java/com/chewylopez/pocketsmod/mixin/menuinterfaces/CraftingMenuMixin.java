package com.chewylopez.pocketsmod.mixin.menuinterfaces;

import net.minecraft.world.inventory.CraftingMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(CraftingMenu.class)
public class CraftingMenuMixin {

    @ModifyConstant(method = {"<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V"}, constant = {@Constant(intValue = 3, ordinal = 5)})
    private int rowAdd(int original) {
        return 4;
    }

    @ModifyConstant(method = {"<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V"}, constant = {@Constant(intValue = 142)})
    private int changeHotbarHeight(int original) {
        return original + 18;
    }

}
