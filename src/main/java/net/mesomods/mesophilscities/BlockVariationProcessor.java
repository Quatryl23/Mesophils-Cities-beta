package net.mesomods.mesophilscities;

import org.apache.logging.log4j.LogManager;

import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelReader;
import net.minecraft.util.RandomSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Holder;
import net.minecraft.core.BlockPos;

import net.mesomods.mesophilscities.init.MesophilsCitiesModBlocks;

import javax.annotation.Nullable;

import java.util.stream.Collectors;
import java.util.Optional;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.Codec;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.world.level.block.Blocks;

	

public class BlockVariationProcessor extends StructureProcessor {
	private final List<VariationGroup> variationGroups;
	private static BlockPos lastPivotPos = null;
	private static BlockPos processingStartPos = null;
	private static BlockPos processedPos = null;
	private Map<Block, Block> replaceMap = new HashMap();
	private final boolean createLoaded = net.mesomods.mesophilscities.LoadedModsCache.CREATE;



	public static final Codec<BlockVariationProcessor> CODEC = RecordCodecBuilder.create((codec) -> {
		return codec.group(
			VariationGroup.CODEC.listOf().fieldOf("variation_groups").forGetter((a) -> a.variationGroups)).apply(codec, BlockVariationProcessor::new);
	});

	public BlockVariationProcessor(List<VariationGroup> vG) {
		this.variationGroups = vG;
	}
	
		
	/*public static final Codec<BlockVariationProcessor> CODEC = RecordCodecBuilder.create((codec) -> {
		return codec.group(
			RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("variants").forGetter((a) -> a.variationsSet),
			RegistryCodecs.homogeneousList(Registries.BLOCK).optionalFieldOf("inputs").forGetter((b) -> b.optionalInputsSet),
			Codec.list(RegistryCodecs.homogeneousList(Registries.BLOCK)).optionalFieldOf("linked_variants").forGetter((f) -> f.optionalLinkedVariantsSet),
			Codec.list(RegistryCodecs.homogeneousList(Registries.BLOCK)).optionalFieldOf("linked_inputs").forGetter((f) -> f.optionalLinkedInputsSet),
			Codec.floatRange(0.0F, 1.0F).optionalFieldOf("variation_chance").forGetter((c) -> c.optionalVariationChance),
			Codec.floatRange(0.0F, 1.0F).optionalFieldOf("ignore_chance").forGetter((d) -> d.optionalIgnoreChance),
			Codec.BOOL.optionalFieldOf("avoid_duplicates").forGetter((e) -> e.optionalAvoidDuplicates)
			).apply(codec, BlockVariationProcessor::new);
	});
	private HolderSet<Block> variationsSet;
	private Optional<HolderSet<Block>> optionalInputsSet;
	private Optional<List<HolderSet<Block>>> optionalLinkedVariantsSet;
	private Optional<Float> optionalVariationChance;
	private Optional<Float> optionalIgnoreChance;
	private Optional<Boolean> optionalAvoidDuplicates;
	
	public BlockVariationProcessor(HolderSet<Block> variationsField, Optional<HolderSet<Block>> inputsField, Optional<List<HolderSet<Block>>> linkedVariantsField, Optional<Float> variationChanceField, Optional<Float> ignoreChanceField, Optional<Boolean> avoidDuplicatesField) {
		this.variationsSet = variationsField;
		this.optionalInputsSet = inputsField;
		this.optionalLinkedVariantsSet = linkedVariantsField;
		this.optionalVariationChance = variationChanceField;
		this.optionalIgnoreChance = ignoreChanceField;
		this.optionalAvoidDuplicates = avoidDuplicatesField;
		
	}*/

