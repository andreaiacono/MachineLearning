package org.aitek.ml.demo;

import org.aitek.ml.neuralnetworks.AndOperatorNetwork;
import org.aitek.ml.neuralnetworks.OrOperatorNetwork;
import org.aitek.ml.neuralnetworks.PricePrediction;
import org.neuroph.core.NeuralNetwork;

public class NeuralNetworkSample {

	public static void main(String[] args) throws Exception {

		NeuralNetwork orOperator = OrOperatorNetwork.getNetwork();
		printTruthTable(orOperator, "OR");

		NeuralNetwork andOperator = AndOperatorNetwork.getNetwork();
		printTruthTable(andOperator, "AND");

		double[] data = { 77.77D, 76.85D, 77.25D, 79.15D, 81.23D, 82.04D, 83.46D, 85.71D, 88.25D, 88.42D, 88.40D, 87.54D, 87.02D, 87.25D, 86.7D, 85.73D, 85.38D, 86.96D, 88.17D, 88.56D, 86.77D, 82.85D, 82.13D };// 2009
		PricePrediction pricePrediction = new PricePrediction(data, 1000);

		double[] newData = { 88.17D, 88.56D, 86.77, 82.85D };
		double prediction = pricePrediction.predict(newData);
		System.out.println("Price Prediction: " + prediction);
	}

	public static void printTruthTable(NeuralNetwork network, String label) {

		System.out.println("\n\n  X1\tX2\t| " + label);
		System.out.println("-----------------------");
		for (int x1 = 0; x1 < 2; x1++) {
			for (int x2 = 0; x2 < 2; x2++) {

				network.setInput(x1, x2);
				network.calculate();
				System.out.println("  " + x1 + "\t" + x2 + "\t| " + (int) network.getOutput()[0]);
			}
		}
	}
}
