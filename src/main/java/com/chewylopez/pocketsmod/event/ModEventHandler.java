package com.chewylopez.pocketsmod.event;

import com.chewylopez.pocketsmod.Config;
import com.chewylopez.pocketsmod.PocketsMod;
import com.chewylopez.pocketsmod.server.ScrollRowPacket;
import com.chewylopez.pocketsmod.server.SelectTabPacket;
import com.chewylopez.pocketsmod.server.SetTabIconPacket;
import com.chewylopez.pocketsmod.server.SetTabNamePacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = PocketsMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventHandler {

    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");

        registrar.playToServer(SelectTabPacket.TYPE,  SelectTabPacket.STREAM_CODEC,  SelectTabPacket::handle);
        registrar.playToServer(ScrollRowPacket.TYPE,  ScrollRowPacket.STREAM_CODEC,  ScrollRowPacket::handle);
        registrar.playToServer(SetTabIconPacket.TYPE, SetTabIconPacket.STREAM_CODEC, SetTabIconPacket::handle);
        registrar.playToServer(SetTabNamePacket.TYPE, SetTabNamePacket.STREAM_CODEC, SetTabNamePacket::handle);

    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading event) {
        Config.ITEMSTACK_SIZE = Config.ITEMSTACK_SIZE_CONFIG.get();
    }

    @SubscribeEvent
    public static void onReload(final ModConfigEvent.Reloading event) {
        Config.ITEMSTACK_SIZE = Config.ITEMSTACK_SIZE_CONFIG.get();
    }

}
