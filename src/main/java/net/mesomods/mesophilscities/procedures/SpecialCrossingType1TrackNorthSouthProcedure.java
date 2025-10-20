package net.mesomods.mesophilscities.procedures;

public class SpecialCrossingType1TrackNorthSouthProcedure {
	public static double execute(double x, double z) {
		if (SpecialCrossingType1DirectionProcedure.execute(x, 960 * Math.round((z - 480) / 960) + 480) == 12) {
			return 12;
		}
		return 0;
	}
}
