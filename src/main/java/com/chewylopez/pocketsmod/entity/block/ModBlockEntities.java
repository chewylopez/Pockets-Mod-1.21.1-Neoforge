package com.chewylopez.pocketsmod.entity.block;

import com.chewylopez.pocketsmod.PocketsMod;
import com.chewylopez.pocketsmod.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, PocketsMod.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<StorageConduitBlockEntity>>
            STORAGE_CONDUIT = BLOCK_ENTITIES.register("storage_conduit",
            () -> BlockEntityType.Builder.of(StorageConduitBlockEntity::new, ModBlocks.STORAGE_CONDUIT.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<StorageConduitExtensionBlockEntity>>
            STORAGE_CONDUIT_EXTENSION = BLOCK_ENTITIES.register("storage_conduit_connector",
            () -> BlockEntityType.Builder.of(StorageConduitExtensionBlockEntity::new, ModBlocks.STORAGE_CONDUIT_CONNECTOR.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
