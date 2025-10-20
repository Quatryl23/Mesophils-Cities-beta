package net.mesomods.mesophilscities;

import net.minecraftforge.network.NetworkEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Supplier;

public class UpdateLootTableWandPlayerDataPacket {
	private boolean emptyTargetContainer;
	private boolean synchronizeWands;

	public UpdateLootTableWandPlayerDataPacket(boolean emptyTargetContainer, boolean synchronizeWands) {
		this.emptyTargetContainer = emptyTargetContainer;
		this.synchronizeWands = synchronizeWands;
	}

	public static void encode(UpdateLootTableWandPlayerDataPacket pkt, FriendlyByteBuf buf) {
		buf.writeBoolean(pkt.emptyTargetContainer);
		buf.writeBoolean(pkt.synchronizeWands);
	}

	public static UpdateLootTableWandPlayerDataPacket decode(FriendlyByteBuf buf) {
		return new UpdateLootTableWandPlayerDataPacket(buf.readBoolean(), buf.readBoolean());
	}

	public static void handle(UpdateLootTableWandPlayerDataPacket packet, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayer player = ctx.get().getSender();
			player.getCapability(ModCapabilities.LOOT_TABLE_WAND_PLAYER_DATA).ifPresent(data -> {
				data.setEmptyTargetContainer(packet.emptyTargetContainer);
				data.setSynchronizeWands(packet.synchronizeWands);
			});
		});
		ctx.get().setPacketHandled(true);
	}
}
