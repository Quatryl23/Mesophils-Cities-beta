package net.mesomods.mesophilscities;

import net.minecraft.util.RandomSource;

import java.util.List;
import java.util.ArrayList;

public class WeightedTemplatePieceList {
    	private final List<CityStructureTemplatePiece> templatePieces = new ArrayList();
	    private int totalWeight = 0;
	    private final List<Integer> cumulativeWeights = new ArrayList();

	

	public void add(CityStructureTemplatePiece piece, int weight) {
		weight = Math.min(Math.max(weight, 1), 100);
		templatePieces.add(piece);
		totalWeight += weight;
		cumulativeWeights.add(totalWeight);
	}

	public boolean isEmpty() {
		return templatePieces.isEmpty();
	}

	public CityStructureTemplatePiece random(RandomSource random) {
		if (isEmpty()) {
			return null;
		}
		int randomInt = random.nextInt(totalWeight) + 1;
		int elementWeight = 0;
		int elementNumber = 0;
		while (elementWeight < randomInt) {
			elementWeight = cumulativeWeights.get(elementNumber);
			elementNumber++;
		}
		return templatePieces.get(elementNumber - 1);
	}

	public List<Object[]> getAllWithWeights() {
		if (isEmpty()) return null;
		List<Object[]> list = new ArrayList();
		for (int i=0; i<templatePieces.size(); i++) {
			int weight = (i == 0) ? cumulativeWeights.get(i) : cumulativeWeights.get(i) - cumulativeWeights.get(i-1);
			list.add(new Object[] {templatePieces.get(i), weight});
		}
		return list;
	}
}
