package net.mesomods.mesophilscities;

import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Supplier;
import java.util.Map;
import java.util.ArrayList;

public class RequestLootTablesPacket {
	public RequestLootTablesPacket() {
	}

	// Encode to bytes
	public static void encode(RequestLootTablesPacket pkt, FriendlyByteBuf buf) {
	}

	// Decode from bytes
	public static RequestLootTablesPacket decode(FriendlyByteBuf buf) {
		return new RequestLootTablesPacket();
	}

	// Handle on server side
	public static void handle(RequestLootTablesPacket pkt, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayer player = ctx.get().getSender();
			if (player != null) {
				ResourceManager manager = player.getServer().getResourceManager();
				Map<ResourceLocation, Resource> resources = manager.listResources("loot_tables", rl -> rl.getPath().endsWith(".json"));
				// Send back to client
				LootTableRequestNetwork.CHANNEL.sendTo(new ResponseLootTablesPacket(new ArrayList<>(resources.keySet())), player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
