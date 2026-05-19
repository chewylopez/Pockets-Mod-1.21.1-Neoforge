package com.chewylopez.pocketsmod;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec.IntValue ITEMSTACK_SIZE_CONFIG = BUILDER.comment("itemstack max size").defineInRange("itemstacksize", 64, 1, 999);
    public static final ModConfigSpec SPEC = BUILDER.build();

    public static int ITEMSTACK_SIZE = 128;
}
