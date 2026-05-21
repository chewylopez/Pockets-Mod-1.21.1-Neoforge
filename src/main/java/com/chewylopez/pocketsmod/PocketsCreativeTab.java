package com.chewylopez.pocketsmod;

import com.chewylopez.pocketsmod.block.ModBlocks;
import com.chewylopez.pocketsmod.enchantment.custom.PocketsEnchantment;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class PocketsCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVETABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PocketsMod.MODID);

    public static final Supplier<CreativeModeTab> MainModTab = CREATIVETABS.register("pockets_mod_tab", () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.CHEST))
            .title(Component.translatable("pocketsmod:pockets_mod_tab"))
            .displayItems((parameters, output) -> {
                output.accept(ModBlocks.STORAGE_CONDUIT);
                output.accept(ModBlocks.STORAGE_CONDUIT_CONNECTOR);

                ResourceKey<Enchantment> magicPockets = ResourceKey.create(
                        Registries.ENCHANTMENT,
                        ResourceLocation.fromNamespaceAndPath(PocketsMod.MODID, "magic_pockets")
                );

                parameters.holders()
                        .lookupOrThrow(Registries.ENCHANTMENT)
                        .get(magicPockets)
                        .ifPresent(enchantment -> output.accept(
                                EnchantedBookItem.createForEnchantment(
                                        new EnchantmentInstance(enchantment, enchantment.value().getMaxLevel())
                                )
                        ));
            }).build());

    public static void register(IEventBus eventBus) {
        CREATIVETABS.register(eventBus);
    }
}
