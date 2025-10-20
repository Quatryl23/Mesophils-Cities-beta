
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mesomods.mesophilscities.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.level.levelgen.feature.Feature;

import net.mesomods.mesophilscities.world.features.DataFeature;
import net.mesomods.mesophilscities.MesophilsCitiesMod;

@Mod.EventBusSubscriber
public class MesophilsCitiesModFeatures {
	public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, MesophilsCitiesMod.MODID);
	public static final RegistryObject<Feature<?>> DATA = REGISTRY.register("data", DataFeature::new);
}
