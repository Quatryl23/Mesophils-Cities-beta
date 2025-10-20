package net.mesomods.mesophilscities;

import net.minecraft.world.level.block.Rotation;
import net.minecraft.util.RandomSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CityMultiPieceTemplateType {
	int sizeX;
	int sizeZ;
	WeightedTemplatePieceList[][] north;
	WeightedTemplatePieceList[][] south;
	WeightedTemplatePieceList[][] west;
	WeightedTemplatePieceList[][] east;

	public CityMultiPieceTemplateType(int x, int z) {
		sizeX = x;
		sizeZ = z;
		int i;
		int j;
		north = new WeightedTemplatePieceList[x][z];
		south = new WeightedTemplatePieceList[x][z];
		west = new WeightedTemplatePieceList[z][x];
		east = new WeightedTemplatePieceList[z][x];
		for (i = 0; i < x; i++) {
    		for (j = 0; j < z; j++) {
       			north[i][j] = new WeightedTemplatePieceList();
       			south[i][j] = new WeightedTemplatePieceList();
       			west[j][i] = new WeightedTemplatePieceList();
       			east[j][i] = new WeightedTemplatePieceList();
    		}
		}
	}

	public CityStructureTemplatePiece randomOfDirection(int x, int z, String direction, RandomSource random) {
		//LogManager.getLogger().info("Trying to access MultiTemplateType with piece id " + x + "-" + z + " and direction " + direction);
		switch (direction) {
			case "n": return north[x][z].random(random);
			case "s": return south[x][z].random(random);
			case "w": return west[x][z].random(random);
			case "e": return east[x][z].random(random);
			default: return null;
		}
	}

	public CityStructureTemplatePiece randomOfAxis(int x, int z, String axis, RandomSource random) {
		if (axis.equals("x")) {
			return random.nextBoolean() ? west[x][z].random(random) : east[x][z].random(random);
		} else {
			return random.nextBoolean() ? north[x][z].random(random) : south[x][z].random(random);
		}
	}

	public void addToDirection(int x, int z, CityStructureTemplatePiece piece, int weight, String direction, byte rotationMode) {
		switch (direction) {
			case "n", "north" :
				north[x][z].add(piece, weight); return;
			case "s", "south" :
				switch (rotationMode) {
					case 0: south[x][z].add(piece, weight); return;
					case 1: south[sizeX-x-1][sizeZ-z-1].add(piece.rotated(Rotation.CLOCKWISE_180), weight); return;
					case 2: south[sizeX-x-1][sizeZ-z-1].add(piece.rotated(Rotation.CLOCKWISE_180), weight); return;
				}	
			case "w", "west" :
			switch (rotationMode) {
					case 0: west[x][z].add(piece, weight); return;
					case 1: west[x][z].add(piece, weight); return;
					case 2: west[z][sizeX-x-1].add(piece.rotated(Rotation.COUNTERCLOCKWISE_90), weight); return;
				}
			case "e", "east" :
			switch (rotationMode) {
					case 0: east[x][z].add(piece, weight); return;
					case 1: east[sizeX-x-1][sizeZ-z-1].add(piece.rotated(Rotation.CLOCKWISE_180), weight); return;
					case 2: east[sizeZ-z-1][x].add(piece.rotated(Rotation.CLOCKWISE_90), weight); return;
				}
		}
	}

	public void addToAxis(int x, int z, CityStructureTemplatePiece piece, int weight, String axis) {
		if (axis.equals("x")) {
			west[x][z].add(piece, weight);
			east[sizeX-x-1][sizeZ-z-1].add(piece.rotated(Rotation.CLOCKWISE_180), weight);
		} else {
			north[x][z].add(piece, weight);
			south[sizeX-x-1][sizeZ-z-1].add(piece.rotated(Rotation.CLOCKWISE_180), weight);
		}
	}
	
	public void addToAll(int x, int z, CityStructureTemplatePiece piece, int weight) {
			north[x][z].add(piece, weight);
			south[sizeX-x-1][sizeZ-z-1].add(piece.rotated(Rotation.CLOCKWISE_180), weight);
			west[z][sizeX-x-1].add(piece.rotated(Rotation.COUNTERCLOCKWISE_90), weight);
			east[sizeZ-z-1][x].add(piece.rotated(Rotation.CLOCKWISE_90), weight);
	}

	public boolean add(int x, int z, CityStructureTemplatePiece piece, int weight, String direction, byte rotationMode) {
		switch (direction) {
			case "n", "s", "w", "e", "north", "south", "east", "west" -> {
				addToDirection(x, z, piece, weight, direction, rotationMode);
			}
			case "x", "z" -> {
				addToAxis(x, z, piece, weight, direction);
			}
			case "rotate" -> {
				addToAll(x, z, piece, weight);
			}
			default -> {
				// invalid direction declared in json file
				return false;
			}
		}
		return true;
	}

	public int addFallback(String location, int height, String[] directions) {
		int addedTemplates = 0;
		//System.out.println("Adding fallback for " + location);
		for (int i=0; i < sizeX; i++) {
			for (int j=0; j < sizeZ; j++) {
				//System.out.println(j+"-"+i);
				if (north[i][j].isEmpty()) {
					CityStructureTemplatePiece piece = new CityStructureTemplatePiece().fallbackPiece(location + directions[0] + "_" + j + i, height);
					north[i][j].add(piece, 1);
					addedTemplates++;
				}
				if (south[i][j].isEmpty()) {
					CityStructureTemplatePiece piece = new CityStructureTemplatePiece().fallbackPiece(location + directions[1] + "_" + j + i, height);
					south[i][j].add(piece, 1);
					addedTemplates++;
				}
				if (west[j][i].isEmpty()) {
					CityStructureTemplatePiece piece = new CityStructureTemplatePiece().fallbackPiece(location + directions[2] + "_" + j + i, height);
					west[j][i].add(piece, 1);
					addedTemplates++;
				}
				if (east[j][i].isEmpty()) {
					CityStructureTemplatePiece piece = new CityStructureTemplatePiece().fallbackPiece(location + directions[3] + "_" + j + i, height);
					east[j][i].add(piece, 1);
					addedTemplates++;
				}
			}
		}
		return addedTemplates;
	}

	/* public int addBridgeFallback(String location, int height, String[] directions) {
		int addedTemplates = 0;
		System.out.println("Adding fallback for " + location);
		
	} */
}
