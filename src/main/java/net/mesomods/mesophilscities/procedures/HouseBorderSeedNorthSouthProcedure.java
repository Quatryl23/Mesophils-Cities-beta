package net.mesomods.mesophilscities.procedures;

import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.nbt.ListTag;

public class HouseBorderSeedNorthSouthProcedure {
	public static double execute(double x, double z, double Pathshift, double mid, double pos) {
		ListTag seedproperties;
		double border1 = 0;
		double border2 = 0;
		double border3 = 0;
		double midx = 0;
		double posx = 0;
		double path123 = 0;
		double seed = 0;
		double path = 0;
		posx = pos;
		midx = mid;
		if (0 == TrainStreetCalculationProcedure.execute(x, z + 16) && !PathDirectionProcedure.execute(x, z + 16) || 0 == TrainStreetCalculationProcedure.execute(x, z - 32) && !PathDirectionProcedure.execute(x, z - 32)) {
			if (posx > midx) {
				path123 = 1;
			} else if (posx == midx) {
				path123 = 2;
			} else if (posx < midx) {
				path123 = 3;
			}
		} else if (!(0 == TrainCalculationProcedure.execute(x, z + 16) || 0 == TrainCalculationProcedure.execute(x, z - 32))
				|| (22 == MainStationEdgeProcedure.execute(x, z + 16) || 12 == MainStationEdgeProcedure.execute(x, z - 32)) && MainStationDirectionProcedure.execute(Math.round(x / 960) * 960, Math.round(z / 960) * 960)) {
			return 13;
		} else if (!(0 == DataCalculationStreetsProcedure.execute(x, z + 16) || 0 == DataCalculationStreetsProcedure.execute(x, z - 32))) {
			if (HouseDistanceXProcedure.execute(x, z + 16) == 0) {
				return 0;
			} else {
				return 13;
			}
		}
		return 30 * path123 + 10 * Pathshift + Mth.nextInt(RandomSource.create((long) (1.1 * x + 2.9 * z)), 1, 2);
	}
}
