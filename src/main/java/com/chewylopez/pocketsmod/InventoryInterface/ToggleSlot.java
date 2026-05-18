package com.chewylopez.pocketsmod.InventoryInterface;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ToggleSlot extends Slot {

    private final ContainerData data;
    private final int dataIndex;

    public ToggleSlot(Container container, int slot, int x, int y, ContainerData data, int dataIndex) {
        super(container, slot, x, y);
        this.data = data;
        this.dataIndex = dataIndex;
    }

    @Override
    public boolean isActive() {
        return data.get(dataIndex) == 1;
    }

    @Override
    public boolean mayPickup(Player player) {
        return data.get(dataIndex) == 1;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return data.get(dataIndex) == 1 && super.mayPlace(stack);
    }
}
