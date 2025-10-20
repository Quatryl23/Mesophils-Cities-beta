package net.mesomods.mesophilscities.procedures;

public class DataCalculationStreetsProcedure {
	public static double execute(double x, double z) {
		double seednorth = 0;
		double seedsouth = 0;
		double seedeast = 0;
		double seedwest = 0;
		double horizontal = 0;
		double vertical = 0;
		double x240 = 0;
		double z240 = 0;
		double Street = 0;
		double xmax = 0;
		double xmin = 0;
		double zmax = 0;
		double zmin = 0;
		boolean neartrainnorth = false;
		boolean neartrainsouth = false;
		boolean neartrainwest = false;
		boolean neartraineast = false;
		x240 = Math.floor((x - 128) / 240) * 240 + 128;
		z240 = Math.floor((z - 128) / 240) * 240 + 128;
		horizontal = Math.floor((x - x240) / 48 + 1);
		vertical = Math.floor((z - z240) / 48 + 1);
		neartrainnorth = (x240 + 112) % 480 == 0 && (TrainCalculationProcedure.execute(x240 + 112, z240 + 16) % 8 > 3 || TrainCalculationProcedure.execute(x240 + 112, z240 - 32) > 7);
		neartrainsouth = (x240 + 112) % 480 == 0 && (TrainCalculationProcedure.execute(x240 + 112, z240 + 256) % 8 > 3 || TrainCalculationProcedure.execute(x240 + 112, z240 + 208) > 7);
		neartrainwest = (z240 + 112) % 480 == 0 && (TrainCalculationProcedure.execute(x240 + 16, z240 + 112) % 2 == 3 || TrainCalculationProcedure.execute(x240 - 32, z240 + 112) % 4 > 1);
		neartraineast = (z240 + 112) % 480 == 0 && (TrainCalculationProcedure.execute(x240 + 256, z240 + 112) % 2 == 3 || TrainCalculationProcedure.execute(x240 + 208, z240 + 112) % 4 > 1);
		if (neartrainnorth || neartrainsouth || neartrainwest || neartraineast) {
			return DataCalculationStreetsNearTrainProcedure.execute(x240, z240, neartraineast, neartrainnorth, neartrainsouth, neartrainwest, horizontal, vertical);
		} else {
			seednorth = XorshiftSeedProcedure.execute(x240 + 112, z240, neartrainnorth, false);
			seedsouth = XorshiftSeedProcedure.execute(x240 + 112, z240 + 240, neartrainsouth, false);
			seedwest = XorshiftSeedProcedure.execute(x240, z240 + 112, neartrainwest, true);
			seedeast = XorshiftSeedProcedure.execute(x240 + 240, z240 + 112, neartraineast, true);
			xmax = Math.max(seednorth < 10 ? seednorth : Math.max(Math.floor(seednorth / 10), seednorth % 10), seedsouth < 10 ? seedsouth : Math.max(Math.floor(seedsouth / 10), seedsouth % 10));
			xmin = Math.min(seednorth < 10 ? seednorth : Math.min(Math.floor(seednorth / 10), seednorth % 10), seedsouth < 10 ? seedsouth : Math.min(Math.floor(seedsouth / 10), seedsouth % 10));
			zmax = Math.max(seedeast < 10 ? seedeast : Math.max(Math.floor(seedeast / 10), seedeast % 10), seedwest < 10 ? seedwest : Math.max(Math.floor(seedwest / 10), seedwest % 10));
			zmin = Math.min(seedeast < 10 ? seedeast : Math.min(Math.floor(seedeast / 10), seedeast % 10), seedwest < 10 ? seedwest : Math.min(Math.floor(seedwest / 10), seedwest % 10));
			if (StreetDependency1Procedure.execute(true, xmax, xmin, zmin, seednorth, horizontal, vertical)) {
				Street = Street + 4;
			}
			if (StreetDependency1Procedure.execute(false, xmax, xmin, zmax, seedsouth, horizontal, vertical)) {
				Street = Street + 8;
			}
			if (StreetDependency1Procedure.execute(false, zmax, zmin, xmax, seedeast, vertical, horizontal)) {
				Street = Street + 2;
			}
			if (StreetDependency1Procedure.execute(true, zmax, zmin, xmin, seedwest, vertical, horizontal)) {
				Street = Street + 1;
			}
			if (Street == 1) {
				if (xmax > horizontal) {
					return 3;
				}
			} else if (Street == 2) {
				if (xmin < horizontal) {
					return 3;
				}
			} else if (Street == 3) {
				return 3;
			} else if (Street == 4) {
				if (zmax > vertical) {
					return 12;
				}
			} else if (Street == 5) {
				if (StreetDependency2plusProcedure.execute(xmax, zmax, xmin, zmin, vertical, horizontal)) {
					if (StreetDependency2plusProcedure.execute(zmax, xmax, zmin, xmin, horizontal, vertical)) {
						return 15;
					} else {
						return 13;
					}
				} else {
					if (StreetDependency2plusProcedure.execute(zmax, xmax, zmin, xmin, horizontal, vertical)) {
						return 7;
					} else {
						return 5;
					}
				}
			} else if (Street == 6) {
				if (StreetDependency2plusProcedure.execute(xmax, zmax, xmin, zmin, vertical, horizontal)) {
					if (StreetDependency3plusProcedure.execute(zmax, xmax, zmin, xmin, horizontal, vertical)) {
						return 15;
					} else {
						return 14;
					}
				} else {
					if (StreetDependency3plusProcedure.execute(zmax, xmax, zmin, xmin, horizontal, vertical)) {
						return 7;
					} else {
						return 6;
					}
				}
			} else if (Street == 7) {
				if (StreetDependency2plusProcedure.execute(xmax, zmax, xmin, zmin, vertical, horizontal)) {
					return 15;
				} else {
					return 7;
				}
			} else if (Street == 8) {
				if (zmin < vertical) {
					return 12;
				}
			} else if (Street == 9) {
				if (StreetDependency3plusProcedure.execute(xmax, zmax, xmin, zmin, vertical, horizontal)) {
					if (StreetDependency2plusProcedure.execute(zmax, xmax, zmin, xmin, horizontal, vertical)) {
						return 15;
					} else {
						return 13;
					}
				} else {
					if (StreetDependency2plusProcedure.execute(zmax, xmax, zmin, xmin, horizontal, vertical)) {
						return 11;
					} else {
						return 9;
					}
				}
			} else if (Street == 10) {
				if (StreetDependency3plusProcedure.execute(xmax, zmax, xmin, zmin, vertical, horizontal)) {
					if (StreetDependency3plusProcedure.execute(zmax, xmax, zmin, xmin, horizontal, vertical)) {
						return 15;
					} else {
						return 14;
					}
				} else {
					if (StreetDependency3plusProcedure.execute(zmax, xmax, zmin, xmin, horizontal, vertical)) {
						return 11;
					} else {
						return 10;
					}
				}
			} else if (Street == 11) {
				if (StreetDependency3plusProcedure.execute(xmax, zmax, xmin, zmin, vertical, horizontal)) {
					return 15;
				} else {
					return 11;
				}
			} else if (Street == 12) {
				return 12;
			} else if (Street == 13) {
				if (StreetDependency2plusProcedure.execute(zmax, xmax, zmin, xmin, horizontal, vertical)) {
					return 15;
				} else {
					return 13;
				}
			} else if (Street == 14) {
				if (StreetDependency3plusProcedure.execute(zmax, xmax, zmin, xmin, horizontal, vertical)) {
					return 15;
				} else {
					return 14;
				}
			} else if (Street == 15) {
				return 15;
			}
		}
		return 0;
	}
}
