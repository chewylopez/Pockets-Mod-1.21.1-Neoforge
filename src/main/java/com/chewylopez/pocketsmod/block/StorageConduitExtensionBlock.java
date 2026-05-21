package com.chewylopez.pocketsmod.block;

import com.chewylopez.pocketsmod.entity.block.StorageConduitBlockEntity;
import com.chewylopez.pocketsmod.entity.block.StorageConduitExtensionBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.MapColor;

public class StorageConduitExtensionBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final MapCodec<StorageConduitBlock> CODEC = simpleCodec(StorageConduitBlock::new);

    public StorageConduitExtensionBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext placeContext) {

        Level level = placeContext.getLevel();
        BlockPos pos = placeContext.getClickedPos();

        for (Direction dir : Direction.values()) {
            if (level.getBlockEntity(pos.relative(dir)) instanceof StorageConduitBlockEntity) {
                return defaultBlockState().setValue(FACING, dir);
            }
        }
        for (Direction dir : Direction.values()) {
            if (level.getBlockEntity(pos.relative(dir)) instanceof StorageConduitExtensionBlockEntity) {
                return defaultBlockState().setValue(FACING, dir);
            }
        }
        return defaultBlockState().setValue(FACING, placeContext.getNearestLookingDirection().getOpposite());
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new StorageConduitExtensionBlockEntity(pos, state);
    }
}
