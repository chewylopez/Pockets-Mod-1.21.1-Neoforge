package com.chewylopez.pocketsmod.datagen;

import com.chewylopez.pocketsmod.enchantment.custom.ModEnchantments;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.tags.EnchantmentTags;

import java.util.concurrent.CompletableFuture;

public class ModEnchantmentTagProvider extends EnchantmentTagsProvider {

    public ModEnchantmentTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(EnchantmentTags.IN_ENCHANTING_TABLE)
                .add(ModEnchantments.POCKETS);

        tag(EnchantmentTags.ON_RANDOM_LOOT)
                .add(ModEnchantments.POCKETS);
    }
}