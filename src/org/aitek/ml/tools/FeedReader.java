package org.aitek.ml.tools;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jsoup.parser.Parser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FeedReader {

	private final SQLiteWrapper sqLiteWrapper;

	public FeedReader() throws Exception {

		sqLiteWrapper = new SQLiteWrapper();
		sqLiteWrapper.createTables();
		String data = Utils.readTextFile(new File("resources/rss_feeds.txt"), "UTF-8");
		String[] lines = data.split("\n");
		for (String feed : lines) {
			if (feed.length() == 0) break;
			insertFeedWords(feed.split("\\|")[0], new URL(feed.split("\\|")[1]));
		}

	}

	public void insertFeedWords(String feedName, URL url) {

		System.out.println("Inserting " + feedName);
		Map<String, Integer> feedWords = new HashMap<String, Integer>();

		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(url.openStream());
			// String idTag = "link";
			String descriptionTag = "description";
			String titleTag = "title";

			NodeList nodes = doc.getElementsByTagName("item");

			if (nodes.getLength() == 0) {
				nodes = doc.getElementsByTagName("entry");
				descriptionTag = "summary";
				// idTag = "id";
			}

			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);
				// String feed = getElementValue(element, idTag);
				String feedContent = getElementValue(element, descriptionTag) + getElementValue(element, titleTag);
				org.jsoup.nodes.Document parsedHtml = Parser.parse(feedContent, "/");
				String[] words = parsedHtml.body().text().replaceAll("[\\.,;\":!?-]", " ").replaceAll("'", "").replaceAll("\\(", "").replaceAll("\\)", "").toLowerCase().split(" ");
				for (String word : words) {
					if (word.trim().length() > 0) {

						if (feedWords.get(word) == null) {
							feedWords.put(word, 1);
						}
						else {
							feedWords.put(word, feedWords.get(word) + 1);
						}
					}
				}
			}

			for (String word : feedWords.keySet()) {

				String insertStatement = "INSERT INTO Words (feed, word, occurrences) VALUES ('" + feedName + "', '" + word + "', " + feedWords.get(word) + ");";
				sqLiteWrapper.insert(insertStatement);
				// System.out.println(insertStatement);
			}
			sqLiteWrapper.commit();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected String getElementValue(Element parent, String label) {

		Node item = parent.getElementsByTagName(label).item(0);
		if (item != null) return item.getTextContent();
		return "";
	}

	public static void main(String[] args) throws Exception {

		FeedReader feedReader = new FeedReader();
	}
}
