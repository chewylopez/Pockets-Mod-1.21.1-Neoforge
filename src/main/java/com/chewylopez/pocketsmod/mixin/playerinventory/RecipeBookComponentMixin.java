package com.chewylopez.pocketsmod.mixin.playerinventory;

import com.chewylopez.pocketsmod.event.RecipeBookToggleEvent;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RecipeBookComponent.class)
public class RecipeBookComponentMixin {

    @Shadow
    private boolean visible;

    @Inject(method = "toggleVisibility", at = @At("TAIL"))
    private void onToggle(CallbackInfo ci) {
        NeoForge.EVENT_BUS.post(new RecipeBookToggleEvent(this.visible));
    }

    @ModifyConstant(method = {"render"}, constant = {@Constant(intValue = 166)})
    private int changePosition(int original) {
        return original + 18;
    }

}