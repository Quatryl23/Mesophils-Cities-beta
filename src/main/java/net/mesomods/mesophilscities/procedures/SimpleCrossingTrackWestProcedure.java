package net.mesomods.mesophilscities.procedures;

public class SimpleCrossingTrackWestProcedure {
	public static double execute(double x, double z) {
		if (SimpleCrossingDirectionProcedure.execute(960 * Math.round(x / 960), z) % 2 == 1) {
			return 3;
		}
		return 0;
	}
}
