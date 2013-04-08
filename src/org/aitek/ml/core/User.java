package org.aitek.ml.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User implements Voter {

	private String name;
	private Map<Rankable, Integer> votes;

	public User(String name) {

		this.name = name;
		votes = new HashMap<Rankable, Integer>();
	}

	@Override
	public void setVote(Rankable item, Integer value) {

		if (item != null && value != null) {
			votes.put(item, value);
		}
	}

	@Override
	public Integer getVote(Rankable item) {

		if (item != null) {

			Integer value = votes.get(item);
			if (value != null) {
				return value;
			}
		}

		return null;
	}

	@Override
	public String getName() {

		return name;
	}

	public static List<Voter> createUsers(String[] names) {

		List<Voter> users = new ArrayList<Voter>();

		for (String name : names) {
			users.add(new User(name));
		}

		return users;
	}

	@Override
	public String toString() {

		return name;
	}
}
