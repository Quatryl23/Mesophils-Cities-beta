package net.mesomods.mesophilscities;

import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.nbt.CompoundTag;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class StylePackLoadChecker {
	@SubscribeEvent
	public static void checkStylePackExistance(PlayerEvent.PlayerLoggedInEvent event) {
		ServerPlayer player = (ServerPlayer) event.getEntity();
		CompoundTag playerData = player.getPersistentData();
		CompoundTag data = playerData.getCompound(Player.PERSISTED_NBT_TAG);
		if (CityTemplatePoolLoader.showNoStylePackWarning) {
			MesophilsCitiesModNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new StylePackLoadCheckerPacket(true));
		} else {
			if (data.contains("suppressStylePackWarning")) {
				data.remove("suppressStylePackWarning");
			}

		}
	}
}
