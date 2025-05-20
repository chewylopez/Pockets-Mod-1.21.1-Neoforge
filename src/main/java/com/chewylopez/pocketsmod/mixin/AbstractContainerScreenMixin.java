package com.chewylopez.pocketsmod.mixin;


import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AbstractContainerScreen.class)
public class AbstractContainerScreenMixin extends Screen {

    protected AbstractContainerScreenMixin(Component title) {
        super(title);
    }

    /*
    @ModifyConstant(method = {"mouseClicked"}, constant = {@Constant(intValue = 40)})
    private int changeOffhandSlot(int og) {
        return 58;
    }
     */

    @ModifyConstant(method = {"checkHotbarMouseClicked"}, constant = {@Constant(intValue = 40)})
    private int changeOffhandSlot2(int og) {
        return 58;
    }
}
