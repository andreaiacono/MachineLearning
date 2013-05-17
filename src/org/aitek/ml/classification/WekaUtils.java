package org.aitek.ml.classification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.aitek.ml.classification.TwitterBayesianClassifier.ClassName;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

public class WekaUtils {

	public static void CsvToArff(String csvSrc, String arffDest, String[] options) throws Exception {

		// loads CSV data
		CSVLoader loader = new CSVLoader();
		loader.setSource(new File(csvSrc));
		loader.setOptions(options);
		loader.setEnclosureCharacters("\"");
		Instances data = loader.getDataSet();

		// saves data as ARFF
		ArffSaver saver = new ArffSaver();
		saver.setInstances(data);
		saver.setFile(new File(arffDest));
		saver.setDestination(new File(arffDest));
		saver.writeBatch();
	}

	/**
	 * create instances from a file formatted in this way:
	 * 
	 * class\ttweet
	 * 
	 * where class is the type of tweet (as of BayesianClassifier.ClassName)
	 * 
	 * @param filename
	 * @param isTraining if true the file contains data to train the classifier (containing the
	 *            class); if false the file contains only the tweets
	 * @return
	 * @throws Exception
	 */
	public static Instances createDatasetFromTextFile(String filename, boolean isTraining) throws Exception {

		BufferedReader reader = null;
		Instances dataset = null;

		try {
			FastVector vectorClassName = new FastVector(ClassName.values().length);
			for (ClassName className : ClassName.values()) {
				vectorClassName.addElement(className.toString());
			}
			Attribute classAttribute = new Attribute("class", vectorClassName);
			Attribute tweetAttribute = new Attribute("tweet", (FastVector) null);

			FastVector datasetAttributes = isTraining ? new FastVector(2) : new FastVector(1);
			datasetAttributes.addElement(tweetAttribute);
			if (isTraining) datasetAttributes.addElement(classAttribute);

			dataset = new Instances("Dataset_" + filename, datasetAttributes, 1000);

			reader = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split("\t");
				Instance instance = null;

				if (isTraining) {
					instance = new Instance(2);
					instance.setValue(classAttribute, tokens[0]);
					instance.setValue(tweetAttribute, tokens[1]);
				}
				else {
					instance = new Instance(2);
					instance.setValue(tweetAttribute, tokens[0]);
					instance.setValue(tweetAttribute, "foo");
				}
				dataset.add(instance);
				// System.out.println("Inserted: " + line);
			}
		}
		finally {
			if (reader != null) reader.close();
		}

		System.out.println(filename + " imported:" + dataset.numInstances());
		return dataset;
	}

}
