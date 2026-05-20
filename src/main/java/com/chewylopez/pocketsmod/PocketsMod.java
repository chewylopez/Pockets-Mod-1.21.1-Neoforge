package com.chewylopez.pocketsmod;

import com.chewylopez.pocketsmod.block.ModBlocks;
import com.chewylopez.pocketsmod.client.ConfigScreen;
import com.chewylopez.pocketsmod.entity.block.ModBlockEntities;
import com.chewylopez.pocketsmod.item.ModItems;
import com.chewylopez.pocketsmod.player.ModMenuTypes;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(PocketsMod.MODID)
public class PocketsMod
{
    public static final String MODID = "pocketsmod";

    public PocketsMod(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        ModLoadingContext.get().registerExtensionPoint(
                IConfigScreenFactory.class,
                () -> (minecraft, parent) -> new ConfigScreen(parent)
        );
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
}
