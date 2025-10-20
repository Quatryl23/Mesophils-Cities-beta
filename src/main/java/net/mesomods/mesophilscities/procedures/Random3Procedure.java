package net.mesomods.mesophilscities.procedures;

import net.minecraft.util.Mth;

public class Random3Procedure {
	public static double execute(double x, double z) {
		return Mth.nextInt(CityRandomSourceProcedure.execute(x, z, 1244190425), 2, 3);
	}
}
