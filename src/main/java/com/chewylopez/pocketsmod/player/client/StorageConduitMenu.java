package com.chewylopez.pocketsmod.player.client;

import com.chewylopez.pocketsmod.InventoryInterface.StorageToggleSlot;
import com.chewylopez.pocketsmod.block.ModBlocks;
import com.chewylopez.pocketsmod.entity.block.NeighborProxyHandler;
import com.chewylopez.pocketsmod.entity.block.StorageConduitBlockEntity;
import com.chewylopez.pocketsmod.player.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import java.util.ArrayList;
import java.util.List;

public class StorageConduitMenu extends AbstractContainerMenu {

    public static final int VIEW_SLOTS = NeighborProxyHandler.MAX_SLOTS;

    private final StorageConduitBlockEntity blockEntity;
    private final ContainerData data;
    private int scrollRow;
    private final List<TabMetadata> tabMetas;

    // Server-side
    public StorageConduitMenu(int syncId, Inventory inv, StorageConduitBlockEntity blockEntity) {
        super(ModMenuTypes.STORAGE_CONDUIT_MENU.get(), syncId);
        this.blockEntity = blockEntity;
        this.scrollRow = blockEntity.getScrollRow();

        this.data = new ContainerData() {
            @Override public int get(int i) {
                return switch (i) {
                    case 0 -> blockEntity.getActiveTab();
                    case 1 -> blockEntity.getTabCount();
                    case 2 -> blockEntity.proxy.getWindowSlotCount();
                    case 3 -> blockEntity.proxy.getTotalSlotCount();
                    default -> 0;
                };
            }

            @Override public void set(int i, int v) {
                //do nothing
            }
            @Override public int getCount() {
                return 4;
            }
        };

        addDataSlots(data);
        addContainerInventorySlots(blockEntity.proxy);
        addPlayerInventorySlots(inv);

        this.tabMetas = new ArrayList<>();

        for (int i = 0; i < blockEntity.getTabCount(); i++){
            tabMetas.add(blockEntity.getTabMeta(i));
        }
    }

    // Client-side
    public StorageConduitMenu(int syncId, Inventory playerInv, FriendlyByteBuf buf) {
        super(ModMenuTypes.STORAGE_CONDUIT_MENU.get(), syncId);

        this.blockEntity = null;

        SimpleContainerData cd = new SimpleContainerData(4);
        int tabCount = buf.readVarInt();

        cd.set(0, buf.readVarInt()); // activeTab
        cd.set(1, tabCount);
        cd.set(2, buf.readVarInt()); // windowSlots
        cd.set(3, buf.readVarInt()); // totalSlots

        this.scrollRow = buf.readVarInt();
        this.tabMetas = new ArrayList<>();
        for (int i = 0; i < tabCount; i++) {
            tabMetas.add(TabMetadata.decode(buf));
        }
        this.data = cd;

        addDataSlots(data);
        addContainerInventorySlots(new ItemStackHandler(VIEW_SLOTS));
        addPlayerInventorySlots(playerInv);
    }

    private void addContainerInventorySlots(IItemHandler handler) {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 9; col++) {
                int idx = row * 9 + col;
                addSlot(new StorageToggleSlot(handler, idx, 8 + col * 18, 18 + row * 18, () -> data.get(2)));
            }
        }
    }

    private void addPlayerInventorySlots(Inventory inv) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(inv, col + row * 9 + 9, 8 + col * 18, 140 + row * 18));
            }
        }
        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(inv, col, 8 + col * 18, 198));
        }
    }

    public void handleTabSelect(int index) {
        if (blockEntity != null) blockEntity.setActiveTab(index);
    }

    public void handleScroll(int row) {
        if (blockEntity != null) blockEntity.setScrollRow(row);
    }

    /** Called client-side for immediate feedback before server round-trip. */
    public void setScrollRowClient(int row) { this.scrollRow = row; }

    public int getScrollRow()    {
        return scrollRow;
    }
    public int getActiveTab()    {
        return data.get(0);
    }
    public int getTabCount()     {
        return data.get(1);
    }
    public int getTotalSlots()   {
        return data.get(3);
    }

    public int getMaxScrollRow() {
        return Math.max(0, (getTotalSlots() + 8) / 9 - 6);
    }

    public TabMetadata getTabMeta(int idx) {
        return (idx >= 0 && idx < tabMetas.size()) ? tabMetas.get(idx) : TabMetadata.EMPTY;
    }

    public void setTabMetaIconClient(int idx, ItemStack icon) {
        ensureSize(idx);
        tabMetas.set(idx, new TabMetadata(icon, tabMetas.get(idx).name()));
    }

    public void setTabMetaNameClient(int idx, String name) {
        ensureSize(idx);
        tabMetas.set(idx, new TabMetadata(tabMetas.get(idx).icon(), name));
    }

    private void ensureSize(int idx) {
        while (tabMetas.size() <= idx) tabMetas.add(TabMetadata.EMPTY);
    }

    public void handleSetIcon(int idx, ItemStack icon) {
        if (blockEntity != null) blockEntity.setTabIcon(idx, icon);
    }

    public void handleSetName(int idx, String name) {
        if (blockEntity != null) blockEntity.setTabName(idx, name);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;
        ItemStack stack = slot.getItem(), original = stack.copy();
        boolean toPlayer = index < VIEW_SLOTS;
        if (!moveItemStackTo(stack, toPlayer ? VIEW_SLOTS : 0, toPlayer ? slots.size() : VIEW_SLOTS, toPlayer))
            return ItemStack.EMPTY;
        if (stack.isEmpty()) slot.set(ItemStack.EMPTY); else slot.setChanged();
        if (stack.getCount() == original.getCount()) return ItemStack.EMPTY;
        slot.onTake(player, stack);
        return original;
    }

    @Override
    public boolean stillValid(Player player) {
        if (blockEntity == null) return true;
        return AbstractContainerMenu.stillValid(
                ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()),
                player, ModBlocks.STORAGE_CONDUIT.get());
    }
}