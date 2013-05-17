package org.aitek.ml.demo;

import org.aitek.ml.clustering.KMeansClustering;

public class Clustering {

	public static final double[][] points = { { 1, 1 }, { 2, 1 }, { 1, 2 }, { 5, 2 }, { 1, 6 }, { 4, 5 }, { 9, 3 }, { 1, 9 }, { 6, 9 } };

	public static void main(String[] args) throws Exception {

		KMeansClustering kmenClustering = new KMeansClustering();
		kmenClustering.cluster(points, 3);
	}
}
