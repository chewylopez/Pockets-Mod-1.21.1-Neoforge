package com.chewylopez.pocketsmod.mixin.item;

import com.chewylopez.pocketsmod.Config;
import com.chewylopez.pocketsmod.PocketsMod;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public class ItemMixin {

    @Mutable @Shadow @Final
    public static int DEFAULT_MAX_STACK_SIZE;

    @Mutable @Shadow @Final
    public static int ABSOLUTE_MAX_STACK_SIZE;

    @Unique
    private static int getDefaultMaxStackSize() {
        return Config.ITEMSTACK_SIZE;
    }

    @Inject(method = {"<clinit>"}, at = {@At("HEAD")})
    private static void inject(CallbackInfo ci) {
        DEFAULT_MAX_STACK_SIZE = getDefaultMaxStackSize();
        ABSOLUTE_MAX_STACK_SIZE = 999;
    }

}
