package com.chewylopez.pocketsmod.player.client;

import com.chewylopez.pocketsmod.PocketsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, PocketsMod.MODID);



    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }

}
