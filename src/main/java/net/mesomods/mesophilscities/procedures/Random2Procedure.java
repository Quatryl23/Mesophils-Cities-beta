package net.mesomods.mesophilscities.procedures;

import net.minecraft.util.Mth;

public class Random2Procedure {
	public static double execute(double x, double z) {
		return Mth.nextInt(CityRandomSourceProcedure.execute(x, z, 1241190425), 11, 12);
	}
}
