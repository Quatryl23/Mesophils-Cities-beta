package net.mesomods.mesophilscities.procedures;

import net.minecraft.world.level.Level;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.core.BlockPos;
import net.mesomods.mesophilscities.block.CityPortalBlock;

public class TrySpawningPortalProcedure {
	public static boolean execute(Level world, int x, int y, int z) {
		ListTag nearest_valid_portal_position;
		boolean portal_on_x_axis = false;
		for (int i=0; i<9; i++) {
		nearest_valid_portal_position = CheckForPortalHouse240Procedure.execute(world, (i == 3 || i == 5 || i == 8) ? (x + 240) : (i == 2 || i == 7 || i == 0) ? x : (x - 240), (i > 5) ? (z + 240) : (i > 3) ? z : (i > 0) ? (z - 240) : z);
		portal_on_x_axis = ((nearest_valid_portal_position.get(2)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) < 2;
		if (((x == ((nearest_valid_portal_position.get(0)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) || x == ((nearest_valid_portal_position.get(0)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) + 1 && portal_on_x_axis)
				&& (z == ((nearest_valid_portal_position.get(1)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) || z == ((nearest_valid_portal_position.get(1)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) + 1 && !portal_on_x_axis)
				&& y > 63) || ( world.dimension() == Level.OVERWORLD )) {
			BlockPos pos = new BlockPos((int)x, (int)y, (int)z);
			CityPortalBlock.portalSpawn(world, pos);
			return true;
		}
	} return false;
}
}
