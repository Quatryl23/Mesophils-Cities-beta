package net.mesomods.mesophilscities.procedures;

public class TestForBridgeProcedure {
	public static boolean execute(double x, double z) {
		if (CityRandomSourceProcedure.execute(x, z, 1202190425).nextBoolean()) {
			return true;
		}
		return true;
	}
}
