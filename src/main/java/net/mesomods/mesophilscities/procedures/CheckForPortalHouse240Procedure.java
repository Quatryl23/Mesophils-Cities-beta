package net.mesomods.mesophilscities.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.ServerChatEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.core.Direction;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class CheckForPortalHouse240Procedure {
	@SubscribeEvent
	public static void onChat(ServerChatEvent event) {
		execute(event, event.getPlayer().level(), event.getPlayer().getX(), event.getPlayer().getZ());
	}

	public static ListTag execute(LevelAccessor world, double x, double z) {
		return execute(null, world, x, z);
	}

	private static ListTag execute(@Nullable Event event, LevelAccessor world, double x, double z) {
		ListTag portal_house;
		double checkatx = 0;
		double checkatz = 0;
		double i = 0;
		double j = 0;
		double k = 0;
		double l = 0;
		double checkatx240 = 0;
		double checkatz240 = 0;
		Direction direction = Direction.NORTH;
		checkatx240 = Math.floor((x - 128) / 240) * 240 + 240;
		checkatz240 = Math.floor((z - 128) / 240) * 240 + 240;
		portal_house = new ListTag();
		for ( k = (int) 0; k < (int) 99; k++) {
			for ( l = (int) 0; l <= (int) Math.min(Math.floor(k / 2), 14); l++) {
				checkatx = checkatx240;
				checkatz = checkatz240;
				for ( i = (int) 0; i < (int) 29; i++) {
					for ( j = (int) 0; j <= (int) Math.min(Math.floor(i / 2), 14); j++) {
						if (TrainStreetCalculationProcedure.execute(checkatx, checkatz) == 0) {
							portal_house = PathDirectionProcedure.execute(checkatx, checkatz)
									? CheckForPortalHousePlacementTrueProcedure.execute(checkatx, checkatz)
									: CheckForPortalHousePlacementFalseProcedure.execute(checkatx, checkatz);
							if (!portal_house.isEmpty()) {
								break;
							}
						}
						if (i % 4 == 0) {
							checkatz = checkatz - 48;
						} else if (i % 4 == 1) {
							checkatx = checkatx + 48;
						} else if (i % 4 == 2) {
							checkatz = checkatz + 48;
						} else {
							checkatx = checkatx - 48;
						}
					}
					if (!portal_house.isEmpty()) {
						break;
					}
				}
				if (!portal_house.isEmpty()) {
					break;
				}
				if (k % 4 == 0) {
					checkatz240 = checkatz240 - 720;
				} else if (k % 4 == 1) {
					checkatz240 = checkatx240 + 720;
				} else if (k % 4 == 2) {
					checkatz240 = checkatz240 + 720;
				} else {
					checkatz240 = checkatx240 - 720;
				}
			}
			if (!portal_house.isEmpty()) {
				break;
			}
		}
		if (((portal_house.get(2)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) == 0) {
			checkatx = checkatx + 5;
			checkatz = checkatz + 12;
			direction = Direction.NORTH;
		} else if (((portal_house.get(2)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) == 1) {
			checkatx = checkatx + 5;
			checkatz = checkatz + 3;
			direction = Direction.SOUTH;
		} else if (((portal_house.get(2)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) == 2) {
			checkatx = checkatx + 9;
			checkatz = checkatz + 7;
			direction = Direction.WEST;
		} else if (((portal_house.get(2)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) == 3) {
			checkatx = checkatx + 2;
			checkatz = checkatz + 7;
			direction = Direction.EAST;
		} else if (((portal_house.get(2)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) == 4) {
			checkatx = checkatx + 7;
			checkatz = checkatz + 9;
			direction = Direction.NORTH;
		} else if (((portal_house.get(2)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) == 5) {
			checkatx = checkatx + 7;
			checkatz = checkatz + 2;
			direction = Direction.SOUTH;
		} else if (((portal_house.get(2)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) == 6) {
			checkatx = checkatx + 12;
			checkatz = checkatz + 5;
			direction = Direction.WEST;
		} else if (((portal_house.get(2)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0) == 7) {
			checkatx = checkatx + 3;
			checkatz = checkatz + 5;
			direction = Direction.EAST;
		}
		portal_house.setTag(0, IntTag.valueOf((int) (checkatx - 20 + 4 * ((portal_house.get(0)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0))));
		portal_house.setTag(1, IntTag.valueOf((int) (checkatz - 20 + 4 * ((portal_house.get(1)) instanceof IntTag _intTag ? _intTag.getAsInt() : 0))));
		portal_house.setTag(2, (direction == Direction.NORTH) ? IntTag.valueOf(0) : (direction == Direction.SOUTH) ? IntTag.valueOf(1) : (direction == Direction.WEST) ? IntTag.valueOf(2) : IntTag.valueOf(3));
		return portal_house;
	}
}
