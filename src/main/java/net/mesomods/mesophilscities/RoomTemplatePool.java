package net.mesomods.mesophilscities;

import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.util.RandomSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.Util;

import java.util.function.Function;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.Codec;
import com.mojang.datafixers.util.Either;
import org.checkerframework.checker.initialization.qual.Initialized;

public class RoomTemplatePool {
	public final List<RoomTemplatePoolElement> elementList;
	private final Optional<Boolean> optionalAddParentProcessors;
	private final Optional<Boolean> optionalReplaceExistingBlocks;
	private final List<ResourceLocation> processorList;
	private static final Codec<List<ResourceLocation>> PROCESSORS_CODEC = Codec.either(ResourceLocation.CODEC, ResourceLocation.CODEC.listOf()).xmap(e -> e.map(List::of, Function.identity()),
			list -> list.size() == 1 ? Either.left(list.get(0)) : Either.right(list));
	public static final Codec<RoomTemplatePool> CODEC = RecordCodecBuilder.create(codec -> {
		return codec
				.group(RoomTemplatePoolElement.CODEC.listOf().fieldOf("elements").forGetter((a) -> a.elementList), PROCESSORS_CODEC.optionalFieldOf("processors").forGetter((a) -> Optional.ofNullable(a.processorList)),
						Codec.BOOL.optionalFieldOf("add_parent_processors").forGetter((a) -> a.optionalAddParentProcessors), Codec.BOOL.optionalFieldOf("replace_existing_blocks").forGetter((a) -> a.optionalReplaceExistingBlocks))
				.apply(codec, RoomTemplatePool::new);
	});

	public RoomTemplatePool(List<RoomTemplatePoolElement> e, Optional<List<ResourceLocation>> p, Optional<Boolean> a, Optional<Boolean> b) {
		this.elementList = e;
		this.processorList = p.orElse(List.of());
		this.optionalAddParentProcessors = a;
		this.optionalReplaceExistingBlocks = b;
	}

	public RoomTemplatePoolElement getRandomTemplate(RandomSource random, ServerLevel level) {
		int elementCount = elementList.size();
		int[] cumulativeWeights = new int[elementCount];
		for (int i = 0; i < elementCount; i++) {
			RoomTemplatePoolElement element = elementList.get(i);
			int previousWeight = (i == 0) ? 0 : cumulativeWeights[i - 1];
			cumulativeWeights[i] = element.getWeight() + previousWeight;
		}
		int r = random.nextInt(cumulativeWeights[elementCount - 1]);
		for (int i = 0; i < elementCount; i++) {
			if (cumulativeWeights[i] > r) {
				RoomTemplatePoolElement element = elementList.get(i);
				element.addPoolArguments(processorList, optionalAddParentProcessors, optionalReplaceExistingBlocks, level);
				return element;
			}
		}
		return null;
	}
}

class RoomTemplatePoolElement {
	private final ResourceLocation location;
	private StructureTemplate template = null;
	private final List<ResourceLocation> processorList;
	private final List<StructureProcessor> processors = new ArrayList();
	private final int weight;
	private final Optional<Boolean> optionalAddParentProcessors;
	private boolean addParentProcessors;
	private final Optional<Boolean> optionalReplaceExistingBlocks;
	private boolean replaceExistingBlocks;
	private boolean initialized;
	private static final Codec<List<ResourceLocation>> PROCESSORS_CODEC = Codec.either(ResourceLocation.CODEC, ResourceLocation.CODEC.listOf()).xmap(e -> e.map(List::of, Function.identity()),
			list -> list.size() == 1 ? Either.left(list.get(0)) : Either.right(list));
	public static final Codec<RoomTemplatePoolElement> CODEC = RecordCodecBuilder.create(codec -> {
		return codec.group(ResourceLocation.CODEC.fieldOf("location").forGetter((a) -> a.location), PROCESSORS_CODEC.optionalFieldOf("processors").forGetter((a) -> Optional.ofNullable(a.processorList)),
				Codec.intRange(1, 1000).optionalFieldOf("weight").forGetter((a) -> Optional.ofNullable(a.weight)), Codec.BOOL.optionalFieldOf("add_parent_processors").forGetter((a) -> a.optionalAddParentProcessors),
				Codec.BOOL.optionalFieldOf("replace_existing_blocks").forGetter((a) -> a.optionalReplaceExistingBlocks)).apply(codec, RoomTemplatePoolElement::new);
	});

	public RoomTemplatePoolElement(ResourceLocation t, Optional<List<ResourceLocation>> p, Optional<Integer> w, Optional<Boolean> a, Optional<Boolean> b) {
		this.location = t;
		this.processorList = p.orElse(null);
		this.weight = w.orElse(1);
		this.optionalAddParentProcessors = a;
		this.optionalReplaceExistingBlocks = b;
		this.initialized = false;
	}

	public int getWeight() {
		return this.weight;
	}

	public List<StructureTemplate.StructureBlockInfo> getShuffledJigsawBlocks(Rotation rotation, RandomSource random) {
		ObjectArrayList<StructureTemplate.StructureBlockInfo> list = this.template.filterBlocks(BlockPos.ZERO, (new StructurePlaceSettings()).setRotation(rotation), Blocks.JIGSAW, true);
		Util.shuffle(list, random);
		return list;
	}

	public StructureTemplate getTemplate() {
		return this.template;
	}

	public List<StructureProcessor> getProcessors() {
		return processors;
	}

	public boolean shouldUseParentProcessors() {
		return addParentProcessors;
	}

	public boolean shouldReplaceExistingBlocks() {
		return replaceExistingBlocks;
	}

	public void addPoolArguments(List<ResourceLocation> processorList2, Optional<Boolean> optional, Optional<Boolean> optional2, ServerLevel level) {
		if (this.initialized) {
			return;
		}
		RegistryAccess regAccess = level.registryAccess();
		StructureTemplateManager structureManager = level.getServer().getStructureManager();
		this.template = structureManager.getOrCreate(this.location);
		if (processorList != null) {
			for (ResourceLocation listLocation : processorList) {
				StructureProcessorList list = regAccess.registryOrThrow(Registries.PROCESSOR_LIST).get(listLocation);
				for (StructureProcessor processor : list.list()) {
					processors.add(processor);
				}
			}
		}
		if (processorList2 != null) {
			for (ResourceLocation listLocation : processorList2) {
				StructureProcessorList list = regAccess.registryOrThrow(Registries.PROCESSOR_LIST).get(listLocation);
				for (StructureProcessor processor : list.list()) {
					processors.add(processor);
				}
			}
		}
		if (optionalAddParentProcessors.isPresent()) {
			addParentProcessors = optionalAddParentProcessors.get();
		} else if (optional.isPresent()) {
			addParentProcessors = optional.get();
		} else {
			addParentProcessors = processors.isEmpty() ? true : false;
		}
		if (optionalReplaceExistingBlocks.isPresent()) {
			replaceExistingBlocks = optionalReplaceExistingBlocks.get();
		} else if (optional2.isPresent()) {
			replaceExistingBlocks = optional2.get();
		} else {
			replaceExistingBlocks = true;
		}
		this.initialized = true;
	}
}
