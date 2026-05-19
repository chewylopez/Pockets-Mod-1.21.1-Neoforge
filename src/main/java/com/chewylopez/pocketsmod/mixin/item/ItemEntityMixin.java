package com.chewylopez.pocketsmod.mixin.item;

import com.chewylopez.pocketsmod.Config;
import com.chewylopez.pocketsmod.PocketsMod;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {

    @Unique
    private static int getDefaultMaxStackSize() {
        return Config.ITEMSTACK_SIZE;
    }

    @ModifyConstant(method = {"merge(Lnet/minecraft/world/entity/item/ItemEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)V"},
            constant = {@Constant(intValue = 64)})
    private static int merge(int val) {
        return getDefaultMaxStackSize();
    }
}