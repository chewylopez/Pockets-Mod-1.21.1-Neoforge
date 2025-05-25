package com.chewylopez.pocketsmod.mixin.playerinventory;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @ModifyConstant(method = "pickBlock", constant = @Constant(intValue = 36, ordinal = 0))
    private int changeInventoryReferenceSize(int constant){
        return constant + 9;
    }

}
