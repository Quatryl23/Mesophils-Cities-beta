package net.mesomods.mesophilscities;

import org.apache.logging.log4j.LogManager;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.JigsawReplacementProcessor;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.LevelReader;
import net.minecraft.util.RandomSource;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Vec3i;
import net.minecraft.core.Holder;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.minecraft.core.registries.BuiltInRegistries;

@Mod.EventBusSubscriber
public class CityStructureFeature extends Feature<NoneFeatureConfiguration> {
	public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, MesophilsCitiesMod.MODID);
	public static final RegistryObject<Feature<?>> CITY_STRUCTURE_FEATURE = REGISTRY.register("city_structure_feature", () -> new CityStructureFeature(NoneFeatureConfiguration.CODEC));

	public CityStructureFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		// place main template
		RandomSource random = context.random();
		WorldGenLevel worldGenLevel = context.level();
		BlockPos dataPos = context.origin().atY(18);
		BlockPos placePos = dataPos;
		BlockState dataHolderHouse = worldGenLevel.getBlockState(dataPos.atY(17));
		BlockState dataHolderMain = worldGenLevel.getBlockState(dataPos);
		boolean housePlaced = this.placeHouse(dataHolderHouse, placePos, worldGenLevel, dataPos, random, context);
		boolean stationPlaced = this.placeStation(dataHolderHouse, placePos, worldGenLevel, dataPos, random, context);
		boolean trainOrStreetPlaced = this.placeStreetOrTrain(dataHolderMain, placePos, worldGenLevel, dataPos, random, context);
		return housePlaced;
	}

	private boolean placeHouse(BlockState dataHolder, BlockPos placePos, WorldGenLevel worldGenLevel, BlockPos dataPos, RandomSource random, FeaturePlaceContext<NoneFeatureConfiguration> context) {
		TagKey<Block> HouseTag = TagKey.create(Registries.BLOCK, new ResourceLocation("mesophils_cities", "house_direction"));
		TagKey<Block> PathTag = TagKey.create(Registries.BLOCK, new ResourceLocation("mesophils_cities", "path_replace"));
		for (int k = -16; k < 32; k += 4) {
			for (int l = -16; l < 32; l += 4) {
				BlockPos checkPos = placePos.offset(k, 0, l);
				if (worldGenLevel.getBlockState(checkPos).is(PathTag)) {
					getPlaceTemplate("path", "004004", "", random, worldGenLevel, checkPos, 4, 4, context);
				}
			}
		}
		if (dataHolder.is(HouseTag)) {
			TagKey<Block> HouseIndicator = TagKey.create(Registries.BLOCK, new ResourceLocation("mesophils_cities", "house_indicator"));
			TagKey<Block> PortalHouseTag = TagKey.create(Registries.BLOCK, new ResourceLocation("mesophils_cities", "portal_house_direction"));
			HouseLoop : for (int i = -16; i < 0; i += 4) {
				for (int j = -16; j < 0; j += 4) {
					BlockPos checkPos = placePos.offset(i, 0, j);
					if (worldGenLevel.getBlockState(checkPos).is(HouseIndicator)) {
						placePos = checkPos;
						BlockState sizeXDataHolder = worldGenLevel.getBlockState(dataPos.atY(17).east());
						BlockState sizeZDataHolder = worldGenLevel.getBlockState(dataPos.atY(17).south());
						int sizeX = this.getHouseSize(sizeXDataHolder);
						int sizeZ = this.getHouseSize(sizeZDataHolder);
						String subtype = "";
						String direction = "";
						if (dataHolder.is(PortalHouseTag)) {
							if (sizeZ < sizeX) {
								subtype = "portal_house_x";
							} else {
								subtype = "portal_house_z";
							}
							direction = getPortalHouseDirection(dataHolder);
						} else {
							subtype = "0" + sizeX + "0" + sizeZ;
							direction = getHouseDirection(dataHolder);
						}
						if (sizeX == 0 || sizeZ == 0) {
							LogManager.getLogger().warn("Found City Structure Feature of type 'house' with incorrect size '" + subtype + "' at " + placePos.toString() + ". Placement will be skipped");
							return false;
						}
						// System.out.println(" [CityStructureFeature Debug] Trying to place City Structure Feature of type " + TemplateType + " at " + placePos.toString() + "");
						getPlaceTemplate("house", subtype, direction, random, worldGenLevel, placePos, sizeX, sizeZ, context, false);
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean placeStation(BlockState dataHolder, BlockPos placePos, WorldGenLevel worldGenLevel, BlockPos dataPos, RandomSource random, FeaturePlaceContext<NoneFeatureConfiguration> context) {
		TagKey<Block> StationTag = TagKey.create(Registries.BLOCK, new ResourceLocation("mesophils_cities", "stations"));
		if (dataHolder.is(StationTag)) {
			TagKey<Block> StationXTag = TagKey.create(Registries.BLOCK, new ResourceLocation("mesophils_cities", "station_x_a"));
			TagKey<Block> StationZTag = TagKey.create(Registries.BLOCK, new ResourceLocation("mesophils_cities", "station_z_a"));
			BlockState sizeXDataHolder = worldGenLevel.getBlockState(dataPos.atY(17).east());
			BlockState sizeZDataHolder = worldGenLevel.getBlockState(dataPos.atY(17).south());
			int posX = (this.getHouseSize(sizeXDataHolder) / 4) - 3;
			int posZ = (this.getHouseSize(sizeZDataHolder) / 4) - 3;
			String direction;
			if (dataHolder.is(StationXTag)) {
				direction = "w";
			} else {
				direction = "n";
			}
			String piecePos = posX + "-" + posZ;
			// System.out.println(" [CityStructureFeature Debug] Trying to place City Structure Feature of type " + TemplateType + " at " + placePos.toString() + "");
			this.getPlaceTemplate("station", piecePos, direction, random, worldGenLevel, placePos.offset(-16, 0, -16), 0, 0, context, true);
			return true;
		}
		return false;
	}

	private boolean placeStreetOrTrain(BlockState dataHolder, BlockPos placePos, WorldGenLevel worldGenLevel, BlockPos dataPos, RandomSource random, FeaturePlaceContext<NoneFeatureConfiguration> context) {
		if (dataHolder.toString().contains("_")) {
			String type = "";
			String subtype = "";
			String direction = "";
			String[] subtypeAndDirection = {"", ""};
			TagKey<Block> TrainTag = TagKey.create(Registries.BLOCK, new ResourceLocation("minecraft", "wool"));
			TagKey<Block> StreetTag = TagKey.create(Registries.BLOCK, new ResourceLocation("mesophils_cities", "street_marker"));
			TagKey<Block> BridgeTag = TagKey.create(Registries.BLOCK, new ResourceLocation("mesophils_cities", "bridge_marker"));
			String color = dataHolder.toString().substring(16, dataHolder.toString().indexOf('_'));
			if (dataHolder.is(TrainTag)) {
				type = "train";
				subtypeAndDirection = getTrainStreetType(color);
				subtype = subtypeAndDirection[0];
				direction = subtypeAndDirection[1];
			} else if (dataHolder.is(StreetTag)) {
				type = "street";
				if (dataHolder.is(BridgeTag)) {
					subtypeAndDirection = getBridgeType(dataHolder.toString().substring(16, dataHolder.toString().indexOf('}')));
				} else {
					subtypeAndDirection = getTrainStreetType(color);
				}
				subtype = subtypeAndDirection[0];
				direction = subtypeAndDirection[1];
				if (subtype.equals("bridge")) {
					type = "bridge";
					subtype = subtypeAndDirection[1].substring(2);
					direction = subtypeAndDirection[1].substring(0, 1);
				}
			} else {
				return false;
			}
			getPlaceTemplate(type, subtype, direction, random, worldGenLevel, placePos.offset(-16, 0, -16), 48, 48, context);
			return true;
		}
		return false;
	}

	private void getPlaceTemplate(String type, String subtype, String direction, RandomSource random, WorldGenLevel worldGenLevel, BlockPos placePos, int sizeX, int sizeZ, FeaturePlaceContext<NoneFeatureConfiguration> context) {
		this.getPlaceTemplate(type, subtype, direction, random, worldGenLevel, placePos, sizeX, sizeZ, context, true);
	}

	private void getPlaceTemplate(String type, String subtype, String direction, RandomSource random, WorldGenLevel worldGenLevel, BlockPos placePos, int sizeX, int sizeZ, FeaturePlaceContext<NoneFeatureConfiguration> context, boolean ignoreAir) {
		ServerLevel level = worldGenLevel.getLevel();
		//LogManager.getLogger().info("Trying to place template with type " + type + " subtype " + subtype + " direction " + direction);
		CityStructureTemplatePiece templatePiece = CityTemplatePoolLoader.getRandomPiece(type, subtype, direction, random, placePos);
		if (templatePiece == null) {
			LogManager.getLogger().warn("Couldn´t find city template with type " + type + " subtype " + subtype + " direction " + direction);
			return;
		}
		if (templatePiece.location == null) {
			return;
		}
		// Load the structure template
		StructureTemplateManager structureManager = level.getServer().getStructureManager();
		StructureTemplate template = structureManager.getOrCreate(templatePiece.location);
		if (template.getSize() == new Vec3i(0, 0, 0)) {
			LogManager.getLogger().warn("Couldn´t find Structure nbt file at " + templatePiece.location);
		}
		if (sizeX == 0 || sizeZ == 0) {
			Vec3i templateSize = template.getSize();
			sizeX = templateSize.getX();
			sizeZ = templateSize.getZ();
		}
		Rotation rotation = templatePiece.rotation;
		//rotation = Rotation.NONE;
		switch (rotation) {
			case CLOCKWISE_90 :
				placePos = placePos.offset(sizeX - 1, 0, 0);
				break;
			case COUNTERCLOCKWISE_90 :
				placePos = placePos.offset(0, 0, sizeZ - 1);
				break;
			case CLOCKWISE_180 :
				placePos = placePos.offset(sizeX - 1, 0, sizeZ - 1);
				break;
			default :
		}
		placePos = placePos.atY(templatePiece.height);
		StructurePlaceSettings placeSettings = new StructurePlaceSettings().setRotation(rotation).setMirror(Mirror.NONE).setRandom(random).setIgnoreEntities(false); //not .addProcessor(BlockIgnoreProcessor.AIR)
		if (templatePiece.processors != null) {
			List<StructureProcessor> processors = new ArrayList();
			for (ResourceLocation processorListLocation : templatePiece.processors) {
			//LogManager.getLogger().warn("[x:" + placePos.getX() + ", z:" + placePos.getZ() + "] [" + subtype + "] Using processor at location: " + processorListLocation.toString());
				StructureProcessorList processorList = level.registryAccess().registryOrThrow(Registries.PROCESSOR_LIST).get(processorListLocation);
				if (processorList == null) {
					LogManager.getLogger().warn("Couldn´t find Processor List json file at " + processorListLocation);
					continue;
				}
				processors.addAll(processorList.list());
			}
			this.addProcessorsAndCombineVariationProcessors(placeSettings, processors, random);
		}
		StructurePlaceSettings placeSettingsFinal = placeSettings.copy();
		placeSettingsFinal.addProcessor(new UnprotectedBlockProcessor(MesophilsCitiesModTags.CITY_WORLDGEN_REPLACEABLE));
		template.placeInWorld(worldGenLevel, placePos, placePos, placeSettingsFinal, random, 4);
		placeJigsawChildren(template, worldGenLevel, level, random, structureManager, placePos, placeSettings);
		/*
		Holder<StructureTemplatePool> poolHolder = Holder.direct(new StructureTemplatePool(Holder.direct(StructureTemplatePool.EMPTY), List.of(Pair.of(StructurePoolElement.single(), 1)), StructureTemplatePool.Projection.byName("rigid")));
		JigsawPlacement.generateJigsaw(level, poolHolder, resoure, 10, p_227207_, p_227209_)
		*/
	}

	private void placeJigsawChildren(StructureTemplate parentTemplate, WorldGenLevel worldGenLevel, ServerLevel level, RandomSource random, StructureTemplateManager manager, BlockPos placePos,
			StructurePlaceSettings parentSettings) {
		for (StructureTemplate.StructureBlockInfo jigsawBlock : parentTemplate.filterBlocks(placePos, parentSettings, Blocks.JIGSAW, true)) {
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
						
						/*String debugMessage = "Placing jigsaw room at " + targetJigsawPos + " with house pos " + placePos + " with processors ";
						for (StructureProcessor processor : settings.getProcessors()) {
							debugMessage += processor.toString();
						}
						LogManager.getLogger().warn(debugMessage);
						*/
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
	}

	private void addProcessorsAndCombineVariationProcessors(StructurePlaceSettings settings, List<StructureProcessor> list, RandomSource random) {
		BlockVariationProcessor firstVariationProcessor = null;
		for (StructureProcessor processor : list) {
			if (processor instanceof BlockVariationProcessor variationProcessor) {
				if (firstVariationProcessor == null) {
					variationProcessor.initalizeVariants(null, random);
					firstVariationProcessor = variationProcessor;
				} else {
					variationProcessor.addToProcessor(firstVariationProcessor, random);
					continue;
				}
			}
			settings.addProcessor(processor);
		}
	}

	private int getHouseSize(BlockState DataHolder) {
		switch (DataHolder.toString()) {
			case "Block{minecraft:dark_oak_log}[axis=y]" :
				return 12;
			case "Block{minecraft:spruce_log}[axis=y]" :
				return 16;
			case "Block{minecraft:oak_log}[axis=y]" :
				return 20;
			case "Block{minecraft:jungle_log}[axis=y]" :
				return 24;
			case "Block{minecraft:birch_log}[axis=y]" :
				return 28;
			case "Block{minecraft:acacia_log}[axis=y]" :
				return 32;
			default :
				LogManager.getLogger().warn("Invalid city data block found: " + DataHolder.toString());
		}
		return 0;
	}

	private String getHouseDirection(BlockState DataHolder) {
		switch (DataHolder.toString()) {
			case "Block{minecraft:iron_block}" :
				return "n";
			case "Block{minecraft:gold_block}" :
				return "s";
			case "Block{minecraft:diamond_block}" :
				return "w";
			case "Block{minecraft:emerald_block}" :
				return "e";
			default :
				LogManager.getLogger().warn("Invalid city data block found: " + DataHolder.toString());
		}
		return "";
	}

	private String getPortalHouseDirection(BlockState DataHolder) {
		switch (DataHolder.toString()) {
			case "Block{minecraft:raw_iron_block}" :
				return "n";
			case "Block{minecraft:raw_gold_block}" :
				return "s";
			case "Block{minecraft:lapis_block}" :
				return "w";
			case "Block{minecraft:raw_copper_block}" :
				return "e";
			default :
				LogManager.getLogger().warn("Invalid city data block found: " + DataHolder.toString());
		}
		return "";
	}

	private String[] getTrainStreetType(String color) {
		switch (color) {
			case "brown" :
				return new String[]{"crossing", ""};
			case "white" :
				return new String[]{"straight", "x"};
			case "black" :
				return new String[]{"straight", "z"};
			case "purple" :
				return new String[]{"curve", "n"};
			case "orange" :
				return new String[]{"curve", "e"};
			case "cyan" :
				return new String[]{"curve", "w"};
			case "lime" :
				return new String[]{"curve", "s"};
			case "red" :
				return new String[]{"branch", "n"};
			case "green" :
				return new String[]{"branch", "s"};
			case "blue" :
				return new String[]{"branch", "w"};
			case "yellow" :
				return new String[]{"branch", "e"};
		}
		return new String[]{"", ""};
	}

	private String[] getBridgeType(String color) {
		switch (color) {
			case "tinted_glass" :
				return new String[]{"bridge", "x/0-0"};
			case "pink_stained_glass" :
				return new String[]{"bridge", "x/1-0"};
			case "magenta_stained_glass" :
				return new String[]{"bridge", "x/2-0"};
			case "light_gray_stained_glass" :
				return new String[]{"bridge", "z/0-0"};
			case "gray_stained_glass" :
				return new String[]{"bridge", "z/0-1"};
			case "light_blue_stained_glass" :
				return new String[]{"bridge", "z/0-2"};
			case "red_mushroom_block" :
				return new String[]{"level_crossing", "x"};
			case "brown_mushroom_block" :
				return new String[]{"level_crossing", "z"};
		}
		return new String[]{"", ""};
	}
}
