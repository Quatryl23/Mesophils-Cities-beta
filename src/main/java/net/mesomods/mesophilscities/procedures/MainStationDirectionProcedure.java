package net.mesomods.mesophilscities.procedures;

public class MainStationDirectionProcedure {
	public static boolean execute(double x, double z) {
		double localseed = 0;
		return CityRandomSourceProcedure.execute(x, z, 1206190425).nextBoolean();
	}
}
