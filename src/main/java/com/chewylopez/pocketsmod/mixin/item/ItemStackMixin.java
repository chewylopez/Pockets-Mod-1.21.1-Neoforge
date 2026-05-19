package com.chewylopez.pocketsmod.mixin.item;

import com.chewylopez.pocketsmod.Config;
import com.chewylopez.pocketsmod.PocketsMod;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Unique
    private static int getDefaultMaxStackSize() {
        return Config.ITEMSTACK_SIZE;
    }

    @ModifyExpressionValue(
            method = "lambda$static$3(Lcom/mojang/serialization/codecs/RecordCodecBuilder$Instance;)Lcom/mojang/datafixers/kinds/App;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ExtraCodecs;intRange(II)Lcom/mojang/serialization/Codec;")
    )
    private static Codec<Integer> replaceCodec(Codec<Integer> original) {
        return Codec.INT.flatXmap(
                n -> n >= 1 && n <= 999
                        ? DataResult.success(n)
                        : DataResult.error(() -> "Count " + n + " out of range"),
                n -> n >= 1 && n <= 999
                        ? DataResult.success(n)
                        : DataResult.error(() -> "Count " + n + " out of range")
        );
    }

    @Inject(method = "getMaxStackSize", at = @At("HEAD"), cancellable = true)
    private void dynamicMaxStackSize(CallbackInfoReturnable<Integer> cir) {
        int natural = ((ItemStack)(Object)this).getItem().getDefaultMaxStackSize();
        if (natural > 16) {
            cir.setReturnValue(Math.min(Config.ITEMSTACK_SIZE, 999));
        }
        if(natural > 4 && natural <= 16){
            cir.setReturnValue(Math.min(Config.ITEMSTACK_SIZE/4, 999));
        }
    }
}
