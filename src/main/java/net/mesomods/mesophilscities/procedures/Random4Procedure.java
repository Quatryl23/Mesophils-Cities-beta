package net.mesomods.mesophilscities.procedures;

import net.minecraft.util.Mth;

public class Random4Procedure {
	public static double execute(double x, double z) {
		return Mth.nextInt(CityRandomSourceProcedure.execute(x, z, 1246190425), 11, 12);
	}
}
