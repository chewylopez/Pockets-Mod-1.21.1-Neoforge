package com.chewylopez.pocketsmod.block;

import com.chewylopez.pocketsmod.PocketsMod;
import com.chewylopez.pocketsmod.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(PocketsMod.MODID);

    public static final DeferredBlock<Block> STORAGE_CONDUIT = registerBlock("storage_conduit", () -> new StorageConduitBlock(BlockBehaviour.Properties.of().strength(4.5f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> STORAGE_CONDUIT_CONNECTOR = registerBlock("storage_conduit_connector", () -> new StorageConduitExtensionBlock(BlockBehaviour.Properties.of().strength(2.5f).requiresCorrectToolForDrops()));

    static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}

