package net.mesomods.mesophilscities.procedures;

public class NorthmaxProcedure {
	public static double execute(double x, double z) {
		boolean neartrainnorth = false;
		double seednorth = 0;
		double horizontal = 0;
		double vertical = 0;
		double x240 = 0;
		double z240 = 0;
		x240 = Math.floor((x - 128) / 240) * 240 + 128;
		z240 = Math.floor((z - 128) / 240) * 240 + 128;
		horizontal = Math.floor((x - x240) / 48 + 1);
		vertical = Math.floor((z - z240) / 48 + 1);
		neartrainnorth = (x240 + 112) % 480 == 0 && (TrainCalculationProcedure.execute(x240 + 112, z240 + 16) % 8 > 3 || TrainCalculationProcedure.execute(x240 + 112, z240 - 32) > 7);
		seednorth = XorshiftSeedProcedure.execute(x240 + 112, z240, neartrainnorth, false);
		return seednorth < 10 ? seednorth : Math.max(Math.floor(seednorth / 10), seednorth % 10);
	}
}
