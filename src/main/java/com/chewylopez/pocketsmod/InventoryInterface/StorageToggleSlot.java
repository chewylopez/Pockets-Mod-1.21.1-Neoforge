package com.chewylopez.pocketsmod.InventoryInterface;

import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

import java.util.function.IntSupplier;

public class StorageToggleSlot extends SlotItemHandler {
    private final IntSupplier limit;

    public StorageToggleSlot(IItemHandler handler, int index, int x, int y, IntSupplier limit) {
        super(handler, index, x, y);
        this.limit = limit;
    }

    @Override
    public boolean isActive() {
        return index < limit.getAsInt();
    }
}
