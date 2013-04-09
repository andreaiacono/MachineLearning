package org.aitek.ml.demo;

import java.util.List;

import org.aitek.ml.core.Rankable;
import org.aitek.ml.core.Voter;
import org.aitek.ml.tools.RandomData;

public class Similarity {

	public static void main(String[] args) throws Exception {

		List<Voter> users = RandomData.createUsers(10);
		List<Rankable> items = RandomData.createItems(100);
		RandomData.readDataset(items, users);

		System.out.println(RandomData.getDataAsCsv(items, users, false));
		// RandomData.setRandomVotes(items, users);
	}

}
