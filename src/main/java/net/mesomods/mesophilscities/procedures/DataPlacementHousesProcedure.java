package net.mesomods.mesophilscities.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.nbt.ListTag;

public class DataPlacementHousesProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		ListTag sizexz_pos_xz;
		double testnumbr = 0;
		double sizex = 0;
		double sizez = 0;
		double posx = 0;
		double posz = 0;
		double originx = 0;
		double originz = 0;
		double midx = 0;
		double midz = 0;
		double Pathshiftx = 0;
		double Pathshiftz = 0;
		double seednorth = 0;
		double seedsouth = 0;
		double seedwest = 0;
		double seedeast = 0;
		double border1_n = 0;
		double border2_n = 0;
		double border3_n = 0;
		double path_n = 0;
		double border1_s = 0;
		double border2_s = 0;
		double border3_s = 0;
		double path_s = 0;
		double border1_w = 0;
		double border2_w = 0;
		double border3_w = 0;
		double path_w = 0;
		double border1_e = 0;
		double border2_e = 0;
		double border3_e = 0;
		double path_e = 0;
		double path = 0;
		double seed = 0;
		double border2 = 0;
		double border1 = 0;
		double testnumbr2 = 0;
		double path_n2 = 0;
		double border1_n2 = 0;
		double border2_n2 = 0;
		double path_s2 = 0;
		double border1_s2 = 0;
		double border2_s2 = 0;
		double path_w2 = 0;
		double border1_w2 = 0;
		double border2_w2 = 0;
		double path_e2 = 0;
		double border1_e2 = 0;
		double border2_e2 = 0;
		if (PathDirectionProcedure.execute(Math.round((x - 16) / 48) * 48, Math.round((z - 16) / 48) * 48)) {
			DataPlacementHousesTrueProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48, ((x % 48 + 48) % 48) / 16 + 1 + 3 * (((z % 48 + 48) % 48) / 16));
		} else {
			DataPlacementHousesFalseProcedure.execute(world, Math.round((x - 16) / 48) * 48, y, Math.round((z - 16) / 48) * 48, ((x % 48 + 48) % 48) / 16 + 1 + 3 * (((z % 48 + 48) % 48) / 16));
		}
	}
}
