package net.mesomods.mesophilscities.procedures;

public class RandomDirectionProcedure {
	public static boolean execute(double x, double z) {
		return CityRandomSourceProcedure.execute(x, z, 1247190425).nextBoolean();
	}
}
