package net.mesomods.mesophilscities.procedures;

import net.minecraft.util.RandomSource;

import net.mesomods.mesophilscities.SeedCache;

public class CityRandomSourceProcedure {
	public static RandomSource execute(double x, double z, double seed) {
		return RandomSource.create((long) ((int) x ^ (long) SeedCache.CITY_SEED ^ (int) z ^ (int) seed));
	}
}
