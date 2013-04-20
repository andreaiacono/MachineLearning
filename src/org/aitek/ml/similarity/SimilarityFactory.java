package org.aitek.ml.similarity;

public class SimilarityFactory {

	public enum SimilarityMethod {
		EUCLIDEAN, MANHATTAN, PEARSON;
	}

	public static Similarity getSimilarity(SimilarityMethod type) {

		switch (type) {
			case EUCLIDEAN:
				return new EuclideanDistance();
			case MANHATTAN:
				return new ManhattanDistance();
			case PEARSON:
				return new PearsonCorrelation();
		}
		return null;
	}

}
