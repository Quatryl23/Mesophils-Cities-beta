package net.mesomods.mesophilscities;

import net.mesomods.mesophilscities.WeightedTemplatePieceList;
import net.mesomods.mesophilscities.CityStructureTemplatePiece;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Rotation;
import java.util.List;

public class CityTemplateType {
	private WeightedTemplatePieceList north = new WeightedTemplatePieceList();
	private WeightedTemplatePieceList south = new WeightedTemplatePieceList();
	private WeightedTemplatePieceList west = new WeightedTemplatePieceList();
	private WeightedTemplatePieceList east = new WeightedTemplatePieceList();

	public CityStructureTemplatePiece randomOfDirection(String direction, RandomSource random) {
		switch (direction) {
			case "n" :
				return north.random(random);
			case "s" :
				return south.random(random);
			case "w" :
				return west.random(random);
			case "e" :
				return east.random(random);
		}
		return new CityStructureTemplatePiece();
	}

	public CityStructureTemplatePiece randomOfAxis(String axis, RandomSource random) {
		boolean positive = random.nextBoolean();
		if (axis.equals("x")) {
			return positive ? east.random(random) : west.random(random);	
			} else {
			return positive ? south.random(random) : north.random(random);	
			}
	}

	public CityStructureTemplatePiece randomOfRandom(RandomSource random) {
		switch (random.nextInt(4)) {
			case 0:
			return north.random(random);
			case 1:
			return south.random(random);
			case 2: 
			return west.random(random);
			case 3:
			return east.random(random);
		}
		return new CityStructureTemplatePiece();
	}

	public void addToDirection(CityStructureTemplatePiece piece, int weight, String direction) {
		switch (direction) {
			case "n", "north" :
				north.add(piece, weight); break;
			case "s", "south" :
				south.add(piece, weight); break;
			case "w", "west" :
				west.add(piece, weight); break;
			case "e", "east" :
				east.add(piece, weight); break;
		}
	}

	public void addToCurveDirection(CityStructureTemplatePiece piece, int weight, String direction) {
		switch (direction) {
			case "nw", "northwest", "north_west" :
				north.add(piece, weight); break;
			case "se", "southeast", "south_east":
				south.add(piece, weight); break;
			case "sw", "southwest", "south_west":
				west.add(piece, weight); break;
			case "ne", "northeast", "north_east":
				east.add(piece, weight); break;
		}
	}

	public void addToAxis(CityStructureTemplatePiece piece, int weight, String axis) {
		if (axis.equals("x")) {
			west.add(piece, weight);
			east.add(piece.rotated(Rotation.CLOCKWISE_180), weight);
		} else {
			north.add(piece, weight);
			south.add(piece.rotated(Rotation.CLOCKWISE_180), weight);
		}
	}

	public void addToAll(CityStructureTemplatePiece piece, int weight) {
		north.add(piece, weight);
		south.add(piece.rotated(Rotation.CLOCKWISE_180), weight);
		west.add(piece.rotated(Rotation.COUNTERCLOCKWISE_90), weight);
		east.add(piece.rotated(Rotation.CLOCKWISE_90), weight);
	}

	public void addToAllFromX(CityStructureTemplatePiece piece, int weight) {
		north.add(piece.rotated(Rotation.COUNTERCLOCKWISE_90), weight);
		south.add(piece.rotated(Rotation.CLOCKWISE_90), weight);
		west.add(piece, weight);
		east.add(piece.rotated(Rotation.CLOCKWISE_180), weight);
	}

	public boolean add(CityStructureTemplatePiece piece, int weight, String direction) {
		switch (direction) {
			case "n", "s", "w", "e", "north", "south", "west", "east" -> {
				addToDirection(piece, weight, direction);
			}
			case "nw", "ne", "sw", "se", "northwest", "northeast", "southwest", "southeast", "north_west", "north_east", "south_west", "south_east" -> {
				addToCurveDirection(piece, weight, direction);
			}
			case "x", "z" -> {
				addToAxis(piece, weight, direction);
			}
			case "rotate", "rotate_n", "rotate_z" -> {
				addToAll(piece, weight);
			}
			case "alt_rotate", "rotate_w", "rotate_x" -> {
				addToAllFromX(piece, weight);
			}
			default -> {
				// invalid direction declared in json file
				return false;
			}
		}
		return true;
	}

	public int addFallback(String location, int height, String[] directions) {
		//System.out.println("Adding fallback for " + location);
		int addedTemplates = 0;
		if (north.isEmpty()) {
			CityStructureTemplatePiece piece = new CityStructureTemplatePiece().fallbackPiece(location + directions[0], height);
			north.add(piece, 1);
			addedTemplates++;
			//System.out.println("Adding fallback north");
		}
		if (south.isEmpty()) {
			CityStructureTemplatePiece piece = new CityStructureTemplatePiece().fallbackPiece(location + directions[1], height);
			south.add(piece, 1);
			addedTemplates++;
			//System.out.println("Adding fallback south");
		}
		if (west.isEmpty()) {
			CityStructureTemplatePiece piece = new CityStructureTemplatePiece().fallbackPiece(location + directions[2], height);
			west.add(piece, 1);
			addedTemplates++;
			//System.out.println("Adding fallback west");
		}
		if (east.isEmpty()) {
			CityStructureTemplatePiece piece = new CityStructureTemplatePiece().fallbackPiece(location + directions[3], height);
			east.add(piece, 1);
			addedTemplates++;
			//System.out.println("Adding fallback east");
		}
		return addedTemplates;
	}

	public WeightedTemplatePieceList getListNorth() {
		return north;
	}

	public WeightedTemplatePieceList getListSouth() {
		return south;
	}
	
	public WeightedTemplatePieceList getListEast() {
		return east;
	}
	
	public WeightedTemplatePieceList getListWest() {
		return west;
	}

	public WeightedTemplatePieceList getList(String direction) {
		switch (direction) {
			case "n" :
				return north;
			case "s" :
				return south;
			case "w" :
				return west;
			case "e" :
				return east;
		}
		return new WeightedTemplatePieceList();
	}

	public List<WeightedTemplatePieceList> getAllDirectionLists() {
		return List.of(north, south, west, east);
	}

	
	

}
