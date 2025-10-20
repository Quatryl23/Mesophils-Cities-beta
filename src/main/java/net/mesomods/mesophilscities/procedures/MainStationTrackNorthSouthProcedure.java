package net.mesomods.mesophilscities.procedures;

public class MainStationTrackNorthSouthProcedure {
	public static double execute(double x, double z) {
		if (!MainStationDirectionProcedure.execute(x, 960 * Math.round(z / 960))) {
			return 12;
		}
		return 0;
	}
}
