package net.mesomods.mesophilscities.procedures;

public class RandomPathShiftSeedProcedure {
	public static double execute(double x, double z) {
		if (CityRandomSourceProcedure.execute(x, z, 1217190425).nextBoolean()) {
			return 1;
		}
		return 0;
	}
}
