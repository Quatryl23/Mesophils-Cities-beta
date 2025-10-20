package net.mesomods.mesophilscities.procedures;

import net.minecraft.util.Mth;

public class RandomHouseBlockMidZProcedure {
	public static double execute(double x, double z, double sizez) {
		if (sizez == 1) {
			return 0;
		}
		return Mth.nextInt(CityRandomSourceProcedure.execute(x, z, 1234190425), 1, (int) (sizez - 1));
	}
}
