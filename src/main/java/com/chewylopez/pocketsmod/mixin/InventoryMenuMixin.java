package com.chewylopez.pocketsmod.mixin;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(InventoryMenu.class)
public abstract class InventoryMenuMixin extends AbstractContainerMenu {

    protected InventoryMenuMixin(@Nullable MenuType<?> type, int syncId) {
        super(type, syncId);
    }

    @ModifyConstant(method = {"<init>"}, constant = {@Constant(intValue = 39)})
    private int armorIndexChange(int og) {
        return og + 18;
    }

    @ModifyConstant(method = {"<init>"}, constant = {@Constant(intValue = 40)})
    private int offhandIndexChange(int og) {
        return og + 18;
    }

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void addMoreRows(Inventory inventory, boolean onServer, Player owner, CallbackInfo info) {

        if(true){
            for (int n = 0; n < 2; n++) {
                for (int m = 0; m < 9; m++) {
                    addSlot(new Slot((Container) inventory, m + (n + 1) * 9 + 27, 8 + m * 18, 174 + n * 18));
                }
            }
        }
    }
}