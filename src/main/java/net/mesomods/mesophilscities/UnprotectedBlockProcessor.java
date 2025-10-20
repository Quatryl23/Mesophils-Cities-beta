package net.mesomods.mesophilscities;

import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelReader;
import net.minecraft.tags.TagKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import com.mojang.serialization.Codec;

public class UnprotectedBlockProcessor extends StructureProcessor {
	public final TagKey<Block> replaceableBlocks;
	public static final Codec<UnprotectedBlockProcessor> CODEC = TagKey.codec(Registries.BLOCK).xmap(UnprotectedBlockProcessor::new, UnprotectedBlockProcessor::getReplaceableBlocks);

	public UnprotectedBlockProcessor(TagKey<Block> tag) {
		replaceableBlocks = tag;
	}

	public TagKey<Block> getReplaceableBlocks() {
		return replaceableBlocks;
	}

	@Override
	public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos pos, BlockPos pos2, StructureTemplate.StructureBlockInfo blockInfo2, StructureTemplate.StructureBlockInfo blockInfo, StructurePlaceSettings settings, StructureTemplate template) {
		return !Feature.isReplaceable(this.replaceableBlocks).test(level.getBlockState(blockInfo.pos())) ? blockInfo : null;
	}

	@Override
	protected StructureProcessorType<?> getType() {
		return ModStructureProcessors.UNPROTECTED_BLOCK_PROCESSOR.get();
	}
}
