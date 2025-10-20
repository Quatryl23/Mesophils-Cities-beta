package net.mesomods.mesophilscities.procedures;

public class SouthminProcedure {
	public static double execute(double x, double z) {
		boolean neartrainsouth = false;
		double seedsouth = 0;
		double horizontal = 0;
		double vertical = 0;
		double x240 = 0;
		double z240 = 0;
		x240 = Math.floor((x - 128) / 240) * 240 + 128;
		z240 = Math.floor((z - 128) / 240) * 240 + 128;
		horizontal = Math.floor((x - x240) / 48 + 1);
		vertical = Math.floor((z - z240) / 48 + 1);
		neartrainsouth = (x240 + 112) % 480 == 0 && (TrainCalculationProcedure.execute(x240 + 112, z240 + 256) % 8 > 3 || TrainCalculationProcedure.execute(x240 + 112, z240 + 208) > 7);
		seedsouth = XorshiftSeedProcedure.execute(x240 + 112, z240 + 240, neartrainsouth, false);
		return seedsouth < 10 ? seedsouth : Math.min(Math.floor(seedsouth / 10), seedsouth % 10);
	}
}
