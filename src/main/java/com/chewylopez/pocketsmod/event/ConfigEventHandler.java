package com.chewylopez.pocketsmod.event;

import com.chewylopez.pocketsmod.Config;
import com.chewylopez.pocketsmod.PocketsMod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.event.level.LevelEvent;

@EventBusSubscriber(modid = PocketsMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ConfigEventHandler {

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading event) {
        Config.ITEMSTACK_SIZE = Config.ITEMSTACK_SIZE_CONFIG.get();
    }

    @SubscribeEvent
    public static void onReload(final ModConfigEvent.Reloading event) {
        Config.ITEMSTACK_SIZE = Config.ITEMSTACK_SIZE_CONFIG.get();
    }

}
