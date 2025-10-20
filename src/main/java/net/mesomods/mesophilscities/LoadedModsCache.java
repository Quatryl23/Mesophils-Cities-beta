package net.mesomods.mesophilscities;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class LoadedModsCache {
	public static boolean CREATE;
	public static boolean REFURBISHED_FURNITURE;

	public LoadedModsCache() {
		CREATE = ModList.get().isLoaded("create");
		REFURBISHED_FURNITURE = ModList.get().isLoaded("refurbished_furniture");
	}

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		new LoadedModsCache();
	}
}
