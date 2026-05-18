package com.chewylopez.pocketsmod.event;

import com.chewylopez.pocketsmod.InventoryInterface.IInventoryPartition;
import com.chewylopez.pocketsmod.InventoryInterface.IToggleSlot;
import com.chewylopez.pocketsmod.InventoryInterface.ToggleSlot;
import com.chewylopez.pocketsmod.PocketsMod;
import com.chewylopez.pocketsmod.enchantment.custom.ModEnchantments;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ContainerScreenEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import org.joml.Matrix4f;

@EventBusSubscriber(modid = PocketsMod.MODID)
public class InventoryEvents {

    private static final int HELMET_POCKET_INDEX = 55;
    private static final int CHEST_POCKET_INDEX = 59;
    private static final int LEGS_POCKET_INDEX = 63;
    private static final int BOOTS_POCKET_INDEX = 67;

    //armor taken on or off
    @SubscribeEvent
    public static void equipArmor(LivingEquipmentChangeEvent event){

        if (event.getEntity() instanceof Player player) {

            ItemStack armorStack = player.getItemBySlot(event.getSlot());

            if (event.getSlot().isArmor() && !player.isCreative()) {

                if(armorStack.getItem() instanceof Equipable || (armorStack.getItem() instanceof BlockItem bi && bi.getBlock() instanceof Equipable)) {
                    if (event.getSlot() == EquipmentSlot.HEAD) {
                            NonNullList<ItemStack> pocket = ((IInventoryPartition) player.getInventory()).pockets_Mod_1_21_1_Neoforge$getHelmetInventoryPartition();
                            updatePocketsSlots(player, armorStack, EquipmentSlot.HEAD, pocket);
                    }
                    else if (event.getSlot() == EquipmentSlot.CHEST) {
                            NonNullList<ItemStack> pocket = ((IInventoryPartition) player.getInventory()).pockets_Mod_1_21_1_Neoforge$getChestInventoryPartition();
                            updatePocketsSlots(player, armorStack, EquipmentSlot.CHEST, pocket);
                    }
                    else if (event.getSlot() == EquipmentSlot.LEGS) {
                            NonNullList<ItemStack> pocket = ((IInventoryPartition) player.getInventory()).pockets_Mod_1_21_1_Neoforge$getLegsInventoryPartition();
                            updatePocketsSlots(player, armorStack, EquipmentSlot.LEGS, pocket);
                    }
                    else if (event.getSlot() == EquipmentSlot.FEET) {
                            NonNullList<ItemStack> pocket = ((IInventoryPartition) player.getInventory()).pockets_Mod_1_21_1_Neoforge$getBootsInventoryPartition();
                            updatePocketsSlots(player, armorStack, EquipmentSlot.FEET, pocket);
                    }
                }
                else {
                    if (event.getSlot() == EquipmentSlot.HEAD) {
                        if(event.getTo().isEmpty()){
                            NonNullList<ItemStack> pocket = ((IInventoryPartition) player.getInventory()).pockets_Mod_1_21_1_Neoforge$getHelmetInventoryPartition();
                            updatePocketsSlots(player, armorStack, EquipmentSlot.HEAD, pocket);
                        }
                    }
                    else if (event.getSlot() == EquipmentSlot.CHEST) {
                        if(event.getTo().isEmpty()) {
                            NonNullList<ItemStack> pocket = ((IInventoryPartition) player.getInventory()).pockets_Mod_1_21_1_Neoforge$getChestInventoryPartition();
                            updatePocketsSlots(player, armorStack, EquipmentSlot.CHEST, pocket);
                        }
                    }
                    else if (event.getSlot() == EquipmentSlot.LEGS) {
                        if(event.getTo().isEmpty()) {
                            NonNullList<ItemStack> pocket = ((IInventoryPartition) player.getInventory()).pockets_Mod_1_21_1_Neoforge$getLegsInventoryPartition();
                            updatePocketsSlots(player, armorStack, EquipmentSlot.LEGS, pocket);
                        }
                    }
                    else if (event.getSlot() == EquipmentSlot.FEET) {
                        if(event.getTo().isEmpty()) {
                            NonNullList<ItemStack> pocket = ((IInventoryPartition) player.getInventory()).pockets_Mod_1_21_1_Neoforge$getBootsInventoryPartition();
                            updatePocketsSlots(player, armorStack, EquipmentSlot.FEET, pocket);
                        }
                    }
                }
            }
        }
    }



