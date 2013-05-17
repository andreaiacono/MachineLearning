package org.aitek.ml.classification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.DatabaseLoader;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class TwitterBayesianClassifier {

	private final FilteredClassifier model;
	private boolean isTrained;

	public enum ClassName {
		event, health, camera, apparel, art, tech, home
	}

	public TwitterBayesianClassifier() {

		model = new FilteredClassifier();
		model.setFilter(new StringToWordVector());
		model.setClassifier(new NaiveBayes());
	}

	/**
	 * 
	 * @param url the JDBC URL to access the database (e.g.: "jdbc:mysql://localhost:3306/db_name")
	 * @param username the user used to access the database
	 * @param password the password for the user
	 * @param query the query to retrieve data (e.g.: "SELECT * FROM data")
	 * @throws Exception
	 */
	public void train(String url, String username, String password, String query) throws Exception {

		DatabaseLoader loader = new DatabaseLoader();
		loader.setSource(url, username, password);
		loader.setQuery(query);
		trainClassifier(loader.getDataSet());
		isTrained = true;
	}

	/**
	 * trains the classifier with data loaded from an ARFF file
	 * 
	 * @param file the file ARFF containing the data
	 * @throws Exception
	 */
	public Instances train(File file) throws Exception {

		// reads data from file
		BufferedReader trainReader = new BufferedReader(new FileReader(file));
		Instances trainingInstances = new Instances(trainReader);

		// the class of the tweet is 1-th field (0 based)
		trainingInstances.setClassIndex(1);

		// trains the classifier with given data
		trainClassifier(trainingInstances);

		isTrained = true;
		return trainingInstances;
	}

	/**
	 * trains the classifier with the related instances
	 * 
	 * @param trainingInstances
	 * @throws Exception
	 */
	private void trainClassifier(Instances trainingInstances) throws Exception {

		// trains the classifier with given data
		model.buildClassifier(trainingInstances);
	}

	public void test() {

	}

	/**
	 * classifies the tweet using the trained classifier
	 * 
	 * @param tweet
	 * @return
	 * @throws Exception
	 */
	public Instance classifyTweet(String tweet) throws Exception {

		if (!isTrained) {
			throw new Exception("The classifier is not trained. Call method 'train()' before calling 'classifyTweet()'.");
		}
		Instance tweetInstance = new Instance(1);
		// tweetInstance.setClassMissing();
		double cls = model.classifyInstance(tweetInstance);
		tweetInstance.setClassValue(cls);

		return tweetInstance;
	}

	/**
	 * classifies a file containing tweets using the trained classifier
	 * 
	 * @param classifyFile
	 * @return
	 * @throws Exception
	 */
	public Instances classifyTweets(File classifyFile) throws Exception {

		BufferedReader classifyReader = new BufferedReader(new FileReader(classifyFile));
		Instances classifyInstances = new Instances(classifyReader);
		classifyInstances.setClassIndex(0);
		for (int i = 0; i < classifyInstances.numInstances(); i++) {
			Instance instance = classifyInstances.instance(i);
			instance.setClassMissing();
			double cls = model.classifyInstance(instance);
			instance.setClassValue(cls);
		}

		return classifyInstances;
	}

	public FilteredClassifier getModel() {

		return model;
	}

	// /**
	// * @param args
	// */
	// public static void main(String[] args) throws Exception {
	//
	// // WekaUtils.CsvToArff("resources/tweets_train.csv", "resources/tweets_train.arff", new
	// // String[] { "-N", "1" });
	// // WekaUtils.CsvToArff("resources//tweets_to_be_classified.csv",
	// // "resources//tweets_to_be_classified.arff", new String[] {});
	//
	// // reads training file
	// BufferedReader trainReader = new BufferedReader(new
	// FileReader("resources/tweets_train.arff"));
	// Instances trainInsts = new Instances(trainReader);
	//
	// // creates the bayesian classifier
	// FilteredClassifier model = new FilteredClassifier();
	// StringToWordVector stringtowordvector = new StringToWordVector();
	// stringtowordvector.setUseStoplist(true);
	// model.setFilter(new StringToWordVector());
	// model.setClassifier(new NaiveBayes());
	//
	// // trains the classifier with given data
	// model.buildClassifier(trainInsts);
	// // System.out.println(model);
	//
	// BufferedReader classifyReader = new BufferedReader(new
	// FileReader("resources/tweets_to_be_classified.arff"));
	// Instances classifyInsts = new Instances(classifyReader);
	// classifyInsts.setClassIndex(0);
	// for (int i = 0; i < classifyInsts.numInstances(); i++) {
	// Instance instance = classifyInsts.instance(i);
	// instance.setClassMissing();
	// double cls = model.classifyInstance(instance);
	// instance.setClassValue(cls);
	// }
	// System.out.println("CLASIFICATION:" + classifyInsts);
	//
	// }

}
