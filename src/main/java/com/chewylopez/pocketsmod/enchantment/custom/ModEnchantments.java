package com.chewylopez.pocketsmod.enchantment.custom;

import com.chewylopez.pocketsmod.PocketsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModEnchantments {
    public static final ResourceKey<Enchantment> POCKETS = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(PocketsMod.MODID, "magic_pockets"));

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        var enchantments = context.lookup(Registries.ENCHANTMENT);
        var items = context.lookup(Registries.ITEM);

        register(context, POCKETS, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ItemTags.EQUIPPABLE_ENCHANTABLE), //supported
                items.getOrThrow(ItemTags.ARMOR_ENCHANTABLE), 2, 4, //primary
                Enchantment.dynamicCost(10, 5),
                Enchantment.dynamicCost(10, 15), 2, EquipmentSlotGroup.ARMOR)));
    }

    private static void register(BootstrapContext<Enchantment> registry, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        registry.register(key, builder.build(key.location()));
    }

}