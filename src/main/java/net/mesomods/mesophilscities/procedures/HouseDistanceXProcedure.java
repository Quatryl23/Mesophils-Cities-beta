package net.mesomods.mesophilscities.procedures;

public class HouseDistanceXProcedure {
	public static double execute(double x, double z) {
		double Trainid = 0;
		double streetid = 0;
		Trainid = TrainCalculationProcedure.execute(x, z);
		streetid = DataCalculationStreetsProcedure.execute(x, z);
		if (Trainid != 0 && Trainid != 3 || MainStationDirectionProcedure.execute(Math.round(x / 960) * 960, Math.round(z / 960) * 960) && MainStationEdgeProcedure.execute(x, z) % 10 != 0) {
			return 2;
		}
		return 0;
	}
}
