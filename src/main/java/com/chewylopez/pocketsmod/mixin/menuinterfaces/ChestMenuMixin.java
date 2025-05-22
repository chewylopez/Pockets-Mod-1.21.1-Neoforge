package com.chewylopez.pocketsmod.mixin.menuinterfaces;

import net.minecraft.world.inventory.ChestMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ChestMenu.class)
public class ChestMenuMixin {

    @ModifyConstant(method = {"<init>(Lnet/minecraft/world/inventory/MenuType;ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/Container;I)V"}, constant = {@Constant(intValue = 3, ordinal = 0)})
    private int rowAdd(int original) {
        return 4;
    }

    @ModifyConstant(method = {"<init>(Lnet/minecraft/world/inventory/MenuType;ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/Container;I)V"}, constant = {@Constant(intValue = 161)})
    private int changeHotbarHeight(int original) {
        return original + 18;
    }

}
