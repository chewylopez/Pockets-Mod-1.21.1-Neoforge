package com.chewylopez.pocketsmod.mixin.item;

import com.chewylopez.pocketsmod.PocketsMod;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.serialization.Codec;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @ModifyExpressionValue(method = {"lambda$static$3(Lcom/mojang/serialization/codecs/RecordCodecBuilder$Instance;)Lcom/mojang/datafixers/kinds/App;"},
            at = {@At(value = "INVOKE", target = "Lnet/minecraft/util/ExtraCodecs;intRange(II)Lcom/mojang/serialization/Codec;")})
    private static Codec<Integer> replaceCodec(Codec<Integer> original) {
        return Codec.intRange(0, PocketsMod.GLOBAL_MAX_STACK);
    }
}
