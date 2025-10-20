package net.mesomods.mesophilscities;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.Capability;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModCapabilitiesAttacher {
	@SubscribeEvent
	public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof Player) {
			event.addCapability(new ResourceLocation("mesophils_cities", "loot_table_wand_player_data"), new ICapabilityProvider() {
				private final LootTableWandPlayerData data = new LootTableWandPlayerData();

				@Override
				public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
					return capability == ModCapabilities.LOOT_TABLE_WAND_PLAYER_DATA ? LazyOptional.of(() -> data).cast() : LazyOptional.empty();
				}
			});
		}
	}
}
