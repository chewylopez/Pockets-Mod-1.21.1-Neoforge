package com.chewylopez.pocketsmod.event;

import com.chewylopez.pocketsmod.PocketsMod;
import com.chewylopez.pocketsmod.player.client.StorageConduitScreen;
import com.chewylopez.pocketsmod.player.ModMenuTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = PocketsMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientEventHandler {

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.STORAGE_CONDUIT_MENU.get(), StorageConduitScreen::new);
    }

}
