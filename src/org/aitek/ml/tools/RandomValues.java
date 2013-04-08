package org.aitek.ml.tools;

import java.util.ArrayList;
import java.util.List;

import org.aitek.ml.core.Item;
import org.aitek.ml.core.Rankable;
import org.aitek.ml.core.User;
import org.aitek.ml.core.Voter;

public class RandomValues {

	static final int MAX_VOTE = 50;

	static List<Rankable> createItems(int n) {

		List<Rankable> items = new ArrayList<Rankable>();

		// no lambdas in Java 7!
		for (int j = 0; j < n; j++) {
			items.add(new Item("Item n." + j));
		}

		return items;
	}

	static List<Voter> createUsers(int n) {

		List<Voter> users = new ArrayList<Voter>();

		// no lambdas in Java 7!
		for (int j = 0; j < n; j++) {
			users.add(new User("User n." + j));
		}

		return users;
	}

	static void setRandomVote(Voter user, Rankable item) {

		int value = (int) (Math.random() * MAX_VOTE);
		user.setVote(item, value);
	}

	static void setRandomVotesForUser(Voter user, List<Rankable> items) {

		for (Rankable item : items) {
			setRandomVote(user, item);
		}
	}

	static void setRandomVotesForItem(Rankable item, List<Voter> users) {

		for (Voter user : users) {
			setRandomVote(user, item);
		}
	}

	static void setRandomVotes(List<Rankable> items, List<Voter> users) {

		for (Voter user : users) {
			for (Rankable item : items) {
				setRandomVote(user, item);
			}
		}
	}

}
