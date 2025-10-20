package net.mesomods.mesophilscities.procedures;

public class TrainStreetCalculationProcedure {
	public static double execute(double x, double z) {
		if (DataCalculationStreetsProcedure.execute(x, z) == 0) {
			return TrainCalculationProcedure.execute(x, z);
		} else if (DataCalculationStreetsProcedure.execute(x, z) <= 15) {
			return DataCalculationStreetsProcedure.execute(x, z);
		} else if (DataCalculationStreetsProcedure.execute(x, z) == 35 || DataCalculationStreetsProcedure.execute(x, z) == 44 || DataCalculationStreetsProcedure.execute(x, z) == 67 || DataCalculationStreetsProcedure.execute(x, z) == 76) {
			return 15;
		} else if (DataCalculationStreetsProcedure.execute(x, z) == 19 || DataCalculationStreetsProcedure.execute(x, z) == 51) {
			return 3;
		} else if (DataCalculationStreetsProcedure.execute(x, z) == 28 || DataCalculationStreetsProcedure.execute(x, z) == 60) {
			return 12;
		}
		return 0;
	}
}
