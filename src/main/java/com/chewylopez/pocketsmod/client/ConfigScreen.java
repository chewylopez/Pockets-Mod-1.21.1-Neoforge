package com.chewylopez.pocketsmod.client;

import com.chewylopez.pocketsmod.Config;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class ConfigScreen extends Screen {

    private final Screen parent;

    public ConfigScreen(Screen parent) {
        super(Component.literal("Magic Pockets Config"));
        this.parent = parent;
    }

    @Override
    protected void init() {

        String currentValue = switch (Config.ITEMSTACK_SIZE) {
            case 64  -> "64 (default)";
            case 128 -> "128 (default modded)";
            case 256 -> "256";
            case 512 -> "512";
            case 999 -> "999";
            case 1   -> "1 (challenge mode)";
            default  -> "64 (default)";
        };

        CycleButton<String> itemStackButton = CycleButton.<String>builder(Component::literal)
                .withValues("64 (default)", "128 (default modded)", "256", "512", "999", "1 (challenge mode)").withInitialValue(currentValue)
                .create(this.width / 2 - 100, 100, 200, 20, Component.literal("ItemStack Size"), (btn, value) -> {
                    Config.ITEMSTACK_SIZE = switch (value) {
                        case "64 (default)"        -> 64;
                        case "128 (default modded)"-> 128;
                        case "256"                 -> 256;
                        case "512"                 -> 512;
                        case "999"                 -> 999;
                        case "1 (challenge mode)"  -> 1;
                        default                    -> 64;
                    };
                    Config.ITEMSTACK_SIZE_CONFIG.set(Config.ITEMSTACK_SIZE);
                });

        StringWidget title = new StringWidget(this.width/2-100, 70, 200, 20, Component.literal("change max itemstack limit"), font.self());
        addRenderableWidget(title);

        addRenderableWidget(itemStackButton);

        Button done = Button.builder(Component.literal("Done"), button -> this.minecraft.setScreen(parent)).bounds(this.width / 2 - 100, this.height - 40, 200, 20).build();
        addRenderableWidget(done);

        addRenderableWidget(Button.builder(Component.literal("Update Value"), button -> {
            Config.ITEMSTACK_SIZE_CONFIG.set(Config.ITEMSTACK_SIZE);
            System.out.println("ItemStack size updated: " + Config.ITEMSTACK_SIZE);

        }).bounds(this.width / 2 - 100, 130, 200, 20).build());
    }
}

