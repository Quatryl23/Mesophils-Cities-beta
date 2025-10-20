package net.mesomods.mesophilscities;

import net.minecraftforge.fml.common.Mod;

import net.minecraft.resources.ResourceLocation;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PortalTemplatePoolInjector {
	public static ResourceLocation villagePlains = null;
	public static ResourceLocation villageTaiga = null;
	public static ResourceLocation villageSnowy = null;
	public static ResourceLocation villageSavanna = null;
	public static ResourceLocation villageDesert = null;

	public PortalTemplatePoolInjector() {
	}
	// NOT FINISHED YET
	/*@SubscribeEvent
	public static void onServerAboutToStart(ServerAboutToStartEvent startEvent) {
		for (int i = 0; i < 5; i++) {
			ResourceLocation injectPoolLocation = (i) -> {
				switch (i) {
					case 0: return villagePlains;
					case 1: return villageTaiga;
					case 2: return villageSnowy;
					case 3: return villageSavanna;
					case 4: return villageDesert;
				}
			};
			if (injectPoolLocation == null) continue;
			String originalPoolLocationString = (i) -> {
				switch (i) {
					case 0: return new ResourceLocation("minecraft:village/plains/houses");
					case 1: return new ResourceLocation("minecraft:village/taiga/houses");
					case 2: return new ResourceLocation("minecraft:village/snowy/houses");
					case 3: return new ResourceLocation("minecraft:village/savanna/houses");
					case 4: return new ResourceLocation("minecraft:village/desert/houses");
				}
			};
			ResourceLocation originalPoolLocation = new ResourceLocation(originalPoolLocationString);
			WritableRegistry<StructureTemplatePool> poolRegistry = (WritableRegistry<StructureTemplatePool>) startEvent.getServer().registryAccess().registryOrThrow(Registries.TEMPLATE_POOL);
			StructureTemplatePool originalPool = poolRegistry.get(originalPoolLocation);
			StructureTemplatePool injectPool = poolRegistry.get(injectPoolLocation);
			StructureTemplatePool newPool = new StructureTemplatePool(originalPool.getFallback(), );
			poolRegistry.registerOrOverride(OptionalInt.empty(), originalPoolLocation, newPool, Lifecycle.stable());
		}
	}
	*/
}
