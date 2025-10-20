package net.mesomods.mesophilscities;

import org.apache.logging.log4j.LogManager;

import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelReader;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;

import javax.annotation.Nullable;

import java.util.List;
import java.util.ArrayList;

import com.mojang.serialization.Codec;
import net.minecraft.server.players.OldUsersConverter;

public class ElectricityMergeProcessor extends StructureProcessor {
	// Used to connect electricity nodes (Refurbished furniture) of jigsaw-connected rooms
	public static final Codec<ElectricityMergeProcessor> CODEC = Codec.unit(new ElectricityMergeProcessor());

	@Override
	public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos pos, BlockPos pivot, StructureTemplate.StructureBlockInfo original, StructureTemplate.StructureBlockInfo blockInfo, StructurePlaceSettings settings,
			@Nullable StructureTemplate template) {
		CompoundTag nbt = blockInfo.nbt() != null ? blockInfo.nbt().copy() : null;
		// double-check for Connections and NodePos to avoid false dectection of Electricity Nodes e.g with Create´s tracks having 'Connections' as well
		if (nbt == null || !nbt.contains("Connections") || !nbt.contains("NodePos")) {
			return blockInfo;
		}
		BlockEntity existingBlockEntity = level.getBlockEntity(blockInfo.pos());
		if (existingBlockEntity == null) {
			return blockInfo;
		}
		CompoundTag existingNbt = existingBlockEntity.saveWithoutMetadata();
		if (!existingNbt.contains("Connections") || !existingNbt.contains("NodePos")) {
			return blockInfo;
		}
		LogManager.getLogger()
				.warn("Electricity Merge Processor applies to " + blockInfo.state().toString() + " at pos " + blockInfo.pos().toString() + ", with old block being " + level.getBlockState(blockInfo.pos()).toString() + " with nbt " + existingNbt);
		List<Long> combinedConnections = new ArrayList();
		long[] newConnections = nbt.getLongArray("Connections");
		for (long connection : newConnections) {
			combinedConnections.add(connection);
		}
		long[] existingConnections = existingNbt.getLongArray("Connections");
		BlockPos existingNodePos = BlockPos.of(existingNbt.getLong("NodePos"));
		BlockPos newNodePos = BlockPos.of(nbt.getLong("NodePos"));
		
		for (long connection : existingConnections) {
			BlockPos offset = BlockPos.of(connection).subtract(existingNodePos);
			combinedConnections.add(newNodePos.offset(offset).asLong());
		}
		nbt.putLongArray("Connections", combinedConnections);;
		return new StructureTemplate.StructureBlockInfo(blockInfo.pos(), blockInfo.state(), nbt);
	}

	@Override
	protected StructureProcessorType<?> getType() {
		return net.mesomods.mesophilscities.ModStructureProcessors.ELECTRICITY_MERGE_PROCESSOR.get();
	}
}
