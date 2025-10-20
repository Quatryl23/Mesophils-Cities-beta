package net.mesomods.mesophilscities;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import net.minecraft.resources.ResourceLocation;

public class LootTableRequestNetwork {
	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation("mesophils_cities", "loot_table_request"), () -> "1.0", s -> true, s -> true);

	public static void registerPackets(FMLCommonSetupEvent event) {
		CHANNEL.registerMessage(0, RequestLootTablesPacket.class, RequestLootTablesPacket::encode, RequestLootTablesPacket::decode, RequestLootTablesPacket::handle);
		CHANNEL.registerMessage(1, ResponseLootTablesPacket.class, ResponseLootTablesPacket::encode, ResponseLootTablesPacket::decode, ResponseLootTablesPacket::handle);
		CHANNEL.registerMessage(2, UpdateLootTableWandPacket.class, UpdateLootTableWandPacket::encode, UpdateLootTableWandPacket::decode, UpdateLootTableWandPacket::handle);
		CHANNEL.registerMessage(3, UpdateLootTableWandPlayerDataPacket.class, UpdateLootTableWandPlayerDataPacket::encode, UpdateLootTableWandPlayerDataPacket::decode, UpdateLootTableWandPlayerDataPacket::handle);
		CHANNEL.registerMessage(4, UpdateLootTableWandItemPreviewPacket.class, UpdateLootTableWandItemPreviewPacket::encode, UpdateLootTableWandItemPreviewPacket::decode, UpdateLootTableWandItemPreviewPacket::handle);
	}
}
