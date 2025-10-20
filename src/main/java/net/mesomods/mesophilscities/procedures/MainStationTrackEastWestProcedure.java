package net.mesomods.mesophilscities.procedures;

public class MainStationTrackEastWestProcedure {
	public static double execute(double x, double z) {
		if (MainStationDirectionProcedure.execute(960 * Math.round(x / 960), z)) {
			return 3;
		}
		return 0;
	}
}
