package net.mesomods.mesophilscities.procedures;

public class RandomNearTrain2Procedure {
	public static boolean execute(double x, double z) {
		return CityRandomSourceProcedure.execute(x, z, 1157190425).nextBoolean();
	}
}
