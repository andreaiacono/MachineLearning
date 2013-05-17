package org.aitek.ml.demo;

import java.io.File;

import org.aitek.ml.classification.TwitterBayesianClassifier;

public class BayesianClassifier {

	public static void main(String[] args) throws Exception {

		// creates the classifier
		TwitterBayesianClassifier classifier = new TwitterBayesianClassifier();

		// trains it with supervised data from file or database
		classifier.train(new File("resources/tweets_train.arff"));
		// classifier.train("jdbc:mysql://localhost:3306/db_name", "user", "password", "SELECT * FROM data");
		System.out.println("" + Double.NaN + " " +  classifier.getModel());

		// classify a file of tweets
		System.out.println(classifier.classifyTweets(new File("resources/tweets_to_classify.arff")));
	}
}
