package com.chewylopez.pocketsmod.mixin.playerinventory;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = Inventory.class, priority = 1)
public class InventoryMixin {

    @Unique
    private static int VANILLA_INVENTORY_SIZE = 45;
    @Unique
    private static int EXTRA_SLOTS_SIZE = 9;
    @Unique
    private static int POCKETS_SIZE = 16;

    @Mutable @Shadow @Final
    public NonNullList<ItemStack> items;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void changeItemsSize(CallbackInfo ci) {
        this.items = NonNullList.withSize(45, ItemStack.EMPTY);
    }
    @ModifyConstant(method = "getSlotWithRemainingSpace", constant = @Constant(intValue = 40, ordinal = 0))
    private int changeInventoryReferenceSize(int constant) {
        return constant + 9;
    }
    @ModifyConstant(method = "getSlotWithRemainingSpace", constant = @Constant(intValue = 40, ordinal = 1))
    private int changeInventoryReferenceSize2(int constant) {
        return constant + 9;
    }

}

