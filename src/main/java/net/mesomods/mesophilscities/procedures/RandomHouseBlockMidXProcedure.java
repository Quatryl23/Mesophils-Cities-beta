package net.mesomods.mesophilscities.procedures;

import net.minecraft.util.Mth;

public class RandomHouseBlockMidXProcedure {
	public static double execute(double x, double z, double sizex) {
		if (sizex == 1) {
			return 0;
		}
		return Mth.nextInt(CityRandomSourceProcedure.execute(x, z, 1230190425), 1, (int) (sizex - 1));
	}
}
