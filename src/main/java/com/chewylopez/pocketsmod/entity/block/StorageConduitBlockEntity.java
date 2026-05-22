package com.chewylopez.pocketsmod.entity.block;

import com.chewylopez.pocketsmod.InventoryInterface.InventorySource;
import com.chewylopez.pocketsmod.player.client.StorageConduitMenu;
import com.chewylopez.pocketsmod.player.client.TabMetadata;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.*;

public class StorageConduitBlockEntity extends BlockEntity implements MenuProvider {

    private static final int SCAN_INTERVAL = 20;

    private static final int MAX_CHAINED_CONNECTORS = 20;

    private final List<InventorySource> tabs = new ArrayList<>();
    private int activeTab = 0;
    private int tickCounter = 0;
    private int scrollRow = 0;

    public final NeighborProxyHandler proxy = new NeighborProxyHandler(this);

    public StorageConduitBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STORAGE_CONDUIT.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, StorageConduitBlockEntity be) {
        if (level.isClientSide) return;
        if (++be.tickCounter < SCAN_INTERVAL) return;
        be.tickCounter = 0;
        be.scanAll(level, pos);
    }

    private void scanAll(Level level, BlockPos pos) {
        InventorySource previous = (activeTab >= 0 && activeTab < tabs.size()) ? tabs.get(activeTab) : null;
        tabs.clear();

        Set<BlockPos> visited        = new HashSet<>();
        Set<BlockPos> seenInventories = new HashSet<>();
        visited.add(pos);

        for (Direction dir : Direction.values()) {
            BlockPos neighbor = pos.relative(dir);
            BlockEntity be = level.getBlockEntity(neighbor);
            if (be instanceof StorageConduitExtensionBlockEntity ext) {
                followNetwork(level, ext, visited, seenInventories);
            } else if (!(be instanceof StorageConduitBlockEntity)) {
                IItemHandler h = level.getCapability(
                        Capabilities.ItemHandler.BLOCK, neighbor, dir.getOpposite());
                if (h != null && seenInventories.add(canonicalPos(level, neighbor))) {
                    tabs.add(new InventorySource(neighbor, dir.getOpposite()));
                }
            }
        }

        if (previous != null) {
            int idx = tabs.indexOf(previous);
            if (idx >= 0) activeTab = idx;
            else { activeTab = Math.min(activeTab, Math.max(0, tabs.size() - 1)); scrollRow = 0; }
        } else {
            activeTab = 0; scrollRow = 0;
        }
    }

    private void followNetwork(Level level, StorageConduitExtensionBlockEntity ext,
                               Set<BlockPos> visited, Set<BlockPos> seenInventories) {
        if (visited.size() > MAX_CHAINED_CONNECTORS) return;
        if (!visited.add(ext.getBlockPos())) return;

        for (InventorySource src : ext.getInventorySources()) {
            if (seenInventories.add(canonicalPos(level, src.pos()))) {
                tabs.add(src);
            }
        }

        for (Direction dir : Direction.values()) {
            BlockPos next = ext.getBlockPos().relative(dir);
            if (visited.contains(next)) continue;
            if (level.getBlockEntity(next) instanceof StorageConduitExtensionBlockEntity nextExt) {
                followNetwork(level, nextExt, visited, seenInventories);
            }
        }
    }

    private static BlockPos canonicalPos(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (state.getBlock() instanceof ChestBlock
                && state.hasProperty(ChestBlock.TYPE)
                && state.getValue(ChestBlock.TYPE) == ChestType.RIGHT) {
            return pos.relative(ChestBlock.getConnectedDirection(state));
        }
        return pos;
    }

    public void setScrollRow(int row) {
        int max = Math.max(0, (proxy.getTotalSlotCount() + 8) / 9 - 6);
        scrollRow = Math.max(0, Math.min(row, max));
    }
    public int getScrollRow() { return scrollRow; }

    // Reset scroll whenever the active tab changes
    public void setActiveTab(int index) {
        if (index >= 0 && index < tabs.size()) {
            activeTab = index;
            scrollRow = 0;
        }
    }

    private final Map<InventorySource, TabMetadata> tabMeta = new HashMap<>();

    public TabMetadata getTabMeta(int idx) {
        if (idx < 0 || idx >= tabs.size()){
            return TabMetadata.EMPTY;
        }
        return tabMeta.getOrDefault(tabs.get(idx), TabMetadata.EMPTY);
    }

    public void setTabIcon(int idx, ItemStack icon) {
        if (idx < 0 || idx >= tabs.size()) return;
        InventorySource src = tabs.get(idx);
        TabMetadata current = tabMeta.getOrDefault(src, TabMetadata.EMPTY);
        tabMeta.put(src, new TabMetadata(icon.copyWithCount(1), current.name()));
        setChanged();
    }

    public void setTabName(int idx, String name) {
        if (idx < 0 || idx >= tabs.size()) return;
        InventorySource src = tabs.get(idx);
        TabMetadata current = tabMeta.getOrDefault(src, TabMetadata.EMPTY);
        tabMeta.put(src, new TabMetadata(current.icon(), name));
        setChanged();
    }


    public List<InventorySource> getTabs() {
        return tabs;
    }
    public int getActiveTab() {
        return activeTab;
    }
    public int getTabCount() {
        return tabs.size();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.pocketsmod.storage_conduit");
    }

    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
        return new StorageConduitMenu(syncId, inv, this);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("ActiveTab", activeTab);

        ListTag tabList = new ListTag();
        for (InventorySource src : tabs){
            tabList.add(src.save());
        }
        tag.put("Tabs", tabList);

        ListTag metaList = new ListTag();
        for (var entry : tabMeta.entrySet()) {
            TabMetadata meta = entry.getValue();
            if (!meta.hasIcon() && !meta.hasName()) continue;
            CompoundTag t = new CompoundTag();
            t.put("Src", entry.getKey().save());
            if (meta.hasIcon()) {
                t.putString("Icon", BuiltInRegistries.ITEM.getKey(meta.icon().getItem()).toString());
                t.putByte("IconCount", (byte) meta.icon().getCount());
            }
            if (meta.hasName()){
                t.putString("Name", meta.name());
            }
            metaList.add(t);
        }
        tag.put("TabMetadata", metaList);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        activeTab = tag.getInt("ActiveTab");

        tabs.clear();
        ListTag tabList = tag.getList("Tabs", Tag.TAG_COMPOUND);
        for (int i = 0; i < tabList.size(); i++) {
            InventorySource src = InventorySource.load(tabList.getCompound(i));
            if (src != null) tabs.add(src);
        }

        tabMeta.clear();
        ListTag metaList = tag.getList("TabMetadata", Tag.TAG_COMPOUND);
        for (int i = 0; i < metaList.size(); i++) {
            CompoundTag t = metaList.getCompound(i);
            InventorySource src = InventorySource.load(t.getCompound("Src"));
            if (src == null) continue;
            ItemStack icon = t.contains("Icon")
                    ? new ItemStack(
                    BuiltInRegistries.ITEM.get(ResourceLocation.parse(t.getString("Icon"))),
                    t.getByte("IconCount") & 0xFF)
                    : ItemStack.EMPTY;
            tabMeta.put(src, new TabMetadata(icon, t.getString("Name")));
        }
    }
}