package com.chewylopez.pocketsmod.enchantment;

import com.chewylopez.pocketsmod.PocketsMod;
import com.chewylopez.pocketsmod.enchantment.custom.PocketsEnchantment;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEnchantmentEffects {

    public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> ENTITY_ENCHANTMENT_EFFECTS = DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, PocketsMod.MODID);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> POCKETSTATUS = ENTITY_ENCHANTMENT_EFFECTS.register("pockets", () -> PocketsEnchantment.CODEC);

    public static void register(IEventBus eventBus) {
        ENTITY_ENCHANTMENT_EFFECTS.register(eventBus);
    }
}