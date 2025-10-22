package net.mesomods.mesophilscities;

import org.checkerframework.checker.units.qual.s;

import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MesophilsCitiesModNetwork {
	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation("mesophils_cities", "mod_network"), () -> "1.0", s -> true, s -> true);

	@SubscribeEvent
	public static void registerPackets(FMLCommonSetupEvent event) {
		CHANNEL.registerMessage(0, StylePackLoadCheckerPacket.class, StylePackLoadCheckerPacket::encode, StylePackLoadCheckerPacket::decode, StylePackLoadCheckerPacket::handle);
		CHANNEL.registerMessage(1, SuppressStylePackWarningPacket.class, SuppressStylePackWarningPacket::encode, SuppressStylePackWarningPacket::decode, SuppressStylePackWarningPacket::handle);
	}
}
