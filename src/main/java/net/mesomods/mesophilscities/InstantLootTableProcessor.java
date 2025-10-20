package net.mesomods.mesophilscities;

import org.apache.logging.log4j.LogManager;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootDataManager;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.RandomSource;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Holder;
import net.minecraft.core.BlockPos;

import javax.annotation.Nullable;

import java.util.function.Consumer;
import java.util.List;
import java.util.ArrayList;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.Codec;

public class InstantLootTableProcessor extends StructureProcessor {
	public final HolderSet<Block> targetsSet;
	public final ResourceLocation lootTableLocation;
	public static final Codec<InstantLootTableProcessor> CODEC = RecordCodecBuilder.create((codec) -> {
		return codec.group(RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("targets").forGetter((a) -> a.targetsSet), ResourceLocation.CODEC.fieldOf("loot_table").forGetter((a) -> a.lootTableLocation)).apply(codec,
				InstantLootTableProcessor::new);
	});

	public InstantLootTableProcessor(HolderSet<Block> targetsSetArg, ResourceLocation lootTableLocationArg) {
		this.targetsSet = targetsSetArg;
		this.lootTableLocation = lootTableLocationArg;
	}

	@Nullable
	@Override
	public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos pos, BlockPos pivot, StructureTemplate.StructureBlockInfo original, StructureTemplate.StructureBlockInfo blockInfo, StructurePlaceSettings settings,
			StructureTemplate template) {
		Block block = blockInfo.state().getBlock();
		Holder<Block> holder = ForgeRegistries.BLOCKS.getHolder(blockInfo.state().getBlock()).get();
			if (targetsSet.contains(holder)) {
				if (level instanceof WorldGenRegion worldGenLevel) {
					ServerLevel serverLevel = worldGenLevel.getLevel();
					LootDataManager lootManager = serverLevel.getServer().getLootData();
					LootTable lootTable = lootManager.getLootTable(lootTableLocation);
					if (lootTable == null) {
						LogManager.getLogger().warn("Invalid Loot Table referenced: " + lootTableLocation.toString() + " could not be found.");
						return blockInfo;
					}
					LootParams lootParams = new LootParams.Builder(serverLevel).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(blockInfo.pos())).create(LootContextParamSets.CHEST);
					RandomSource random = settings.getRandom(blockInfo.pos());
					LootContext lootContext = new LootContext.Builder(lootParams).create(new ResourceLocation("mesophils_cities", "block_loot_" + blockInfo.pos().asLong()));
					// using a Consumer<ItemStack> because getRandomItems(LootContext) is private in the LootTable class
					List<ItemStack> lootList = new ArrayList();
					Consumer<ItemStack> lootGetter = lootList::add;
					lootTable.getRandomItems(lootContext, lootGetter);
					CompoundTag nbt = blockInfo.nbt();
					//LogManager.getLogger().info("Trying to fill block " + block.toString() + " with loot table " + lootTableLocation.toString() + " resulting in Items " + lootList.toString() + " added to " + nbt.toString() + " at " + blockInfo.pos());
					if (nbt.contains("Items")) {
						ListTag items = nbt.getList("Items", 10);
						byte[] slotFillOrder = {(byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5};
						for (int i=0; i<5; i++) {
							int r1 = random.nextInt(6);
							byte b = slotFillOrder[r1];
							slotFillOrder[r1] = slotFillOrder[i];
							slotFillOrder[i] = b;
						}
						byte slotId = 0;
						//works with all blocks that store the Items in an "Items" field, like the Chiseled Bookshelf
						for (ItemStack stack : lootList) {
							CompoundTag itemInfo = stack.save(new CompoundTag());
							itemInfo.putByte("Slot", slotFillOrder[slotId]);
							slotId++;
							items.add(itemInfo);
						}
					}
					//LogManager.getLogger().info("Filled block " + block.toString() + " with loot table " + lootTableLocation.toString() + " resulting in " + nbt.toString());
					return new StructureTemplate.StructureBlockInfo(blockInfo.pos(), blockInfo.state(), nbt);
				}
			}
		return blockInfo;
	}

	@Override
	protected StructureProcessorType<?> getType() {
		return ModStructureProcessors.INSTANT_LOOT_TABLE_PROCESSOR.get();
	}
}
