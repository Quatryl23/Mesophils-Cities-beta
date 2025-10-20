package net.mesomods.mesophilscities;

import org.apache.logging.log4j.LogManager;

import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.JigsawReplacementProcessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.util.RandomSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.Holder;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import java.util.Optional;
import java.util.List;

import com.mojang.serialization.Codec;

public class RoomConnectProcessor extends StructureProcessor {
	public static final RoomConnectProcessor INSTANCE = new RoomConnectProcessor();
	public static final Codec<RoomConnectProcessor> CODEC = Codec.unit(() -> INSTANCE);
// not doing anything right now
	public RoomConnectProcessor() {
	}
/*
	@Override
	public List<StructureTemplate.StructureBlockInfo> finalizeProcessing(ServerLevelAccessor level, BlockPos pos1, BlockPos pos2, List<StructureTemplate.StructureBlockInfo> originalBlockInfos, List<StructureTemplate.StructureBlockInfo> blockInfos,
			StructurePlaceSettings templateSettings) {
		RandomSource random = templateSettings.getRandom(pos2);
		for (StructureTemplate.StructureBlockInfo blockInfo : blockInfos) {
			if (!blockInfo.state().is(Blocks.JIGSAW))
				continue;
			BlockState state = jigsawBlock.state();
			Direction dir = JigsawBlock.getFrontFacing(state);
			BlockPos connectPos = jigsawBlock.pos().relative(dir);
			ResourceKey<RoomTemplatePool> poolKey = ResourceKey.create(RoomTemplatePoolRegistry.ROOM_TEMPLATE_POOL, new ResourceLocation(jigsawBlock.nbt().getString("pool")));
			Optional<? extends Holder<RoomTemplatePool>> optional = level.registryAccess().registryOrThrow(RoomTemplatePoolRegistry.ROOM_TEMPLATE_POOL).getHolder(poolKey);
			if (optional.isEmpty()) {
				LogManager.getLogger().warn("Found invalid resource location '" + poolKey.location() + "' in a Jigsaw Block at: " + jigsawBlock.pos());
				continue;
			}
			RoomTemplatePoolElement element = optional.get().value().getRandomTemplate(random, level);
			StructureTemplate.StructureBlockInfo jigsaw2 = null;
			checkPlacements : for (Rotation rotation : Rotation.getShuffled(random)) {
				List<StructureTemplate.StructureBlockInfo> targetJigsaws = element.getShuffledJigsawBlocks(rotation, random);
				for (StructureTemplate.StructureBlockInfo targetJigsaw : targetJigsaws) {
					jigsaw2 = targetJigsaw;
					if (JigsawBlock.canAttach(jigsawBlock, targetJigsaw)) {
						BlockPos targetJigsawRelativePos = targetJigsaw.pos();
						BlockPos targetJigsawPos = connectPos.subtract(targetJigsawRelativePos);
						StructureTemplate template = element.getTemplate();
						StructurePlaceSettings settings = new StructurePlaceSettings().setRotation(rotation).addProcessor(JigsawReplacementProcessor.INSTANCE);
						if (!element.shouldReplaceExistingBlocks()) {
							settings.addProcessor(KeepExistingBlocksProcessor.INSTANCE);
						}
						List<StructureProcessor> processors = element.getProcessors();
						this.addProcessorsAndCombineVariationProcessors(settings, processors, random);
						if (element.shouldUseParentProcessors()) {
							parentSettings.getProcessors().forEach(settings::addProcessor);
						}
						settings.addProcessor(RoomConnectProcessor.INSTANCE);
						LogManager.getLogger().warn("Placing jigsaw room at " + targetJigsawPos + " with house pos " + placePos);
						List<StructureTemplate.StructureBlockInfo> processedChildrenTemplate = StructureTemplate.processBlockInfos(level, targetJigsawPos, targetJisgawPos, settings, p_74522_, template);
						template.placeInWorld(worldGenLevel, targetJigsawPos, targetJigsawPos, settings, random, 4);
						//PoolElementStructurePiece piece = new PoolElementStructurePiece(manager, element, targetJigsawPos, connectPos.getY(), rotation, BoundingBox.infinite());
						//piece.place(worldGenLevel, level.structureManager(), context.chunkGenerator(), random, BoundingBox.fromCorners(context.origin().offset(-16, 0, -16).atY(0), context.origin().offset(31, 320, 31).atY(320)), targetJigsawPos, true);
						break checkPlacements;
					}
				}
			}
			worldGenLevel.setBlock(jigsawBlock.pos(), JigsawReplacementProcessor.INSTANCE.processBlock(worldGenLevel, BlockPos.ZERO, BlockPos.ZERO, jigsawBlock, jigsawBlock, parentSettings).state(), 2);
			//if (jigsaw2 != null)
			//worldGenLevel.setBlock(connectPos, JigsawReplacementProcessor.INSTANCE.processBlock(worldGenLevel, BlockPos.ZERO, BlockPos.ZERO, jigsaw2, jigsaw2, parentSettings).state(), 2);
		}
		return p_278053_;
	}
*/
	protected StructureProcessorType<?> getType() {
		return ModStructureProcessors.ROOM_CONNECT_PROCESSOR.get();
	}
}