	@Nullable
	@Override
	public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos buggedPos, BlockPos pivot, StructureTemplate.StructureBlockInfo original, StructureTemplate.StructureBlockInfo blockInfo, StructurePlaceSettings settings, StructureTemplate template) {
		BlockPos pos = blockInfo.pos();
		RandomSource randomsource = settings.getRandom(blockInfo.pos());
		/*if (!pivot.equals(lastPivotPos)) {
			lastPivotPos = pivot;
			processingStartPos = pos;
			replaceMap = new HashMap();
		}		
		if (pos.equals(processingStartPos)) {
			this.newVariants(randomsource);
		}
		if (pos.equals(processedPos)) {
			return blockInfo;
		}
		
		processedPos = pos; */
		Block block = blockInfo.state().getBlock();
		StructureTemplate.StructureBlockInfo blockVariation;
		if (replaceMap.containsKey(block)) {
			blockVariation = new StructureTemplate.StructureBlockInfo(blockInfo.pos(), replaceMap.get(block).withPropertiesOf(blockInfo.state()), blockInfo.nbt());
		} else if (block == MesophilsCitiesModBlocks.TEMPORARY_GLASS.get()) {// && (output.containsKey(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockInfo.nbt().getCompound("ForgeData").getString("window")))) || output.containsKey(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockInfo.nbt().getCompound("ForgeData").getString("wall")))))) {
			if (blockInfo.nbt() != null) {
				CompoundTag blockNbt = blockInfo.nbt();
				Block windowBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockInfo.nbt().getCompound("ForgeData").getString("window")));
				Block wallBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockInfo.nbt().getCompound("ForgeData").getString("wall")));
				if (replaceMap.containsKey(windowBlock)) blockNbt.getCompound("ForgeData").putString("window", ForgeRegistries.BLOCKS.getKey(replaceMap.get(windowBlock)).toString());
				if (replaceMap.containsKey(wallBlock)) blockNbt.getCompound("ForgeData").putString("wall", ForgeRegistries.BLOCKS.getKey(replaceMap.get(wallBlock)).toString());
				blockVariation = new StructureTemplate.StructureBlockInfo(blockInfo.pos(), blockInfo.state(), blockNbt);
				} else {
					blockVariation = blockInfo;
				}
		} else if (block == Blocks.JIGSAW) {
			if (blockInfo.nbt() != null) {
				CompoundTag blockNbt = blockInfo.nbt();
				Block finalState = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockNbt.getString("final_state")));
				if (replaceMap.containsKey(finalState)) {
					 blockNbt.putString("final_state", ForgeRegistries.BLOCKS.getKey(replaceMap.get(finalState)).toString());
					 blockVariation = new StructureTemplate.StructureBlockInfo(blockInfo.pos(), blockInfo.state(), blockNbt);
				} else {
					blockVariation = blockInfo;
				}
			} else {
				blockVariation = blockInfo;
			}
		} else if (createLoaded && ForgeRegistries.BLOCKS.getHolder(block).get().is(MesophilsCitiesModTags.COPYCAT_BLOCK) && blockInfo.nbt() != null && replaceMap.containsKey(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockInfo.nbt().getCompound("Material").getString("Name"))))) {
			if (blockInfo.nbt() != null) {
				Block material = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockInfo.nbt().getCompound("Material").getString("Name")));
				CompoundTag blockNbt = blockInfo.nbt();
				String blockString = ForgeRegistries.BLOCKS.getKey(replaceMap.get(material)).toString();
				blockInfo.nbt().getCompound("Material").putString("Name", blockString);
				String itemString = ForgeRegistries.ITEMS.getKey(replaceMap.get(material).asItem()).toString();
				blockInfo.nbt().getCompound("Item").putString("id", itemString);
				blockInfo.nbt().getCompound("Item").putByte("Count", (byte) 1);
				blockVariation = new StructureTemplate.StructureBlockInfo(blockInfo.pos(), blockInfo.state(), blockNbt);
			} else {
				blockVariation = blockInfo;
			}
		} else {
			blockVariation = blockInfo;
		}
		return blockVariation;
	}

	public Map<Block, Block> getReplaceMap() {
		return replaceMap;
	}

	public String toString() {
		String s = "Block Variation Processor with ReplaceMap:\n";
		for (Map.Entry<Block, Block> entry : this.getReplaceMap().entrySet()) {
			s += ("Block " + entry.getKey().toString() + " becomes " + entry.getValue().toString() + "\n");
		}
		return s;
	}
	public void initalizeVariants(Map<Block,Block> targetReplaceMap, RandomSource random) {
		this.initalizeVariants(targetReplaceMap, random, true);
	}

	//should be called before the processor is added to StructurePlaceSettings; Otherwise the processor will not work
	public void initalizeVariants(Map<Block,Block> targetReplaceMap, RandomSource random, boolean clearFirst) {
		if (targetReplaceMap == null) 
			targetReplaceMap = replaceMap;
		if (clearFirst)
			targetReplaceMap.clear();
		for (VariationGroup variationGroup : variationGroups) {
			if (random.nextFloat() < variationGroup.getIgnoreChance()) {
    			continue;
				}
			float variationChance = variationGroup.getVariationChance();
			boolean avoidDuplicates = variationGroup.shouldAvoidDuplicates();
			List<Variation> variations = variationGroup.getVariations();
			List<List<Block>> inputLists = new ArrayList<>();
			List<List<Block>> outputLists = new ArrayList<>();
			int maxInputSize = 0;
			int maxOutputSize = 0;
			int variationsCount = variations.size();
			for (Variation variation : variations) {
				List<Block> inputList = variation.getInputList();
				maxInputSize = Math.max(maxInputSize, inputList.size());
				inputLists.add(inputList);
				List<Block> outputList = variation.getOutputList();
				maxOutputSize = Math.max(maxOutputSize, outputList.size());
				outputLists.add(outputList);
			}
			if (maxOutputSize == 0) {
				LogManager.getLogger().warn("Found invalid processor of type mesophils_cities:block_variation");
				continue;
			}
			List<Integer> weights = variationGroup.getWeights().isPresent() ? variationGroup.getWeights().get() : null;
			List<Integer> cumulativeWeights = new ArrayList();
			if (weights != null) {
				if (weights.size() > maxOutputSize) {
					while (weights.size() > maxOutputSize) {
						weights.remove(weights.size() - 1);
					}
				}
				if (weights.size() < maxOutputSize) {
					while (weights.size() < maxOutputSize) {
						weights.add(1);
					}
				}
				cumulativeWeights = this.getCumulativeWeights(weights);
			}
			List<Integer> unusedElements = new ArrayList<>();
			for (int i = 0;  i < maxInputSize; i++) {
				if (variationChance < random.nextFloat()) continue;
				int r;
				if (avoidDuplicates) {
					if (unusedElements.isEmpty()) {
						for (int k = 0; k < maxOutputSize; k++) {
					unusedElements.add(k);
				}
					}
					int s = (weights == null) ? random.nextInt(unusedElements.size()) : this.getRandomWithWeights(cumulativeWeights, random);
					r = unusedElements.get(s);
					unusedElements.remove(s);
					if (weights != null) {
						weights.remove(s);
						cumulativeWeights = this.getCumulativeWeights(weights);
					}
				} else {
					r = (weights == null) ? random.nextInt(maxOutputSize) : this.getRandomWithWeights(cumulativeWeights, random);
				}
				for (int j = 0; j < variationsCount; j++) {
					List<Block> inputs = inputLists.get(j);
					List<Block> outputs = outputLists.get(j);
					if (inputs.size() > i && outputs.size() > r) {
						targetReplaceMap.put(inputs.get(i), outputs.get(r));
					}
				}
			}
		}
		//LogManager.getLogger().warn("The block variations map added at "+ processingStartPos.toString() +"looks like this: "+ replaceMap.toString());
	}

	public void addToProcessor(BlockVariationProcessor combinedProcessor, RandomSource random) {
		this.initalizeVariants(combinedProcessor.getReplaceMap(), random, false);
	}
		
		/*List<Block> variations = variationsSet.stream().map(Holder::value).collect(Collectors.toList()));
		List<Block> inputs = optionalInputsSet.isPresent() ? optionalInputsSet.get().stream().map(Holder::value).collect(Collectors.toList())) : variations;
		List<List<Block>> linkedVariants = new ArrayList<>();
		if (optionalLinkedVariantsSet.isPresent()) {
			for (HolderSet<Block> holderSet : optionalLinkedVariantsSet.get()) {
				linkedVariants.add(holderSet.stream().map(Holder::value).collect(Collectors.toList()));
			}
		}
		float variationChance = optionalVariationChance.orElse(1.0F);
		float ignoreChance = optionalIgnoreChance.orElse(0.0F);
		boolean avoidDuplicates = optionalAvoidDuplicates.orElse(false);
		
		
		if (ignoreChance > random.nextFloat())
			return;
		if (avoidDuplicates) {
			List<Block> unusedVariations = variations;
			for (Block input : inputs) {
				boolean variationSkip = variationChance < random.nextFloat();
				if (unusedVariations.isEmpty())
				unusedVariations = variations;
				int i = random.nextInt(unusedVariations.size());
				replaceMap.put(block, unusedVariations.get(i));
				if (!linkedVariants.isEmpty()) {
					for (List<Block> linkedVariant : linkedVariants) {
						replaceMap.put()
					}
				}
				for (Block block : input) {
					j++;
					if (replaceMap.containsKey(input))
					continue;
					if (variationSkip) {							
						replaceMap.put(block, block);
						} else {
							if (j >= unusedVariations.get(i).size()) continue;
							replaceMap.put(block, unusedVariations.get(i).get(j));
						}
				}
				unusedVariations.remove(i);
			}
		} else {
			int size = variations.size();
			for (List<Block> input : inputs) {
			boolean variationSkip = variationChance < random.nextFloat();
			int i = random.nextInt(size);
			int j = -1;
			for (Block block: input){
				j++;
				if (replaceMap.containsKey(block))
					continue;
				if (variationSkip) {
					replaceMap.put(block, block);
				} else {
					if (j >= variations.get(i).size()) continue;
					replaceMap.put(block, variations.get(i).get(j));
				}
			}
				
			}
		}
	}*/
	private List<Integer> getCumulativeWeights(List<Integer> weights) {
		int cumulativeSum = 0;
		List<Integer> cumulativeSums = new ArrayList();
		for (int weight : weights) {
			cumulativeSum += weight;
			cumulativeSums.add(cumulativeSum);
			}
			return cumulativeSums;
	}
	private int getRandomWithWeights(List<Integer> cumulativeWeights, RandomSource random) {
		int r = random.nextInt(cumulativeWeights.get(cumulativeWeights.size() - 1));
		for (int i=0; i<cumulativeWeights.size(); i++) {
			if (r <= cumulativeWeights.get(i)) {
				return i;
			}
		}
		return 0;
	}

	protected StructureProcessorType<?> getType() {
		return ModStructureProcessors.BLOCK_VARIATION_PROCESSOR.get();
	}
}

