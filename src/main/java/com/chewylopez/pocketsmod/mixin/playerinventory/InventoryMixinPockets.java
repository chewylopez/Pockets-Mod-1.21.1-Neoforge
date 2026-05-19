package com.chewylopez.pocketsmod.mixin.playerinventory;

import com.chewylopez.pocketsmod.InventoryInterface.IInventoryPartition;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Mixin(value = Inventory.class, priority = 2)
public class InventoryMixinPockets implements IInventoryPartition {

    @Mutable @Shadow @Final
    private List<NonNullList<ItemStack>> compartments;

    @Shadow @Final
    public Player player;
    @Final @Shadow
    public NonNullList<ItemStack> items;
    @Final @Shadow
    public NonNullList<ItemStack> armor;
    @Final @Shadow
    public NonNullList<ItemStack> offhand;

    @Unique
    public NonNullList<ItemStack> helmet_pocket;
    @Unique
    public NonNullList<ItemStack> chest_pocket;
    @Unique
    public NonNullList<ItemStack> legs_pocket;
    @Unique
    public NonNullList<ItemStack> boots_pocket;

    @Inject(method = {"<init>"},at =  @At("TAIL"))
    private void addToCompartments(CallbackInfo ci) {

        this.helmet_pocket = NonNullList.withSize(4, ItemStack.EMPTY);
        this.chest_pocket = NonNullList.withSize(4, ItemStack.EMPTY);
        this.legs_pocket = NonNullList.withSize(4, ItemStack.EMPTY);
        this.boots_pocket = NonNullList.withSize(4, ItemStack.EMPTY);

        List<NonNullList<ItemStack>> mutable = new ArrayList<>();

        mutable.add(this.items);
        mutable.add(this.armor);
        mutable.add(this.offhand);

        mutable.add(this.helmet_pocket);
        mutable.add(this.chest_pocket);
        mutable.add(this.legs_pocket);
        mutable.add(this.boots_pocket);

        this.compartments = mutable;
    }

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
    public NonNullList<ItemStack> pockets_Mod_1_21_1_Neoforge$getBootsInventoryPartition() {
        return boots_pocket;
    }

    @Inject(method = "save", at = @At("RETURN"))
    private void mymod$save(ListTag tag, CallbackInfoReturnable<ListTag> cir) {

        for (int i = 0; i < 4; i++) {
            ItemStack stack = helmet_pocket.get(i);
            if (!stack.isEmpty()) {
                CompoundTag compound = new CompoundTag();
                compound.putByte("Slot", (byte)(200 + i));
                tag.add(stack.save(this.player.registryAccess(), compound));
            }
        }

        for (int i = 0; i < 4; i++) {
            ItemStack stack = chest_pocket.get(i);
            if (!stack.isEmpty()) {
                CompoundTag compound = new CompoundTag();
                compound.putByte("Slot", (byte)(210 + i));
                tag.add(stack.save(this.player.registryAccess(), compound));
            }
        }

        for (int i = 0; i < 4; i++) {
            ItemStack stack = legs_pocket.get(i);
            if (!stack.isEmpty()) {
                CompoundTag compound = new CompoundTag();
                compound.putByte("Slot", (byte)(220 + i));
                tag.add(stack.save(this.player.registryAccess(), compound));
            }
        }

        for (int i = 0; i < 4; i++) {
            ItemStack stack = boots_pocket.get(i);
            if (!stack.isEmpty()) {
                CompoundTag compound = new CompoundTag();
                compound.putByte("Slot", (byte)(230 + i));
                tag.add(stack.save(this.player.registryAccess(), compound));
            }
        }

    }

    @Inject(method = "load", at = @At("HEAD"))
    private void mymod$load(ListTag tag, CallbackInfo ci) {
        for (int i = 0; i < tag.size(); i++) {
            CompoundTag compound = tag.getCompound(i);
            int slot = compound.getByte("Slot") & 255;
            if (slot >= 200 && slot < 200 + helmet_pocket.size()) {
                helmet_pocket.set(slot - 200, ItemStack.parseOptional(this.player.registryAccess(), compound));
            }
            else if (slot >= 210 && slot < 210 + chest_pocket.size()) {
                chest_pocket.set(slot - 210, ItemStack.parseOptional(this.player.registryAccess(), compound));
            }
            else if (slot >= 220 && slot < 220 + legs_pocket.size()) {
                legs_pocket.set(slot - 220, ItemStack.parseOptional(this.player.registryAccess(), compound));
            }
            else if (slot >= 230 && slot < 230 + boots_pocket.size()) {
                boots_pocket.set(slot - 230, ItemStack.parseOptional(this.player.registryAccess(), compound));
            }
        }
    }
}
