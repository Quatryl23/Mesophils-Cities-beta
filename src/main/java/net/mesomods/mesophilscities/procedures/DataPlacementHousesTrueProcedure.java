package net.mesomods.mesophilscities.procedures;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.core.BlockPos;

public class DataPlacementHousesTrueProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, double id) {
		ListTag sizexz_pos_xz;
		ListTag place_block;
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
		double Pathshiftx48 = 0;
		double Pathshiftz48 = 0;
		sizexz_pos_xz = DataCalculationPositionHouseblockProcedure.execute(x, z);
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
		Pathshiftx48 = RandomPathShiftSeedProcedure.execute(x + 48, originz);
		Pathshiftz48 = RandomPathShiftSeedProcedure.execute(originx, z + 48);
		testnumbr = 0;
		for (int index0 = 0; index0 < 8; index0++) {
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
				seed = HouseBorderSeedNorthSouthProcedure.execute(x + 48, z - 16, Pathshiftx48, midx, posx + 1);
			} else if (testnumbr == 6) {
				seed = HouseBorderSeedNorthSouthProcedure.execute(x + 48, z + 32, Pathshiftx48, midx, posx + 1);
			} else if (testnumbr == 7) {
				seed = HouseBorderSeedEastWestProcedure.execute(x - 16, z + 48, Pathshiftz48, midz, posz + 1);
			} else if (testnumbr == 8) {
				seed = HouseBorderSeedEastWestProcedure.execute(x + 32, z + 48, Pathshiftz48, midz, posz + 1);
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
		if (id == 1) {
			testnumbr = -16;
			for (int index1 = 0; index1 < 12; index1++) {
				world.setBlock(BlockPos.containing(x + testnumbr, y, z - 20 + 4 * path_w), Blocks.DEEPSLATE_TILES.defaultBlockState(), 3);
				testnumbr = testnumbr + 4;
			}
			if (path_n > 0) {
				testnumbr = -16;
				for (int index2 = 0; index2 < (int) (path_w - 1); index2++) {
					world.setBlock(BlockPos.containing(x - 20 + 4 * path_n, y, z + testnumbr), Blocks.DEEPSLATE_TILES.defaultBlockState(), 3);
					testnumbr = testnumbr + 4;
				}
			}
			if (path_s > 0) {
				testnumbr = 4 * path_w - 16;
				for (int index3 = 0; index3 < (int) (12 - path_w); index3++) {
					world.setBlock(BlockPos.containing(x - 20 + 4 * path_s, y, z + testnumbr), Blocks.DEEPSLATE_TILES.defaultBlockState(), 3);
					testnumbr = testnumbr + 4;
				}
			}
		}
		if (border1_w > 0 && border1_e > 0) {
			if (border1_n > 0) {
				if (Random1Procedure.execute(x, z) < 5 && (id == 1 && border1_n < 5 || id == 2 && border1_n >= 5) || Random1Procedure.execute(x, z) >= 5 && (id == 4 && border1_n < 5 || id == 5 && border1_n >= 5)) {
					assert Boolean.TRUE; //#dbg:DataPlacementHousesTrue:A
					BlockPlacementHousesProcedure.execute(world, x, y, z, path_n > 0 ? true : false, false, true, false, (path_n > 0) ? path_n : (border3_n > 0) ? border3_n : border2_n, border1_n, path_w, Random1Procedure.execute(x, z));
				}
			}
			if (border2_n > 0 && (border3_n > 0 || path_n > 0)) {
				if (Random3Procedure.execute(x, z) < 5 && (id == 1 && (path_n > 0 ? path_n + 1 : border3_n) < 5 || id == 2 && (path_n > 0 ? path_n + 1 : border3_n) >= 5)
						|| Random3Procedure.execute(x, z) >= 5 && (id == 4 && (path_n > 0 ? path_n + 1 : border3_n) < 5 || id == 5 && (path_n > 0 ? path_n + 1 : border3_n) >= 5)) {
					assert Boolean.TRUE; //#dbg:DataPlacementHousesTrue:D
					BlockPlacementHousesProcedure.execute(world, x, y, z, false, false, true, path_n > 0 ? true : false, border2_n, path_n > 0 ? path_n + 1 : border3_n, path_w, Random3Procedure.execute(x, z));
				}
			}
			if (id == 3 && border1_e < 5 || id == 6 && border1_e >= 5) {
				assert Boolean.TRUE; //#dbg:DataPlacementHousesTrue:G
				BlockPlacementHousesProcedure.execute(world, x, y, z, TrainStreetCalculationProcedure.execute(x + 48, z) != 0 || border1_n2 == 0 ? true : false, false, true, border2_n == 0 ? true : false,
						(TrainStreetCalculationProcedure.execute(x + 48, z) == 6)
								? (border2_e != 0 && (border3_e != 0 || path_e != 0)) ? (17 - HouseDistanceXProcedure.execute(x + 48, z)) : ((border1_s2 > 0 ? border1_s2 : path_s2) + 12)
								: (TrainStreetCalculationProcedure.execute(x + 48, z) == 10)
										? ((border1_n2 > 0 ? border1_n2 : path_n2) + 12)
										: (TrainStreetCalculationProcedure.execute(x + 48, z) != 0)
												? (17 - HouseDistanceXProcedure.execute(x + 48, z))
												: (!PathDirectionProcedure.execute(x + 48, z)) ? (border1_n2 > 0) ? (Random1Procedure.execute(x + 48, z) + 12) : (path_n2 + 12) : (border1_n2 > 0) ? (border1_n2 + 12) : (path_n2 + 12),
						border2_n > 0 ? border2_n : path_n + 1, path_e, border1_e);
			}
		}
		if (border2_w > 0 && border2_e > 0) {
			if (border1_s > 0) {
				if (path_w < 4 && (id == 1 && border1_s < 5 || id == 2 && border1_s >= 5) || path_w >= 4 && (id == 4 && border1_s < 5 || id == 5 && border1_s >= 5)) {
					assert Boolean.TRUE; //#dbg:DataPlacementHousesTrue:B
					BlockPlacementHousesProcedure.execute(world, x, y, z, path_s > 0 ? true : false, true, false, false, (path_s > 0) ? path_s : (border3_s > 0) ? border3_s : border2_s, border1_s, Random2Procedure.execute(x, z), path_w + 1);
				}
			}
			if (border2_s > 0 && (border3_s > 0 || path_s > 0)) {
				if (path_w < 4 && (id == 1 && (path_s > 0 ? path_s + 1 : border3_s) < 5 || id == 2 && (path_s > 0 ? path_s + 1 : border3_s) >= 5)
						|| path_w >= 4 && (id == 4 && (path_s > 0 ? path_s + 1 : border3_s) < 5 || id == 5 && (path_s > 0 ? path_s + 1 : border3_s) >= 5)) {
					assert Boolean.TRUE; //#dbg:DataPlacementHousesTrue:E
					BlockPlacementHousesProcedure.execute(world, x, y, z, false, true, false, path_s > 0 ? true : false, border2_s, path_s > 0 ? path_s + 1 : border3_s, Random4Procedure.execute(x, z), path_e + 1);
				}
			}
			if (id == 3 && path_e < 4 || id == 6 && path_e >= 4) {
				assert Boolean.TRUE; //#dbg:DataPlacementHousesTrue:H
				BlockPlacementHousesProcedure.execute(world, x, y, z, TrainStreetCalculationProcedure.execute(x + 48, z) != 0 || border1_s2 == 0 ? true : false, true, false, border2_s == 0 ? true : false,
						(TrainStreetCalculationProcedure.execute(x + 48, z) == 6)
								? ((border1_s2 > 0 ? border1_s2 : path_s2) + 12)
								: (TrainStreetCalculationProcedure.execute(x + 48, z) == 10)
										? (border1_e != 0) ? (17 - HouseDistanceXProcedure.execute(x + 48, z)) : ((border1_n2 > 0 ? border1_n2 : path_n2) + 12)
										: (TrainStreetCalculationProcedure.execute(x + 48, z) != 0)
												? (17 - HouseDistanceXProcedure.execute(x + 48, z))
												: (!PathDirectionProcedure.execute(x + 48, z)) ? (border1_s2 > 0) ? (Random3Procedure.execute(x + 48, z) + 12) : (path_s2 + 12) : (border1_s2 > 0) ? (border1_s2 + 12) : (path_s2 + 12),
						border2_s > 0 ? border2_s : path_s + 1, border2_e, path_e + 1);
			}
		}
		if (border1_s > 0) {
			if (id == 7 && border1_s < 5 || id == 8 && border1_s >= 5) {
				assert Boolean.TRUE; //#dbg:DataPlacementHousesTrue:C
				BlockPlacementHousesProcedure.execute(world, x, y, z, path_s > 0 ? true : false, border2_w == 0 ? true : false, TrainStreetCalculationProcedure.execute(x, z + 48) != 0 || border1_w2 == 0 ? true : false, false,
						(path_s > 0) ? path_s : (border3_s > 0) ? border3_s : border2_s, border1_s,
						(TrainStreetCalculationProcedure.execute(x, z + 48) == 9)
								? (border2_s != 0 && (border3_s != 0 || path_s != 0)) ? (17 - HouseDistanceZProcedure.execute(x, z + 48)) : ((border1_e2 > 0 ? border1_e2 : path_e2) + 12)
								: (TrainStreetCalculationProcedure.execute(x, z + 48) == 10)
										? ((border1_w2 > 0 ? border1_w2 : path_w2) + 12)
										: (TrainStreetCalculationProcedure.execute(x, z + 48) != 0)
												? (17 - HouseDistanceZProcedure.execute(x, z + 48))
												: PathDirectionProcedure.execute(x, z + 48) ? (border1_w2 > 0) ? (Random1Procedure.execute(x, z + 48) + 12) : (path_w2 + 12) : (border1_w2 > 0) ? (border1_w2 + 12) : (path_w2 + 12),
						border2_w > 0 ? Random2Procedure.execute(x, z) : path_w + 1);
			}
		}
		if (border2_s > 0 && (border3_s > 0 || path_s > 0)) {
			if (id == 7 && (path_s > 0 ? path_s + 1 : border3_s) < 5 || id == 8 && (path_s > 0 ? path_s + 1 : border3_s) >= 5) {
				assert Boolean.TRUE; //#dbg:DataPlacementHousesTrue:F
				BlockPlacementHousesProcedure.execute(world, x, y, z, false, border2_e == 0 ? true : false, TrainStreetCalculationProcedure.execute(x, z + 48) != 0 || border1_e2 == 0 ? true : false, path_s > 0 ? true : false, border2_s,
						path_s > 0 ? path_s + 1 : border3_s,
						(TrainStreetCalculationProcedure.execute(x, z + 48) == 9)
								? ((border1_e2 > 0 ? border1_e2 : path_e2) + 12)
								: (TrainStreetCalculationProcedure.execute(x, z + 48) == 10)
										? (border1_s != 0) ? (17 - HouseDistanceZProcedure.execute(x, z + 48)) : ((border1_w2 > 0 ? border1_w2 : path_w2) + 12)
										: (TrainStreetCalculationProcedure.execute(x, z + 48) != 0)
												? (17 - HouseDistanceZProcedure.execute(x, z + 48))
												: PathDirectionProcedure.execute(x, z + 48) ? (border1_e2 > 0) ? (Random3Procedure.execute(x, z + 48) + 12) : (path_e2 + 12) : (border1_e2 > 0) ? (border1_e2 + 12) : (path_e2 + 12),
						border2_e > 0 ? Random4Procedure.execute(x, z) : path_e + 1);
			}
		}
		if (id == 9) {
			assert Boolean.TRUE; //#dbg:DataPlacementHousesTrue:I
			BlockPlacementHousesProcedure.execute(world, x, y, z, border1_s2 == 0 || Math.abs(TrainStreetCalculationProcedure.execute(x + 48, z + 48)) % 8 > 3, border2_e == 0,
					border1_e2 == 0 || Math.abs(TrainStreetCalculationProcedure.execute(x + 48, z + 48)) % 2 == 1, border2_s == 0, border1_s2 > 0 ? border1_s2 + 12 : path_s2 + 12, border2_s > 0 ? border2_s : path_s + 1,
					border1_e2 > 0 ? border1_e2 + 12 : path_e2 + 12, border2_e > 0 ? border2_e : path_e + 1);
		}
	}
}
