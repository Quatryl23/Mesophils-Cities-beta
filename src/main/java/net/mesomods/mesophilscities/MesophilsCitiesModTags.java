package net.mesomods.mesophilscities;

import net.minecraft.world.level.block.Block;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

public class MesophilsCitiesModTags {
	public static final TagKey<Block> CITY_WORLDGEN_REPLACEABLE = TagKey.create(Registries.BLOCK, new ResourceLocation("mesophils_cities", "city_worldgen_replaceable"));
	public static final TagKey<Block> COPYCAT_BLOCK = TagKey.create(Registries.BLOCK, new ResourceLocation("mesophils_cities", "copycat_blocks"));
	public static final TagKey<Block> WINDOW = TagKey.create(Registries.BLOCK, new ResourceLocation("mesophils_cities", "window"));

}
