package com.chewylopez.pocketsmod.mixin.menuinterfaces;

import net.minecraft.world.inventory.BeaconMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BeaconMenu.class)
public class BeaconMenuMixin {

    @ModifyConstant(method = {"<init>(ILnet/minecraft/world/Container;Lnet/minecraft/world/inventory/ContainerData;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V"}, constant = {@Constant(intValue = 3, ordinal = 1)})
    private int rowAdd(int original) {
        return 4;
    }

    @ModifyConstant(method = {"<init>(ILnet/minecraft/world/Container;Lnet/minecraft/world/inventory/ContainerData;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V"}, constant = {@Constant(intValue = 195)})
    private int changeHotbarHeight(int original) {
        return original+18;
    }

}
