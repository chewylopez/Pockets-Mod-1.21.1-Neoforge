package com.chewylopez.pocketsmod.mixin;


import com.chewylopez.pocketsmod.PocketsMod;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DataComponents.class)
public class DataComponentsMixin {
    @ModifyExpressionValue(method = {"<clinit>"}, at = {@At(value = "CONSTANT", args = {"intValue=64"})})
    private static int getMaxCountPerStack(int original) {
        return PocketsMod.GLOBAL_MAX_STACK;
    }

    @ModifyExpressionValue(method = {"lambda$static$1"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/util/ExtraCodecs;intRange(II)Lcom/mojang/serialization/Codec;")})
    private static Codec<Integer> replaceCodec(Codec<Integer> original) {
        return Codec.intRange(0, PocketsMod.GLOBAL_MAX_STACK);
    }
}
