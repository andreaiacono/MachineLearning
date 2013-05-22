package org.aitek.ml.demo;

import org.aitek.ml.neuralnetworks.AndOperatorNetwork;
import org.aitek.ml.neuralnetworks.OrOperatorNetwork;
import org.neuroph.core.NeuralNetwork;

public class NeuralNetworkLogicOperators {

	public static void main(String[] args) throws Exception {

		NeuralNetwork orOperator = OrOperatorNetwork.getNetwork();
		printTruthTable(orOperator, "OR");

		NeuralNetwork andOperator = AndOperatorNetwork.getNetwork();
		printTruthTable(andOperator, "AND");
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
