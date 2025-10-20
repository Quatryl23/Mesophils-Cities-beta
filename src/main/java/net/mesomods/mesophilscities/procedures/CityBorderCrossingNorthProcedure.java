package net.mesomods.mesophilscities.procedures;

public class CityBorderCrossingNorthProcedure {
	public static double execute(double x, double z) {
		double number = 0;
		if (!MainStationDirectionProcedure.execute(x, z + 480)) {
			number = 8;
			if (SimpleCrossingDirectionProcedure.execute(x, z - 480) != 0) {
				number = number + 4;
			}
			if (SpecialCrossingType1DirectionProcedure.execute(x - 480, z) == 3) {
				number = number + 1;
			}
			if (SpecialCrossingType2DirectionProcedure.execute(x + 480, z) == 3) {
				number = number + 2;
			}
			return number;
		}
		return 0;
	}
}
