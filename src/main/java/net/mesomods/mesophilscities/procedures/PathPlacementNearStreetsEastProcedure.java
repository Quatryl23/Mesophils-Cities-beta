package net.mesomods.mesophilscities.procedures;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.core.BlockPos;

public class PathPlacementNearStreetsEastProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		double testnumbr = 0;
		double sizex = 0;
		double sizez = 0;
		double posx = 0;
		double posz = 0;
		double originx = 0;
		double originz = 0;
		double midx = 0;
		double midz = 0;
		double Pathshiftx = 0;
		double Pathshiftz = 0;
		double seednorth = 0;
		double seedsouth = 0;
		double seedwest = 0;
		double seedeast = 0;
		double border1_e = 0;
		double border2_e = 0;
		double border3_e = 0;
		double path_e = 0;
		double path = 0;
		double seed = 0;
		double border2 = 0;
		double border1 = 0;
		double testnumbr2 = 0;
		double path_n2 = 0;
		double border1_n2 = 0;
		double border2_n2 = 0;
		double path_s2 = 0;
		double border1_s2 = 0;
		double border2_s2 = 0;
		double path_e2 = 0;
		double border1_e2 = 0;
		double border2_e2 = 0;
		ListTag sizexz_pos_xz;
		ListTag place_block;
		sizexz_pos_xz = DataCalculationPositionHouseblockProcedure.execute(x + 48, z);
		sizex = (sizexz_pos_xz.get(0)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0;
		sizez = (sizexz_pos_xz.get(1)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0;
		posx = (sizexz_pos_xz.get(2)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0;
		posz = (sizexz_pos_xz.get(3)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0;
		posx = posx - 1;
		originx = x - 48 * posx;
		originz = z - 48 * posz;
		midx = RandomHouseBlockMidXProcedure.execute(originx, originz, sizex);
		midz = RandomHouseBlockMidZProcedure.execute(originx, originz, sizez);
		Pathshiftx = RandomPathShiftSeedProcedure.execute(x, originz);
		Pathshiftz = RandomPathShiftSeedProcedure.execute(originx, z);
		testnumbr = 0;
		seed = HouseBorderSeedEastWestProcedure.execute(x + 32, z - 0, Pathshiftz, midz, posz);
		border1 = 0;
		border2 = 0;
		path = 0;
		if (seed == 1) {
			border1 = 4;
			border2 = 10;
		} else if (seed == 2) {
			border1 = 4;
			border2 = 10;
		} else if (seed == 11) {
			border1 = 3;
			border2 = 10;
		} else if (seed == 12) {
			border1 = 4;
			border2 = 11;
		} else if (seed == 0) {
			border1 = 5;
			border2 = 9;
		} else if (seed == 13) {
			border1 = 3;
			border2 = 11;
		}
		if (seed == 31) {
			border2 = 10;
			path = 4;
		} else if (seed == 32) {
			border2 = 11;
			path = 4;
		} else if (seed == 41) {
			border2 = 9;
			path = 3;
		} else if (seed == 42) {
			border2 = 10;
			path = 3;
		} else if (seed == 61) {
			border1 = 2;
			border2 = 11;
			path = 6;
		} else if (seed == 62) {
			border1 = 3;
			border2 = 11;
			path = 6;
		} else if (seed == 71) {
			border1 = 3;
			border2 = 11;
			path = 7;
		} else if (seed == 72) {
			border1 = 3;
			border2 = 12;
			path = 7;
		} else if (seed == 91) {
			border1 = 4;
			path = 9;
		} else if (seed == 92) {
			border1 = 3;
			path = 9;
		} else if (seed == 101) {
			border1 = 5;
			path = 10;
		} else if (seed == 102) {
			border1 = 4;
			path = 10;
		}
		path_e = path;
		if (path_e > 0) {
			testnumbr = 16 + 4 * HouseDistanceXProcedure.execute(x, z);
			for (int index0 = 0; index0 < (int) (4 - HouseDistanceXProcedure.execute(x, z)); index0++) {
				world.setBlock(BlockPos.containing(x + testnumbr, y, z - 20 + 4 * path_e), Blocks.DEEPSLATE_TILES.defaultBlockState(), 3);
				testnumbr = testnumbr + 4;
			}
		}
	}
}
