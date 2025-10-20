package net.mesomods.mesophilscities.procedures;

public class SpecialCrossingType2TrackEastWestProcedure {
	public static double execute(double x, double z) {
		if (SpecialCrossingType2DirectionProcedure.execute(960 * Math.round((x - 480) / 960) + 480, z) == 3) {
			return 3;
		}
		return 0;
	}
}
