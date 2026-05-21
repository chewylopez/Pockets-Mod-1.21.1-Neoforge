package com.chewylopez.pocketsmod.entity.block;

import com.chewylopez.pocketsmod.InventoryInterface.InventorySource;
import com.chewylopez.pocketsmod.block.StorageConduitExtensionBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;

public class StorageConduitExtensionBlockEntity extends BlockEntity {

    public StorageConduitExtensionBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STORAGE_CONDUIT_EXTENSION.get(), pos, state);
    }

    public List<InventorySource> getInventorySources() {
        Level level = getLevel();
        if (level == null) return List.of();

        List<InventorySource> sources = new ArrayList<>();
        for (Direction dir : Direction.values()) {
            BlockPos neighbor = getBlockPos().relative(dir);
            if (level.getBlockEntity(neighbor) instanceof StorageConduitBlockEntity)  continue;
            if (level.getBlockEntity(neighbor) instanceof StorageConduitExtensionBlockEntity) continue;
            IItemHandler h = level.getCapability(Capabilities.ItemHandler.BLOCK, neighbor, dir.getOpposite());
            if (h != null) sources.add(new InventorySource(neighbor, dir.getOpposite()));
        }
        return sources;
    }
}
