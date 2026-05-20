package com.chewylopez.pocketsmod.server;

import com.chewylopez.pocketsmod.PocketsMod;
import com.chewylopez.pocketsmod.client.StorageConduitMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ScrollRowPacket(int row) implements CustomPacketPayload {

    public static final Type<ScrollRowPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(PocketsMod.MODID, "scroll_row"));

    public static final StreamCodec<FriendlyByteBuf, ScrollRowPacket> STREAM_CODEC = StreamCodec.of((buf, pkt) -> buf.writeVarInt(pkt.row()), buf -> new ScrollRowPacket(buf.readVarInt()));

    @Override public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ScrollRowPacket pkt, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if (ctx.player().containerMenu instanceof StorageConduitMenu menu) {
                menu.handleScroll(pkt.row());
            }
        });
    }
}