    //when book is opened
    @SubscribeEvent
    public static void recipeBookOpened(RecipeBookToggleEvent event) {

        Minecraft mc = Minecraft.getInstance();

        if ((mc.screen instanceof InventoryScreen screen)) {
            if (mc.player.inventoryMenu instanceof IToggleSlot toggleable) {
                if(event.isNowVisible()){
                    for (int i = 0; i < 16; i++) {
                        toggleable.setToggleEnabled(i, false);
                    }
                }
                else {
                    updatePocketsNoChange(mc);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onRenderBackground(ContainerScreenEvent.Render.Background event) {

        AbstractContainerScreen<?> screen = event.getContainerScreen();
        if (!(screen.getMenu() instanceof InventoryMenu menu)) return;

        GuiGraphics graphics = event.getGuiGraphics();
        int leftPos = screen.getGuiLeft();
        int topPos = screen.getGuiTop();

        for (Slot slot : menu.slots) {
            if (slot instanceof ToggleSlot ts && ts.isActive()) {
                graphics.blitSprite(ResourceLocation.fromNamespaceAndPath("pocketsmod", "container/pockets_slot"), leftPos + slot.x - 1, topPos + slot.y - 1, 18, 18);
                renderPocketsSlotEffect(graphics, leftPos + slot.x, topPos + slot.y);
            }
        }
    }

    @SubscribeEvent
    public static void refreshOnOpen(ScreenEvent.Opening event){
        if (event.getScreen() instanceof InventoryScreen) {
            Minecraft mc = Minecraft.getInstance();
            updatePocketsNoChange(mc);
        }
    }

    private static void updatePocketsNoChange(Minecraft mc) {

        Player player = mc.player;

        if(player instanceof LocalPlayer) {
            AbstractContainerMenu menu = player.containerMenu;
            ItemStack armorStack;

            armorStack = player.getItemBySlot(EquipmentSlot.HEAD);
            NonNullList<ItemStack> pocket1 = ((IInventoryPartition) player.getInventory()).pockets_Mod_1_21_1_Neoforge$getHelmetInventoryPartition();
            updatePocketsSlots(player, armorStack, EquipmentSlot.HEAD, pocket1);

            armorStack = player.getItemBySlot(EquipmentSlot.CHEST);
            NonNullList<ItemStack> pocket2 = ((IInventoryPartition) player.getInventory()).pockets_Mod_1_21_1_Neoforge$getChestInventoryPartition();
            updatePocketsSlots(player, armorStack, EquipmentSlot.CHEST, pocket2);

            armorStack = player.getItemBySlot(EquipmentSlot.LEGS);
            NonNullList<ItemStack> pocket3 = ((IInventoryPartition) player.getInventory()).pockets_Mod_1_21_1_Neoforge$getLegsInventoryPartition();
            updatePocketsSlots(player, armorStack, EquipmentSlot.LEGS, pocket3);

            armorStack = player.getItemBySlot(EquipmentSlot.FEET);
            NonNullList<ItemStack> pocket4 = ((IInventoryPartition) player.getInventory()).pockets_Mod_1_21_1_Neoforge$getBootsInventoryPartition();
            updatePocketsSlots(player, armorStack, EquipmentSlot.FEET, pocket4);
        }
    }

    private static void updatePocketsSlots(Player player, ItemStack items, EquipmentSlot slot, NonNullList<ItemStack> pocket){

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

    private static void renderPocketsSlotEffect(GuiGraphics graphics, int x, int y) {
        Matrix4f pose = graphics.pose().last().pose();

        int opacityPasses = 8;

        for (int i = 0; i < opacityPasses; i++) {
            VertexConsumer consumer = graphics.bufferSource().getBuffer(RenderType.ENTITY_GLINT_DIRECT);

            int textureZoom = 1;

            consumer.addVertex(pose, x, y + 16, 0).setUv(0, textureZoom);
            consumer.addVertex(pose, x + 16, y + 16, 0).setUv(textureZoom, textureZoom);
            consumer.addVertex(pose, x + 16, y, 0).setUv(textureZoom, 0);
            consumer.addVertex(pose, x, y, 0).setUv(0, 0);

            graphics.flush();
        }
    }

}