class Variation {
	private final Optional<HolderSet<Block>> inputs;
	private final HolderSet<Block> outputs;
	public Variation(Optional<HolderSet<Block>> i, HolderSet<Block> o) {
		this.inputs = i;
		this.outputs = o;
	}
	public List<Block> getInputList() {
		return inputs.isPresent() ? inputs.get().stream().map(Holder::value).collect(Collectors.toList()) : outputs.stream().map(Holder::value).collect(Collectors.toList());
	}
	public List<Block> getOutputList() {
		return outputs.stream().map(Holder::value).collect(Collectors.toList());
	}
	public Optional<HolderSet<Block>> getInputs() {
		return inputs;
	}
	public HolderSet<Block> getOutputs() {
		return outputs;
	}

	public static final Codec<Variation> CODEC = RecordCodecBuilder.create((codec) -> {
		return codec.group(RegistryCodecs.homogeneousList(Registries.BLOCK).optionalFieldOf("inputs").forGetter(Variation::getInputs),
            RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("outputs").forGetter(Variation::getOutputs)
        ).apply(codec, Variation::new);
	});
}

class VariationGroup {
	private final Optional<Float> ignoreChance;
	private final Optional<Float> variationChance;
	private final Optional<Boolean> avoidDuplicates;
	private final List<Variation> variations;
	private final Optional<List<Integer>> weights;

