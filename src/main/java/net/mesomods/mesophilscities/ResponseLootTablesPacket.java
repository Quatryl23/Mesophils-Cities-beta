package net.mesomods.mesophilscities;

import net.minecraftforge.network.NetworkEvent;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.client.Minecraft;

import java.util.function.Supplier;
import java.util.List;

public class ResponseLootTablesPacket {
	private final List<ResourceLocation> lootTables;

	public ResponseLootTablesPacket(List<ResourceLocation> lootTables) {
		this.lootTables = lootTables;
	}

	public static void encode(ResponseLootTablesPacket pkt, FriendlyByteBuf buf) {
		buf.writeCollection(pkt.lootTables, FriendlyByteBuf::writeResourceLocation);
	}

	public static ResponseLootTablesPacket decode(FriendlyByteBuf buf) {
		return new ResponseLootTablesPacket(buf.readList(FriendlyByteBuf::readResourceLocation));
	}

	public static void handle(ResponseLootTablesPacket pkt, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			if (Minecraft.getInstance().screen instanceof LootTableWandSelectionGUI gui) {
				gui.initWithLootTableData(pkt.lootTables);
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
