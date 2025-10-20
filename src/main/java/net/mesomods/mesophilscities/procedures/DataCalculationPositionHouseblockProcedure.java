package net.mesomods.mesophilscities.procedures;

import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.IntTag;

public class DataCalculationPositionHouseblockProcedure {
	public static ListTag execute(double x, double z) {
		boolean neartrainnorth = false;
		boolean neartrainsouth = false;
		boolean neartrainwest = false;
		boolean neartraineast = false;
		ListTag sizexz_posxz;
		double seednorth = 0;
		double seedsouth = 0;
		double seedeast = 0;
		double seedwest = 0;
		double horizontal = 0;
		double vertical = 0;
		double x240 = 0;
		double z240 = 0;
		double xmax = 0;
		double xmin = 0;
		double zmax = 0;
		double zmin = 0;
		double northmax = 0;
		double southmax = 0;
		double northmin = 0;
		double southmin = 0;
		double westmax = 0;
		double eastmax = 0;
		double westmin = 0;
		double eastmin = 0;
		double position = 0;
		double sizex = 0;
		double sizez = 0;
		double positionx = 0;
		double positionz = 0;
		double temporal_testing_value_x = 0;
		double temporal_testing_value_z = 0;
		double trainidnorth = 0;
		double trainidsouth = 0;
		double trainidwest = 0;
		double trainideast = 0;
		double train = 0;
		double bridge = 0;
		double train2 = 0;
		x240 = Math.floor((x - 128) / 240) * 240 + 128;
		z240 = Math.floor((z - 128) / 240) * 240 + 128;
		horizontal = Math.floor((x - x240) / 48 + 1);
		vertical = Math.floor((z - z240) / 48 + 1);
		neartrainnorth = (x240 + 112) % 480 == 0 && (TrainCalculationProcedure.execute(x240 + 112, z240 + 16) % 8 > 3 || TrainCalculationProcedure.execute(x240 + 112, z240 - 32) > 7);
		neartrainsouth = (x240 + 112) % 480 == 0 && (TrainCalculationProcedure.execute(x240 + 112, z240 + 256) % 8 > 3 || TrainCalculationProcedure.execute(x240 + 112, z240 + 208) > 7);
		neartrainwest = (z240 + 112) % 480 == 0 && (TrainCalculationProcedure.execute(x240 + 16, z240 + 112) % 2 == 3 || TrainCalculationProcedure.execute(x240 - 32, z240 + 112) % 4 > 1);
		neartraineast = (z240 + 112) % 480 == 0 && (TrainCalculationProcedure.execute(x240 + 256, z240 + 112) % 2 == 3 || TrainCalculationProcedure.execute(x240 + 208, z240 + 112) % 4 > 1);
		train = TrainCalculationProcedure.execute(x240 + 112, z240 + 112);
		trainidnorth = TrainCalculationProcedure.execute(x240 + 112, z240 - 128);
		trainidsouth = TrainCalculationProcedure.execute(x240 + 112, z240 + 352);
		trainidwest = TrainCalculationProcedure.execute(x240 - 128, z240 + 112);
		trainideast = TrainCalculationProcedure.execute(x240 + 352, z240 + 112);
		seednorth = XorshiftSeedProcedure.execute(x240 + 112, z240, neartrainnorth, false);
		seedsouth = XorshiftSeedProcedure.execute(x240 + 112, z240 + 240, neartrainsouth, false);
		seedwest = XorshiftSeedProcedure.execute(x240, z240 + 112, neartrainwest, true);
		seedeast = XorshiftSeedProcedure.execute(x240 + 240, z240 + 112, neartraineast, true);
		northmax = seednorth < 10 ? seednorth : Math.max(Math.floor(seednorth / 10), seednorth % 10);
		northmin = seednorth < 10 ? seednorth : Math.min(Math.floor(seednorth / 10), seednorth % 10);
		westmax = seedwest < 10 ? seedwest : Math.max(Math.floor(seedwest / 10), seedwest % 10);
		westmin = seedwest < 10 ? seedwest : Math.min(Math.floor(seedwest / 10), seedwest % 10);
		southmax = seedsouth < 10 ? seedsouth : Math.max(Math.floor(seedsouth / 10), seedsouth % 10);
		southmin = seedsouth < 10 ? seedsouth : Math.min(Math.floor(seedsouth / 10), seedsouth % 10);
		eastmax = seedeast < 10 ? seedeast : Math.max(Math.floor(seedeast / 10), seedeast % 10);
		eastmin = seedeast < 10 ? seedeast : Math.min(Math.floor(seedeast / 10), seedeast % 10);
		xmax = Math.max(southmax, northmax);
		xmin = Math.min(southmin, northmin);
		zmax = Math.max(westmax, eastmax);
		zmin = Math.min(westmin, eastmin);
		if (train == 0) {
			if (horizontal >= xmin && horizontal < xmax && vertical >= zmin && vertical < zmax) {
				position = 0;
			} else if (horizontal >= southmax && vertical >= eastmax) {
				position = 1;
			} else if (horizontal >= northmax && vertical < eastmin) {
				position = 2;
			} else if (horizontal < southmin && vertical >= westmax) {
				position = 3;
			} else if (horizontal < northmin && vertical < westmin) {
				position = 4;
			} else if (vertical < zmin && horizontal >= northmin && horizontal < northmax) {
				position = 5;
			} else if (vertical >= zmax && horizontal >= southmin && horizontal < southmax) {
				position = 6;
			} else if (horizontal < xmin && vertical >= westmin && vertical < westmax) {
				position = 7;
			} else if (horizontal >= xmax && vertical >= eastmin && vertical < eastmax) {
				position = 8;
			}
		} else {
			if (horizontal >= southmax && vertical >= eastmax) {
				position = 1;
			} else if (horizontal >= northmax && vertical < eastmin) {
				position = 2;
			} else if (horizontal < southmin && vertical >= westmax) {
				position = 3;
			} else if (horizontal < northmin && vertical < westmin) {
				position = 4;
			} else if ((train != 3 && train != 11 && vertical < zmin || (train == 3 || train == 11) && vertical < (RandomNearTrain1Procedure.execute(x240 + 112, z240 + 112) ? westmin : eastmin)) && horizontal >= northmin && horizontal < northmax) {
				position = 5;
			} else if ((train != 3 && train != 7 && vertical >= zmax || (train == 3 || train == 7) && vertical >= (RandomNearTrain2Procedure.execute(x240 + 112, z240 + 112) ? westmax : eastmax)) && horizontal >= southmin && horizontal < southmax) {
				position = 6;
			} else if ((train != 12 && train != 13 && horizontal < xmin || (train == 12 || train == 13) && horizontal < (RandomNearTrain1Procedure.execute(x240 + 112, z240 + 112) ? northmin : southmin)) && vertical >= westmin && vertical < westmax) {
				position = 7;
			} else if ((train != 12 && train != 13 && horizontal >= xmax || (train == 12 || train == 13) && horizontal >= (RandomNearTrain2Procedure.execute(x240 + 112, z240 + 112) ? northmax : southmax)) && vertical >= eastmin
					&& vertical < eastmax) {
				position = 8;
			} else {
				position = 9;
			}
			if (neartrainnorth && position == 5 || neartrainsouth && position == 6 || neartrainwest && position == 7 || neartraineast && position == 8
					|| position < 5 && horizontal >= xmin && horizontal < xmax && vertical >= zmin && vertical < zmax && (train == 5 || train == 6 || train == 9 || train == 10)) {
				position = 9;
			}
		}
		if (position == 0) {
			sizex = xmax - xmin;
			sizez = zmax - zmin;
			positionx = horizontal - xmin;
			positionz = vertical - zmin;
		} else if (position == 1) {
			sizex = 5 - southmax + NorthminProcedure.execute(x240 + 240, z240 + 240);
			sizez = 5 - eastmax + WestminProcedure.execute(x240 + 240, z240 + 240);
			positionx = horizontal - southmax;
			positionz = vertical - eastmax;
		} else if (position == 2) {
			sizex = 5 - northmax + SouthminProcedure.execute(x240 + 240, z240 - 240);
			sizez = 5 - WestmaxProcedure.execute(x240 + 240, z240 - 240) + eastmin;
			positionx = horizontal - northmax;
			positionz = vertical + 5 - WestmaxProcedure.execute(x240 + 240, z240 - 240);
		} else if (position == 3) {
			sizex = 5 - NorthmaxProcedure.execute(x240 - 240, z240 + 240) + southmin;
			sizez = 5 - westmax + EastminProcedure.execute(x240 - 240, z240 + 240);
			positionx = horizontal + 5 - NorthmaxProcedure.execute(x240 - 240, z240 + 240);
			positionz = vertical - westmax;
		} else if (position == 4) {
			sizex = 5 - SouthmaxProcedure.execute(x240 - 240, z240 - 240) + northmin;
			sizez = 5 - EastmaxProcedure.execute(x240 - 240, z240 - 240) + westmin;
			positionx = horizontal + 5 - SouthmaxProcedure.execute(x240 - 240, z240 - 240);
			positionz = vertical + 5 - EastmaxProcedure.execute(x240 - 240, z240 - 240);
		} else if (position == 5) {
			sizex = northmax - northmin;
			if (train == 3 || train == 7) {
				sizez = 5 - (RandomNearTrain2Procedure.execute(x240 + 112, z240 - 128) ? WestmaxProcedure.execute(x240, z240 - 240) : EastmaxProcedure.execute(x240, z240 - 240)) + zmin;
			} else {
				sizez = 5 - ZmaxProcedure.execute(x240, z240 - 240) + zmin;
			}
			positionx = horizontal - northmin;
			if (train == 3 || train == 7) {
				positionz = 5 - (RandomNearTrain2Procedure.execute(x240 + 112, z240 - 128) ? WestmaxProcedure.execute(x240, z240 - 240) : EastmaxProcedure.execute(x240, z240 - 240)) + vertical;
			} else {
				positionz = 5 - ZmaxProcedure.execute(x240, z240 - 240) + vertical;
			}
		} else if (position == 6) {
			sizex = southmax - southmin;
			if (train == 3 || train == 11) {
				sizez = 5 - zmax + (RandomNearTrain1Procedure.execute(x240 + 112, z240 + 352) ? WestminProcedure.execute(x240, z240 + 240) : EastminProcedure.execute(x240, z240 + 240));
			} else {
				sizez = 5 - zmax + ZminProcedure.execute(x240, z240 + 240);
			}
			positionx = horizontal - southmin;
			positionz = vertical - zmax;
		} else if (position == 7) {
			if (train == 12 || train == 14) {
				sizex = 5 - xmax + (RandomNearTrain2Procedure.execute(x240 - 128, z240 + 112) ? NorthmaxProcedure.execute(x240 - 240, z240) : SouthmaxProcedure.execute(x240 - 240, z240));
			} else {
				sizex = 5 - XmaxProcedure.execute(x240 - 240, z240) + xmin;
			}
			sizez = westmax - westmin;
			if (train == 12 || train == 14) {
				positionx = horizontal + 5 - (RandomNearTrain2Procedure.execute(x240 - 128, z240 + 112) ? NorthmaxProcedure.execute(x240 - 240, z240) : SouthmaxProcedure.execute(x240 - 240, z240));
			} else {
				positionx = horizontal + 5 - XmaxProcedure.execute(x240 - 240, z240);
			}
			positionz = vertical - westmin;
		} else if (position == 8) {
			if (train == 12 || train == 13) {
				sizex = 5 - xmax + (RandomNearTrain1Procedure.execute(x240 + 352, z240 + 112) ? NorthminProcedure.execute(x240 + 240, z240) : SouthminProcedure.execute(x240 + 240, z240));
			} else {
				sizex = 5 - xmax + XminProcedure.execute(x240 + 240, z240);
			}
			sizez = eastmax - eastmin;
			positionx = horizontal - xmax;
			positionz = vertical - eastmin;
		}
		if (position == 9) {
			sizex = -1;
			sizez = -1;
			positionx = 0;
			positionz = 0;
			if (train == 3 || train == -3 && (horizontal < 2 || horizontal >= 4 || (x240 % 960 + 960) % 960 != 0)) {
				bridge = BridgeXProcedure.execute(x, z);
				if (train == -3) {
					if (horizontal < 2) {
						bridge = 2;
					} else if (horizontal >= 4) {
						bridge = 4;
					}
				}
				if (vertical >= 3) {
					if (horizontal >= bridge) {
						assert Boolean.TRUE; //#dbg:DataCalculationPositionHouseblock:D
						train2 = TrainCalculationProcedure.execute(x240 + 352, z240 + 112);
						if (Math.abs(train2) == 3) {
							if (train2 == -3) {
								sizex = 7 - bridge;
								sizez = 2;
								positionx = horizontal - bridge;
								positionz = vertical - 3;
							} else {
								sizex = (5 + BridgeXProcedure.execute(x + 240, z)) - bridge;
								sizez = 2;
								positionx = horizontal - bridge;
								positionz = vertical - 3;
							}
						} else if (train2 == 7) {
							sizex = (10 + BridgeXProcedure.execute(x + 480, z)) - bridge;
							sizez = 2;
							positionx = horizontal - bridge;
							positionz = vertical - 3;
						} else if (train2 == 9 || train2 == 11 || train2 == 13 || train2 == 15) {
							sizex = 8 - bridge;
							sizez = 2 + BridgeZProcedure.execute(x + 240, z + 240);
							positionx = horizontal - bridge;
							positionz = vertical - 3;
						} else if (train2 == 5) {
							sizex = 10 - bridge;
							sizez = 10 - BridgeZProcedure.execute(x + 240, z - 240);
							positionx = horizontal - bridge;
							positionz = 5 - BridgeZProcedure.execute(x + 240, z - 240) + vertical;
						}
					} else {
						assert Boolean.TRUE; //#dbg:DataCalculationPositionHouseblock:C
						train2 = TrainCalculationProcedure.execute(x240 - 128, z240 + 112);
						if (Math.abs(train2) == 3) {
							if (train2 == -3) {
								sizex = 1 + bridge;
								sizez = 2;
								positionx = 1 + horizontal;
								positionz = vertical - 3;
							} else {
								sizex = (5 + bridge) - BridgeXProcedure.execute(x - 240, z);
								sizez = 2;
								positionx = (5 + horizontal) - BridgeXProcedure.execute(x - 240, z);
								positionz = vertical - 3;
							}
						} else if (train2 == 7) {
							sizex = (10 + bridge) - BridgeXProcedure.execute(x - 480, z);
							sizez = 2;
							positionx = (10 + horizontal) - BridgeXProcedure.execute(x - 480, z);
							positionz = vertical - 3;
						} else if (train2 == 10 || train2 == 11 || train2 == 14 || train2 == 15) {
							sizex = 2 + bridge;
							sizez = 2 + BridgeZProcedure.execute(x - 240, z + 240);
							positionx = 2 + horizontal;
							positionz = vertical - 3;
						} else if (train2 == 6) {
							sizex = 4 + bridge;
							sizez = 10 - BridgeZProcedure.execute(x - 240, z - 240);
							positionx = 4 + horizontal;
							positionz = 5 - BridgeZProcedure.execute(x - 240, z - 240) + vertical;
						}
					}
				} else {
					if (horizontal >= bridge) {
						assert Boolean.TRUE; //#dbg:DataCalculationPositionHouseblock:B
						train2 = TrainCalculationProcedure.execute(x240 + 352, z240 + 112);
						if (Math.abs(train2) == 3) {
							if (train2 == -3) {
								sizex = 7 - bridge;
								sizez = 2;
								positionx = horizontal - bridge;
								positionz = vertical - 1;
							} else {
								sizex = (5 + BridgeXProcedure.execute(x + 240, z)) - bridge;
								sizez = 2;
								positionx = horizontal - bridge;
								positionz = vertical - 1;
							}
						} else if (train2 == 11) {
							sizex = (10 + BridgeXProcedure.execute(x + 480, z)) - bridge;
							sizez = 2;
							positionx = horizontal - bridge;
							positionz = vertical - 1;
						} else if (train2 == 5 || train2 == 7 || train2 == 13 || train2 == 15) {
							sizex = 8 - bridge;
							sizez = 8 - BridgeZProcedure.execute(x + 240, z - 240);
							positionx = horizontal - bridge;
							positionz = 5 - BridgeZProcedure.execute(x + 240, z - 240) + vertical;
						} else if (train2 == 9) {
							sizex = 10 - bridge;
							sizez = 4 + BridgeZProcedure.execute(x + 240, z + 240);
							positionx = horizontal - bridge;
							positionz = vertical - 1;
						}
					} else {
						assert Boolean.TRUE; //#dbg:DataCalculationPositionHouseblock:A
						train2 = TrainCalculationProcedure.execute(x240 - 128, z240 + 112);
						if (Math.abs(train2) == 3) {
							if (train2 == -3) {
								sizex = 1 + bridge;
								sizez = 2;
								positionx = 1 + horizontal;
								positionz = vertical - 1;
							} else {
								sizex = (5 + bridge) - BridgeXProcedure.execute(x - 240, z);
								sizez = 2;
								positionx = (5 + horizontal) - BridgeXProcedure.execute(x - 240, z);
								positionz = vertical - 1;
							}
						} else if (train2 == 11) {
							sizex = (10 + bridge) - BridgeXProcedure.execute(x - 480, z);
							sizez = 2;
							positionx = (10 + horizontal) - BridgeXProcedure.execute(x - 480, z);
							positionz = vertical - 1;
						} else if (train2 == 6 || train2 == 7 || train2 == 14 || train2 == 15) {
							sizex = 2 + bridge;
							sizez = 8 - BridgeZProcedure.execute(x - 240, z - 240);
							positionx = 2 + horizontal;
							positionz = 5 - BridgeZProcedure.execute(x - 240, z - 240) + vertical;
						} else if (train2 == 10) {
							sizex = 4 + bridge;
							sizez = 4 + BridgeZProcedure.execute(x - 240, z + 240);
							positionx = 4 + horizontal;
							positionz = vertical - 1;
						}
					}
				}
			} else if (train == 12 || train == -12 && (vertical < 2 || vertical >= 4 || (z240 % 960 + 960) % 960 != 0)) {
				bridge = BridgeZProcedure.execute(x, z);
				if (train == -12) {
					if (vertical < 2) {
						bridge = 2;
					} else if (vertical >= 4) {
						bridge = 4;
					}
				}
				if (horizontal >= 3) {
					assert Boolean.TRUE; //#dbg:DataCalculationPositionHouseblock:vorubergehend
					if (vertical >= bridge) {
						assert Boolean.TRUE; //#dbg:DataCalculationPositionHouseblock:D
						train2 = TrainCalculationProcedure.execute(x240 + 112, z240 + 352);
						if (Math.abs(train2) == 12) {
							if (train2 == -12) {
								sizex = 2;
								sizez = 7 - bridge;
								positionx = horizontal - 3;
								positionz = vertical - bridge;
							} else {
								sizex = 2;
								sizez = (5 + BridgeZProcedure.execute(x, z + 240)) - bridge;
								positionx = horizontal - 3;
								positionz = vertical - bridge;
							}
						} else if (train2 == 13) {
							sizex = 2;
							sizez = (10 + BridgeZProcedure.execute(x, z + 480)) - bridge;
							positionx = horizontal - 3;
							positionz = vertical - bridge;
						} else if (train2 == 6 || train2 == 7 || train2 == 14 || train2 == 15) {
							sizex = 2 + BridgeXProcedure.execute(x + 240, z + 240);
							sizez = 8 - bridge;
							positionx = horizontal - 3;
							positionz = vertical - bridge;
						} else if (train2 == 5) {
							sizex = 10 - BridgeXProcedure.execute(x - 240, z + 240);
							sizez = 10 - bridge;
							positionx = 5 - BridgeXProcedure.execute(x - 240, z + 240) + horizontal;
							positionz = vertical - bridge;
						}
					} else {
						assert Boolean.TRUE; //#dbg:DataCalculationPositionHouseblock:C
						train2 = TrainCalculationProcedure.execute(x240 + 112, z240 - 128);
						if (Math.abs(train2) == 12) {
							if (train2 == -12) {
								sizex = 2;
								sizez = 1 + bridge;
								positionx = horizontal - 3;
								positionz = 1 + vertical;
							} else {
								sizex = 2;
								sizez = (5 + bridge) - BridgeZProcedure.execute(x, z - 240);
								positionx = horizontal - 3;
								positionz = (5 + vertical) - BridgeZProcedure.execute(x, z - 240);
							}
						} else if (train2 == 13) {
							sizex = 2;
							sizez = (10 + bridge) - BridgeZProcedure.execute(x, z - 480);
							positionx = horizontal - 3;
							positionz = (10 + vertical) - BridgeZProcedure.execute(x, z - 480);
						} else if (train2 == 10 || train2 == 11 || train2 == 14 || train2 == 15) {
							sizex = 2 + BridgeXProcedure.execute(x + 240, z - 240);
							sizez = 2 + bridge;
							positionx = horizontal - 3;
							positionz = 2 + vertical;
						} else if (train2 == 9) {
							sizex = 10 - BridgeXProcedure.execute(x - 240, z - 240);
							sizez = 4 + bridge;
							positionx = 5 - BridgeXProcedure.execute(x - 240, z - 240) + horizontal;
							positionz = 4 + vertical;
						}
					}
				} else {
					if (vertical >= bridge) {
						assert Boolean.TRUE; //#dbg:DataCalculationPositionHouseblock:B
						train2 = TrainCalculationProcedure.execute(x240 + 112, z240 + 352);
						if (Math.abs(train2) == 12) {
							if (train2 == -12) {
								sizex = 2;
								sizez = 7 - bridge;
								positionx = horizontal - 1;
								positionz = vertical - bridge;
							} else {
								sizex = 2;
								sizez = (5 + BridgeZProcedure.execute(x, z + 240)) - bridge;
								positionx = horizontal - 1;
								positionz = vertical - bridge;
							}
						} else if (train2 == 14) {
							sizex = 2;
							sizez = (10 + BridgeZProcedure.execute(x, z + 480)) - bridge;
							positionx = horizontal - 1;
							positionz = vertical - bridge;
						} else if (train2 == 5 || train2 == 7 || train2 == 13 || train2 == 15) {
							sizex = 8 - BridgeXProcedure.execute(x - 240, z + 240);
							sizez = 8 - bridge;
							positionx = 5 - BridgeXProcedure.execute(x - 240, z + 240) + horizontal;
							positionz = vertical - bridge;
						} else if (train2 == 6) {
							sizex = 4 + BridgeXProcedure.execute(x + 240, z + 240);
							sizez = 10 - bridge;
							positionx = horizontal - 1;
							positionz = vertical - bridge;
						}
					} else {
						assert Boolean.TRUE; //#dbg:DataCalculationPositionHouseblock:A
						train2 = TrainCalculationProcedure.execute(x240 + 112, z240 - 128);
						if (Math.abs(train2) == 12) {
							if (train2 == -12) {
								sizex = 2;
								sizez = 1 + bridge;
								positionx = horizontal - 1;
								positionz = 1 + vertical;
							} else {
								sizex = 2;
								sizez = (5 + bridge) - BridgeZProcedure.execute(x, z - 240);
								positionx = horizontal - 1;
								positionz = (5 + vertical) - BridgeZProcedure.execute(x, z - 240);
							}
						} else if (train2 == 14) {
							sizex = 2;
							sizez = (10 + bridge) - BridgeZProcedure.execute(x, z - 480);
							positionx = horizontal - 1;
							positionz = (10 + vertical) - BridgeZProcedure.execute(x, z - 480);
						} else if (train2 == 9 || train2 == 11 || train2 == 13 || train2 == 15) {
							sizex = 8 - BridgeXProcedure.execute(x - 240, z - 240);
							sizez = 2 + bridge;
							positionx = 5 - BridgeXProcedure.execute(x - 240, z - 240) + horizontal;
							positionz = 2 + vertical;
						} else if (train2 == 10) {
							sizex = 4 + BridgeXProcedure.execute(x + 240, z - 240);
							sizez = 4 + bridge;
							positionx = horizontal - 1;
							positionz = 4 + vertical;
						}
					}
				}
			} else if (train == 7 && vertical >= 3) {
				sizex = (10 + BridgeXProcedure.execute(x + 240, z)) - BridgeXProcedure.execute(x - 240, z);
				sizez = 2;
				positionx = (5 + horizontal) - BridgeXProcedure.execute(x - 240, z);
				positionz = vertical - 3;
			} else if (train == 11 && vertical < 3) {
				sizex = (10 + BridgeXProcedure.execute(x + 240, z)) - BridgeXProcedure.execute(x - 240, z);
				sizez = 2;
				positionx = (5 + horizontal) - BridgeXProcedure.execute(x - 240, z);
				positionz = vertical - 1;
			} else if (train == 13 && horizontal >= 3) {
				sizex = 2;
				sizez = (10 + BridgeZProcedure.execute(x, z + 240)) - BridgeZProcedure.execute(x, z - 240);
				positionx = horizontal - 3;
				positionz = (5 + vertical) - BridgeZProcedure.execute(x, z - 240);
			} else if (train == 14 && horizontal < 3) {
				sizex = 2;
				sizez = (10 + BridgeZProcedure.execute(x, z + 240)) - BridgeZProcedure.execute(x, z - 240);
				positionx = horizontal - 1;
				positionz = (5 + vertical) - BridgeZProcedure.execute(x, z - 240);
			} else if ((train == 5 || train == 7 || train == 13 || train == 15) && horizontal < 3 && vertical < 3) {
				sizex = 8 - BridgeXProcedure.execute(x - 240, z);
				sizez = 8 - BridgeZProcedure.execute(x, z - 240);
				positionx = (5 + horizontal) - BridgeXProcedure.execute(x - 240, z);
				positionz = (5 + vertical) - BridgeZProcedure.execute(x, z - 240);
			} else if ((train == 6 || train == 7 || train == 14 || train == 15) && horizontal >= 3 && vertical < 3) {
				sizex = 2 + BridgeXProcedure.execute(x + 240, z);
				sizez = 8 - BridgeZProcedure.execute(x, z - 240);
				positionx = horizontal - 3;
				positionz = (5 + vertical) - BridgeZProcedure.execute(x, z - 240);
			} else if ((train == 9 || train == 11 || train == 13 || train == 15) && horizontal < 3 && vertical >= 3) {
				sizex = 8 - BridgeXProcedure.execute(x - 240, z);
				sizez = 2 + BridgeZProcedure.execute(x, z + 240);
				positionx = (5 + horizontal) - BridgeXProcedure.execute(x - 240, z);
				positionz = vertical - 3;
			} else if ((train == 10 || train == 11 || train == 14 || train == 15) && horizontal >= 3 && vertical >= 3) {
				sizex = 2 + BridgeXProcedure.execute(x + 240, z);
				sizez = 2 + BridgeZProcedure.execute(x, z + 240);
				positionx = horizontal - 3;
				positionz = vertical - 3;
			} else if (train == 5 && (horizontal >= 3 || vertical >= 3)) {
				sizex = 10 - BridgeXProcedure.execute(x - 240, z);
				sizez = 10 - BridgeZProcedure.execute(x, z - 240);
				positionx = (5 + horizontal) - BridgeXProcedure.execute(x - 240, z);
				positionz = (5 + vertical) - BridgeZProcedure.execute(x, z - 240);
			} else if (train == 6 && (horizontal < 3 || vertical >= 3)) {
				sizex = 4 + BridgeXProcedure.execute(x + 240, z);
				sizez = 10 - BridgeZProcedure.execute(x, z - 240);
				positionx = horizontal - 1;
				positionz = (5 + vertical) - BridgeZProcedure.execute(x, z - 240);
			} else if (train == 9 && (horizontal >= 3 || vertical < 3)) {
				sizex = 10 - BridgeXProcedure.execute(x - 240, z);
				sizez = 4 + BridgeZProcedure.execute(x, z + 240);
				positionx = (5 + horizontal) - BridgeXProcedure.execute(x - 240, z);
				positionz = vertical - 1;
			} else if (train == 10 && (horizontal < 3 || vertical < 3)) {
				sizex = 4 + BridgeXProcedure.execute(x + 240, z);
				sizez = 4 + BridgeZProcedure.execute(x, z + 240);
				positionx = horizontal - 1;
				positionz = vertical - 1;
			}
		}
		sizexz_posxz = new ListTag();
		sizexz_posxz.addTag(0, IntTag.valueOf((int) sizex));
		sizexz_posxz.addTag(1, IntTag.valueOf((int) sizez));
		sizexz_posxz.addTag(2, IntTag.valueOf((int) positionx));
		sizexz_posxz.addTag(3, IntTag.valueOf((int) positionz));
		return sizexz_posxz;
	}
}
