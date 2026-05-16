package com.chewylopez.pocketsmod.mixin.playerinventory;

import com.chewylopez.pocketsmod.mixin.playerinventory.interfaces.IInventoryPartition;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin({Inventory.class})
public abstract class InventoryMixin implements IInventoryPartition {

    @Unique
    private static int VANILLA_INVENTORY_SIZE = 36;
    @Unique
    private static int EXTRA_SLOTS_SIZE = 9;
    @Unique
    private static int POCKETS_SIZE = 16;


    @Shadow @Final
    private List<NonNullList<ItemStack>> compartments;

    @Mutable @Unique @Final
    private NonNullList<ItemStack> helmet_pocket = NonNullList.withSize(4, ItemStack.EMPTY);
    @Mutable @Unique @Final
    private NonNullList<ItemStack> chest_pocket = NonNullList.withSize(4, ItemStack.EMPTY);
    @Mutable @Unique @Final
    private NonNullList<ItemStack> legs_pocket = NonNullList.withSize(4, ItemStack.EMPTY);
    @Mutable @Unique @Final
    private NonNullList<ItemStack> boots_pocket = NonNullList.withSize(4, ItemStack.EMPTY);


    @Override
    public NonNullList<ItemStack> pockets_Mod_1_21_1_Neoforge$getHelmetInventoryPartition() {
        return helmet_pocket;
    }
    @Override
    public NonNullList<ItemStack> pockets_Mod_1_21_1_Neoforge$getChestInventoryPartition() {
        return chest_pocket;
    }
    @Override
    public NonNullList<ItemStack> pockets_Mod_1_21_1_Neoforge$getLegsInventoryPartition() {
        return legs_pocket;
    }
    @Override
    public NonNullList<ItemStack> pockets_Mod_1_21_1_Neoforge$createBootsInventoryPartition() {
        return boots_pocket;
    }

    @Inject(method = {"<init>"},at =  @At("TAIL"))
    private void addToCompartments(Player player, CallbackInfo ci) {
        this.compartments.add(this.helmet_pocket);
        this.compartments.add(this.chest_pocket);
        this.compartments.add(this.legs_pocket);
        this.compartments.add(this.boots_pocket);
    }


    @ModifyArg(method = {"<init>"}, index = 0, at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/core/NonNullList;withSize(ILjava/lang/Object;)Lnet/minecraft/core/NonNullList;"))
    private int modifyInvSize(int size) {
        return VANILLA_INVENTORY_SIZE + EXTRA_SLOTS_SIZE + POCKETS_SIZE; //61
    }
    @ModifyConstant(method = "getSlotWithRemainingSpace", constant = @Constant(intValue = 40, ordinal = 0))
    private int changeInventoryReferenceSize(int constant) {
        return constant + EXTRA_SLOTS_SIZE + POCKETS_SIZE;
    }
    @ModifyConstant(method = "getSlotWithRemainingSpace", constant = @Constant(intValue = 40, ordinal = 1))
    private int changeInventoryReferenceSize2(int constant) {
        return constant + EXTRA_SLOTS_SIZE + POCKETS_SIZE;
    }


}

