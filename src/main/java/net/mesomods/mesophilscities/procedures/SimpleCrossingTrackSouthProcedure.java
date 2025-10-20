package net.mesomods.mesophilscities.procedures;

public class SimpleCrossingTrackSouthProcedure {
	public static double execute(double x, double z) {
		if (SimpleCrossingDirectionProcedure.execute(x, 960 * Math.round(z / 960)) > 7) {
			return 12;
		}
		return 0;
	}
}
