package com.chewylopez.pocketsmod.mixin.playerinventory;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Objects;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {GuiGraphics.class}, remap = false)
public abstract class GuiGraphicsMixin {

    private PoseStack p;

    private static double calculateStringScale(Font font, String countString) {
       return 0.9;
    }

    @WrapOperation(method = {"renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V")})
    private void nullifyRenderItemDecorations(PoseStack instance, float x, float y, float z, Operation<Void> original) {
        p = instance;
    }

    @WrapOperation(method = {"renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)I")})
    private int nullifyRenderItemDecorations2(GuiGraphics instance, Font font, String text, int x, int y, int color, boolean dropShadow, Operation<Integer> original) {
        return 0;
    }

    @Inject(method = {"renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)I")})
    private void renderText(Font font, ItemStack stack, int x, int y, String text, CallbackInfo ci) {

        String s = text == null ? String.valueOf(stack.getCount()) : text;
        float scale = (float)calculateStringScale(font, s);
        float inverseScale = 1.0F / scale;
        p.scale(scale, scale, 1.0F);
        Objects.requireNonNull(font);
        p.translate((x + 16) * inverseScale - font.width(s) + 1.0, (y + 16) * inverseScale - 7.0F, 200.0F);
        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        font.drawInBatch(s, 0.0F, 0.0F, 16777215, true, p

                .last().pose(), (MultiBufferSource)bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
        bufferSource.endBatch();
    }
}