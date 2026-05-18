package com.chewylopez.pocketsmod.event;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;

public class RecipeBookToggleEvent extends Event {
    private final boolean nowVisible;


    public RecipeBookToggleEvent(boolean nowVisible) {
        this.nowVisible = nowVisible;
    }

    public boolean isNowVisible() {
        return nowVisible;
    }
}