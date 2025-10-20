package net.mesomods.mesophilscities;

import net.minecraftforge.network.NetworkEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.nbt.CompoundTag;
import java.util.function.Supplier;

public class UpdateLootTableWandPacket {
	private int slot;
	private CompoundTag nbt;

	public UpdateLootTableWandPacket(int slot, CompoundTag nbt) {
		this.slot = slot;
		this.nbt = nbt;
	}

	public static void encode(UpdateLootTableWandPacket pkt, FriendlyByteBuf buf) {
		buf.writeInt(pkt.slot);
		buf.writeNbt(pkt.nbt);
	}

	public static UpdateLootTableWandPacket decode(FriendlyByteBuf buf) {
		return new UpdateLootTableWandPacket(buf.readInt(), buf.readNbt());
	}

	public static void handle(UpdateLootTableWandPacket packet, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayer player = ctx.get().getSender();
			if (player != null) {
				player.getInventory().getItem(packet.slot).setTag(packet.nbt);
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
