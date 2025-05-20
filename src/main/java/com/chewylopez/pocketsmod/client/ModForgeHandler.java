package com.chewylopez.pocketsmod.client;

import com.chewylopez.pocketsmod.PocketsMod;
import com.chewylopez.pocketsmod.util.Keybinds;
import com.mojang.serialization.Decoder;
import net.minecraft.world.SimpleMenuProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = PocketsMod.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ModForgeHandler {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if(Keybinds.POCKETS_KEY.consumeClick()){
            System.out.print("test");
            //event.getEntity().openMenu(new SimpleMenuProvider(PocketsMenu));
        }
    }

}
