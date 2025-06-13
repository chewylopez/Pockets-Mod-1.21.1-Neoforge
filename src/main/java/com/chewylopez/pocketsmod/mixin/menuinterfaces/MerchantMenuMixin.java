package com.chewylopez.pocketsmod.mixin.menuinterfaces;

import net.minecraft.world.inventory.MerchantMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(MerchantMenu.class)
public class MerchantMenuMixin {

    @ModifyConstant(method = {"<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/item/trading/Merchant;)V"}, constant = {@Constant(intValue = 3, ordinal = 0)})
    private int rowAdd(int original) {
        return 4;
    }

    @ModifyConstant(method = {"<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/item/trading/Merchant;)V"}, constant = {@Constant(intValue = 142)})
    private int changeHotbarHeight(int original) {
        return original + 18;
    }

    @ModifyConstant(method = {"quickMoveStack"}, constant = {@Constant(intValue = 39, ordinal = 0)})
    private int changeInventoryMenuIndexReference(int original) {
        return original + 9;
    }
    @ModifyConstant(method = {"quickMoveStack"}, constant = {@Constant(intValue = 39, ordinal = 1)})
    private int changeInventoryMenuIndexReferenc2(int original) {
        return original + 9;
    }
    @ModifyConstant(method = {"quickMoveStack"}, constant = {@Constant(intValue = 39, ordinal = 2)})
    private int changeInventoryMenuIndexReferenc3(int original) {
        return original + 9;
    }
    @ModifyConstant(method = {"quickMoveStack"}, constant = {@Constant(intValue = 39, ordinal = 3)})
    private int changeInventoryMenuIndexReferenc4(int original) {
        return original + 9;
    }
    @ModifyConstant(method = {"quickMoveStack"}, constant = {@Constant(intValue = 30, ordinal = 0)})
    private int changeInventoryMenuIndexReferenc5(int original) {
        return original + 9;
    }
    @ModifyConstant(method = {"quickMoveStack"}, constant = {@Constant(intValue = 30, ordinal = 1)})
    private int changeInventoryMenuIndexReferenc6(int original) {
        return original + 9;
    }
    @ModifyConstant(method = {"quickMoveStack"}, constant = {@Constant(intValue = 30, ordinal = 2)})
    private int changeInventoryMenuIndexReferenc7(int original) {
        return original + 9;
    }

    @ModifyConstant(method = {"tryMoveItems"}, constant = {@Constant(intValue = 39, ordinal = 0)})
    private int changeInventoryMenuIndexReference8(int original) {
        return original + 9;
    }
    @ModifyConstant(method = {"tryMoveItems"}, constant = {@Constant(intValue = 39, ordinal = 1)})
    private int changeInventoryMenuIndexReferenc9(int original) {
        return original + 9;
    }

    @ModifyConstant(method = {"moveFromInventoryToPaymentSlot"}, constant = {@Constant(intValue = 39, ordinal = 0)})
    private int changeInventoryMenuIndexReference10(int original) {
        return original + 9;
    }


}
