package net.mesomods.mesophilscities;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;

import net.mesomods.mesophilscities.init.MesophilsCitiesModItems;

@Mod.EventBusSubscriber(modid = "mesophils_cities", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ContainerRightClickManager {
	@SubscribeEvent
	public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		/* if (event.getEntity().getItemInHand(event.getHand()).is(MesophilsCitiesModItems.LOOT_TABLE_WAND.get()) && event.getLevel().getBlockEntity(event.getPos()) instanceof RandomizableContainerBlockEntity && !event.getLevel().isClientSide()) {
			event.setUseBlock(Event.Result.DENY);
		}*/
	}
}
