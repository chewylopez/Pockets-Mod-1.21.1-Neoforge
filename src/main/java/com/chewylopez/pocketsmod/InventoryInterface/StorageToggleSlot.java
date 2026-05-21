package com.chewylopez.pocketsmod.InventoryInterface;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

import java.util.function.IntSupplier;

public class StorageToggleSlot extends SlotItemHandler {
    private final IntSupplier limit;

    public StorageToggleSlot(IItemHandler handler, int index, int x, int y, IntSupplier limit) {
        super(handler, index, x, y);
        this.limit = limit;
    }

    public boolean isEnabled() {
        return index < limit.getAsInt();
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public ItemStack getItem() {
        return isEnabled() ? super.getItem() : ItemStack.EMPTY;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return isEnabled() && super.mayPlace(stack);
    }

    @Override
    public boolean mayPickup(Player player) {
        return isEnabled() && super.mayPickup(player);
    }
}