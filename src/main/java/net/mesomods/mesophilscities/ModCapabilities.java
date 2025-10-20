package net.mesomods.mesophilscities;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.Capability;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCapabilities {
	public static Capability<ILootTableWandPlayerData> LOOT_TABLE_WAND_PLAYER_DATA = CapabilityManager.get(new CapabilityToken<>() {
	});

	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.register(ILootTableWandPlayerData.class);
	}
}
