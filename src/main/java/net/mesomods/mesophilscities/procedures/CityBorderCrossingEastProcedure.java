package net.mesomods.mesophilscities.procedures;

public class CityBorderCrossingEastProcedure {
	public static double execute(double x, double z) {
		double number = 0;
		if (MainStationDirectionProcedure.execute(x - 480, z)) {
			number = 1;
			if (SimpleCrossingDirectionProcedure.execute(x + 480, z) != 0) {
				number = number + 2;
			}
			if (SpecialCrossingType2DirectionProcedure.execute(x, z - 480) == 12) {
				number = number + 4;
			}
			if (SpecialCrossingType1DirectionProcedure.execute(x, z + 480) == 12) {
				number = number + 8;
			}
			return number;
		}
		return 0;
	}
}
