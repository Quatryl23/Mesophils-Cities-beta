package net.mesomods.mesophilscities.procedures;

import net.minecraft.util.Mth;

public class XorshiftSeedProcedure {
	public static double execute(double x, double z, boolean neartrain, boolean xorz) {
		double localseed = 0;
		localseed = Mth.nextInt(CityRandomSourceProcedure.execute(x, z, 1904180425), 0, 24);
		if ((xorz ? z : x) % 1920 == 0 && (((xorz ? x : z) + 112) % 1920 == 0 || ((xorz ? x : z) - 128) % 1920 == 0)
				|| ((xorz ? z : x) % 1920 + 1920) % 1920 == 960 && ((((xorz ? x : z) - 128) % 1920 + 1920) % 1920 == 960 || (((xorz ? x : z) + 112) % 1920 + 1920) % 1920 == 960)) {
			return 15;
		} else {
			if (4 > localseed) {
				return 14;
			} else if (7 > localseed) {
				if (neartrain) {
					if (XorshiftSeedBasicProcedure.execute(xorz ? x : x + 240, xorz ? z + 240 : z) > 3) {
						if (XorshiftSeedBasicProcedure.execute(xorz ? x : x - 240, xorz ? z - 240 : z) < 22) {
							return 15;
						} else {
							return 25;
						}
					} else {
						if (XorshiftSeedBasicProcedure.execute(xorz ? x : x - 240, xorz ? z - 240 : z) < 22) {
							return 14;
						} else {
							return 24;
						}
					}
				} else {
					return 2;
				}
			} else if (12 > localseed) {
				if (neartrain) {
					if (XorshiftSeedBasicProcedure.execute(xorz ? x : x + 240, xorz ? z + 240 : z) > 3) {
						if (XorshiftSeedBasicProcedure.execute(xorz ? x : x - 240, xorz ? z - 240 : z) < 22) {
							return 15;
						} else {
							return 25;
						}
					} else {
						if (XorshiftSeedBasicProcedure.execute(xorz ? x : x - 240, xorz ? z - 240 : z) < 22) {
							return 14;
						} else {
							return 24;
						}
					}
				} else {
					return 3;
				}
			} else if (15 > localseed) {
				if (neartrain) {
					if (XorshiftSeedBasicProcedure.execute(xorz ? x : x + 240, xorz ? z + 240 : z) > 3) {
						if (XorshiftSeedBasicProcedure.execute(xorz ? x : x - 240, xorz ? z - 240 : z) < 22) {
							return 15;
						} else {
							return 25;
						}
					} else {
						if (XorshiftSeedBasicProcedure.execute(xorz ? x : x - 240, xorz ? z - 240 : z) < 22) {
							return 14;
						} else {
							return 24;
						}
					}
				} else {
					return 4;
				}
			} else if (22 > localseed) {
				if (neartrain) {
					if (XorshiftSeedBasicProcedure.execute(xorz ? x : x + 240, xorz ? z + 240 : z) > 3) {
						if (XorshiftSeedBasicProcedure.execute(xorz ? x : x - 240, xorz ? z - 240 : z) < 22) {
							return 15;
						} else {
							return 25;
						}
					} else {
						if (XorshiftSeedBasicProcedure.execute(xorz ? x : x - 240, xorz ? z - 240 : z) < 22) {
							return 14;
						} else {
							return 24;
						}
					}
				} else {
					return 24;
				}
			}
			if (XorshiftSeedBasicProcedure.execute(xorz ? x : x + 240, xorz ? z + 240 : z) > 3) {
				return 25;
			}
		}
		return 24;
	}
}
