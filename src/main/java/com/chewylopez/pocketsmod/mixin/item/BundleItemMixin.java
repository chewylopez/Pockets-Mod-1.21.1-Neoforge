package com.chewylopez.pocketsmod.mixin.item;

import com.chewylopez.pocketsmod.Config;
import com.chewylopez.pocketsmod.PocketsMod;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.levelgen.Aquifer;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(BundleItem.class)
public class BundleItemMixin {

    @Mutable @Shadow @Final
    private static int TOOLTIP_MAX_WEIGHT;

    @Unique
    private static int getDefaultMaxStackSize() {
        return Config.ITEMSTACK_SIZE;
    }

    @Inject(method = {"<clinit>"}, at = {@At("HEAD")})
    private static void inject(CallbackInfo ci) {
        TOOLTIP_MAX_WEIGHT = getDefaultMaxStackSize();
    }

    @Inject(method = {"appendHoverText"}, at = {@At("TAIL")})
    private void changeToolTip(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag, CallbackInfo ci) {
        BundleContents bundlecontents = stack.get(DataComponents.BUNDLE_CONTENTS);
        if (bundlecontents != null) {
            int i = Mth.mulAndTruncate(bundlecontents.weight(), getDefaultMaxStackSize());
            tooltipComponents.add(Component.translatable("item.minecraft.bundle.fullness", i, getDefaultMaxStackSize()).withStyle(ChatFormatting.GRAY));
        }
    }
}
