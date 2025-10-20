package net.mesomods.mesophilscities.procedures;

public class MainStationIDProcedure {
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
			id = 10 * ((4 + lmax) - ((((StationDirection ? x : z) + 144) % 960 + 960) % 960) / 48);
			id = id + ((((StationDirection ? z : x) + 144) % 960 + 960) % 960) / 48 + 2 + wmin;
			if (((((StationDirection ? x : z) + 144) % 960 + 960) % 960) / 48 >= lmin + 3 && ((((StationDirection ? z : x) + 144) % 960 + 960) % 960) / 48 >= wmin + 3 && ((((StationDirection ? x : z) + 144) % 960 + 960) % 960) / 48 <= lmax + 3
					&& ((((StationDirection ? z : x) + 144) % 960 + 960) % 960) / 48 <= wmax + 3) {
				return id;
			} else {
				return 0;
			}
		}
		return 0;
	}
}
