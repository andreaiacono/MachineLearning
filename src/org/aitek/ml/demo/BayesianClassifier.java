package org.aitek.ml.demo;

import org.aitek.ml.classification.TwitterBayesianClassifier;
import org.aitek.ml.classification.TwitterBayesianClassifier.ClassName;

public class BayesianClassifier {

	public static void main(String[] args) throws Exception {

		// creates the classifier
		TwitterBayesianClassifier classifier = new TwitterBayesianClassifier();

		// trains it with supervised data from file or database
		classifier.trainFromTweetsFile("resources/tweets_train.txt");
		// classifier.train("jdbc:mysql://localhost:3306/db_name", "user", "password",
		// "SELECT * FROM data");

		// classify some new tweets
		ClassName className = classifier.classifyMessage("RT @SLRCameraTree: Canon EOS Digital Rebel T2i 18MP w/ Full HD 1080p Video, 3.7... http://t.co/a5qFtfvdkB &lt;&lt;== #Deal #SLR #Cameras #Nikon #Canon #Sony #DSLR");
		System.out.println();
		classifier.classifyMessage("Toronto Raptors Tickets only $19 and pick from 2 Games &amp; 2 Seating Options #Deals #Toronto #rtz - http://t.co/MgmiH3d1MF");
		classifier.classifyMessage("Famous Footwear - 15% Off Sitewide http://t.co/vgmQxfJV4W #Deal - http://t.co/QImHB6xJ5b");
	}
}
