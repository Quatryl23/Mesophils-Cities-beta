
package net.mesomods.mesophilscities;

import org.apache.logging.log4j.LogManager;

import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;
import net.minecraftforge.fml.ModList;


import javax.annotation.Nullable;

import com.mojang.serialization.Codec;

public class SpecialRotationProcessor extends StructureProcessor {
	public static final Codec<SpecialRotationProcessor> CODEC = Codec.unit(new SpecialRotationProcessor());

	@Override
	public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos pos, BlockPos pivot, StructureTemplate.StructureBlockInfo original, StructureTemplate.StructureBlockInfo blockInfo, StructurePlaceSettings settings,
			@Nullable StructureTemplate template) {
		CompoundTag tag = blockInfo.nbt() != null ? blockInfo.nbt().copy() : null;
		if (tag != null) {
			//LogManager.getLogger().info("Found block entity of block " + blockInfo.state().toString() + " at " + blockInfo.pos().toString());
			int i;
			// compatibility with electricity blocks from Mr Crayfish´s Furniture Mod Refurbished
			if (tag.contains("NodePos", Tag.TAG_LONG)) {
				BlockPos nodePos = BlockPos.of(tag.getLong("NodePos"));
				long[] nodes = tag.getLongArray("Connections");
				i = 0;
				long[] rotatedNodes = new long[nodes.length];
				for (long node : nodes) {
					BlockPos otherNodePos = BlockPos.of(node);
					BlockPos offset = BlockPos.ZERO;
					int offsetX = otherNodePos.getX() - nodePos.getX();
					int offsetZ = otherNodePos.getZ() - nodePos.getZ();
					int offsetY = otherNodePos.getY() - nodePos.getY();
					switch (settings.getRotation()) {
						// rotate
						case CLOCKWISE_90 :
							offset = new BlockPos(-offsetZ, offsetY, offsetX);
							break;
						case CLOCKWISE_180 :
							offset = new BlockPos(-offsetX, offsetY, -offsetZ);
							break;
						case COUNTERCLOCKWISE_90 :
							offset = new BlockPos(offsetZ, offsetY, -offsetX);
							break;
						default :
							offset = new BlockPos(offsetX, offsetY, offsetZ);
					}
					rotatedNodes[i] = nodePos.offset(offset).asLong();
					i++;
				}
				tag.putLongArray("Connections", rotatedNodes);
				// APPLIES TO ELECTRICITY GENERATORS: Enables them to use their loot table
				if (tag.contains("Energy")) {
					tag.putBoolean("Enabled", true);
					int energyTicks = settings.getRandom(nodePos).nextInt(1, 640) * 25600; // 128-640 for hour-long supply
					tag.putInt("Energy", energyTicks);
					tag.putInt("TotalEnergy", energyTicks);
				}
			}
			// compatibility with create mod

            if (ModList.get().isLoaded("create")) {

			if (tag.contains("id", Tag.TAG_STRING)) {
				if (tag.getString("id").equals("create:track")) {
					ListTag connections = tag.getList("Connections", Tag.TAG_COMPOUND);
					i = 0;
					for (Tag connectionTag : connections) {
						CompoundTag connection = (CompoundTag) connectionTag;
						//accessing some values that must be changed
						CompoundTag newConnection = connection.copy();
						ListTag starts1 = newConnection.getList("Starts", Tag.TAG_COMPOUND).getCompound(0).getList("V", Tag.TAG_DOUBLE);
						ListTag starts2 = newConnection.getList("Starts", Tag.TAG_COMPOUND).getCompound(1).getList("V", Tag.TAG_DOUBLE);
						CompoundTag posTag = newConnection.getList("Positions", Tag.TAG_COMPOUND).getCompound(1);
						int x = posTag.getInt("X");
						int y = posTag.getInt("Y");
						int z = posTag.getInt("Z");
						ListTag axis1 = newConnection.getList("Axes", Tag.TAG_COMPOUND).getCompound(0).getList("V", Tag.TAG_DOUBLE);
						double axis1x = axis1.getDouble(0);
						double axis1z = axis1.getDouble(2);
						ListTag axis2 = newConnection.getList("Axes", Tag.TAG_COMPOUND).getCompound(1).getList("V", Tag.TAG_DOUBLE);
						double axis2x = axis2.getDouble(0);
						double axis2z = axis2.getDouble(2);
						int xNew = x;
						int yNew = y;
						int zNew = z;
						double axis1xNew = axis1x;
						double axis1zNew = axis1z;
						double axis2xNew = axis2x;
						double axis2zNew = axis2z;

						switch (settings.getRotation()) {
							// changing relative coordinates based on rotation
							case CLOCKWISE_90 :
								xNew = -z;
								zNew = x;
								axis1xNew = -axis1z;
								axis1zNew = axis1x;
								axis2xNew = -axis2z;
								axis2zNew = axis2x;
								break;
							case CLOCKWISE_180 :
								xNew = -x;
								zNew = -z;
								axis1xNew = -axis1x;
								axis1zNew = -axis1z;
								axis2xNew = -axis2x;
								axis2zNew = -axis2z;
								break;
							case COUNTERCLOCKWISE_90 :
								xNew = z;
								zNew = -x;
								axis1xNew = axis1z;
								axis1zNew = -axis1x;
								axis2xNew = axis2z;
								axis2zNew = -axis2x;
								break;
							default :
						}
						//setting new values
						posTag.putInt("X", xNew);
						posTag.putInt("Y", yNew);
						posTag.putInt("Z", zNew);
						axis1.set(0, DoubleTag.valueOf(axis1xNew));
						axis1.set(2, DoubleTag.valueOf(axis1zNew));
						axis2.set(0, DoubleTag.valueOf(axis2xNew));
						axis2.set(2, DoubleTag.valueOf(axis2zNew));
						starts1.set(0, DoubleTag.valueOf(0.5 + 0.5 * axis1xNew));
						starts1.set(2, DoubleTag.valueOf(0.5 + 0.5 * axis1zNew));
						starts2.set(0, DoubleTag.valueOf(xNew + 0.5 + 0.5 * axis2xNew));
						starts2.set(2, DoubleTag.valueOf(zNew + 0.5 + 0.5 * axis2zNew));
						//replacing old nbt tag with new one
						connections.set(i, newConnection);
						i++;
					}
				} else if (tag.getString("id").equals("create:track_signal") || tag.getString("id").equals("create:track_station")) {
					// rotating create signals and stations
					CompoundTag targetTrack = tag.getCompound("TargetTrack");
					ListTag prevAxis = tag.getList("PrevAxis", Tag.TAG_DOUBLE);
					double axisX = prevAxis.getDouble(0);
					double axisZ = prevAxis.getDouble(2);
					double axisXtemp = axisX;
					tag.remove("Id");
					CompoundTag bezierKey = tag.contains("Bezier") ? tag.getCompound("Bezier").getCompound("Key") : null;
					int bezierX = 0;
					int bezierZ = 0;
					int bezierXtemp = 0;
					if (bezierKey != null) {
						bezierX = bezierKey.getInt("X");
						bezierZ = bezierKey.getInt("Z");
						bezierXtemp = bezierX;
					}

					// int axisXtemp = axisX;
					boolean ortho = tag.getBoolean("Ortho");
					boolean changeDirection = ortho;
					int targetX = targetTrack.getInt("X");
					int targetZ = targetTrack.getInt("Z");
					int targetXtemp = targetX;
					boolean targetDirection = tag.getBoolean("TargetDirection");
					boolean isEastWest;
					if (Math.abs(axisX) == Math.abs(axisZ)) {
						isEastWest = axisX < 0;
						changeDirection = true;
					} else {
						isEastWest = axisX != 0;
					}
					String doorControl = tag.contains("DoorControl") ? tag.getString("DoorControl") : null;

					//LogManager.getLogger().info("Modifying signal or station at " + blockInfo.pos().toString() + ", currently having the following nbt data: axisX: " + axisX + ", axisZ: " + axisZ + ", bezierX: " + bezierX + ", bezierZ: " + bezierZ
					//		+ ", targetX: " + targetX + ", targetZ: " + targetZ + ", DoorControl: " + doorControl);
					int repeats = 0;
					switch (settings.getRotation()) {
						// changing relative coordinates based on rotation
						case CLOCKWISE_90 :
							repeats = 1;
							targetX = -targetZ;
							targetZ = targetXtemp;
							axisX = -axisZ;
							axisZ = axisXtemp;
							if (bezierKey != null) {
								bezierX = -bezierZ;
								bezierZ = bezierXtemp;
							}
							if (isEastWest ^ ortho) {
								targetDirection = !targetDirection;

							}
							break;

						case CLOCKWISE_180 :
							repeats = 2;
							targetX = -targetX;
							targetZ = -targetZ;
							axisX = -axisX;
							axisZ = -axisZ;
							if (bezierKey != null) {
								bezierX = -bezierX;
								bezierZ = -bezierZ;
							}
							targetDirection = !targetDirection;
							break;

						case COUNTERCLOCKWISE_90 :
							repeats = 3;
							targetX = targetZ;
							targetZ = -targetXtemp;
							axisX = axisZ;
							axisZ = -axisXtemp;
							if (bezierKey != null) {
								bezierX = bezierZ;
								bezierZ = -bezierXtemp;
							}
							if (!isEastWest ^ ortho) {
								targetDirection = !targetDirection;

							}
							break;

						default :
					}
					if (doorControl != null && repeats != 0) {
						doorControl = this.rotateString(doorControl, repeats);
						tag.putString("DoorControl", doorControl);
					}
					if (changeDirection) {
						tag.putBoolean("TargetDirection", targetDirection);
					}
					if (axisX == -1) {
						axisX = 1;
					}
					if (axisZ == -1) {
						axisZ = 1;
					}
					prevAxis.set(0, DoubleTag.valueOf(axisX));
					prevAxis.set(2, DoubleTag.valueOf(axisZ));
					targetTrack.putInt("X", targetX);
					targetTrack.putInt("Z", targetZ);
					if (bezierKey != null) {
						bezierKey.putInt("X", bezierX);
						bezierKey.putInt("Z", bezierZ);
					}
					/* if (tag.getString("id").equals("create:track_station")) {
						MesophilsCitiesMod.queueServerWork(20, () -> {
							ResourceKey<Level> dimension = ResourceKey.create(Registries.DIMENSION, new ResourceLocation("mesophils_cities", "city"));
							com.simibubi.create.content.train.GlobalStation station = com.simibubi.create.RAILWAYS.getStation(dimension, blockInfo.pos());
							if (station != null) {
								station.name = "ooo";
								com.simibubi.create.RAILWAYS.markTracksDirty();
							}

						});
					} */
					//LogManager.getLogger().info("Modified signal or station at " + blockInfo.pos().toString() + " to have the following nbt data: axisX: " + axisX + ", axisZ: " + axisZ + ", bezierX: " + bezierX + ", bezierZ: " + bezierZ
					//		+ ", targetX: " + targetX + ", targetZ: " + targetZ + ", DoorControl: " + doorControl);

				}
			}
		}
		}
		return new StructureTemplate.StructureBlockInfo(blockInfo.pos(), blockInfo.state(), tag);
	}

	private String rotateString(String dir, int repeats) {
		for (int i = repeats; i > 0; i--) {
			switch (dir) {
				case "NORTH" :
					dir = "EAST";
					break;
				case "EAST" :
					dir = "SOUTH";
					break;
				case "SOUTH" :
					dir = "WEST";
					break;
				case "WEST" :
					dir = "NORTH";
					break;
				default :
					return dir;
			}
		}
		return dir;
	}

	@Override
	protected StructureProcessorType<?> getType() {
		return net.mesomods.mesophilscities.ModStructureProcessors.SPECIAL_ROTATION_PROCESSOR.get();
	}
}
