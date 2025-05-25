package com.chewylopez.pocketsmod.mixin.playerinventory;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeModeInventoryScreenMixin extends AbstractContainerScreen {

    public CreativeModeInventoryScreenMixin(AbstractContainerMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 45))
    private static int changeContainerSize(int constant){
        return constant + 9;
    }

    @ModifyConstant(method = "slotClicked", constant = @Constant(intValue = 36, ordinal = 0))
    private int changeInventoryReferenceSize(int constant){
        return constant + 9;
    }
    @ModifyConstant(method = "slotClicked", constant = @Constant(intValue = 36, ordinal = 1))
    private int changeInventoryReferenceSize2(int constant){
        return constant + 9;
    }
    @ModifyConstant(method = "slotClicked", constant = @Constant(intValue = 36, ordinal = 2))
    private int changeInventoryReferenceSize3(int constant){
        return constant + 9;
    }
    @ModifyConstant(method = "slotClicked", constant = @Constant(intValue = 45, ordinal = 0))
    private int changeInventoryReferenceSize4(int constant){
        return constant + 9;
    }
    @ModifyConstant(method = "slotClicked", constant = @Constant(intValue = 45, ordinal = 1))
    private int changeInventoryReferenceSize5(int constant){
        return constant + 9;
    }

    @ModifyConstant(method = "selectTab", constant = @Constant(intValue = 36, ordinal = 0))
    private int changeInventoryReferenceSize7(int constant){
        return constant + 9;
    }
    @ModifyConstant(method = "selectTab", constant = @Constant(intValue = 45, ordinal = 0))
    private int changeOffhandIndex(int constant){
        return constant + 9;
    }

    @ModifyArg(method = "handleHotbarLoadOrSave", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;handleCreativeModeItemAdd(Lnet/minecraft/world/item/ItemStack;I)V"), index = 1)
    private static int modifySlotIndex(int originalIndex) {
        return originalIndex + 9;
    }

    @ModifyConstant(method = "selectTab", constant = @Constant(intValue = 112, ordinal = 1))
    private int changeDestroyItemSlotPosition(int constant){
        return constant + 18;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void screenHeightChange(LocalPlayer player, FeatureFlagSet enabledFeatures, boolean displayOperatorCreativeTab, CallbackInfo ci){
        this.imageHeight += 18;
    }

    @ModifyConstant(method = "selectTab", constant = @Constant(intValue = 112, ordinal = 0))
    private int changeHotbarPosition(int constant){
        return constant + 18;
    }

}
