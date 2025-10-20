package net.mesomods.mesophilscities.procedures;

import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.nbt.ListTag;

public class HouseBorderSeedEastWestProcedure {
	public static double execute(double x, double z, double Pathshift, double mid, double pos) {
		ListTag seedproperties;
		double border1 = 0;
		double border2 = 0;
		double border3 = 0;
		double path123 = 0;
		double seed = 0;
		double path = 0;
		double midz = 0;
		double posz = 0;
		posz = pos;
		midz = mid;
		if (TrainStreetCalculationProcedure.execute(x + 16, z) == 0 && PathDirectionProcedure.execute(x + 16, z) || TrainStreetCalculationProcedure.execute(x - 32, z) == 0 && PathDirectionProcedure.execute(x - 32, z)) {
			if (posz > midz) {
				path123 = 1;
			} else if (posz == midz) {
				path123 = 2;
			} else if (posz < midz) {
				path123 = 3;
			}
		} else if (!(TrainCalculationProcedure.execute(x + 16, z) == 0 || TrainCalculationProcedure.execute(x - 32, z) == 0)
				|| (22 == MainStationEdgeProcedure.execute(x + 16, z) || 12 == MainStationEdgeProcedure.execute(x - 32, z)) && !MainStationDirectionProcedure.execute(Math.round(x / 960) * 960, Math.round(z / 960) * 960)) {
			return 13;
		} else if (!(DataCalculationStreetsProcedure.execute(x + 16, z) == 0 || DataCalculationStreetsProcedure.execute(x - 32, z) == 0)) {
			if (HouseDistanceZProcedure.execute(x + 16, z) == 0) {
				return 0;
			} else {
				return 13;
			}
		}
		return 30 * path123 + 10 * Pathshift + Mth.nextInt(RandomSource.create((long) (2.3 * x + 4.7 * z)), 1, 2);
	}
}
