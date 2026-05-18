package com.chewylopez.pocketsmod.event;

import com.chewylopez.pocketsmod.InventoryInterface.IInventoryPartition;
import com.chewylopez.pocketsmod.InventoryInterface.IToggleSlot;
import com.chewylopez.pocketsmod.PocketsMod;
import com.chewylopez.pocketsmod.enchantment.custom.ModEnchantments;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;

@EventBusSubscriber(modid = PocketsMod.MODID)
public class InventoryEvents {

    private static int HELMET_POCKET_INDEX = 55;
    private static int CHEST_POCKET_INDEX = 59;
    private static int LEGS_POCKET_INDEX = 63;
    private static int BOOTS_POCKET_INDEX = 67;


    //armor taken on or off
    @SubscribeEvent
    public static void equipArmor(LivingEquipmentChangeEvent event){

        if (event.getEntity() instanceof Player player) {

            ItemStack armorStack = player.getItemBySlot(event.getSlot());

            if (event.getSlot().isArmor()) {

                if(armorStack.getItem() instanceof ArmorItem item) {

                    if (item.getType() == ArmorItem.Type.HELMET) {
                        NonNullList<ItemStack> pocket = ((IInventoryPartition) player.getInventory()).pockets_Mod_1_21_1_Neoforge$getHelmetInventoryPartition();
                        throwItemsArmor(player, armorStack, ArmorItem.Type.HELMET, pocket);
                    }
                    else if (item.getType() == ArmorItem.Type.CHESTPLATE) {
                        NonNullList<ItemStack> pocket = ((IInventoryPartition) player.getInventory()).pockets_Mod_1_21_1_Neoforge$getChestInventoryPartition();
                        throwItemsArmor(player, armorStack, ArmorItem.Type.CHESTPLATE, pocket);
                    }
                    else if (item.getType() == ArmorItem.Type.LEGGINGS) {
                        NonNullList<ItemStack> pocket = ((IInventoryPartition) player.getInventory()).pockets_Mod_1_21_1_Neoforge$getLegsInventoryPartition();
                        throwItemsArmor(player, armorStack, ArmorItem.Type.LEGGINGS, pocket);
                    }
                    else if (item.getType() == ArmorItem.Type.BOOTS) {
                        NonNullList<ItemStack> pocket = ((IInventoryPartition) player.getInventory()).pockets_Mod_1_21_1_Neoforge$getBootsInventoryPartition();
                        throwItemsArmor(player, armorStack, ArmorItem.Type.BOOTS, pocket);
                    }
                }
                else {

                    if (event.getSlot() == EquipmentSlot.HEAD) {
                        if(event.getTo().isEmpty()){
                            NonNullList<ItemStack> pocket = ((IInventoryPartition) player.getInventory()).pockets_Mod_1_21_1_Neoforge$getHelmetInventoryPartition();
                            throwItems(player, armorStack, EquipmentSlot.HEAD, pocket);
                        }
                    }
                    else if (event.getSlot() == EquipmentSlot.CHEST) {
                        if(event.getTo().isEmpty()) {
                            NonNullList<ItemStack> pocket = ((IInventoryPartition) player.getInventory()).pockets_Mod_1_21_1_Neoforge$getChestInventoryPartition();
                            throwItems(player, armorStack, EquipmentSlot.CHEST, pocket);
                        }
                    }
                    else if (event.getSlot() == EquipmentSlot.LEGS) {
                        if(event.getTo().isEmpty()) {
                            NonNullList<ItemStack> pocket = ((IInventoryPartition) player.getInventory()).pockets_Mod_1_21_1_Neoforge$getLegsInventoryPartition();
                            throwItems(player, armorStack, EquipmentSlot.LEGS, pocket);
                        }
                    }
                    else if (event.getSlot() == EquipmentSlot.FEET) {
                        if(event.getTo().isEmpty()) {
                            NonNullList<ItemStack> pocket = ((IInventoryPartition) player.getInventory()).pockets_Mod_1_21_1_Neoforge$getBootsInventoryPartition();
                            throwItems(player, armorStack, EquipmentSlot.FEET, pocket);
                        }
                    }
                }
            }
        }
    }

