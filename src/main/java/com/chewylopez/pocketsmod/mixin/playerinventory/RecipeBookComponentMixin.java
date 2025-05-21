package com.chewylopez.pocketsmod.mixin.playerinventory;

import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(RecipeBookComponent.class)
public class RecipeBookComponentMixin {

    @ModifyConstant(method = {"render"}, constant = {@Constant(intValue = 166)})
    private int changePosition(int original) {
        return original + 18;
    }

}
