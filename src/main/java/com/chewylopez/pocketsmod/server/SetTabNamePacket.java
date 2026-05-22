package com.chewylopez.pocketsmod.server;

import com.chewylopez.pocketsmod.PocketsMod;
import com.chewylopez.pocketsmod.player.client.StorageConduitMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SetTabNamePacket(int tabIndex, String name) implements CustomPacketPayload {

    public static final Type<SetTabNamePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(PocketsMod.MODID, "set_tab_name"));

    public static final StreamCodec<FriendlyByteBuf, SetTabNamePacket> STREAM_CODEC =
            StreamCodec.of(
                    (buf, pkt) -> { buf.writeVarInt(pkt.tabIndex()); buf.writeUtf(pkt.name()); },
                    buf -> new SetTabNamePacket(buf.readVarInt(), buf.readUtf())
            );

    @Override public Type<? extends CustomPacketPayload> type() { return TYPE; }

    public static void handle(SetTabNamePacket pkt, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if (ctx.player().containerMenu instanceof StorageConduitMenu menu)
                menu.handleSetName(pkt.tabIndex(), pkt.name());
        });
    }
}