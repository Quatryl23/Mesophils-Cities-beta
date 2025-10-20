package net.mesomods.mesophilscities;

import net.mesomods.mesophilscities.CityMultiPieceTemplateType;

import net.minecraft.util.RandomSource;

import java.util.List;
import java.util.ArrayList;


public class CityMultiPieceTemplateList {
	private final List<CityMultiPieceTemplateType> templates = new ArrayList();
	    private int totalWeight = 0;
	    private final List<Integer> cumulativeWeights = new ArrayList();
	    public int sizeX;
	    public int sizeZ;
	public CityMultiPieceTemplateList(int x, int z) {
		sizeX = x;
		sizeZ = z;
	}

	

	public void add(CityMultiPieceTemplateType piece, int weight) {
		weight = Math.min(Math.max(weight, 1), 100);
		templates.add(piece);
		totalWeight += weight;
		cumulativeWeights.add(totalWeight);
	}

	public boolean isEmpty() {
		return templates.isEmpty();
	}

	public CityMultiPieceTemplateType random(RandomSource random) {
		int randomInt = random.nextInt(totalWeight) + 1;
		int elementWeight = 0;
		int elementNumber = 0;
		while (elementWeight < randomInt) {
			elementWeight = cumulativeWeights.get(elementNumber);
			elementNumber++;
		}
		return templates.get(elementNumber - 1);
	}

	public CityMultiPieceTemplateType[] getAll() {
		return templates.toArray(new CityMultiPieceTemplateType[0]);
	}

}
