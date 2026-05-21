package com.chewylopez.pocketsmod.datagen;

import com.chewylopez.pocketsmod.PocketsMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = PocketsMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        ModDatapackProvider modDatapackProvider = new ModDatapackProvider(packOutput, lookupProvider);

        generator.addProvider(event.includeServer(), modDatapackProvider);

        generator.addProvider(event.includeServer(), new ModEnchantmentTagProvider(packOutput, modDatapackProvider.getRegistryProvider()));
    }
}