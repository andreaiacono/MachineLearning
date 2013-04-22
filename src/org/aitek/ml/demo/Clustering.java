package org.aitek.ml.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aitek.ml.tools.SQLiteWrapper;

public class Clustering {

	public static void main(String[] args) throws Exception {

		Map<String, List<String>> feedsWords = loadData();

		for (String feed : feedsWords.keySet()) {

			System.out.println(feed);
		}

	}

	private static Map<String, List<String>> loadData() throws Exception {

		Map<String, List<String>> feedsWords = new HashMap<String, List<String>>();
		SQLiteWrapper sqLiteWrapper = new SQLiteWrapper();
		List<String> feeds = sqLiteWrapper.getFeeds();

		for (String feed : feeds) {

			List<String> words = sqLiteWrapper.getWords(feed);
			feedsWords.put(feed, words);
		}

		return feedsWords;
	}
}
