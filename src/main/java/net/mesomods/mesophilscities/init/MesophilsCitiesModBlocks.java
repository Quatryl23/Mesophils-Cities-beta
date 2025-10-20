
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mesomods.mesophilscities.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;

import net.mesomods.mesophilscities.block.TemporaryGlassBlock;
import net.mesomods.mesophilscities.block.TechnicalTickerBlock;
import net.mesomods.mesophilscities.block.InfestedReinforcedDeepslateBlock;
import net.mesomods.mesophilscities.block.CityPortalBlock;
import net.mesomods.mesophilscities.MesophilsCitiesMod;

public class MesophilsCitiesModBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, MesophilsCitiesMod.MODID);
	public static final RegistryObject<Block> CITY_PORTAL = REGISTRY.register("city_portal", () -> new CityPortalBlock());
	public static final RegistryObject<Block> TEMPORARY_GLASS = REGISTRY.register("temporary_glass", () -> new TemporaryGlassBlock());
	public static final RegistryObject<Block> INFESTED_REINFORCED_DEEPSLATE = REGISTRY.register("infested_reinforced_deepslate", () -> new InfestedReinforcedDeepslateBlock());
	public static final RegistryObject<Block> TECHNICAL_TICKER = REGISTRY.register("technical_ticker", () -> new TechnicalTickerBlock());
	// Start of user code block custom blocks
	// End of user code block custom blocks
}
