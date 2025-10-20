package net.mesomods.mesophilscities.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ByteTag;

public class StreetHouseBlockEastProcedure {
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
		double Pathshiftx48 = 0;
		double Pathshiftz48 = 0;
		double path_s = 0;
		double border2_n = 0;
		double path_w = 0;
		double border1_n = 0;
		double border2_s = 0;
		double border1_s = 0;
		double border2_w = 0;
		double border1_w = 0;
		double path_n = 0;
		sizexz_pos_xz = DataCalculationPositionHouseblockProcedure.execute(x, z);
		place_block = new ListTag();
		place_block.addTag(0, IntTag.valueOf(0));
		place_block.addTag(1, IntTag.valueOf(0));
		place_block.addTag(2, IntTag.valueOf(0));
		place_block.addTag(3, IntTag.valueOf(0));
		place_block.addTag(4, ByteTag.valueOf(false));
		place_block.addTag(5, ByteTag.valueOf(false));
		place_block.addTag(6, ByteTag.valueOf(false));
		place_block.addTag(7, ByteTag.valueOf(false));
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
		for (int index0 = 0; index0 < 4; index0++) {
			testnumbr = testnumbr + 1;
			if (testnumbr == 1) {
				seed = HouseBorderSeedEastWestProcedure.execute(x + 32, z - 0, Pathshiftz, midz, posz);
			} else if (testnumbr == 2) {
				seed = HouseBorderSeedNorthSouthProcedure.execute(x + 48, z - 16, Pathshiftx48, midx, posx + 1);
			} else if (testnumbr == 3) {
				seed = HouseBorderSeedNorthSouthProcedure.execute(x + 48, z + 32, Pathshiftx48, midx, posx + 1);
			} else if (testnumbr == 4) {
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
				path_e = path;
				border1_e = border1;
				border2_e = border2;
			} else if (testnumbr == 2) {
				path_n2 = path;
				border1_n2 = border1;
				border2_n2 = border2;
			} else if (testnumbr == 3) {
				path_s2 = path;
				border1_s2 = border1;
				border2_s2 = border2;
			} else if (testnumbr == 4) {
				path_e2 = path;
				border1_e2 = border1;
				border2_e2 = border2;
			}
		}
		if (border1_e > 0) {
			if (id == 3 && border1_e < 5 || id == 6 && border1_e >= 5) {
				assert Boolean.TRUE; //#dbg:StreetHouseBlockEast:C
				BlockPlacementHousesProcedure.execute(world, x, y, z, TrainStreetCalculationProcedure.execute(x + 48, z) != 0 || border1_n2 == 0, false, path_e > 0, true,
						(TrainStreetCalculationProcedure.execute(x + 48, z) == 6)
								? (border2_e != 0 && (border3_e != 0 || path_e != 0)) ? (17 - HouseDistanceXProcedure.execute(x + 48, z)) : ((border1_s2 != 0 ? border1_s2 : path_s2) + 12)
								: (TrainStreetCalculationProcedure.execute(x + 48, z) == 10)
										? ((border1_n2 != 0 ? border1_n2 : path_n2) + 12)
										: (TrainStreetCalculationProcedure.execute(x + 48, z) != 0)
												? (17 - HouseDistanceXProcedure.execute(x + 48, z))
												: (!PathDirectionProcedure.execute(x + 48, z)) ? (border1_n2 > 0) ? (Random1Procedure.execute(x + 48, z) + 12) : (path_n2 + 12) : (border1_n2 > 0) ? (border1_n2 + 12) : (path_n2 + 12),
						9 + HouseDistanceXProcedure.execute(x, z), (path_e > 0) ? path_e : (border3_e > 0) ? border3_e : border2_e, border1_e);
			}
		}
		if (border2_e > 0 && (border3_e > 0 || path_e > 0)) {
			if (id == 3 && (path_e > 0 ? path_e + 1 : border3_e) < 5 || id == 6 && (path_e > 0 ? path_e + 1 : border3_e) >= 5) {
				assert Boolean.TRUE; //#dbg:StreetHouseBlockEast:F
				BlockPlacementHousesProcedure.execute(world, x, y, z, TrainStreetCalculationProcedure.execute(x + 48, z) != 0 || border1_s2 == 0, path_e > 0, false, true,
						(TrainStreetCalculationProcedure.execute(x + 48, z) == 6)
								? ((border1_s2 != 0 ? border1_s2 : path_s2) + 12)
								: (TrainStreetCalculationProcedure.execute(x + 48, z) == 10)
										? (border1_e != 0) ? (17 - HouseDistanceXProcedure.execute(x + 48, z)) : ((border1_n2 != 0 ? border1_n2 : path_n2) + 12)
										: (TrainStreetCalculationProcedure.execute(x + 48, z) != 0)
												? (17 - HouseDistanceXProcedure.execute(x + 48, z))
												: (!PathDirectionProcedure.execute(x + 48, z)) ? (border1_s2 > 0) ? (Random3Procedure.execute(x + 48, z) + 12) : (path_s2 + 12) : (border1_s2 > 0) ? (border1_s2 + 12) : (path_s2 + 12),
						9 + HouseDistanceXProcedure.execute(x, z), border2_e, path_e > 0 ? path_e + 1 : border3_e);
			}
		}
		if (id == 9) {
			assert Boolean.TRUE; //#dbg:StreetHouseBlockEast:I
			BlockPlacementHousesProcedure.execute(world, x, y, z, TrainStreetCalculationProcedure.execute(x + 48, z + 48) % 8 > 3 || border1_s2 == 0, border2_e == 0, TrainStreetCalculationProcedure.execute(x + 48, z + 48) % 2 == 1 || border1_e2 == 0,
					true, border1_s2 > 0 ? border1_s2 + 12 : path_s2 + 12, 9 + HouseDistanceXProcedure.execute(x, z), border1_e2 > 0 ? border1_e2 + 12 : path_e2 + 12, border2_e > 0 ? border2_e : path_e + 1);
		}
	}
}
