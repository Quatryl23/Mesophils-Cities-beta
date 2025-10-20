package net.mesomods.mesophilscities.procedures;

public class SimpleCrossingDirectionProcedure {
	public static double execute(double x, double z) {
		double number = 0;
		if (MainStationDirectionProcedure.execute(x - 960, z)) {
			number = number + 1;
		}
		if (MainStationDirectionProcedure.execute(x + 960, z)) {
			number = number + 2;
		}
		if (!MainStationDirectionProcedure.execute(x, z - 960)) {
			number = number + 4;
		}
		if (!MainStationDirectionProcedure.execute(x, z + 960)) {
			number = number + 8;
		}
		if (number == 1 || number == 2 || number == 4 || number == 8) {
			return 0;
		}
		return number;
	}
}
