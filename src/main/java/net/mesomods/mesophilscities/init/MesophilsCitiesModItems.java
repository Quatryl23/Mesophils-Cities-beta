
/*
*    MCreator note: This file will be REGENERATED on each build.
*/
package net.mesomods.mesophilscities.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;

import net.mesomods.mesophilscities.item.SilverfishBottleItem;
import net.mesomods.mesophilscities.item.ReinforcedPlateItem;
import net.mesomods.mesophilscities.item.LootTableWandItem;
import net.mesomods.mesophilscities.MesophilsCitiesMod;

public class MesophilsCitiesModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, MesophilsCitiesMod.MODID);
	public static final RegistryObject<Item> TEMPORARY_GLASS = block(MesophilsCitiesModBlocks.TEMPORARY_GLASS);
	public static final RegistryObject<Item> SILVERFISH_BOTTLE = REGISTRY.register("silverfish_bottle", () -> new SilverfishBottleItem());
	public static final RegistryObject<Item> INFESTED_REINFORCED_DEEPSLATE = block(MesophilsCitiesModBlocks.INFESTED_REINFORCED_DEEPSLATE);
	public static final RegistryObject<Item> REINFORCED_PLATE = REGISTRY.register("reinforced_plate", () -> new ReinforcedPlateItem());
	public static final RegistryObject<Item> TECHNICAL_TICKER = block(MesophilsCitiesModBlocks.TECHNICAL_TICKER);
	public static final RegistryObject<Item> LOOT_TABLE_WAND = REGISTRY.register("loot_table_wand", () -> new LootTableWandItem());

	// Start of user code block custom items
	// user code block custom items
	// End of user code block custom items
	private static RegistryObject<Item> block(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
	}
}
