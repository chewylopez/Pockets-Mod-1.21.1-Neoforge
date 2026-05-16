package com.chewylopez.pocketsmod.event;

import com.chewylopez.pocketsmod.PocketsMod;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ContainerScreenEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = PocketsMod.MODID)
public class InventoryEvents {

    //armor taken on or off
    @SubscribeEvent
    public static void equipArmor(LivingEquipmentChangeEvent event){

        if (event.getEntity() instanceof Player player) {
            if (event.getSlot().isArmor()) {
                System.out.println("armor changed");
            }
        }

    }

    //inventory opened external
    @SubscribeEvent
    public static void openInventory(PlayerContainerEvent.Open event) {
        System.out.println("open container");
    }
    
    //player inventory
    @SubscribeEvent
    public static void openInventory(ScreenEvent.Opening event) {
        if (event.getScreen() instanceof InventoryScreen) {
            System.out.println("open inventory");
        }

    }

}
