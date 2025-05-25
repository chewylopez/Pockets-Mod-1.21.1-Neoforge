package com.chewylopez.pocketsmod.mixin.playerinventory;

import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin{

    @ModifyConstant(method = {"handleSetCreativeModeSlot"}, constant = {@Constant(intValue = 45)})
    private int changePacketSizeReference(int original) {
        return original + 9;
    }

}
