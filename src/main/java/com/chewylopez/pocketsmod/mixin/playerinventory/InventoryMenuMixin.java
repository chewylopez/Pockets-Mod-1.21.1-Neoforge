package com.chewylopez.pocketsmod.mixin.playerinventory;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(value = InventoryMenu.class, priority = 1)
public abstract class InventoryMenuMixin extends AbstractContainerMenu {

    @Shadow @Final private ResultContainer resultSlots;

    protected InventoryMenuMixin(@Nullable MenuType<?> type, int syncId) {
        super(type, syncId);
    }

    @ModifyConstant(method = {"<init>"}, constant = {@Constant(intValue = 39)})
    private int armorIndexChange(int original) {
        return original + 9;
    }

    @ModifyConstant(method = {"<init>"}, constant = {@Constant(intValue = 40)})
    private int offhandIndexChange(int original) {
        return original + 9;
    }

    @ModifyConstant(method = {"<init>"}, constant = {@Constant(intValue = 3)})
    private int addrow(int original) {
        return 4;
    }

    @ModifyConstant(method = {"<init>"}, constant = {@Constant(intValue = 142)})
    private int changeHotbarHeight(int original) {
        return 160;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void createPocketsSlots(Inventory playerInventory, boolean active, Player owner, CallbackInfo ci){

        int index = 0;

        for(int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; j++) {
                this.addSlot(new Slot(playerInventory, 50 + index, -30 - (18 * j), 8 + (i * 18)));
                index++;
            }
        }

    }


    @ModifyConstant(method = {"isHotbarSlot"}, constant = @Constant(intValue = 36, ordinal = 0))
    private static int changeInventoryHotbarReference(int constant){
        return constant + 9;
    }
    @ModifyConstant(method = {"isHotbarSlot"}, constant = @Constant(intValue = 45, ordinal = 0))
    private static int changeInventoryHotbarReference2(int constant){
        return constant + 9;
    }
    @ModifyConstant(method = {"isHotbarSlot"}, constant = @Constant(intValue = 45, ordinal = 1))
    private static int changeInventoryHotbarReference3(int constant){
        return constant + 9;
    }

    @ModifyConstant(method = {"quickMoveStack"}, constant = @Constant(intValue = 45, ordinal = 0))
    private int changeInventoryReferenceSize(int constant){
        return constant + 9;
    }
    @ModifyConstant(method = {"quickMoveStack"}, constant = @Constant(intValue = 45, ordinal = 1))
    private int changeInventoryReferenceSize2(int constant){
        return constant + 9;
    }
    @ModifyConstant(method = {"quickMoveStack"}, constant = @Constant(intValue = 45, ordinal = 2))
    private int changeInventoryReferenceSize3(int constant){
        return constant + 9;
    }
    @ModifyConstant(method = {"quickMoveStack"}, constant = @Constant(intValue = 45, ordinal = 3))
    private int changeInventoryReferenceSize4(int constant){
        return constant + 9;
    }
    @ModifyConstant(method = {"quickMoveStack"}, constant = @Constant(intValue = 45, ordinal = 4))
    private int changeInventoryReferenceSize5(int constant){
        return constant + 9;
    }
    @ModifyConstant(method = {"quickMoveStack"}, constant = @Constant(intValue = 46, ordinal = 0))
    private int changeInventoryReferenceSize6(int constant){
        return constant + 9;
    }
    @ModifyConstant(method = {"quickMoveStack"}, constant = @Constant(intValue = 36, ordinal = 0))
    private int changeInventoryReferenceSize7(int constant){
        return constant + 9;
    }
    @ModifyConstant(method = {"quickMoveStack"}, constant = @Constant(intValue = 36, ordinal = 1))
    private int changeInventoryReferenceSize8(int constant){
        return constant + 9;
    }
    @ModifyConstant(method = {"quickMoveStack"}, constant = @Constant(intValue = 45, ordinal = 5))
    private int changeInventoryReferenceSize9(int constant){
        return constant + 9;
    }
    @ModifyConstant(method = {"quickMoveStack"}, constant = @Constant(intValue = 36, ordinal = 2))
    private int changeInventoryReferenceSize10(int constant){
        return constant + 9;
    }
    @ModifyConstant(method = {"quickMoveStack"}, constant = @Constant(intValue = 45, ordinal = 6))
    private int changeInventoryReferenceSize11(int constant){
        return constant + 9;
    }
    @ModifyConstant(method = {"quickMoveStack"}, constant = @Constant(intValue = 36, ordinal = 3))
    private int changeInventoryReferenceSize12(int constant){
        return constant + 9;
    }
    @ModifyConstant(method = {"quickMoveStack"}, constant = @Constant(intValue = 45, ordinal = 7))
    private int changeInventoryReferenceSize13(int constant){
        return constant + 9;
    }


}