package org.aitek.ml.core.similarity;

public class SimilarityFactory {

	public enum SimilarityType {
		EUCLIDEAN, MANHATTAN, PEARSON;
	}

	public static Measurable getSimilarity(SimilarityType type) {

		switch (type) {
			case EUCLIDEAN:
				return new EuclideanDistance();
			case MANHATTAN:
				return new ManhattanDistance();
			case PEARSON:
				return new PearsonCorrelation();
			default:
				return null;
		}

	}

}
