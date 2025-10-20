package net.mesomods.mesophilscities.procedures;

public class StreetDependency1Procedure {
	public static boolean execute(boolean Min, double Max2, double Min2, double MinMax, double direction, double xz, double xz2) {
		return (direction % 10 == xz || Math.floor(direction / 10) == xz) && (!(Max2 > xz && Min2 < xz) || (Min ? xz2 <= MinMax : xz2 >= MinMax));
	}
}
