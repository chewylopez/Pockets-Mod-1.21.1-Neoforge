package com.chewylopez.pocketsmod.InventoryInterface;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;

import javax.annotation.Nullable;

public record InventorySource(BlockPos pos, Direction approach) {

    @Nullable
    public IItemHandler resolve(Level level) {
        return level.getCapability(Capabilities.ItemHandler.BLOCK, pos, approach);
    }

    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putLong("Pos", pos.asLong());
        tag.putString("Approach", approach.getName());
        return tag;
    }

    @Nullable
    public static InventorySource load(CompoundTag tag) {
        Direction dir = Direction.byName(tag.getString("Approach"));
        return dir == null ? null : new InventorySource(BlockPos.of(tag.getLong("Pos")), dir);
    }
}