package com.chewylopez.pocketsmod.mixin.curios;


import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "top.theillusivec4.curios.client.gui.CuriosScreen")
public abstract class CuriosScreenMixin extends AbstractContainerScreen {

    @Shadow
    @Final
    private RecipeBookComponent recipeBookGui;

    @Unique
    private boolean ButtonClicked;

    public CuriosScreenMixin(AbstractContainerMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @ModifyArg(method = {"init"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ImageButton;<init>(IIIILnet/minecraft/client/gui/components/WidgetSprites;Lnet/minecraft/client/gui/components/Button$OnPress;)V"))
    private Button.OnPress recipeBookPosResetter(Button.OnPress action) {
        return buttonWidget -> {
            this.recipeBookGui.toggleVisibility();
            this.leftPos = this.recipeBookGui.updateScreenPosition(this.width, this.imageWidth);
            buttonWidget.setPosition(this.leftPos + 104, this.height / 2 - 31);
            this.ButtonClicked = true;
        };
    }

    @ModifyConstant(method = {"init"}, constant = {@Constant(intValue = 22, ordinal = 0)})
    private int recipeBookPosFix(int og) {
        return 31;
    }

}
