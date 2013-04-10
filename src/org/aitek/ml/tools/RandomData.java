package org.aitek.ml.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.aitek.ml.core.Item;
import org.aitek.ml.core.Rankable;
import org.aitek.ml.core.User;
import org.aitek.ml.core.Voter;

public class RandomData {

	public static final int MAX_VOTE = 5;

	public static List<Rankable> createItems(int n) {

		List<Rankable> items = new ArrayList<Rankable>();

		// no lambdas in Java 7!
		for (int j = 0; j < n; j++) {
			items.add(new Item("Item n." + j));
		}

		return items;
	}

	public static List<Voter> createUsers(int n) {

		List<Voter> users = new ArrayList<Voter>();

		// no lambdas in Java 7!
		for (int j = 0; j < n; j++) {
			users.add(new User("User n." + j));
		}

		return users;
	}

	public static void setRandomVote(Voter user, Rankable item) {

		int value = (int) (Math.random() * MAX_VOTE) + 1;
		user.setVote(item, value);
	}

	public static void setRandomVotesForUser(Voter user, List<Rankable> items) {

		for (Rankable item : items) {
			setRandomVote(user, item);
		}
	}

	public static void setRandomVotesForItem(Rankable item, List<Voter> users) {

		for (Voter user : users) {
			setRandomVote(user, item);
		}
	}

	public static void setRandomVotes(List<Rankable> items, List<Voter> users) {

		for (Voter user : users) {
			for (Rankable item : items) {
				setRandomVote(user, item);
			}
		}
	}

	public static String getDataAsCsv(List<Rankable> items, List<Voter> users, boolean setRandomValues) {

		StringBuilder csv = new StringBuilder();
		for (Voter user : users) {
			csv.append(user).append(",");
		}
		csv.deleteCharAt(csv.length() - 1);
		csv.append("\n");

		for (Rankable item : items) {
			csv.append(item).append(",");
			for (Voter user : users) {
				if (setRandomValues) {
					if (Math.random() > 0.4) {
						csv.append(user.getVote(item)).append(",");
					}
					else {
						csv.append(" ").append(",");
					}
				}
				else {
					csv.append(user.getVote(item)).append(",");
				}
			}
			csv.deleteCharAt(csv.length() - 1);
			csv.append("\n");

		}

		return csv.toString();
	}

	public static void readDataset(List<Rankable> items, List<Voter> users) throws Exception {

		String data = readTextFile(new File("data.csv"), "UTF-8");
		String[] lines = data.split("\n");
		for (int j = 0; j < lines.length - 1; j++) {

			String[] votes = lines[j].split(",");
			for (int i = 0; i < votes.length; i++) {
				try {
					users.get(i).setVote(items.get(j), Integer.parseInt(votes[i]));
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
