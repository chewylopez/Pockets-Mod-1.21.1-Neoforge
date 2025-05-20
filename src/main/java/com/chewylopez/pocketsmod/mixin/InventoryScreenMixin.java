package com.chewylopez.pocketsmod.mixin;

import com.chewylopez.pocketsmod.PocketsMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractContainerScreen {

    @Shadow
    @Final
    private RecipeBookComponent recipeBookComponent;

    //@Shadow
    private boolean ButtonClicked;

    private static final ResourceLocation BONUS_ROWS_TEXTURE = ResourceLocation.fromNamespaceAndPath(PocketsMod.MODID, "textures/gui/bonus_rows.png");

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void fixHeight(Player player, CallbackInfo info) {
        this.imageHeight += 50;
    }

    @Inject(method = {"renderBg"}, at = {@At("TAIL")})
    private void renderBg(GuiGraphics context, float partialTick, int mouseX, int mouseY, CallbackInfo ci) {
        int i = this.leftPos;
        int j = this.topPos + 166;
        context.blit(BONUS_ROWS_TEXTURE, i, j, 0, 0, 176, 50);
    }

    public InventoryScreenMixin(AbstractContainerMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @ModifyArg(method = {"init"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ImageButton;<init>(IIIILnet/minecraft/client/gui/components/WidgetSprites;Lnet/minecraft/client/gui/components/Button$OnPress;)V"))
    private Button.OnPress recipeBookPosResetter(Button.OnPress action) {
        return buttonWidget -> {
            this.recipeBookComponent.toggleVisibility();
            this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
            buttonWidget.setPosition(this.leftPos + 104, this.height / 2 - 47);
            this.ButtonClicked = true;
        };
    }

    @ModifyConstant(method = {"init"}, constant = {@Constant(intValue = 22, ordinal = 0)})
    private int recipeBookPosFix(int og) {
        return 47;
    }
}
