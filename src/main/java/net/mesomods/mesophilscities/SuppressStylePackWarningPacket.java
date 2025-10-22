package net.mesomods.mesophilscities;

import net.minecraftforge.network.NetworkEvent;

import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.nbt.CompoundTag;

import java.util.function.Supplier;

public class SuppressStylePackWarningPacket {
	public static void encode(SuppressStylePackWarningPacket pkt, FriendlyByteBuf buf) {
	}

	public static SuppressStylePackWarningPacket decode(FriendlyByteBuf buf) {
		return new SuppressStylePackWarningPacket();
	}

	public static void handle(SuppressStylePackWarningPacket packet, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			CompoundTag playerData = ctx.get().getSender().getPersistentData();
			CompoundTag tag = playerData.getCompound(Player.PERSISTED_NBT_TAG);
			tag.putBoolean("suppressStylePackWarning", true);
			playerData.put(Player.PERSISTED_NBT_TAG, tag);
		});
		ctx.get().setPacketHandled(true);
	}
}
