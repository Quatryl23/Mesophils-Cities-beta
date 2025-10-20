package net.mesomods.mesophilscities.procedures;

import org.checkerframework.checker.units.qual.h;

public class DataCalculationStreetsNearTrainProcedure {
	public static double execute(double x, double z, boolean east, boolean north, boolean south, boolean west, double h, double v) {
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
		x240 = x;
		z240 = z;
		horizontal = h;
		vertical = v;
		neartrainnorth = north;
		neartrainsouth = south;
		neartrainwest = west;
		neartraineast = east;
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
		if (train == 3 && TestForBridgeProcedure.execute(x, z)) {
			if (westmin == eastmin && westmax == eastmax) {
				bridge = 2;
				if (RandomNearTrain1Procedure.execute(x240 + 112, z240 + 112)) {
					bridge = bridge + 1;
				}
				if (RandomNearTrain2Procedure.execute(x240 + 112, z240 + 112)) {
					bridge = bridge + 1;
				}
				if (zmin == 1 && zmax == 5) {
					if (horizontal == bridge) {
						if (vertical == 1) {
							if (horizontal == northmin || horizontal == northmax) {
								return 15;
							} else {
								return 11;
							}
						} else if (vertical == 2) {
							return 28;
						} else if (vertical == 3) {
							return 44;
						} else if (vertical == 4) {
							return 60;
						} else if (vertical == 5) {
							if (horizontal == southmin || horizontal == southmax) {
								return 15;
							} else {
								return 7;
							}
						}
					}
				} else {
					if (horizontal == bridge) {
						if (vertical == zmin) {
							if (horizontal == northmin || horizontal == northmax) {
								return 15;
							} else {
								return 11;
							}
						} else if (vertical == zmax) {
							if (horizontal == southmin || horizontal == southmax) {
								return 15;
							} else {
								return 7;
							}
						} else if (vertical == 3) {
							return 76;
						} else if (vertical > zmin && vertical < zmax) {
							return 12;
						}
					}
				}
			} else if (westmin == eastmin) {
				bridge = southmain;
				if (horizontal == bridge) {
					if (vertical == zmin) {
						if (horizontal == northmin || horizontal == northmax) {
							return 15;
						} else {
							return 11;
						}
					} else if (vertical == 3) {
						return 76;
					} else if (vertical > zmin && vertical < 3) {
						return 12;
					} else if (vertical == 4) {
						if (westmax > eastmax) {
							return 14;
						} else {
							return 13;
						}
					}
				}
			} else if (westmax == eastmax) {
				bridge = northmain;
				if (horizontal == bridge) {
					if (vertical == 2) {
						if (westmin > eastmin) {
							return 13;
						} else {
							return 14;
						}
					} else if (vertical == 3) {
						return 76;
					} else if (vertical < zmax && vertical > 3) {
						return 12;
					} else if (vertical == zmax) {
						if (horizontal == southmin || horizontal == southmax) {
							return 15;
						} else {
							return 7;
						}
					}
				}
			} else {
				bridge = northmain;
				if (horizontal == bridge) {
					if (vertical == 2) {
						if (westmin > eastmin) {
							return 13;
						} else {
							return 14;
						}
					} else if (vertical == 3) {
						return 76;
					} else if (vertical > 3) {
						if (vertical == 4 && horizontal == southmain) {
							if (westmax > eastmax) {
								return 14;
							} else {
								return 13;
							}
						} else if (horizontal > southmain) {
							if (vertical < eastmax) {
								return 12;
							} else if (vertical == eastmax) {
								if (horizontal == southmin || horizontal == southmax) {
									return 15;
								} else {
									return 7;
								}
							}
						} else if (horizontal < southmain) {
							if (vertical < westmax) {
								return 12;
							} else if (vertical == westmax) {
								if (horizontal == southmin || horizontal == southmax) {
									return 15;
								} else {
									return 7;
								}
							}
						}
					}
				}
			}
		}
		if (train == 12 && TestForBridgeProcedure.execute(x, z)) {
			if (northmin == southmin && northmax == southmax) {
				bridge = 2;
				if (RandomNearTrain1Procedure.execute(x240 + 112, z240 + 112)) {
					bridge = bridge + 1;
				}
				if (RandomNearTrain2Procedure.execute(x240 + 112, z240 + 112)) {
					bridge = bridge + 1;
				}
				if (xmin == 1 && xmax == 5) {
					if (vertical == bridge) {
						if (horizontal == 1) {
							if (vertical == westmin || vertical == westmax) {
								return 15;
							} else {
								return 14;
							}
						} else if (horizontal == 2) {
							return 19;
						} else if (horizontal == 3) {
							return 35;
						} else if (horizontal == 4) {
							return 51;
						} else if (horizontal == 5) {
							if (vertical == eastmin || vertical == eastmax) {
								return 15;
							} else {
								return 13;
							}
						}
					}
				} else {
					if (vertical == bridge) {
						if (horizontal == xmin) {
							if (vertical == westmin || vertical == westmax) {
								return 15;
							} else {
								return 14;
							}
						} else if (horizontal == xmax) {
							if (vertical == eastmin || vertical == eastmax) {
								return 15;
							} else {
								return 13;
							}
						} else if (horizontal == 3) {
							return 67;
						} else if (horizontal > xmin && horizontal < xmax) {
							return 3;
						}
					}
				}
			} else if (northmin == southmin) {
				bridge = eastmain;
				if (vertical == bridge) {
					if (horizontal == xmin) {
						if (vertical == westmin || vertical == westmax) {
							return 15;
						} else {
							return 14;
						}
					} else if (horizontal == 3) {
						return 67;
					} else if (horizontal > xmin && horizontal < 3) {
						return 3;
					} else if (horizontal == 4) {
						if (northmax > southmax) {
							return 11;
						} else {
							return 7;
						}
					}
				}
			} else if (northmax == southmax) {
				bridge = westmain;
				if (vertical == bridge) {
					if (horizontal == 2) {
						if (northmin > southmin) {
							return 7;
						} else {
							return 11;
						}
					} else if (horizontal == 3) {
						return 67;
					} else if (horizontal < xmax && horizontal > 3) {
						return 3;
					} else if (horizontal == xmax) {
						if (vertical == eastmin || vertical == eastmax) {
							return 15;
						} else {
							return 13;
						}
					}
				}
			} else {
				bridge = westmain;
				if (vertical == bridge) {
					if (horizontal == 2) {
						if (northmin > southmin) {
							return 7;
						} else {
							return 11;
						}
					} else if (horizontal == 3) {
						return 67;
					} else if (horizontal > 3) {
						if (horizontal == 4 && vertical == eastmain) {
							if (northmax > southmax) {
								return 11;
							} else {
								return 7;
							}
						} else if (vertical > eastmain) {
							if (horizontal < southmax) {
								return 3;
							} else if (horizontal == southmax) {
								if (vertical == eastmin || vertical == eastmax) {
									return 15;
								} else {
									return 13;
								}
							}
						} else if (vertical < eastmain) {
							if (horizontal < northmax) {
								return 3;
							} else if (horizontal == northmax) {
								if (vertical == eastmin || vertical == eastmax) {
									return 15;
								} else {
									return 13;
								}
							}
						}
					}
				}
			}
		}
		if ((train == 3 || train == -3 || train == 11) && vertical < 3) {
			if (vertical == westmin) {
				if (horizontal == northmain) {
					if (westmin < eastmin) {
						return 13;
					} else if (westmin == eastmin) {
						return 7;
					} else if (westmin > eastmin) {
						return 5;
					}
				} else if (horizontal < northmain) {
					if (horizontal == northmin || horizontal == northmax) {
						return 7;
					} else {
						return 3;
					}
				} else if (horizontal > northmain && westmin < eastmin && (horizontal == northmin || horizontal == northmax)) {
					return 12;
				}
			}
			if (vertical == eastmin) {
				if (horizontal == northmain) {
					if (westmin < eastmin) {
						return 6;
					} else if (westmin == eastmin) {
						return 7;
					} else if (westmin > eastmin) {
						return 14;
					}
				} else if (horizontal > northmain) {
					if (horizontal == northmin || horizontal == northmax) {
						return 7;
					} else {
						return 3;
					}
				} else if (horizontal < northmain && westmin > eastmin && (horizontal == northmin || horizontal == northmax)) {
					return 12;
				}
			}
			if (vertical != westmin && vertical != eastmin && vertical == 1 && (horizontal == northmin || horizontal == northmax)) {
				return 12;
			}
		}
		if ((train == 3 || train == -3 || train == 7) && vertical > 3) {
			if (vertical == westmax) {
				if (horizontal == southmain) {
					if (westmax < eastmax) {
						return 9;
					} else if (westmax == eastmax) {
						return 11;
					} else if (westmax > eastmax) {
						return 13;
					}
				} else if (horizontal < southmain) {
					if (horizontal == southmin || horizontal == southmax) {
						return 11;
					} else {
						return 3;
					}
				} else if (horizontal > southmain && westmax > eastmax && (horizontal == southmin || horizontal == southmax)) {
					return 12;
				}
			}
			if (vertical == eastmax) {
				if (horizontal == southmain) {
					if (westmax < eastmax) {
						return 14;
					} else if (westmax == eastmax) {
						return 11;
					} else if (westmax > eastmax) {
						return 10;
					}
				} else if (horizontal > southmain) {
					if (horizontal == southmin || horizontal == southmax) {
						return 11;
					} else {
						return 3;
					}
				} else if (horizontal < southmain && westmax < eastmax && (horizontal == southmin || horizontal == southmax)) {
					return 12;
				}
			}
			if (vertical != westmax && vertical != eastmax && vertical == 5 && (horizontal == southmin || horizontal == southmax)) {
				return 12;
			}
		}
		if ((train == 12 || train == -12 || train == 14) && horizontal < 3) {
			if (horizontal == northmin) {
				if (vertical == westmain) {
					if (northmin < southmin) {
						return 7;
					} else if (northmin == southmin) {
						return 13;
					} else if (northmin > southmin) {
						return 5;
					}
				} else if (vertical < westmain) {
					if (vertical == westmin || vertical == westmax) {
						return 13;
					} else {
						return 12;
					}
				} else if (vertical > westmain && northmin < southmin && (vertical == westmin || vertical == westmax)) {
					return 3;
				}
			}
			if (horizontal == southmin) {
				if (vertical == westmain) {
					if (northmin < southmin) {
						return 9;
					} else if (northmin == southmin) {
						return 13;
					} else if (northmin > southmin) {
						return 11;
					}
				} else if (vertical > westmain) {
					if (vertical == westmin || vertical == westmax) {
						return 13;
					} else {
						return 12;
					}
				} else if (vertical < westmain && northmin > southmin && (vertical == westmin || vertical == westmax)) {
					return 3;
				}
			}
			if (horizontal != northmin && horizontal != southmin && horizontal == 1 && (vertical == westmin || vertical == westmax)) {
				return 3;
			}
		}
		if ((train == 12 || train == -12 || train == 13) && horizontal > 3) {
			if (horizontal == northmax) {
				if (vertical == eastmain) {
					if (northmax < southmax) {
						return 6;
					} else if (northmax == southmax) {
						return 14;
					} else if (northmax > southmax) {
						return 7;
					}
				} else if (vertical < eastmain) {
					if (vertical == eastmin || vertical == eastmax) {
						return 14;
					} else {
						return 12;
					}
				} else if (vertical > eastmain && northmax > southmax && (vertical == eastmin || vertical == eastmax)) {
					return 3;
				}
			}
			if (horizontal == southmax) {
				if (vertical == eastmain) {
					if (northmax < southmax) {
						return 11;
					} else if (northmax == southmax) {
						return 14;
					} else if (northmax > southmax) {
						return 10;
					}
				} else if (vertical > eastmain) {
					if (vertical == eastmin || vertical == eastmax) {
						return 14;
					} else {
						return 12;
					}
				} else if (vertical < eastmain && northmax < southmax && (vertical == eastmin || vertical == eastmax)) {
					return 3;
				}
			}
			if (horizontal != northmax && horizontal != southmax && horizontal == 5 && (vertical == eastmin || vertical == eastmax)) {
				return 3;
			}
		}
		if ((train == 5 || train == 7 || train == 13 || train == 15) && horizontal < 3 && vertical < 3) {
			if (vertical == westmin) {
				if (horizontal == northmin) {
					return 5;
				} else if (horizontal < northmin) {
					return 3;
				}
			} else if (horizontal == northmin && vertical < westmin) {
				return 12;
			}
		}
		if ((train == 6 || train == 7 || train == 14 || train == 15) && horizontal > 3 && vertical < 3) {
			if (vertical == eastmin) {
				if (horizontal == northmax) {
					return 6;
				} else if (horizontal > northmax) {
					return 3;
				}
			} else if (horizontal == northmax && vertical < eastmin) {
				return 12;
			}
		}
		if ((train == 9 || train == 11 || train == 13 || train == 15) && horizontal < 3 && vertical > 3) {
			if (vertical == westmax) {
				if (horizontal == southmin) {
					return 9;
				} else if (horizontal < southmin) {
					return 3;
				}
			} else if (horizontal == southmin && vertical > westmax) {
				return 12;
			}
		}
		if ((train == 10 || train == 11 || train == 14 || train == 15) && horizontal > 3 && vertical > 3) {
			if (vertical == eastmax) {
				if (horizontal == southmax) {
					return 10;
				} else if (horizontal > southmax) {
					return 3;
				}
			} else if (horizontal == southmax && vertical > eastmax) {
				return 12;
			}
		}
		if (train == 5 && (horizontal > 3 || vertical > 3)) {
			if (horizontal < southmin) {
				if (vertical == westmax) {
					return 3;
				}
			} else if (horizontal == southmin) {
				if (vertical == westmax && vertical == zmax) {
					return 11;
				} else if (vertical == westmax) {
					return 9;
				} else if (vertical == zmax) {
					return 14;
				}
			} else if (horizontal < xmax && vertical == zmax) {
				if (horizontal == southmax) {
					return 11;
				} else {
					return 3;
				}
			} else if (horizontal > xmax && (vertical == eastmin || vertical == eastmax)) {
				return 3;
			}
			if (vertical < eastmin) {
				if (horizontal == northmax) {
					return 12;
				}
			} else if (vertical == eastmin) {
				if (horizontal == northmax && horizontal == xmax) {
					return 14;
				} else if (horizontal == northmax) {
					return 6;
				} else if (horizontal == xmax) {
					return 11;
				}
			} else if (vertical < zmax && horizontal == xmax) {
				if (vertical == eastmax) {
					return 14;
				} else {
					return 12;
				}
			} else if (vertical > zmax && (horizontal == southmin || horizontal == southmax)) {
				return 12;
			}
			if (horizontal == xmax && vertical == zmax) {
				if (horizontal == southmax && vertical == eastmax) {
					return 15;
				} else if (horizontal == southmax) {
					return 13;
				} else if (vertical == eastmax) {
					return 7;
				} else {
					return 5;
				}
			}
		}
		if (train == 6 && (horizontal < 3 || vertical > 3)) {
			if (horizontal > southmax) {
				if (vertical == eastmax) {
					return 3;
				}
			} else if (horizontal == southmax) {
				if (vertical == eastmax && vertical == zmax) {
					return 11;
				} else if (vertical == eastmax) {
					return 10;
				} else if (vertical == zmax) {
					return 13;
				}
			} else if (horizontal > xmin && vertical == zmax) {
				if (horizontal == southmin) {
					return 11;
				} else {
					return 3;
				}
			} else if (horizontal < xmin && (vertical == westmin || vertical == westmax)) {
				return 3;
			}
			if (vertical < westmin) {
				if (horizontal == northmin) {
					return 12;
				}
			} else if (vertical == westmin) {
				if (horizontal == northmin && horizontal == xmin) {
					return 13;
				} else if (horizontal == northmin) {
					return 5;
				} else if (horizontal == xmin) {
					return 11;
				}
			} else if (vertical < zmax && horizontal == xmin) {
				if (vertical == westmax) {
					return 13;
				} else {
					return 12;
				}
			} else if (vertical > zmax && (horizontal == southmin || horizontal == southmax)) {
				return 12;
			}
			if (horizontal == xmin && vertical == zmax) {
				if (horizontal == southmin && vertical == westmax) {
					return 15;
				} else if (horizontal == southmin) {
					return 14;
				} else if (vertical == westmax) {
					return 7;
				} else {
					return 6;
				}
			}
		}
		if (train == 9 && (horizontal > 3 || vertical < 3)) {
			if (horizontal < northmin) {
				if (vertical == westmin) {
					return 3;
				}
			} else if (horizontal == northmin) {
				if (vertical == westmin && vertical == zmin) {
					return 7;
				} else if (vertical == westmin) {
					return 5;
				} else if (vertical == zmin) {
					return 14;
				}
			} else if (horizontal < xmax && vertical == zmin) {
				if (horizontal == northmax) {
					return 7;
				} else {
					return 3;
				}
			} else if (horizontal > xmax && (vertical == eastmin || vertical == eastmax)) {
				return 3;
			}
			if (vertical > eastmax) {
				if (horizontal == southmax) {
					return 12;
				}
			} else if (vertical == eastmax) {
				if (horizontal == southmax && horizontal == xmax) {
					return 14;
				} else if (horizontal == southmax) {
					return 10;
				} else if (horizontal == xmax) {
					return 7;
				}
			} else if (vertical > zmin && horizontal == xmax) {
				if (vertical == eastmin) {
					return 14;
				} else {
					return 12;
				}
			} else if (vertical < zmin && (horizontal == northmin || horizontal == northmax)) {
				return 12;
			}
			if (horizontal == xmax && vertical == zmin) {
				if (horizontal == northmax && vertical == eastmin) {
					return 15;
				} else if (horizontal == northmax) {
					return 13;
				} else if (vertical == eastmin) {
					return 11;
				} else {
					return 9;
				}
			}
		}
		if (train == 10 && (horizontal < 3 || vertical < 3)) {
			if (horizontal > northmax) {
				if (vertical == eastmin) {
					return 3;
				}
			} else if (horizontal == northmax) {
				if (vertical == eastmin && vertical == zmin) {
					return 7;
				} else if (vertical == eastmin) {
					return 6;
				} else if (vertical == zmin) {
					return 13;
				}
			} else if (horizontal > xmin && vertical == zmin) {
				if (horizontal == northmin) {
					return 7;
				} else {
					return 3;
				}
			} else if (horizontal < xmin && (vertical == westmin || vertical == westmax)) {
				return 3;
			}
			if (vertical > westmax) {
				if (horizontal == southmin) {
					return 12;
				}
			} else if (vertical == westmax) {
				if (horizontal == southmin && horizontal == xmin) {
					return 13;
				} else if (horizontal == southmin) {
					return 9;
				} else if (horizontal == xmin) {
					return 7;
				}
			} else if (vertical > zmin && horizontal == xmin) {
				if (vertical == westmin) {
					return 13;
				} else {
					return 12;
				}
			} else if (vertical < zmin && (horizontal == northmin || horizontal == northmax)) {
				return 12;
			}
			if (horizontal == xmin && vertical == zmin) {
				if (horizontal == northmin && vertical == westmin) {
					return 15;
				} else if (horizontal == northmin) {
					return 14;
				} else if (vertical == westmin) {
					return 11;
				} else {
					return 10;
				}
			}
		}
		return 0;
	}
}