    private static void throwItems(Player player, ItemStack items, EquipmentSlot slot,  NonNullList<ItemStack> pocket){

        int index = switch (slot){
            case HEAD -> HELMET_POCKET_INDEX;
            case CHEST -> CHEST_POCKET_INDEX;
            case LEGS -> LEGS_POCKET_INDEX;
            case FEET -> BOOTS_POCKET_INDEX;

            case BODY -> 0;
            case MAINHAND -> 0;
            case OFFHAND -> 0;
        };

        int offset = switch (slot){
            case HEAD -> 0;
            case CHEST -> 4;
            case LEGS -> 8;
            case FEET -> 12;

            case BODY -> 0;
            case MAINHAND -> 0;
            case OFFHAND -> 0;
        };

        AbstractContainerMenu menu = player.containerMenu;

        RegistryAccess registries = player.level().registryAccess();
        Holder<Enchantment> enchHolder = registries.registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(ModEnchantments.POCKETS);

        int pocketsLevel = EnchantmentHelper.getItemEnchantmentLevel(enchHolder, items);

        System.out.println("Enchant: "+ pocketsLevel);

        if(pocketsLevel == 0) {
            for(int i = 0; i < 4; i++) {
                player.drop(pocket.get(i), false);
                menu.getSlot(index + i).set(ItemStack.EMPTY);
                if (menu instanceof IToggleSlot toggleable) {
                    toggleable.setToggleEnabled(offset + i, false);
                }
            }
        }
        else if (pocketsLevel == 1){

            player.drop(pocket.get(3), false);
            menu.getSlot(index + 3).set(ItemStack.EMPTY);
            if (menu instanceof IToggleSlot toggleable) {
                toggleable.setToggleEnabled(offset + 3, false);
            }

            player.drop(pocket.get(2), false);
            menu.getSlot(index + 2).set(ItemStack.EMPTY);
            if (menu instanceof IToggleSlot toggleable) {
                toggleable.setToggleEnabled(offset + 2, false);
            }

            player.drop(pocket.get(1), false);
            menu.getSlot(index + 1).set(ItemStack.EMPTY);
            if (menu instanceof IToggleSlot toggleable) {
                toggleable.setToggleEnabled(offset + 1, false);
            }

            if (menu instanceof IToggleSlot toggleable) {
                toggleable.setToggleEnabled(offset, true);
            }

        }
        else if (pocketsLevel == 2){

            player.drop(pocket.get(3), false);
            menu.getSlot(index + 3).set(ItemStack.EMPTY);
            if (menu instanceof IToggleSlot toggleable) {
                toggleable.setToggleEnabled(offset + 3, false);
            }

            player.drop(pocket.get(2), false);
            menu.getSlot(index + 2).set(ItemStack.EMPTY);
            if (menu instanceof IToggleSlot toggleable) {
                toggleable.setToggleEnabled(offset + 2, false);
            }

            if (menu instanceof IToggleSlot toggleable) {
                toggleable.setToggleEnabled(offset + 1, true);
            }

            if (menu instanceof IToggleSlot toggleable) {
                toggleable.setToggleEnabled(offset, true);
            }

        }
        else if (pocketsLevel == 3){

            player.drop(pocket.get(3), false);
            menu.getSlot(index + 3).set(ItemStack.EMPTY);
            if (menu instanceof IToggleSlot toggleable) {
                toggleable.setToggleEnabled(offset + 3, false);
            }

            if (menu instanceof IToggleSlot toggleable) {
                toggleable.setToggleEnabled(offset + 2, true);
            }

            if (menu instanceof IToggleSlot toggleable) {
                toggleable.setToggleEnabled(offset + 1, true);
            }

            if (menu instanceof IToggleSlot toggleable) {
                toggleable.setToggleEnabled(offset, true);
            }

        }
        else if (pocketsLevel == 4) {
            for(int i = 0; i < 4; i++){
                if (menu instanceof IToggleSlot toggleable) {
                    toggleable.setToggleEnabled(offset + i, true);
                }
            }
        }
    }

