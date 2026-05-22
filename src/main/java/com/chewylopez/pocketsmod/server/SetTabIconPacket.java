package com.chewylopez.pocketsmod.server;

import com.chewylopez.pocketsmod.PocketsMod;
import com.chewylopez.pocketsmod.player.client.StorageConduitMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SetTabIconPacket(int tabIndex, ItemStack icon) implements CustomPacketPayload {

    public static final Type<SetTabIconPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(PocketsMod.MODID, "set_tab_icon"));

    public static final StreamCodec<FriendlyByteBuf, SetTabIconPacket> STREAM_CODEC =
            StreamCodec.of(
                    (buf, pkt) -> {
                        buf.writeVarInt(pkt.tabIndex());
                        buf.writeBoolean(!pkt.icon().isEmpty());
                        if (!pkt.icon().isEmpty()) {
                            buf.writeUtf(BuiltInRegistries.ITEM.getKey(pkt.icon().getItem()).toString());
                            buf.writeByte(pkt.icon().getCount());
                        }
                    },
                    buf -> {
                        int idx = buf.readVarInt();
                        ItemStack icon = ItemStack.EMPTY;
                        if (buf.readBoolean()) {
                            Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(buf.readUtf()));
                            icon = new ItemStack(item, buf.readByte() & 0xFF);
                        }
                        return new SetTabIconPacket(idx, icon);
                    }
            );

    @Override public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SetTabIconPacket pkt, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if (ctx.player().containerMenu instanceof StorageConduitMenu menu) {
                menu.handleSetIcon(pkt.tabIndex(), pkt.icon());
            }
        });
    }
}