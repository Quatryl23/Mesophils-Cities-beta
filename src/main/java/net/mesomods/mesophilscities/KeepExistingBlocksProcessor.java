package net.mesomods.mesophilscities;

import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.LevelReader;
import net.minecraft.core.BlockPos;

import com.mojang.serialization.Codec;
import org.apache.logging.log4j.LogManager;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;

public class KeepExistingBlocksProcessor extends StructureProcessor {
	public static final KeepExistingBlocksProcessor INSTANCE = new KeepExistingBlocksProcessor();
	public static final Codec<KeepExistingBlocksProcessor> CODEC = Codec.unit(() -> INSTANCE);

	public KeepExistingBlocksProcessor() {
	}

	@Override
	public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos pos, BlockPos pos2, StructureTemplate.StructureBlockInfo originalBlockInfo, StructureTemplate.StructureBlockInfo blockInfo, StructurePlaceSettings settings,
			StructureTemplate template) {
		BlockState oldState = level.getBlockState(blockInfo.pos());
		boolean keepOriginal = !oldState.isAir() && !oldState.is(blockInfo.state().getBlock());
		return keepOriginal ? new StructureTemplate.StructureBlockInfo(blockInfo.pos(), oldState, null) : blockInfo;
	}

	protected StructureProcessorType<?> getType() {
		return ModStructureProcessors.KEEP_EXISTING_BLOCKS_PROCESSOR.get();
	}
}
