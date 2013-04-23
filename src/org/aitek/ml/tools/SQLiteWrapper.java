package org.aitek.ml.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLiteWrapper {

	private final Connection connection;

	public SQLiteWrapper() throws Exception {

		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:feeds.db");
		connection.setAutoCommit(false);
	}

	public void createTables() throws Exception {

		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate("CREATE TABLE words (feed varchar(1024), word VARCHAR(256), occurrences INT);");
		}
		catch (SQLException e) {
			System.out.println("An error has occurred creating table: " + e.getMessage());
		}
		finally {
			if (statement != null) {
				statement.close();
			}
		}
	}

	public static Map<String, List<String>> loadData() throws Exception {

		Map<String, List<String>> feedsWords = new HashMap<String, List<String>>();
		SQLiteWrapper sqLiteWrapper = new SQLiteWrapper();
		List<String> feeds = sqLiteWrapper.getFeeds();

		for (String feed : feeds) {

			List<String> words = sqLiteWrapper.getWords(feed);
			feedsWords.put(feed, words);
		}

		return feedsWords;
	}

	public List<String> getFeeds() throws Exception {

		List<String> words = new ArrayList<String>();
		Statement statement = null;
		ResultSet results = null;
		try {
			statement = connection.createStatement();
			results = statement.executeQuery("SELECT DISTINCT feed FROM words;");

			while (results.next()) {
				words.add(results.getString("feed"));
			}
		}
		catch (SQLException e) {
			System.out.println("An error has occurred retrieving feeds: " + e.getMessage());
		}
		finally {
			if (results != null) {
				results.close();
			}
			if (statement != null) {
				statement.close();
			}
		}

		return words;
	}

	public List<String> getWords(String feed) throws Exception {

		List<String> words = new ArrayList<String>();
		Statement statement = null;
		ResultSet results = null;
		try {
			statement = connection.createStatement();
			results = statement.executeQuery("SELECT word FROM words WHERE feed ='" + feed + "';");

			while (results.next()) {
				words.add(results.getString("word"));
			}
		}
		catch (SQLException e) {
			System.out.println("An error has occurred retrieving words: " + e.getMessage());
		}
		finally {
			if (results != null) {
				results.close();
			}
			if (statement != null) {
				statement.close();
			}
		}

		return words;
	}

	public Map<String, Integer> getWordsCount(String feed) throws Exception {

		Map<String, Integer> words = new HashMap<String, Integer>();
		Statement statement = null;
		ResultSet results = null;
		try {
			statement = connection.createStatement();
			results = statement.executeQuery("SELECT word, occurrences FROM words WHERE feed ='" + feed + "';");

			while (results.next()) {
				words.put(results.getString("word"), results.getInt("word"));
			}
		}
		catch (SQLException e) {
			System.out.println("An error has occurred retrieving words: " + e.getMessage());
		}
		finally {
			if (results != null) {
				results.close();
			}
			if (statement != null) {
				statement.close();
			}
		}

		return words;
	}

	public void insert(String insertStatement) throws Exception {

		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.execute(insertStatement);
		}
		catch (SQLException e) {
			System.out.println("An error has occurred inserting record: " + e.getMessage());
		}
		finally {
			if (statement != null) {
				statement.close();
			}
		}
	}

	public void commit() throws Exception {

		connection.commit();
	}

}
