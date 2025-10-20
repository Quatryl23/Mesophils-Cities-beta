
/*
*    MCreator note: This file will be REGENERATED on each build.
*/
package net.mesomods.mesophilscities.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.Block;

import net.mesomods.mesophilscities.block.entity.TemporaryGlassBlockEntity;
import net.mesomods.mesophilscities.block.entity.TechnicalTickerBlockEntity;
import net.mesomods.mesophilscities.MesophilsCitiesMod;
import net.mesomods.mesophilscities.CityPortalBlockEntity;

public class MesophilsCitiesModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MesophilsCitiesMod.MODID);
	public static final RegistryObject<BlockEntityType<?>> TEMPORARY_GLASS = register("temporary_glass", MesophilsCitiesModBlocks.TEMPORARY_GLASS, TemporaryGlassBlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> TECHNICAL_TICKER = register("technical_ticker", MesophilsCitiesModBlocks.TECHNICAL_TICKER, TechnicalTickerBlockEntity::new);
	// Start of user code block custom block entities
	public static final RegistryObject<BlockEntityType<CityPortalBlockEntity>> CITY_PORTAL = REGISTRY.register("city_portal", () -> BlockEntityType.Builder.of(CityPortalBlockEntity::new, MesophilsCitiesModBlocks.CITY_PORTAL.get()).build(null));

	// End of user code block custom block entities
	private static RegistryObject<BlockEntityType<?>> register(String registryname, RegistryObject<Block> block, BlockEntityType.BlockEntitySupplier<?> supplier) {
		return REGISTRY.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
	}
}
