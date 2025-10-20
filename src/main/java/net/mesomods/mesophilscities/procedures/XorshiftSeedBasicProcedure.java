package net.mesomods.mesophilscities.procedures;

import net.minecraft.util.Mth;

public class XorshiftSeedBasicProcedure {
	public static double execute(double x, double z) {
		double localseed = 0;
		return Mth.nextInt(CityRandomSourceProcedure.execute(x, z, 1904180425), 0, 24);
	}
}
