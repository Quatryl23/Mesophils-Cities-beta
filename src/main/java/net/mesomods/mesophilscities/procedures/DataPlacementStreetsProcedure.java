package net.mesomods.mesophilscities.procedures;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class DataPlacementStreetsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		double placement_id = 0;
		placement_id = DataCalculationStreetsProcedure.execute(x, z);
		if (placement_id == 3) {
			world.setBlock(BlockPos.containing(x, y, z), Blocks.WHITE_STAINED_GLASS.defaultBlockState(), 3);
		} else if (placement_id == 5) {
			world.setBlock(BlockPos.containing(x, y, z), Blocks.PURPLE_STAINED_GLASS.defaultBlockState(), 3);
		} else if (placement_id == 6) {
			world.setBlock(BlockPos.containing(x, y, z), Blocks.ORANGE_STAINED_GLASS.defaultBlockState(), 3);
		} else if (placement_id == 7) {
			world.setBlock(BlockPos.containing(x, y, z), Blocks.RED_STAINED_GLASS.defaultBlockState(), 3);
		} else if (placement_id == 9) {
			world.setBlock(BlockPos.containing(x, y, z), Blocks.CYAN_STAINED_GLASS.defaultBlockState(), 3);
		} else if (placement_id == 10) {
			world.setBlock(BlockPos.containing(x, y, z), Blocks.LIME_STAINED_GLASS.defaultBlockState(), 3);
		} else if (placement_id == 11) {
			world.setBlock(BlockPos.containing(x, y, z), Blocks.GREEN_STAINED_GLASS.defaultBlockState(), 3);
		} else if (placement_id == 12) {
			world.setBlock(BlockPos.containing(x, y, z), Blocks.BLACK_STAINED_GLASS.defaultBlockState(), 3);
		} else if (placement_id == 13) {
			world.setBlock(BlockPos.containing(x, y, z), Blocks.BLUE_STAINED_GLASS.defaultBlockState(), 3);
		} else if (placement_id == 14) {
			world.setBlock(BlockPos.containing(x, y, z), Blocks.YELLOW_STAINED_GLASS.defaultBlockState(), 3);
		} else if (placement_id == 15) {
			world.setBlock(BlockPos.containing(x, y, z), Blocks.BROWN_STAINED_GLASS.defaultBlockState(), 3);
		} else {
			if (placement_id == 19) {
				world.setBlock(BlockPos.containing(x, y, z), Blocks.TINTED_GLASS.defaultBlockState(), 3);
			} else if (placement_id == 28) {
				world.setBlock(BlockPos.containing(x, y, z), Blocks.LIGHT_GRAY_STAINED_GLASS.defaultBlockState(), 3);
			} else if (placement_id == 35) {
				world.setBlock(BlockPos.containing(x, y, z), Blocks.PINK_STAINED_GLASS.defaultBlockState(), 3);
			} else if (placement_id == 44) {
				world.setBlock(BlockPos.containing(x, y, z), Blocks.GRAY_STAINED_GLASS.defaultBlockState(), 3);
			} else if (placement_id == 51) {
				world.setBlock(BlockPos.containing(x, y, z), Blocks.MAGENTA_STAINED_GLASS.defaultBlockState(), 3);
			} else if (placement_id == 60) {
				world.setBlock(BlockPos.containing(x, y, z), Blocks.LIGHT_BLUE_STAINED_GLASS.defaultBlockState(), 3);
			} else if (placement_id == 67) {
				world.setBlock(BlockPos.containing(x, y, z), Blocks.RED_MUSHROOM_BLOCK.defaultBlockState(), 3);
			} else if (placement_id == 76) {
				world.setBlock(BlockPos.containing(x, y, z), Blocks.BROWN_MUSHROOM_BLOCK.defaultBlockState(), 3);
			}
		}
	}
}
