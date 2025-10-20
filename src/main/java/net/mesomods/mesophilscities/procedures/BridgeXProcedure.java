package net.mesomods.mesophilscities.procedures;

public class BridgeXProcedure {
	public static double execute(double x, double z) {
		boolean neartrainnorth = false;
		boolean neartrainsouth = false;
		boolean neartrainwest = false;
		boolean neartraineast = false;
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
		double train = 0;
		double northmin = 0;
		double northmax = 0;
		double southmin = 0;
		double southmax = 0;
		double westmin = 0;
		double westmax = 0;
		double eastmin = 0;
		double eastmax = 0;
		double northmain = 0;
		double southmain = 0;
		double westmain = 0;
		double eastmain = 0;
		double store = 0;
		double bridge = 0;
		x240 = Math.floor((x - 128) / 240) * 240 + 128;
		z240 = Math.floor((z - 128) / 240) * 240 + 128;
		neartrainnorth = (x240 + 112) % 480 == 0 && (TrainCalculationProcedure.execute(x240 + 112, z240 + 16) % 8 > 3 || TrainCalculationProcedure.execute(x240 + 112, z240 - 32) > 7);
		neartrainsouth = (x240 + 112) % 480 == 0 && (TrainCalculationProcedure.execute(x240 + 112, z240 + 256) % 8 > 3 || TrainCalculationProcedure.execute(x240 + 112, z240 + 208) > 7);
		neartrainwest = (z240 + 112) % 480 == 0 && (TrainCalculationProcedure.execute(x240 + 16, z240 + 112) % 2 == 3 || TrainCalculationProcedure.execute(x240 - 32, z240 + 112) % 4 > 1);
		neartraineast = (z240 + 112) % 480 == 0 && (TrainCalculationProcedure.execute(x240 + 256, z240 + 112) % 2 == 3 || TrainCalculationProcedure.execute(x240 + 208, z240 + 112) % 4 > 1);
		train = TrainCalculationProcedure.execute(x240 + 112, z240 + 112);
		seednorth = XorshiftSeedProcedure.execute(x240 + 112, z240, neartrainnorth, false);
		seedsouth = XorshiftSeedProcedure.execute(x240 + 112, z240 + 240, neartrainsouth, false);
		seedwest = XorshiftSeedProcedure.execute(x240, z240 + 112, neartrainwest, true);
		seedeast = XorshiftSeedProcedure.execute(x240 + 240, z240 + 112, neartraineast, true);
		westmin = seedwest < 10 ? seedwest : Math.min(Math.floor(seedwest / 10), seedwest % 10);
		westmax = seedwest < 10 ? seedwest : Math.max(Math.floor(seedwest / 10), seedwest % 10);
		eastmin = seedeast < 10 ? seedeast : Math.min(Math.floor(seedeast / 10), seedeast % 10);
		eastmax = seedeast < 10 ? seedeast : Math.max(Math.floor(seedeast / 10), seedeast % 10);
		northmin = seednorth < 10 ? seednorth : Math.min(Math.floor(seednorth / 10), seednorth % 10);
		northmax = seednorth < 10 ? seednorth : Math.max(Math.floor(seednorth / 10), seednorth % 10);
		southmin = seedsouth < 10 ? seedsouth : Math.min(Math.floor(seedsouth / 10), seedsouth % 10);
		southmax = seedsouth < 10 ? seedsouth : Math.max(Math.floor(seedsouth / 10), seedsouth % 10);
		xmax = Math.max(northmax, southmax);
		xmin = Math.min(northmin, southmin);
		zmax = Math.max(westmax, eastmax);
		zmin = Math.min(westmin, eastmin);
		if (RandomNearTrain1Procedure.execute(x240 + 112, z240 + 112)) {
			northmain = northmax;
		} else {
			northmain = northmin;
		}
		if (RandomNearTrain2Procedure.execute(x240 + 112, z240 + 112)) {
			southmain = southmax;
		} else {
			southmain = southmin;
		}
		if (RandomNearTrain1Procedure.execute(x240 + 112, z240 + 112)) {
			westmain = westmax;
		} else {
			westmain = westmin;
		}
		if (RandomNearTrain2Procedure.execute(x240 + 112, z240 + 112)) {
			eastmain = eastmax;
		} else {
			eastmain = eastmin;
		}
		if (westmin == eastmin && westmax == eastmax) {
			bridge = 2;
			if (RandomNearTrain1Procedure.execute(x240 + 112, z240 + 112)) {
				bridge = bridge + 1;
			}
			if (RandomNearTrain2Procedure.execute(x240 + 112, z240 + 112)) {
				bridge = bridge + 1;
			}
		} else if (westmin == eastmin) {
			bridge = southmain;
		} else if (westmax == eastmax) {
			bridge = northmain;
		} else {
			bridge = northmain;
		}
		return bridge;
	}
}
