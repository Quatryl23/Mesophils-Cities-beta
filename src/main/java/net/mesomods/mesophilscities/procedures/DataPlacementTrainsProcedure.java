package net.mesomods.mesophilscities.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.util.RandomSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

public class DataPlacementTrainsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		double placement_id = 0;
		placement_id = TrainCalculationPreciseStationProcedure.execute(x, z);
		if (placement_id == 3) {
			world.setBlock(BlockPos.containing(x, y, z), Blocks.WHITE_WOOL.defaultBlockState(), 3);
		} else if (placement_id == 5) {
			world.setBlock(BlockPos.containing(x, y, z), Blocks.PURPLE_WOOL.defaultBlockState(), 3);
		} else if (placement_id == 6) {
			world.setBlock(BlockPos.containing(x, y, z), Blocks.ORANGE_WOOL.defaultBlockState(), 3);
		} else if (placement_id == 7) {
			world.setBlock(BlockPos.containing(x, y, z), Blocks.RED_WOOL.defaultBlockState(), 3);
		} else if (placement_id == 9) {
			world.setBlock(BlockPos.containing(x, y, z), Blocks.CYAN_WOOL.defaultBlockState(), 3);
		} else if (placement_id == 10) {
			world.setBlock(BlockPos.containing(x, y, z), Blocks.LIME_WOOL.defaultBlockState(), 3);
		} else if (placement_id == 11) {
			world.setBlock(BlockPos.containing(x, y, z), Blocks.GREEN_WOOL.defaultBlockState(), 3);
		} else if (placement_id == 12) {
			world.setBlock(BlockPos.containing(x, y, z), Blocks.BLACK_WOOL.defaultBlockState(), 3);
		} else if (placement_id == 13) {
			world.setBlock(BlockPos.containing(x, y, z), Blocks.BLUE_WOOL.defaultBlockState(), 3);
		} else if (placement_id == 14) {
			world.setBlock(BlockPos.containing(x, y, z), Blocks.YELLOW_WOOL.defaultBlockState(), 3);
		} else if (placement_id == 15) {
			world.setBlock(BlockPos.containing(x, y, z), Blocks.BROWN_WOOL.defaultBlockState(), 3);
		} else if (placement_id % 100 == -3) {
			world.setBlock(BlockPos.containing(x, y - 1, z),
					(ForgeRegistries.BLOCKS.tags().getTag(BlockTags.create(new ResourceLocation("mesophils_cities:station_x_a"))).getRandomElement(RandomSource.create()).orElseGet(() -> Blocks.AIR)).defaultBlockState(), 3);
			if (MainStationIDProcedure.execute(x, z) > 30) {
				world.setBlock(BlockPos.containing(x + 1, y - 1, z), Blocks.DARK_OAK_LOG.defaultBlockState(), 3);
			} else if (MainStationIDProcedure.execute(x, z) > 20) {
				world.setBlock(BlockPos.containing(x + 1, y - 1, z), Blocks.SPRUCE_LOG.defaultBlockState(), 3);
			} else if (MainStationIDProcedure.execute(x, z) > 10) {
				world.setBlock(BlockPos.containing(x + 1, y - 1, z), Blocks.OAK_LOG.defaultBlockState(), 3);
			}
			if (MainStationIDProcedure.execute(x, z) % 10 == 1) {
				world.setBlock(BlockPos.containing(x + 0, y - 1, z + 1), Blocks.DARK_OAK_LOG.defaultBlockState(), 3);
			} else if (MainStationIDProcedure.execute(x, z) % 10 == 2) {
				world.setBlock(BlockPos.containing(x + 0, y - 1, z + 1), Blocks.SPRUCE_LOG.defaultBlockState(), 3);
			} else if (MainStationIDProcedure.execute(x, z) % 10 == 3) {
				world.setBlock(BlockPos.containing(x + 0, y - 1, z + 1), Blocks.OAK_LOG.defaultBlockState(), 3);
			} else if (MainStationIDProcedure.execute(x, z) % 10 == 4) {
				world.setBlock(BlockPos.containing(x + 0, y - 1, z + 1), Blocks.JUNGLE_LOG.defaultBlockState(), 3);
			} else if (MainStationIDProcedure.execute(x, z) % 10 == 5) {
				world.setBlock(BlockPos.containing(x + 0, y - 1, z + 1), Blocks.BIRCH_LOG.defaultBlockState(), 3);
			}
		} else if (placement_id % 100 == -12) {
			world.setBlock(BlockPos.containing(x, y - 1, z),
					(ForgeRegistries.BLOCKS.tags().getTag(BlockTags.create(new ResourceLocation("mesophils_cities:station_z_a"))).getRandomElement(RandomSource.create()).orElseGet(() -> Blocks.AIR)).defaultBlockState(), 3);
			if (MainStationIDProcedure.execute(x, z) > 30) {
				world.setBlock(BlockPos.containing(x, y - 1, z + 1), Blocks.DARK_OAK_LOG.defaultBlockState(), 3);
			} else if (MainStationIDProcedure.execute(x, z) > 20) {
				world.setBlock(BlockPos.containing(x, y - 1, z + 1), Blocks.SPRUCE_LOG.defaultBlockState(), 3);
			} else if (MainStationIDProcedure.execute(x, z) > 10) {
				world.setBlock(BlockPos.containing(x, y - 1, z + 1), Blocks.OAK_LOG.defaultBlockState(), 3);
			}
			if (MainStationIDProcedure.execute(x, z) % 10 == 1) {
				world.setBlock(BlockPos.containing(x + 1, y - 1, z + 0), Blocks.DARK_OAK_LOG.defaultBlockState(), 3);
			} else if (MainStationIDProcedure.execute(x, z) % 10 == 2) {
				world.setBlock(BlockPos.containing(x + 1, y - 1, z + 0), Blocks.SPRUCE_LOG.defaultBlockState(), 3);
			} else if (MainStationIDProcedure.execute(x, z) % 10 == 3) {
				world.setBlock(BlockPos.containing(x + 1, y - 1, z + 0), Blocks.OAK_LOG.defaultBlockState(), 3);
			} else if (MainStationIDProcedure.execute(x, z) % 10 == 4) {
				world.setBlock(BlockPos.containing(x + 1, y - 1, z + 0), Blocks.JUNGLE_LOG.defaultBlockState(), 3);
			} else if (MainStationIDProcedure.execute(x, z) % 10 == 5) {
				world.setBlock(BlockPos.containing(x + 1, y - 1, z + 0), Blocks.BIRCH_LOG.defaultBlockState(), 3);
			}
		}
	}
}
