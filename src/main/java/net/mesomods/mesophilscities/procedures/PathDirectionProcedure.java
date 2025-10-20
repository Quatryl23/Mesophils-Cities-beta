package net.mesomods.mesophilscities.procedures;

import net.minecraft.util.Mth;

public class PathDirectionProcedure {
	public static boolean execute(double x, double z) {
		double pathnotz = 0;
		double pathnotx = 0;
		double localseed = 0;
		double randomx = 0;
		double randomz = 0;
		return CityRandomSourceProcedure.execute(x, z, Mth.nextInt(CityRandomSourceProcedure.execute(x, z, 1442190425), 0, 100000000)).nextBoolean();
	}
}