	public float getIgnoreChance() {
		return ignoreChance.isPresent() ? ignoreChance.get() : 0.0F;
	}

	public float getVariationChance() {
		return variationChance.isPresent() ? variationChance.get() : 1.0F;
	}

	public boolean shouldAvoidDuplicates() {
		return avoidDuplicates.isPresent() ? avoidDuplicates.get() : false;
	}

	public List<Variation> getVariations() {
		return variations;
	}

	public Optional<List<Integer>> getWeights() {
		List<Integer> immutableWeights = weights.orElse(null);
		List<Integer> mutableWeights = new ArrayList();
		if (immutableWeights == null) {
			mutableWeights = null;
		} else {
			mutableWeights.addAll(immutableWeights);
		}
		return Optional.ofNullable(mutableWeights);
	}

	public VariationGroup(Optional<Float> iC, Optional<Float> vC, Optional<Boolean> aD, List<Variation> v, Optional<List<Integer>> w) {
		this.ignoreChance = iC;
		this.variationChance = vC;
		this.avoidDuplicates = aD;
		this.variations = v;
		this.weights = w;
	}

	public static final Codec<VariationGroup> CODEC = RecordCodecBuilder.create(codec -> {
	return codec.group(
            Codec.floatRange(0.0F, 1.0F).optionalFieldOf("ignore_chance").forGetter((a) -> a.ignoreChance),
            Codec.floatRange(0.0F, 1.0F).optionalFieldOf("variation_chance").forGetter((a) -> a.variationChance),
            Codec.BOOL.optionalFieldOf("avoid_duplicates").forGetter((a) -> a.avoidDuplicates),
            Variation.CODEC.listOf().fieldOf("variations").forGetter((a) -> a.variations),
            Codec.INT.listOf().optionalFieldOf("weights").forGetter((a) -> a.weights)
        ).apply(codec, VariationGroup::new);
	});
}
