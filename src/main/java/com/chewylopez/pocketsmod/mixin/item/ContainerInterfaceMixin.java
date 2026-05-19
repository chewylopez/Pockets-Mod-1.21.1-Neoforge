package com.chewylopez.pocketsmod.mixin.item;

import com.chewylopez.pocketsmod.Config;
import com.chewylopez.pocketsmod.PocketsMod;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.Container;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Container.class)
public interface ContainerInterfaceMixin {

    @Unique
    private static int getDefaultMaxStackSize() {
        return Config.ITEMSTACK_SIZE;
    }

    @ModifyReturnValue(method = {"getMaxStackSize()I"}, at = {@At("RETURN")})
    default int getMaxCountPerStack(int constant) {
        return getDefaultMaxStackSize();
    }
}
