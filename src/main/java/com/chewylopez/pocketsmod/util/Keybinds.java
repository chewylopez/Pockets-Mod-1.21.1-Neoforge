package com.chewylopez.pocketsmod.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.settings.KeyConflictContext;

public class Keybinds {
    public static final String KEY_POCKETS_CATEGORY = "key.category.pocketsmod";

    public static final KeyMapping POCKETS_KEY = new KeyMapping(
            KEY_POCKETS_CATEGORY, KeyConflictContext.IN_GAME, InputConstants.getKey(InputConstants.KEY_P, -1), KeyMapping.CATEGORY_INVENTORY);
}
