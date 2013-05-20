package org.aitek.ml.neuralnetworks;

/***
 * The Example is free software; you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 * 
 * The Example is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Neuroph. If
 * not, see <http://www.gnu.org/licenses/>.
 */

import org.aitek.ml.tools.Utils;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.learning.DataSet;
import org.neuroph.core.learning.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.LMS;

/**
 * Predicts a price using a neural network trained with historical data
 * 
 * @author andrea
 * 
 */
public class PricePrediction {

	private final NeuralNetwork neuralNetwork;
	private double datamax = 0;
	private double datamin = 0;

	/**
	 * creates a neural network for price prediction
	 * 
	 * @param data the historical data of the price of a particular item
	 * @param maxIterations the maximum number of iterations for learning
	 */
	public PricePrediction(double[] data, int maxIterations) {

		// creates the network
		neuralNetwork = new MultiLayerPerceptron(4, 9, 1);
		((LMS) neuralNetwork.getLearningRule()).setMaxError(0.001);
		((LMS) neuralNetwork.getLearningRule()).setLearningRate(0.7);
		((LMS) neuralNetwork.getLearningRule()).setMaxIterations(maxIterations);

		// gets min and max of data and normalizes them
		datamax = Utils.getMax(data) * 1.2D;
		datamin = Utils.getMin(data) * 0.8D;

		// trains the netowrk with the historical data; the input is the price of the last 4 days,
		// while the output is the price of the day after the 4th
		DataSet trainingSet = new DataSet(4, 1);
		for (int i = 0; i < data.length - 5; i++) {
			trainingSet.addRow(new DataSetRow(new double[] { (data[i] - datamin) / datamax, (data[i + 1] - datamin) / datamax, (data[i + 2] - datamin) / datamax, (data[i + 3] - datamin) / datamax }, new double[] { (data[i + 4] - datamin) / datamax }));
		}
		System.out.println("\nTraining network, please wait...");
		neuralNetwork.learn(trainingSet);
		System.out.println("PricePrediction Neural network is ready.\n");
	}

	/**
	 * Predicts the new price. As for the training set, the input of the neural network is the last
	 * 4 prices and the output is the prediction of the next price. This method has to get in input
	 * the last 4 prices in order to predict the next one.
	 * 
	 * @param newData the last 4 prices
	 * @return the prediction of the next price
	 */
	public double predict(double[] newData) {

		// puts the new data as the input of the NN
		DataSet dataSet = new DataSet(4, 0);
		dataSet.addRow(new DataSetRow(new double[] { (newData[0] - datamin) / datamax, (newData[1] - datamin) / datamax, (newData[2] - datamin) / datamax, (newData[3] - datamin) / datamax }));
		neuralNetwork.setInput(dataSet.getRowAt(0).getInput());
		neuralNetwork.calculate();

		return neuralNetwork.getOutput()[0] * datamax + datamin;
	}
}