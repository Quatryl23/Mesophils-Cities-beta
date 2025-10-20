
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mesomods.mesophilscities.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleType;

import net.mesomods.mesophilscities.MesophilsCitiesMod;

public class MesophilsCitiesModParticleTypes {
	public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MesophilsCitiesMod.MODID);
	public static final RegistryObject<SimpleParticleType> CITY_PORTAL = REGISTRY.register("city_portal", () -> new SimpleParticleType(false));
}
