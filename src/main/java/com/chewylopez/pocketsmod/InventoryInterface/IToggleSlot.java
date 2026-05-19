package com.chewylopez.pocketsmod.InventoryInterface;

public interface IToggleSlot {
    void setToggleEnabled(int index, boolean enabled);

    boolean getToggleEnabled(int index);
}
