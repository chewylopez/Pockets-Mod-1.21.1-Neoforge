package com.chewylopez.pocketsmod.player.client;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public record TabMetadata(ItemStack icon, String name) {

    public static final TabMetadata EMPTY = new TabMetadata(ItemStack.EMPTY, "");

    public boolean hasIcon() {
        return !icon.isEmpty();
    }
    public boolean hasName() {
        return !name.isBlank();
    }

    public Component displayName(int tabIndex) {
        return hasName() ? Component.literal(name) : Component.literal("Tab " + (tabIndex + 1));
    }

    public static void encode(FriendlyByteBuf buf, TabMetadata meta) {
        buf.writeBoolean(meta.hasIcon());
        if (meta.hasIcon()) {
            buf.writeUtf(BuiltInRegistries.ITEM.getKey(meta.icon().getItem()).toString());
            buf.writeByte(meta.icon().getCount());
        }
        buf.writeUtf(meta.name());
    }

    public static TabMetadata decode(FriendlyByteBuf buf) {
        ItemStack icon = ItemStack.EMPTY;
        if (buf.readBoolean()) {
            Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(buf.readUtf()));
            icon = new ItemStack(item, buf.readByte() & 0xFF);
        }
        return new TabMetadata(icon, buf.readUtf());
    }
}