package com.chewylopez.pocketsmod.mixin.playerinventory;

import net.minecraft.client.gui.screens.recipebook.RecipeBookPage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(RecipeBookPage.class)
public class RecipeBookPageMixin {

    @ModifyConstant(method = {"init"}, constant = {@Constant(intValue = 137, ordinal = 0)})
    private int changeButtonPosition(int original) {
        return original - 6;
    }
    @ModifyConstant(method = {"init"}, constant = {@Constant(intValue = 137, ordinal = 1)})
    private int changeButtonPosition2(int original) {
        return original - 6;
    }

}
