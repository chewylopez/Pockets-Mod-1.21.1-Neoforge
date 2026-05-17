package com.chewylopez.pocketsmod.InventoryInterface;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

public interface IInventoryPartition {
    NonNullList<ItemStack> pockets_Mod_1_21_1_Neoforge$getHelmetInventoryPartition();
    NonNullList<ItemStack> pockets_Mod_1_21_1_Neoforge$getChestInventoryPartition();
    NonNullList<ItemStack> pockets_Mod_1_21_1_Neoforge$getLegsInventoryPartition();
    NonNullList<ItemStack> pockets_Mod_1_21_1_Neoforge$createBootsInventoryPartition();
}
