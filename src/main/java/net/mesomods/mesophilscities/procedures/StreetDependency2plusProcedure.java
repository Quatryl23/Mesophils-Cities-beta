package net.mesomods.mesophilscities.procedures;

public class StreetDependency2plusProcedure {
	public static boolean execute(double Max, double Max2, double Min, double Min2, double xz, double xz2) {
		return xz < Max2 && (!(Max > xz2 && Min < xz2) || xz < Min2);
	}
}
