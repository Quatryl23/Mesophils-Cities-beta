package net.mesomods.mesophilscities.procedures;

public class EastminProcedure {
	public static double execute(double x, double z) {
		boolean neartraineast = false;
		double seedeast = 0;
		double horizontal = 0;
		double vertical = 0;
		double x240 = 0;
		double z240 = 0;
		x240 = Math.floor((x - 128) / 240) * 240 + 128;
		z240 = Math.floor((z - 128) / 240) * 240 + 128;
		horizontal = Math.floor((x - x240) / 48 + 1);
		vertical = Math.floor((z - z240) / 48 + 1);
		neartraineast = (z240 + 112) % 480 == 0 && (TrainCalculationProcedure.execute(x240 + 256, z240 + 112) % 2 == 3 || TrainCalculationProcedure.execute(x240 + 208, z240 + 112) % 4 > 1);
		seedeast = XorshiftSeedProcedure.execute(x240 + 240, z240 + 112, neartraineast, true);
		return seedeast < 10 ? seedeast : Math.min(Math.floor(seedeast / 10), seedeast % 10);
	}
}
