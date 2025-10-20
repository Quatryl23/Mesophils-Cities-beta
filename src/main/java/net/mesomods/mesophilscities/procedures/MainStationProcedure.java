package net.mesomods.mesophilscities.procedures;

public class MainStationProcedure {
	public static boolean execute(double x, double z) {
		boolean neartraineast = false;
		boolean neartrainnorth = false;
		boolean neartrainsouth = false;
		boolean neartrainwest = false;
		double x240 = 0;
		double horizontal = 0;
		double vertical = 0;
		double z240 = 0;
		return ((Math.floor((x - 128) / 240) * 240 + 128) % 1290) % 1290 == 0 && ((Math.floor((z - 128) / 240) * 240 + 128) % 1290) % 1290 == 0;
	}
}
