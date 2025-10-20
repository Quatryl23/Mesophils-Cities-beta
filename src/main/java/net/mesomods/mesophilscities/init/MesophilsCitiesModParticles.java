
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mesomods.mesophilscities.init;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.mesomods.mesophilscities.client.particle.CityPortalParticle;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MesophilsCitiesModParticles {
	@SubscribeEvent
	public static void registerParticles(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(MesophilsCitiesModParticleTypes.CITY_PORTAL.get(), CityPortalParticle::provider);
	}
}
