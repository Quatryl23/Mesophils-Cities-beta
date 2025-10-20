package net.mesomods.mesophilscities.procedures;

public class MainStationEdgeProcedure {
	public static double execute(double x, double z) {
		boolean StationDirection = false;
		double id = 0;
		double lmin = 0;
		double lmax = 0;
		double wmin = 0;
		double wmax = 0;
		if (((x % 1920 + 1920) % 1920 <= 96 || (x % 1920 + 1920) % 1920 >= 1824) && ((z % 1920 + 1920) % 1920 <= 96 || (z % 1920 + 1920) % 1920 >= 1824)
				|| (x % 1920 + 1920) % 1920 >= 864 && (x % 1920 + 1920) % 1920 <= 1056 && (z % 1920 + 1920) % 1920 >= 864 && (z % 1920 + 1920) % 1920 <= 1056) {
			StationDirection = MainStationDirectionProcedure.execute(Math.round(x / 960) * 960, Math.round(z / 960) * 960);
			lmin = LminProcedure.execute();
			lmax = LmaxProcedure.execute();
			wmin = WminProcedure.execute();
			wmax = WmaxProcedure.execute();
			if (((((StationDirection ? x : z) + 144) % 960 + 960) % 960) / 48 == lmin + 3) {
				id = id + 2;
			}
			if (((((StationDirection ? z : x) + 144) % 960 + 960) % 960) / 48 == wmin + 3) {
				id = id + 10;
			}
			if (((((StationDirection ? x : z) + 144) % 960 + 960) % 960) / 48 == lmax + 3) {
				id = id + 1;
			}
			if (((((StationDirection ? z : x) + 144) % 960 + 960) % 960) / 48 == wmax + 3) {
				id = id + 20;
			}
			if (((((StationDirection ? z : x) + 144) % 960 + 960) % 960) / 48 == 3) {
				id = id + 30;
			}
			return id;
		}
		return 0;
	}
}
