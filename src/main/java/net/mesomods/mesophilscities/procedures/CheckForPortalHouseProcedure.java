package net.mesomods.mesophilscities.procedures;

import org.checkerframework.checker.units.qual.s;

import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.IntTag;

public class CheckForPortalHouseProcedure {
	public static ListTag execute(double x, double z, boolean e, boolean n, boolean s, boolean w, double xmax, double xmin, double zmax, double zmin) {
		ListTag portal_house;
		double direction = 0;
		portal_house = new ListTag();
		if ((xmax - xmin == 3 || zmax - zmin == 3) && (xmax - xmin == 4 || zmax - zmin == 4)) {
			portal_house.addTag(0, IntTag.valueOf((int) xmin));
			portal_house.addTag(1, IntTag.valueOf((int) zmin));
			if (RandomDirectionProcedure.execute((x - 20 + 4 * xmin) * zmax, (z - 20 + 4 * zmin) * xmax)) {
				direction = n ? 0 : s ? 1 : w ? 2 : 3;
			} else {
				direction = e ? 3 : w ? 2 : s ? 1 : 0;
			}
			if (zmax - zmin < xmax - xmin) {
				direction = direction + 4;
			}
			portal_house.addTag(2, IntTag.valueOf((int) direction));
		}
		return portal_house;
	}
}
