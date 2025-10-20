package net.mesomods.mesophilscities.procedures;

public class SimpleCrossingTrackNorthProcedure {
	public static double execute(double x, double z) {
		if (SimpleCrossingDirectionProcedure.execute(x, 960 * Math.round(z / 960)) % 8 > 3) {
			return 12;
		}
		return 0;
	}
}
