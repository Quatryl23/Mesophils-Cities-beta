package net.mesomods.mesophilscities.procedures;

public class RandomNearTrain1Procedure {
	public static boolean execute(double x, double z) {
		return CityRandomSourceProcedure.execute(x, z, 1154190425).nextBoolean();
	}
}
