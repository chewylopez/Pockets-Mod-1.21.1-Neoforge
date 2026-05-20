package com.chewylopez.pocketsmod.entity.block;

import com.chewylopez.pocketsmod.InventoryInterface.InventorySource;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class NeighborProxyHandler implements IItemHandlerModifiable {

    public static final int MAX_SLOTS = 54; // visible window only

    private final StorageConduitBlockEntity be;

    public NeighborProxyHandler(StorageConduitBlockEntity be) {
        this.be = be;
    }

    @Nullable
    private IItemHandler live() {
        Level level = be.getLevel();
        List<InventorySource> tabs = be.getTabs();
        int idx = be.getActiveTab();
        if (level == null || tabs.isEmpty() || idx < 0 || idx >= tabs.size()) {
            return null;
        }
        return tabs.get(idx).resolve(level);
    }

    /** Slot index in the 54-slot window → actual index in the underlying inventory. */
    private int actual(int slot) {
        return slot + be.getScrollRow() * 9;
    }

    public int getTotalSlotCount() {
        IItemHandler h = live();
        return h == null ? 0 : h.getSlots();
    }

    public int getWindowSlotCount() {
        IItemHandler h = live();
        if (h == null) {
            return 0;
        }
        return Math.max(0, Math.min(h.getSlots() - be.getScrollRow() * 9, MAX_SLOTS));
    }

    @Override public int getSlots() {
        return MAX_SLOTS;
    }

    @Override public ItemStack getStackInSlot(int slot) {
        IItemHandler h = live(); int a = actual(slot);
        return (h == null || a >= h.getSlots()) ? ItemStack.EMPTY : h.getStackInSlot(a);
    }

    @Override public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        IItemHandler h = live(); int a = actual(slot);
        return (h == null || a >= h.getSlots()) ? stack : h.insertItem(a, stack, simulate);
    }

    @Override public ItemStack extractItem(int slot, int amount, boolean simulate) {
        IItemHandler h = live(); int a = actual(slot);
        return (h == null || a >= h.getSlots()) ? ItemStack.EMPTY : h.extractItem(a, amount, simulate);
    }

    @Override public int getSlotLimit(int slot) {
        IItemHandler h = live(); int a = actual(slot);
        return (h == null || a >= h.getSlots()) ? 64 : h.getSlotLimit(a);
    }

    @Override public boolean isItemValid(int slot, ItemStack stack) {
        IItemHandler h = live(); int a = actual(slot);
        return h != null && a < h.getSlots() && h.isItemValid(a, stack);
    }

    @Override public void setStackInSlot(int slot, ItemStack stack) {
        IItemHandler h = live(); int a = actual(slot);
        if (h instanceof IItemHandlerModifiable m && a < h.getSlots()) {
            m.setStackInSlot(a, stack);
        }
    }
}