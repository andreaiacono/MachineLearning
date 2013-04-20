package org.aitek.ml.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aitek.ml.similarity.Similarity;

public class Reader implements Voter {

	private final String name;
	private final Map<Item, Integer> votes;

	public Reader(String name) {

		this.name = name;
		votes = new HashMap<Item, Integer>();
	}

	@Override
	public void setVote(Item item, Integer value) {

		if (item != null && value != null) {
			votes.put(item, value);
		}
	}

	@Override
	public Integer getVote(Item item) {

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
	public List<Voter> getClosestVoters(List<Voter> voters, List<Item> items, Similarity similarity, int size) {

		List<ScoredReader> scoredReaders = new ArrayList<ScoredReader>();

		for (int j = 0; j < voters.size(); j++) {

			Voter voter = voters.get(j);
			if (voter != this) {

				double distance = similarity.getDistanceBetweenVoters(items, voter, this);
				ScoredReader scoredReader = new ScoredReader(distance, voter);
				scoredReaders.add(scoredReader);
			}
		}

		Collections.sort(scoredReaders);
		size = size < scoredReaders.size() ? scoredReaders.size() : size;
		List<Voter> sortedVoters = new ArrayList<Voter>();
		for (int j = 0; j < size; j++) {
			sortedVoters.add(scoredReaders.get(j).voter);
		}
		return sortedVoters;
	}

	@Override
	public Voter getClosestVoter(List<Voter> voters, List<Item> items, Similarity similarity) {

		double max = -1;
		Voter closerReader = null;
		for (int j = 0; j < voters.size(); j++) {

			Voter voter = voters.get(j);
			if (voter != this) {

				double distance = similarity.getDistanceBetweenVoters(items, voter, this);
				if (max < distance) {
					max = distance;
					closerReader = voter;
				}
			}
		}

		return closerReader;
	}

	public Item getMostDesiderableItem(List<Item> items, List<Voter> voters, Similarity measurable) {

		double max = 0;
		Item item = null;

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
	public Double getWeightedScoreForItem(Item item, List<Item> items, List<Voter> voters, Similarity similarity) {

		double weightedScore = 0;
		double votersDistance = 0;

		for (int j = 0; j < voters.size(); j++) {

			Voter voter = voters.get(j);
			if (voter != this && voter.getVote(item) != null) {

				votersDistance += similarity.getDistanceBetweenVoters(items, voter, this);
				weightedScore += votersDistance * voter.getVote(item);
			}
		}
		return weightedScore / votersDistance;
	}

	@Override
	public String toString() {

		return name;
	}

	public static List<Voter> createVoters(String[] names) {

		List<Voter> voters = new ArrayList<Voter>();

		for (String name : names) {
			voters.add(new Reader(name));
		}

		return voters;
	}

	class ScoredReader implements Comparable<ScoredReader> {

		private final Double score;
		private final Voter voter;

		public ScoredReader(Double score, Voter voter) {

			this.score = score;
			this.voter = voter;
		}

		@Override
		public int compareTo(ScoredReader o) {

			ScoredReader otherReader = o;
			return score.compareTo(otherReader.score);
		}
	}

}
