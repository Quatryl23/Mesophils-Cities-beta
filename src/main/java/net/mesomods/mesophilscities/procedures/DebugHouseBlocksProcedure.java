package net.mesomods.mesophilscities.procedures;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.core.BlockPos;

public class DebugHouseBlocksProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		ListTag sizexz_pos_xz;
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
		double border1_n = 0;
		double border2_n = 0;
		double border3_n = 0;
		double path_n = 0;
		double border1_s = 0;
		double border2_s = 0;
		double border3_s = 0;
		double path_s = 0;
		double border1_w = 0;
		double border2_w = 0;
		double border3_w = 0;
		double path_w = 0;
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
		double path_w2 = 0;
		double border1_w2 = 0;
		double border2_w2 = 0;
		double path_e2 = 0;
		double border1_e2 = 0;
		double border2_e2 = 0;
		if ((z % 48 + 48) % 48 == 0 && (x % 48 + 48) % 48 == 0) {
			sizexz_pos_xz = DataCalculationPositionHouseblockProcedure.execute(x, z);
			if (TrainStreetCalculationProcedure.execute(x, z) == 0) {
				if (PathDirectionProcedure.execute(x, z)) {
					for (int index0 = 0; index0 < 16; index0++) {
						testnumbr = testnumbr + 1;
						world.setBlock(BlockPos.containing(x + testnumbr, y - 3, z), Blocks.NETHER_GOLD_ORE.defaultBlockState(), 3);
					}
				} else {
					for (int index1 = 0; index1 < 16; index1++) {
						testnumbr = testnumbr + 1;
						world.setBlock(BlockPos.containing(x, y - 3, z + testnumbr), Blocks.NETHER_GOLD_ORE.defaultBlockState(), 3);
					}
				}
			}
			sizex = (sizexz_pos_xz.get(0)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0;
			sizez = (sizexz_pos_xz.get(1)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0;
			posx = (sizexz_pos_xz.get(2)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0;
			posz = (sizexz_pos_xz.get(3)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0;
			originx = x - 48 * posx;
			originz = z - 48 * posz;
			midx = RandomHouseBlockMidXProcedure.execute(originx, originz, sizex);
			midz = RandomHouseBlockMidZProcedure.execute(originx, originz, sizez);
			Pathshiftx = RandomPathShiftSeedProcedure.execute(x, originz);
			Pathshiftz = RandomPathShiftSeedProcedure.execute(originx, z);
			testnumbr = 0;
			for (int index2 = 0; index2 < 8; index2++) {
				testnumbr = testnumbr + 1;
				if (testnumbr == 1) {
					seed = HouseBorderSeedNorthSouthProcedure.execute(x - 0, z - 16, Pathshiftx, midx, posx);
				} else if (testnumbr == 2) {
					seed = HouseBorderSeedNorthSouthProcedure.execute(x - 0, z + 32, Pathshiftx, midx, posx);
				} else if (testnumbr == 3) {
					seed = HouseBorderSeedEastWestProcedure.execute(x - 16, z - 0, Pathshiftz, midz, posz);
				} else if (testnumbr == 4) {
					seed = HouseBorderSeedEastWestProcedure.execute(x + 32, z - 0, Pathshiftz, midz, posz);
				} else if (testnumbr == 5) {
					seed = HouseBorderSeedNorthSouthProcedure.execute(x + 48, z - 16, Pathshiftx, midx, posx);
				} else if (testnumbr == 6) {
					seed = HouseBorderSeedNorthSouthProcedure.execute(x + 48, z + 32, Pathshiftx, midx, posx);
				} else if (testnumbr == 7) {
					seed = HouseBorderSeedEastWestProcedure.execute(x - 16, z + 48, Pathshiftz, midz, posz);
				} else if (testnumbr == 8) {
					seed = HouseBorderSeedEastWestProcedure.execute(x + 32, z + 48, Pathshiftz, midz, posz);
				}
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
				if (testnumbr == 1) {
					path_n = path;
					border1_n = border1;
					border2_n = border2;
				} else if (testnumbr == 2) {
					path_s = path;
					border1_s = border1;
					border2_s = border2;
				} else if (testnumbr == 3) {
					path_w = path;
					border1_w = border1;
					border2_w = border2;
				} else if (testnumbr == 4) {
					path_e = path;
					border1_e = border1;
					border2_e = border2;
				} else if (testnumbr == 5) {
					path_n2 = path;
					border1_n2 = border1;
					border2_n2 = border2;
				} else if (testnumbr == 6) {
					path_s2 = path;
					border1_s2 = border1;
					border2_s2 = border2;
				} else if (testnumbr == 7) {
					path_w2 = path;
					border1_w2 = border1;
					border2_w2 = border2;
				} else if (testnumbr == 8) {
					path_e2 = path;
					border1_e2 = border1;
					border2_e2 = border2;
				}
			}
			testnumbr = 0;
			for (int index3 = 0; index3 < (int) (sizex - 1); index3++) {
				testnumbr = testnumbr + 1;
				testnumbr2 = 0;
				for (int index4 = 0; index4 < (int) (sizez - 1); index4++) {
					testnumbr2 = testnumbr2 + 1;
					world.setBlock(BlockPos.containing(x + testnumbr, y - 3, z + testnumbr2), Blocks.STRIPPED_CRIMSON_HYPHAE.defaultBlockState(), 3);
				}
			}
			world.setBlock(BlockPos.containing(x + posx, y - 3, z + posz), Blocks.GOLD_BLOCK.defaultBlockState(), 3);
			world.setBlock(BlockPos.containing(x + midx, y - 2, z + midz), Blocks.LAPIS_BLOCK.defaultBlockState(), 3);
			if (border1_n > 0) {
				world.setBlock(BlockPos.containing(x - 20 + 4 * border1_n, y - 3, z - 16), Blocks.EMERALD_BLOCK.defaultBlockState(), 3);
			}
			if (border2_n > 0) {
				world.setBlock(BlockPos.containing(x - 20 + 4 * border2_n, y - 3, z - 16), Blocks.EMERALD_BLOCK.defaultBlockState(), 3);
			}
			if (path_n > 0) {
				world.setBlock(BlockPos.containing(x - 20 + 4 * path_n, y - 3, z - 16), Blocks.REDSTONE_BLOCK.defaultBlockState(), 3);
				world.setBlock(BlockPos.containing(x - 19 + 4 * path_n, y - 3, z - 16), Blocks.REDSTONE_BLOCK.defaultBlockState(), 3);
				world.setBlock(BlockPos.containing(x - 18 + 4 * path_n, y - 3, z - 16), Blocks.REDSTONE_BLOCK.defaultBlockState(), 3);
				world.setBlock(BlockPos.containing(x - 17 + 4 * path_n, y - 3, z - 16), Blocks.REDSTONE_BLOCK.defaultBlockState(), 3);
			}
			if (border1_s > 0) {
				world.setBlock(BlockPos.containing(x - 20 + 4 * border1_s, y - 3, z + 31), Blocks.EMERALD_BLOCK.defaultBlockState(), 3);
			}
			if (border2_s > 0) {
				world.setBlock(BlockPos.containing(x - 20 + 4 * border2_s, y - 3, z + 31), Blocks.EMERALD_BLOCK.defaultBlockState(), 3);
			}
			if (path_s > 0) {
				world.setBlock(BlockPos.containing(x - 20 + 4 * path_s, y - 3, z + 31), Blocks.REDSTONE_BLOCK.defaultBlockState(), 3);
				world.setBlock(BlockPos.containing(x - 19 + 4 * path_s, y - 3, z + 31), Blocks.REDSTONE_BLOCK.defaultBlockState(), 3);
				world.setBlock(BlockPos.containing(x - 18 + 4 * path_s, y - 3, z + 31), Blocks.REDSTONE_BLOCK.defaultBlockState(), 3);
				world.setBlock(BlockPos.containing(x - 17 + 4 * path_s, y - 3, z + 31), Blocks.REDSTONE_BLOCK.defaultBlockState(), 3);
			}
			if (border1_w > 0) {
				world.setBlock(BlockPos.containing(x - 16, y - 3, z - 20 + 4 * border1_w), Blocks.EMERALD_BLOCK.defaultBlockState(), 3);
			}
			if (border2_w > 0) {
				world.setBlock(BlockPos.containing(x - 16, y - 3, z - 20 + 4 * border2_w), Blocks.EMERALD_BLOCK.defaultBlockState(), 3);
			}
			if (path_w > 0) {
				world.setBlock(BlockPos.containing(x - 16, y - 3, z - 20 + 4 * path_w), Blocks.REDSTONE_BLOCK.defaultBlockState(), 3);
				world.setBlock(BlockPos.containing(x - 16, y - 3, z - 19 + 4 * path_w), Blocks.REDSTONE_BLOCK.defaultBlockState(), 3);
				world.setBlock(BlockPos.containing(x - 16, y - 3, z - 18 + 4 * path_w), Blocks.REDSTONE_BLOCK.defaultBlockState(), 3);
				world.setBlock(BlockPos.containing(x - 16, y - 3, z - 17 + 4 * path_w), Blocks.REDSTONE_BLOCK.defaultBlockState(), 3);
			}
			if (border1_e > 0) {
				world.setBlock(BlockPos.containing(x + 31, y - 3, z - 20 + 4 * border1_e), Blocks.EMERALD_BLOCK.defaultBlockState(), 3);
			}
			if (border2_e > 0) {
				world.setBlock(BlockPos.containing(x + 31, y - 3, z - 20 + 4 * border2_e), Blocks.EMERALD_BLOCK.defaultBlockState(), 3);
			}
			if (path_e > 0) {
				world.setBlock(BlockPos.containing(x + 31, y - 3, z - 20 + 4 * path_e), Blocks.REDSTONE_BLOCK.defaultBlockState(), 3);
				world.setBlock(BlockPos.containing(x + 31, y - 3, z - 19 + 4 * path_e), Blocks.REDSTONE_BLOCK.defaultBlockState(), 3);
				world.setBlock(BlockPos.containing(x + 31, y - 3, z - 18 + 4 * path_e), Blocks.REDSTONE_BLOCK.defaultBlockState(), 3);
				world.setBlock(BlockPos.containing(x + 31, y - 3, z - 17 + 4 * path_e), Blocks.REDSTONE_BLOCK.defaultBlockState(), 3);
			}
			assert Boolean.TRUE; //#dbg:DebugHouseBlocks:placingnow
		}
	}
}
