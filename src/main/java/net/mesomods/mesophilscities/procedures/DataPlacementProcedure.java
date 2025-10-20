package net.mesomods.mesophilscities.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.nbt.IntTag;

public class DataPlacementProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z) {
		double streetid = 0;
		double chunk9id = 0;
		double chunk9idx = 0;
		double chunk9idz = 0;
		if ((x % 48 + 48) % 48 == 0 && (z % 48 + 48) % 48 == 0) {
			DataPlacementTrainsProcedure.execute(world, x, y, z);
			DataPlacementStreetsProcedure.execute(world, x, y, z);
		}
		chunk9idx = ((x % 48 + 48) % 48) / 16 + 1;
		chunk9idz = (((z % 48 + 48) % 48) / 16) * 3;
		chunk9id = chunk9idx + chunk9idz;
		if (TrainCalculationProcedure.execute(Math.round((x - 16) / 48) * 48, Math.round((z - 16) / 48) * 48) != 0 && DataCalculationStreetsProcedure.execute(Math.round((x - 16) / 48) * 48, Math.round((z - 16) / 48) * 48) == 0) {
			streetid = TrainCalculationProcedure.execute(Math.round((x - 16) / 48) * 48, Math.round((z - 16) / 48) * 48);
		} else {
			streetid = DataCalculationStreetsProcedure.execute(Math.round((x - 16) / 48) * 48, Math.round((z - 16) / 48) * 48);
		}
		if (streetid == 19 || streetid == 51) {
			streetid = 3;
		} else if (streetid == 35 || streetid == 67) {
			streetid = 15;
		} else if (streetid == 28 || streetid == 60) {
			streetid = 12;
		} else if (streetid == 44 || streetid == 76) {
			streetid = 15;
		}
		if (MainStationIDProcedure.execute(Math.round((x - 16) / 48) * 48, Math.round((z - 16) / 48) * 48) != 0) {
			if (11 == MainStationEdgeProcedure.execute(Math.round((x - 16) / 48) * 48, Math.round((z - 16) / 48) * 48) % 20) {
				if (chunk9id == 9) {
					StreetHouseBlockSouthEastCornerProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48);
				}
			} else if (1 == MainStationEdgeProcedure.execute(Math.round((x - 16) / 48) * 48, Math.round((z - 16) / 48) * 48)) {
				if (MainStationDirectionProcedure.execute(Math.round(x / 960) * 960, Math.round(z / 960) * 960)) {
					if (chunk9idx == 3) {
						StreetHouseBlockEastProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48, chunk9id);
					}
					if (chunk9id == 1) {
						PathPlacementNearStreetsEastProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48);
					}
				} else {
					if (chunk9idz == 6) {
						StreetHouseBlockSouthProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48, chunk9id);
					}
					if (chunk9id == 1) {
						PathPlacementNearStreetsSouthProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48);
					}
				}
			} else if (2 == MainStationEdgeProcedure.execute(Math.round((x - 16) / 48) * 48, Math.round((z - 16) / 48) * 48)) {
				if (chunk9id == 1) {
					if (MainStationDirectionProcedure.execute(Math.round(x / 960) * 960, Math.round(z / 960) * 960)) {
						PathPlacementNearStreetsWestProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48);
					} else {
						PathPlacementNearStreetsNorthProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48);
					}
				}
			}
			if (1 == Math.floor(MainStationEdgeProcedure.execute(Math.round((x - 16) / 48) * 48, Math.round((z - 16) / 48) * 48) / 10)) {
				if (chunk9id == 1) {
					if (MainStationDirectionProcedure.execute(Math.round(x / 960) * 960, Math.round(z / 960) * 960)) {
						if (streetid == 3) {
							PathPlacementNearStreetsNorthProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48);
						}
					} else {
						if (streetid == 12) {
							PathPlacementNearStreetsWestProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48);
						}
					}
				}
			} else if (2 == Math.floor(MainStationEdgeProcedure.execute(Math.round((x - 16) / 48) * 48, Math.round((z - 16) / 48) * 48) / 10)) {
				if (MainStationDirectionProcedure.execute(Math.round(x / 960) * 960, Math.round(z / 960) * 960)) {
					if (streetid == 3) {
						if (chunk9id == 1) {
							PathPlacementNearStreetsSouthProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48);
						}
						if (chunk9idz == 6) {
							StreetHouseBlockSouthProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48, chunk9id);
						}
					} else {
						if (chunk9id == 9) {
							StreetHouseBlockSouthEastCornerProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48);
						}
					}
				} else {
					if (streetid == 12) {
						if (chunk9id == 1) {
							PathPlacementNearStreetsEastProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48);
						}
						if (chunk9idx == 3) {
							StreetHouseBlockEastProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48, chunk9id);
						}
					} else {
						if (chunk9id == 9) {
							StreetHouseBlockSouthEastCornerProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48);
						}
					}
				}
			}
		} else {
			if (((DataCalculationPositionHouseblockProcedure.execute(Math.round((x - 16) / 48) * 48, Math.round((z - 16) / 48) * 48).get(0)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) != -1
					|| ((DataCalculationPositionHouseblockProcedure.execute(Math.round((x - 16) / 48) * 48, Math.round((z - 16) / 48) * 48).get(1)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) != -1) {
				if (streetid == 0) {
					DataPlacementHousesProcedure.execute(world, x, y, z);
				} else if (streetid == 3) {
					if (chunk9idz == 6) {
						StreetHouseBlockSouthProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48, chunk9id);
					}
					if (chunk9id == 1) {
						PathPlacementNearStreetsNorthProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48);
						PathPlacementNearStreetsSouthProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48);
					}
				} else if (streetid == 5) {
					DataPlacementHousesCurveNWProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48, chunk9id);
					if (chunk9id == 1) {
						PathPlacementStreetCurveNWProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48);
					}
				} else if (streetid == 6) {
					DataPlacementHousesCurveNEProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48, chunk9id);
					if (chunk9id == 1) {
						PathPlacementStreetCurveNEProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48);
					}
				} else if (streetid == 7) {
					if (chunk9idz == 6) {
						StreetHouseBlockSouthProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48, chunk9id);
					}
					if (chunk9id == 1) {
						PathPlacementNearStreetsSouthProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48);
					}
				} else if (streetid == 9) {
					DataPlacementHousesCurveSWProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48, chunk9id);
					if (chunk9id == 1) {
						PathPlacementStreetCurveSWProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48);
					}
				} else if (streetid == 10) {
					if (chunk9id == 1) {
						PathPlacementStreetCurveSEProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48);
					}
					if (chunk9id == 9) {
						StreetHouseBlockSouthEastCornerProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48);
					}
				} else if (streetid == 11) {
					if (chunk9id == 9) {
						StreetHouseBlockSouthEastCornerProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48);
					}
					if (chunk9id == 1) {
						PathPlacementNearStreetsNorthProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48);
					}
				} else if (streetid == 12) {
					if (chunk9idx == 3) {
						StreetHouseBlockEastProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48, chunk9id);
					}
					if (chunk9id == 1) {
						PathPlacementNearStreetsWestProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48);
						PathPlacementNearStreetsEastProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48);
					}
				} else if (streetid == 13) {
					if (chunk9idx == 3) {
						StreetHouseBlockEastProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48, chunk9id);
					}
					if (chunk9id == 1) {
						PathPlacementNearStreetsEastProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48);
					}
				} else if (streetid == 14) {
					if (chunk9id == 9) {
						StreetHouseBlockSouthEastCornerProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48);
					}
					if (chunk9id == 1) {
						PathPlacementNearStreetsWestProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48);
					}
				} else if (streetid == 15) {
					if (chunk9id == 9) {
						StreetHouseBlockSouthEastCornerProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48);
					}
				}
			}
		}
		return true;
	}
}
