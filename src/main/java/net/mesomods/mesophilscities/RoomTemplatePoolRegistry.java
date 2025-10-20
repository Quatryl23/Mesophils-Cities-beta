package net.mesomods.mesophilscities;

import net.minecraftforge.registries.DataPackRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.Registry;
import org.apache.logging.log4j.LogManager;

@Mod.EventBusSubscriber(modid = "mesophils_cities", bus = Mod.EventBusSubscriber.Bus.MOD)
public class RoomTemplatePoolRegistry {
	public static final ResourceKey<Registry<RoomTemplatePool>> ROOM_TEMPLATE_POOL = ResourceKey.createRegistryKey(new ResourceLocation("worldgen/city_template_pool/jigsaw"));

	@SubscribeEvent
	public static void newDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
		event.dataPackRegistry(RoomTemplatePoolRegistry.ROOM_TEMPLATE_POOL, RoomTemplatePool.CODEC);
	}
}
