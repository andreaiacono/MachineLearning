package org.aitek.ml.demo;

import org.aitek.ml.geneticalgorithms.KnapsackProblem;

public class GeneticAlgorithmSample {

	public static void main(String[] args) {

		double[] values = new double[] { 5.0, 1.5, 2.0, 0.5, 3.0, 6.0, 2.5, 4.0, 3.1, 5.5, 3.2, 5.0, 2.2, 6.1, 4.0, 1.0 };
		double[] weights = new double[] { 2.8, 5.0, 2.1, 1.5, 0.5, 5.5, 1.1, 2.2, 4.5, 3.8, 2.1, 6.4, 4.1, 3.2, 4.5, 2.8 };

		KnapsackProblem knapsackProblem = new KnapsackProblem(15, 20, values, weights);
		knapsackProblem.evolve();
	}
}
