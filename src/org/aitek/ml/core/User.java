package org.aitek.ml.core;

import java.util.HashMap;
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

		if (item != null) {
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

		return -1;
	}

	@Override
	public String getName() {

		return name;
	}
}