    private static void throwItemsArmor(Player player, ItemStack items, ArmorItem.Type type, NonNullList<ItemStack> pocket){
        
        int index = switch (type){
            case HELMET -> HELMET_POCKET_INDEX;
            case CHESTPLATE -> CHEST_POCKET_INDEX;
            case LEGGINGS -> LEGS_POCKET_INDEX;
            case BOOTS -> BOOTS_POCKET_INDEX;
            case BODY -> 0;
        };

        int offset = switch (type){
            case HELMET -> 0;
            case CHESTPLATE -> 4;
            case LEGGINGS -> 8;
            case BOOTS -> 12;
            case BODY -> 0;
        };

        AbstractContainerMenu menu = player.containerMenu;

        RegistryAccess registries = player.level().registryAccess();
        Holder<Enchantment> enchHolder = registries.registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(ModEnchantments.POCKETS);

        int pocketsLevel = EnchantmentHelper.getItemEnchantmentLevel(enchHolder, items);

        System.out.println("Enchant: "+ pocketsLevel);

        if(pocketsLevel == 0) {
            for(int i = 0; i < 4; i++) {
                player.drop(pocket.get(i), false);
                menu.getSlot(index + i).set(ItemStack.EMPTY);
                if (menu instanceof IToggleSlot toggleable) {
                    toggleable.setToggleEnabled(offset + i, false);
                }
            }
        }
        else if (pocketsLevel == 1){

            player.drop(pocket.get(3), false);
            menu.getSlot(index + 3).set(ItemStack.EMPTY);
            if (menu instanceof IToggleSlot toggleable) {
                toggleable.setToggleEnabled(offset + 3, false);
            }

            player.drop(pocket.get(2), false);
            menu.getSlot(index + 2).set(ItemStack.EMPTY);
            if (menu instanceof IToggleSlot toggleable) {
                toggleable.setToggleEnabled(offset + 2, false);
            }

            player.drop(pocket.get(1), false);
            menu.getSlot(index + 1).set(ItemStack.EMPTY);
            if (menu instanceof IToggleSlot toggleable) {
                toggleable.setToggleEnabled(offset + 1, false);
            }

            if (menu instanceof IToggleSlot toggleable) {
                toggleable.setToggleEnabled(offset, true);
            }

        }
        else if (pocketsLevel == 2){

            player.drop(pocket.get(3), false);
            menu.getSlot(index + 3).set(ItemStack.EMPTY);
            if (menu instanceof IToggleSlot toggleable) {
                toggleable.setToggleEnabled(offset + 3, false);
            }

            player.drop(pocket.get(2), false);
            menu.getSlot(index + 2).set(ItemStack.EMPTY);
            if (menu instanceof IToggleSlot toggleable) {
                toggleable.setToggleEnabled(offset + 2, false);
            }

            if (menu instanceof IToggleSlot toggleable) {
                toggleable.setToggleEnabled(offset + 1, true);
            }

            if (menu instanceof IToggleSlot toggleable) {
                toggleable.setToggleEnabled(offset, true);
            }

        }
        else if (pocketsLevel == 3){

            player.drop(pocket.get(3), false);
            menu.getSlot(index + 3).set(ItemStack.EMPTY);
            if (menu instanceof IToggleSlot toggleable) {
                toggleable.setToggleEnabled(offset + 3, false);
            }

            if (menu instanceof IToggleSlot toggleable) {
                toggleable.setToggleEnabled(offset + 2, true);
            }

            if (menu instanceof IToggleSlot toggleable) {
                toggleable.setToggleEnabled(offset + 1, true);
            }

            if (menu instanceof IToggleSlot toggleable) {
                toggleable.setToggleEnabled(offset, true);
            }

        }
        else if (pocketsLevel == 4) {
            for(int i = 0; i < 4; i++){
                if (menu instanceof IToggleSlot toggleable) {
                    toggleable.setToggleEnabled(offset + i, true);
                }
            }
        }
    }



    //when book is opened
    @SubscribeEvent
    public static void recipeBookOpened(RecipeBookToggleEvent event) {

        Minecraft mc = Minecraft.getInstance();

        if ((mc.screen instanceof InventoryScreen)){
            if (mc.player.inventoryMenu instanceof IToggleSlot toggleable) {
                for (int i = 0; i < 16; i++) {
                    toggleable.setToggleEnabled(i, !event.isNowVisible());
                }
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
