package net.mesomods.mesophilscities.procedures;

public class ZmaxProcedure {
	public static double execute(double x, double z) {
		boolean neartrainwest = false;
		boolean neartraineast = false;
		double seedeast = 0;
		double seedwest = 0;
		double horizontal = 0;
		double vertical = 0;
		double x240 = 0;
		double z240 = 0;
		x240 = Math.floor((x - 128) / 240) * 240 + 128;
		z240 = Math.floor((z - 128) / 240) * 240 + 128;
		horizontal = Math.floor((x - x240) / 48 + 1);
		vertical = Math.floor((z - z240) / 48 + 1);
		neartrainwest = (z240 + 112) % 480 == 0 && (TrainCalculationProcedure.execute(x240 + 16, z240 + 112) % 2 == 3 || TrainCalculationProcedure.execute(x240 - 32, z240 + 112) % 4 > 1);
		neartraineast = (z240 + 112) % 480 == 0 && (TrainCalculationProcedure.execute(x240 + 256, z240 + 112) % 2 == 3 || TrainCalculationProcedure.execute(x240 + 208, z240 + 112) % 4 > 1);
		seedwest = XorshiftSeedProcedure.execute(x240, z240 + 112, neartrainwest, true);
		seedeast = XorshiftSeedProcedure.execute(x240 + 240, z240 + 112, neartraineast, true);
		return Math.max(seedwest < 10 ? seedwest : Math.max(Math.floor(seedwest / 10), seedwest % 10), seedeast < 10 ? seedeast : Math.max(Math.floor(seedeast / 10), seedeast % 10));
	}
}
