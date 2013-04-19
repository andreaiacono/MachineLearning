package org.aitek.ml.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.aitek.ml.core.Book;
import org.aitek.ml.core.Item;
import org.aitek.ml.core.Reader;
import org.aitek.ml.core.Voter;

public class RandomData {

	public static final int MAX_VOTE = 5;

	public static List<Item> createItems(int n) {

		List<Item> items = new ArrayList<Item>();

		// no lambdas in Java 7!
		for (int j = 0; j < n; j++) {
			items.add(new Book("Item n." + j));
		}

		return items;
	}

	public static List<Voter> createReaders(int n) {

		List<Voter> readers = new ArrayList<Voter>();

		// no lambdas in Java 7!
		for (int j = 0; j < n; j++) {
			readers.add(new Reader("Reader n." + j));
		}

		return readers;
	}

	public static void setRandomVote(Voter reader, Item item) {

		int value = (int) (Math.random() * MAX_VOTE) + 1;
		reader.setVote(item, value);
	}

	public static void setRandomVotesForReader(Voter reader, List<Item> items) {

		for (Item item : items) {
			setRandomVote(reader, item);
		}
	}

	public static void setRandomVotesForItem(Item item, List<Voter> readers) {

		for (Voter reader : readers) {
			setRandomVote(reader, item);
		}
	}

	public static void setRandomVotes(List<Item> items, List<Voter> readers) {

		for (Voter reader : readers) {
			for (Item item : items) {
				setRandomVote(reader, item);
			}
		}
	}

	public static String getDataAsCsv(List<Item> items, List<Voter> voters, boolean setRandomValues) {

		StringBuilder csv = new StringBuilder();
		for (Voter reader : voters) {
			csv.append(reader).append(",");
		}
		csv.deleteCharAt(csv.length() - 1);
		csv.append("\n");

		for (Item item : items) {
			csv.append(item).append(",");
			for (Voter reader : voters) {
				if (setRandomValues) {
					if (Math.random() > 0.4) {
						csv.append(reader.getVote(item)).append(",");
					}
					else {
						csv.append(" ").append(",");
					}
				}
				else {
					csv.append(reader.getVote(item)).append(",");
				}
			}
			csv.deleteCharAt(csv.length() - 1);
			csv.append("\n");

		}

		return csv.toString();
	}

	public static void readDataset(List<Item> items, int itemsNumber, List<Voter> voters, int votersNumber) throws Exception {

		String data = readTextFile(new File("data.csv"), "UTF-8");
		String[] lines = data.split("\n");
		for (int j = 0; j < lines.length - 1 && j < itemsNumber; j++) {

			String[] votes = lines[j].split(",");
			for (int i = 0; i < votes.length && i < votersNumber; i++) {
				try {
					voters.get(i).setVote(items.get(j), Integer.parseInt(votes[i]));
				}
				catch (NumberFormatException nfe) {
					// just not setting vote
				}
			}
		}

	}

	/**
	 * reads a text file
	 * 
	 * @param f the file to read
	 * @param encoding the encoding of the file
	 * @return the content of the file
	 * @throws Exception
	 */
	public static String readTextFile(File f, String encoding) throws Exception {

		InputStreamReader reader = null;
		StringBuilder content = new StringBuilder();
		try {
			reader = new InputStreamReader(new FileInputStream(f), encoding);
			char data[] = new char[4096];
			while ((reader.read(data)) != -1) {
				content.append(data);
			}
		}
		finally {
			if (reader != null) reader.close();
		}
		return content.toString();
	}
}
