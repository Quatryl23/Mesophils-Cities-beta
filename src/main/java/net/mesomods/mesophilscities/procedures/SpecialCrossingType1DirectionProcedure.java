package net.mesomods.mesophilscities.procedures;

public class SpecialCrossingType1DirectionProcedure {
	public static double execute(double x, double z) {
		if (MainStationDirectionProcedure.execute(x - 480, z - 480) && MainStationDirectionProcedure.execute(x + 480, z + 480)) {
			return 12;
		} else if (!MainStationDirectionProcedure.execute(x - 480, z - 480) && !MainStationDirectionProcedure.execute(x + 480, z + 480)) {
			return 3;
		}
		return 0;
	}
}
