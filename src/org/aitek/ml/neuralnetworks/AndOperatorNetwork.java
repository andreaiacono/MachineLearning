package org.aitek.ml.neuralnetworks;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.learning.DataSet;
import org.neuroph.core.learning.DataSetRow;
import org.neuroph.nnet.Perceptron;

public class AndOperatorNetwork {

	private static Perceptron neuralNetwork;

	static {

		neuralNetwork = new Perceptron(2, 1);

		// defines a training set for OR logic operation
		DataSet trainingSet = new DataSet(2, 1);
		trainingSet.addRow(new DataSetRow(new double[] { 0, 0 }, new double[] { 0 }));
		trainingSet.addRow(new DataSetRow(new double[] { 0, 1 }, new double[] { 0 }));
		trainingSet.addRow(new DataSetRow(new double[] { 1, 0 }, new double[] { 0 }));
		trainingSet.addRow(new DataSetRow(new double[] { 1, 1 }, new double[] { 1 }));

		// trains the network for OR logic operator
		neuralNetwork.learn(trainingSet);
	}

	public static NeuralNetwork getNetwork() {

		return neuralNetwork;
	}
}
