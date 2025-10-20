package net.mesomods.mesophilscities.procedures;

import org.checkerframework.checker.units.qual.s;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.core.BlockPos;

public class BlockPlacementHousesProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, boolean e, boolean n, boolean s, boolean w, double xmax, double xmin, double zmax, double zmin) {
		ListTag portalhouse;
		world.setBlock(BlockPos.containing(x - 20 + 4 * xmin, y, z - 20 + 4 * zmin), Blocks.CHISELED_STONE_BRICKS.defaultBlockState(), 3);
		if (RandomDirectionProcedure.execute((x - 20 + 4 * xmin) * zmax, (z - 20 + 4 * zmin) * xmax)) {
			if (n) {
				world.setBlock(BlockPos.containing(Math.ceil((x - 19 + 4 * xmin) / 16) * 16, y - 1, Math.ceil((z - 19 + 4 * zmin) / 16) * 16), Blocks.IRON_BLOCK.defaultBlockState(), 3);
			} else if (s) {
				world.setBlock(BlockPos.containing(Math.ceil((x - 19 + 4 * xmin) / 16) * 16, y - 1, Math.ceil((z - 19 + 4 * zmin) / 16) * 16), Blocks.GOLD_BLOCK.defaultBlockState(), 3);
			} else if (w) {
				world.setBlock(BlockPos.containing(Math.ceil((x - 19 + 4 * xmin) / 16) * 16, y - 1, Math.ceil((z - 19 + 4 * zmin) / 16) * 16), Blocks.DIAMOND_BLOCK.defaultBlockState(), 3);
			} else if (e) {
				world.setBlock(BlockPos.containing(Math.ceil((x - 19 + 4 * xmin) / 16) * 16, y - 1, Math.ceil((z - 19 + 4 * zmin) / 16) * 16), Blocks.EMERALD_BLOCK.defaultBlockState(), 3);
			}
		} else {
			if (e) {
				world.setBlock(BlockPos.containing(Math.ceil((x - 19 + 4 * xmin) / 16) * 16, y - 1, Math.ceil((z - 19 + 4 * zmin) / 16) * 16), Blocks.EMERALD_BLOCK.defaultBlockState(), 3);
			} else if (w) {
				world.setBlock(BlockPos.containing(Math.ceil((x - 19 + 4 * xmin) / 16) * 16, y - 1, Math.ceil((z - 19 + 4 * zmin) / 16) * 16), Blocks.DIAMOND_BLOCK.defaultBlockState(), 3);
			} else if (s) {
				world.setBlock(BlockPos.containing(Math.ceil((x - 19 + 4 * xmin) / 16) * 16, y - 1, Math.ceil((z - 19 + 4 * zmin) / 16) * 16), Blocks.GOLD_BLOCK.defaultBlockState(), 3);
			} else if (n) {
				world.setBlock(BlockPos.containing(Math.ceil((x - 19 + 4 * xmin) / 16) * 16, y - 1, Math.ceil((z - 19 + 4 * zmin) / 16) * 16), Blocks.IRON_BLOCK.defaultBlockState(), 3);
			}
		}
		if ((xmax - xmin == 3 || zmax - zmin == 3) && (xmax - xmin == 4 || zmax - zmin == 4)) {
			for (int i = (int) 0; i <= (int) 8; i++) {
				portalhouse = CheckForPortalHouse240Procedure.execute(world, (i == 3 || i == 5 || i == 8) ? (x + 240) : (i == 2 || i == 7 || i == 0) ? x : (x - 240), (i > 5) ? (z + 240) : (i > 3) ? z : (i > 0) ? (z - 240) : z);
				if (((portalhouse.get(0)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) - (x - 20 + 4 * xmin) < 13 && ((portalhouse.get(0)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) - (x - 20 + 4 * xmin) > 1
						&& ((portalhouse.get(1)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) - (z - 20 + 4 * zmin) < 13 && ((portalhouse.get(1)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) - (z - 20 + 4 * zmin) > 1) {
					assert Boolean.TRUE; //#dbg:BlockPlacementHouses:portal
					if (RandomDirectionProcedure.execute((x - 20 + 4 * xmin) * zmax, (z - 20 + 4 * zmin) * xmax)) {
						if (n) {
							world.setBlock(BlockPos.containing(Math.ceil((x - 19 + 4 * xmin) / 16) * 16, y - 1, Math.ceil((z - 19 + 4 * zmin) / 16) * 16), Blocks.RAW_IRON_BLOCK.defaultBlockState(), 3);
						} else if (s) {
							world.setBlock(BlockPos.containing(Math.ceil((x - 19 + 4 * xmin) / 16) * 16, y - 1, Math.ceil((z - 19 + 4 * zmin) / 16) * 16), Blocks.RAW_GOLD_BLOCK.defaultBlockState(), 3);
						} else if (w) {
							world.setBlock(BlockPos.containing(Math.ceil((x - 19 + 4 * xmin) / 16) * 16, y - 1, Math.ceil((z - 19 + 4 * zmin) / 16) * 16), Blocks.LAPIS_BLOCK.defaultBlockState(), 3);
						} else if (e) {
							world.setBlock(BlockPos.containing(Math.ceil((x - 19 + 4 * xmin) / 16) * 16, y - 1, Math.ceil((z - 19 + 4 * zmin) / 16) * 16), Blocks.RAW_COPPER_BLOCK.defaultBlockState(), 3);
						}
					} else {
						if (e) {
							world.setBlock(BlockPos.containing(Math.ceil((x - 19 + 4 * xmin) / 16) * 16, y - 1, Math.ceil((z - 19 + 4 * zmin) / 16) * 16), Blocks.RAW_COPPER_BLOCK.defaultBlockState(), 3);
						} else if (w) {
							world.setBlock(BlockPos.containing(Math.ceil((x - 19 + 4 * xmin) / 16) * 16, y - 1, Math.ceil((z - 19 + 4 * zmin) / 16) * 16), Blocks.LAPIS_BLOCK.defaultBlockState(), 3);
						} else if (s) {
							world.setBlock(BlockPos.containing(Math.ceil((x - 19 + 4 * xmin) / 16) * 16, y - 1, Math.ceil((z - 19 + 4 * zmin) / 16) * 16), Blocks.RAW_GOLD_BLOCK.defaultBlockState(), 3);
						} else if (n) {
							world.setBlock(BlockPos.containing(Math.ceil((x - 19 + 4 * xmin) / 16) * 16, y - 1, Math.ceil((z - 19 + 4 * zmin) / 16) * 16), Blocks.RAW_IRON_BLOCK.defaultBlockState(), 3);
						}
					}
				}
			}
		}
		if (3 == xmax - xmin) {
			world.setBlock(BlockPos.containing(Math.ceil((x - 19 + 4 * xmin) / 16) * 16 + 1, y - 1, Math.ceil((z - 19 + 4 * zmin) / 16) * 16), Blocks.DARK_OAK_LOG.defaultBlockState(), 3);
		} else if (4 == xmax - xmin) {
			world.setBlock(BlockPos.containing(Math.ceil((x - 19 + 4 * xmin) / 16) * 16 + 1, y - 1, Math.ceil((z - 19 + 4 * zmin) / 16) * 16), Blocks.SPRUCE_LOG.defaultBlockState(), 3);
		} else if (5 == xmax - xmin) {
			world.setBlock(BlockPos.containing(Math.ceil((x - 19 + 4 * xmin) / 16) * 16 + 1, y - 1, Math.ceil((z - 19 + 4 * zmin) / 16) * 16), Blocks.OAK_LOG.defaultBlockState(), 3);
		} else if (6 == xmax - xmin) {
			world.setBlock(BlockPos.containing(Math.ceil((x - 19 + 4 * xmin) / 16) * 16 + 1, y - 1, Math.ceil((z - 19 + 4 * zmin) / 16) * 16), Blocks.JUNGLE_LOG.defaultBlockState(), 3);
		} else if (7 == xmax - xmin) {
			world.setBlock(BlockPos.containing(Math.ceil((x - 19 + 4 * xmin) / 16) * 16 + 1, y - 1, Math.ceil((z - 19 + 4 * zmin) / 16) * 16), Blocks.BIRCH_LOG.defaultBlockState(), 3);
		} else if (8 == xmax - xmin) {
			world.setBlock(BlockPos.containing(Math.ceil((x - 19 + 4 * xmin) / 16) * 16 + 1, y - 1, Math.ceil((z - 19 + 4 * zmin) / 16) * 16), Blocks.ACACIA_LOG.defaultBlockState(), 3);
		}
		if (3 == zmax - zmin) {
			world.setBlock(BlockPos.containing(Math.ceil((x - 19 + 4 * xmin) / 16) * 16, y - 1, Math.ceil((z - 19 + 4 * zmin) / 16) * 16 + 1), Blocks.DARK_OAK_LOG.defaultBlockState(), 3);
		} else if (4 == zmax - zmin) {
			world.setBlock(BlockPos.containing(Math.ceil((x - 19 + 4 * xmin) / 16) * 16, y - 1, Math.ceil((z - 19 + 4 * zmin) / 16) * 16 + 1), Blocks.SPRUCE_LOG.defaultBlockState(), 3);
		} else if (5 == zmax - zmin) {
			world.setBlock(BlockPos.containing(Math.ceil((x - 19 + 4 * xmin) / 16) * 16, y - 1, Math.ceil((z - 19 + 4 * zmin) / 16) * 16 + 1), Blocks.OAK_LOG.defaultBlockState(), 3);
		} else if (6 == zmax - zmin) {
			world.setBlock(BlockPos.containing(Math.ceil((x - 19 + 4 * xmin) / 16) * 16, y - 1, Math.ceil((z - 19 + 4 * zmin) / 16) * 16 + 1), Blocks.JUNGLE_LOG.defaultBlockState(), 3);
		} else if (7 == zmax - zmin) {
			world.setBlock(BlockPos.containing(Math.ceil((x - 19 + 4 * xmin) / 16) * 16, y - 1, Math.ceil((z - 19 + 4 * zmin) / 16) * 16 + 1), Blocks.BIRCH_LOG.defaultBlockState(), 3);
		} else if (8 == zmax - zmin) {
			world.setBlock(BlockPos.containing(Math.ceil((x - 19 + 4 * xmin) / 16) * 16, y - 1, Math.ceil((z - 19 + 4 * zmin) / 16) * 16 + 1), Blocks.ACACIA_LOG.defaultBlockState(), 3);
		}
		if (3 == xmax - xmin) {
			if (3 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a012012
			} else if (4 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a012016
			} else if (5 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a012020
			} else if (6 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a012024
			} else if (7 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a012028
			} else if (8 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a012032
			}
		} else if (4 == xmax - xmin) {
			if (3 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a016012
			} else if (4 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a016016
			} else if (5 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a016020
			} else if (6 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a016024
			} else if (7 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a016028
			} else if (8 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a016032
			}
		} else if (5 == xmax - xmin) {
			if (3 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a020012
			} else if (4 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a020016
			} else if (5 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a020020
			} else if (6 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a020024
			} else if (7 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a020028
			} else if (8 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a020032
			}
		} else if (6 == xmax - xmin) {
			if (3 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a024012
			} else if (4 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a024016
			} else if (5 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a024020
			} else if (6 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a024024
			} else if (7 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a024028
			} else if (8 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a024032
			}
		} else if (7 == xmax - xmin) {
			if (3 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a028012
			} else if (4 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a028016
			} else if (5 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a028020
			} else if (6 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a028024
			} else if (7 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a028028
			} else if (8 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a028032
			}
		} else if (8 == xmax - xmin) {
			if (3 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a032012
			} else if (4 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a032016
			} else if (5 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a032020
			} else if (6 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a032024
			} else if (7 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a032028
			} else if (8 == zmax - zmin) {
				assert Boolean.TRUE; //#dbg:BlockPlacementHouses:a032032
			}
		}
	}
}
