package org.aitek.ml.classification;

/**
 * Java program for classifying short text messages into two classes.
 */

import java.io.BufferedReader;
import java.io.FileReader;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class TwitterBayesianClassifier {

	private Instances trainingData = null;
	private final StringToWordVector wordCounterFilter = new StringToWordVector();
	private final Classifier classifier = new NaiveBayes();

	// the classification types defined
	public enum ClassName {
		event, health, camera, apparel, art, tech, home
	}

	public TwitterBayesianClassifier() throws Exception {

		// creates the structure of data
		FastVector attributes = new FastVector(2);
		attributes.addElement(new Attribute("tweet", (FastVector) null));
		FastVector classValues = new FastVector(ClassName.values().length);
		for (ClassName className : ClassName.values()) {
			classValues.addElement(className.toString());
		}
		attributes.addElement(new Attribute("class", classValues));

		// creates the dataset
		trainingData = new Instances("TweetsClassification", attributes, 600);
		trainingData.setClassIndex(1);
	}

	/**
	 * trains the classifier with a tweet
	 * 
	 * @param tweet the content of the tweet
	 * @param classValue the class related to this tweet
	 * @throws Exception
	 */
	public void trainClassifier(String tweet, String className) throws Exception {

		// creates the instance for this message
		Instance instance = makeInstance(tweet, trainingData);
		instance.setClassValue(className);

		// adds it to the training data.
		trainingData.add(instance);
	}

	/**
	 * classifies a new tweet
	 * 
	 * @param tweet the content of the tweet
	 * @throws Exception
	 */
	public ClassName classifyMessage(String tweet) throws Exception {

		// checks if the classifier has been trained
		if (trainingData.numInstances() == 0) {
			throw new Exception("The classifier hasn't been trained yet.");
		}

		// avoid inserting the new tweet into the training set
		Instances testset = trainingData.stringFreeStructure();

		// classifies the new tweet
		wordCounterFilter.input(makeInstance(tweet, testset));
		double predicted = classifier.classifyInstance(wordCounterFilter.output());
		ClassName className = ClassName.values()[(int) predicted];

		System.out.println("The tweet [" + tweet + "] has been classified as " + className.toString());
		return className;
	}

	/**
	 * converts a tweet into an instance
	 * 
	 * @param tweet
	 * @param data
	 * @return
	 */
	private Instance makeInstance(String tweet, Instances dat2a) {

		// Create instance of length two.
		Instance instance = new Instance(2);

		// Set value for message attribute
		Attribute messageAtt = trainingData.attribute("tweet");
		instance.setValue(messageAtt, messageAtt.addStringValue(tweet));

		// Give instance access to attribute information from the dataset.
		instance.setDataset(trainingData);
		return instance;
	}

	/**
	 * trains the classifier with a file containing tweets and classes. The format has to be:
	 * 'TWEET_CONTENT',CLASS\n
	 * 
	 * @param filename
	 * @throws Exception
	 */
	public void trainFromTweetsFile(String filename) throws Exception {

		BufferedReader reader = null;
		try {

			// reads from file
			reader = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = reader.readLine()) != null) {

				// reads the tweet and the associated class
				String[] tokens = line.split("',");
				String tweet = tokens[0].substring(1);
				String className = tokens[1];

				// updates the training data
				trainClassifier(tweet, className);
				// System.out.println("Trained classifier with: [" + tweet + "] as " + className);
			}

			// initializes the filter, generate word counts and rebuild classifier
			wordCounterFilter.setInputFormat(trainingData);
			Instances filteredData = Filter.useFilter(trainingData, wordCounterFilter);
			classifier.buildClassifier(filteredData);
		}
		finally {
			if (reader != null) reader.close();
		}
	}

	/**
	 * trains the classifier with a single tweet and the related class
	 * 
	 * @param tweet
	 * @param className
	 */
	public void trainFromTweet(String tweet, ClassName className) throws Exception {

		// and updates the training data
		trainClassifier(tweet, className.toString());
	}

	/**
	 * outputs some info about the classifier
	 * 
	 * @throws Exception
	 */
	public void getModel() throws Exception {

		Evaluation eTest = new Evaluation(trainingData);
		eTest.evaluateModel(classifier, trainingData);
		String strSummary = eTest.toSummaryString();
		System.out.println(strSummary);
		// System.err.println(m_Classifier);

		// outputs the confusion matrix
		double[][] cmMatrix = eTest.confusionMatrix();
		for (int row_i = 0; row_i < cmMatrix.length; row_i++) {
			for (int col_i = 0; col_i < cmMatrix.length; col_i++) {
				System.out.print(cmMatrix[row_i][col_i]);
				System.out.print("|");
			}
			System.out.println();
		}

	}

}