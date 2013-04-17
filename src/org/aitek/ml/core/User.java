package org.aitek.ml.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aitek.ml.core.similarity.Measurable;

public class User implements Voter {

	private final String name;
	private final Map<Rankable, Integer> votes;

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

	@Override
	public List<Voter> getClosestVoters(List<Voter> voters, List<Rankable> items, Measurable measurable, int size) {

		List<ScoredUser> scoredUsers = new ArrayList<ScoredUser>();

		for (int j = 0; j < voters.size(); j++) {

			Voter user = voters.get(j);
			if (user != this) {

				double distance = measurable.getDistanceBetweenUsers(items, user, this);
				ScoredUser scoredUser = new ScoredUser(distance, user);
				scoredUsers.add(scoredUser);
			}
		}

		Collections.sort(scoredUsers);
		size = size < scoredUsers.size() ? scoredUsers.size() : size;
		List<Voter> users = new ArrayList<Voter>();
		for (int j = 0; j < size; j++) {
			users.add(scoredUsers.get(j).voter);
		}
		return users;
	}

	@Override
	public Voter getClosestVoter(List<Voter> voters, List<Rankable> items, Measurable measurable) {

		double max = -1;
		Voter closerUser = null;
		for (int j = 0; j < voters.size(); j++) {

			Voter user = voters.get(j);
			if (user != this) {

				double distance = measurable.getDistanceBetweenUsers(items, user, this);
				if (max < distance) {
					max = distance;
					closerUser = user;
				}
			}
		}

		return closerUser;
	}

	public Rankable getMostDesiderableItem(List<Rankable> items, List<Voter> voters, Measurable measurable) {

		double max = 0;
		Rankable item = null;

		for (int j = 0; j < items.size(); j++) {

			double score = getWeightedScoreForItem(items.get(j), items, voters, measurable);
			if (max < score) {
				max = score;
				item = items.get(j);
			}

		}

		return item;
	}

	@Override
	public Double getWeightedScoreForItem(Rankable item, List<Rankable> items, List<Voter> voters, Measurable measurable) {

		double weightedScore = 0;
		double usersDistance = 0;

		for (int j = 0; j < voters.size(); j++) {

			Voter user = voters.get(j);
			if (user != this && user.getVote(item) != null) {

				usersDistance += measurable.getDistanceBetweenUsers(items, user, this);
				weightedScore += usersDistance * user.getVote(item);
			}
		}
		return weightedScore / usersDistance;
	}

	@Override
	public String toString() {

		return name;
	}

	public static List<Voter> createUsers(String[] names) {

		List<Voter> users = new ArrayList<Voter>();

		for (String name : names) {
			users.add(new User(name));
		}

		return users;
	}

	class ScoredUser implements Comparable<ScoredUser> {

		private final Double score;
		private final Voter voter;

		public ScoredUser(Double score, Voter voter) {

			this.score = score;
			this.voter = voter;
		}

		@Override
		public int compareTo(ScoredUser o) {

			ScoredUser otherUser = o;
			return score.compareTo(otherUser.score);
		}
	}

}
