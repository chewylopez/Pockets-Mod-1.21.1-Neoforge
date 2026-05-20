package com.chewylopez.pocketsmod.block;

import com.chewylopez.pocketsmod.client.TabMetadata;
import com.chewylopez.pocketsmod.entity.block.ModBlockEntities;
import com.chewylopez.pocketsmod.entity.block.StorageConduitBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;

public class StorageConduitBlock extends BaseEntityBlock {

    public StorageConduitBlock(BlockBehaviour.Properties properties) {
        super(Properties.of()
                .mapColor(MapColor.STONE)
                .strength(3.5f)
                .requiresCorrectToolForDrops());
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new StorageConduitBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.STORAGE_CONDUIT.get(),
                StorageConduitBlockEntity::tick);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof StorageConduitBlockEntity be) {
            player.openMenu(be, buf -> {
                int count = be.getTabCount();
                buf.writeVarInt(count);               // ← tabCount first
                buf.writeVarInt(be.getActiveTab());   // ← activeTab second
                buf.writeVarInt(be.proxy.getWindowSlotCount());
                buf.writeVarInt(be.proxy.getTotalSlotCount());
                buf.writeVarInt(be.getScrollRow());
                for (int i = 0; i < count; i++) {
                    TabMetadata.encode(buf, be.getTabMeta(i));
                }
            });
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
