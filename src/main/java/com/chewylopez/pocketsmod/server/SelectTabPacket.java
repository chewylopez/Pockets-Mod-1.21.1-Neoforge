package com.chewylopez.pocketsmod.server;

import com.chewylopez.pocketsmod.PocketsMod;
import com.chewylopez.pocketsmod.player.client.StorageConduitMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SelectTabPacket(int tabIndex) implements CustomPacketPayload {

    public static final Type<SelectTabPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(PocketsMod.MODID, "select_tab"));

    public static final StreamCodec<FriendlyByteBuf, SelectTabPacket> STREAM_CODEC = StreamCodec.of((buf, pkt) -> buf.writeVarInt(pkt.tabIndex()), buf -> new SelectTabPacket(buf.readVarInt()));

    @Override public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SelectTabPacket pkt, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if (ctx.player().containerMenu instanceof StorageConduitMenu menu) {
                menu.handleTabSelect(pkt.tabIndex());
            }
        });
    }
}
