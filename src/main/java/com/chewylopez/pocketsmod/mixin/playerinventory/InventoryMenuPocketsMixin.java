package com.chewylopez.pocketsmod.mixin.playerinventory;

import com.chewylopez.pocketsmod.InventoryInterface.IToggleSlot;
import com.chewylopez.pocketsmod.InventoryInterface.ToggleSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = InventoryMenu.class, priority = 2)
public abstract class InventoryMenuPocketsMixin extends AbstractContainerMenu implements IToggleSlot {

    @Unique
    private SimpleContainerData slotToggleData;

    protected InventoryMenuPocketsMixin(@Nullable MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void createPocketsSlots(Inventory playerInventory, boolean active, Player owner, CallbackInfo ci){
        this.slotToggleData = new SimpleContainerData(16);
        this.addDataSlots(this.slotToggleData);

        int index = 0;

        for(int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; j++) {
                this.addSlot(new ToggleSlot(playerInventory, 50 + index, -18 - (18 * j), 8 + (i * 18), this.slotToggleData, index));
                index++;
            }
        }
    }

    @Override
    public void setToggleEnabled(int index, boolean enabled) {
        this.slotToggleData.set(index, enabled ? 1 : 0);
    }

    @Override
    public boolean getToggleEnabled(int index) {
        if(this.slotToggleData.get(index) == 0){
            return false;
        }
        else {
            return true;
        }
    }



}
