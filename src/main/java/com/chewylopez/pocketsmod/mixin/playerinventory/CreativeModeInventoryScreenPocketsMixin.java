package com.chewylopez.pocketsmod.mixin.playerinventory;

import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.core.NonNullList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = CreativeModeInventoryScreen.class, priority = 2)
public class CreativeModeInventoryScreenPocketsMixin {

    @Redirect(method = "selectTab", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;size()I", ordinal = 0))
    private int preventOverflow(NonNullList<?> slots) {
        return slots.size() - 16;
    }

}
