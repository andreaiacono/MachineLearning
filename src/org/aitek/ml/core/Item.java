package org.aitek.ml.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.aitek.ml.core.similarity.Measurable;

public class Item implements Rankable {

	private final String description;

	public Item(String description) {

		this.description = description;
	}

	@Override
	public List<Rankable> getClosestRankables(List<Voter> voters, List<Rankable> items, Measurable measurable, int size) {

		List<ScoredItem> scoredItems = new ArrayList<ScoredItem>();

		for (int j = 0; j < items.size(); j++) {

			Rankable item = items.get(j);
			if (item != this) {

				double distance = measurable.getDistanceBetweenItems(voters, item, this);
				ScoredItem scoredItem = new ScoredItem(distance, item);
				scoredItems.add(scoredItem);
			}
		}

		Collections.sort(scoredItems);
		List<Rankable> users = new ArrayList<Rankable>();
		size = size < scoredItems.size() ? scoredItems.size() : size;
		for (int j = 0; j < size; j++) {
			users.add(scoredItems.get(j).item);
		}
		return users;
	}

	@Override
	public Rankable getClosestRankable(List<Voter> voters, List<Rankable> users, Measurable measurable) {

		double max = -1;
		Rankable closerItem = null;
		for (int j = 0; j < users.size(); j++) {

			Rankable item = users.get(j);
			if (item != this) {

				double distance = measurable.getDistanceBetweenItems(voters, item, this);
				if (max < distance) {
					max = distance;
					closerItem = item;
				}
			}
		}

		return closerItem;
	}

	@Override
	public Double getWeightedScoreForItem(Rankable item, List<Rankable> items, List<Voter> voters, Measurable measurable) {

		return null;
	}

	@Override
	public String getDescription() {

		return description;

	}

	@Override
	public String toString() {

		return description;
	}

	class ScoredItem implements Comparable<ScoredItem> {

		final Double score;
		final Rankable item;

		public ScoredItem(Double score, Rankable item) {

			this.score = score;
			this.item = item;
		}

		@Override
		public int compareTo(ScoredItem o) {

			return score.compareTo(o.score);
		}
	}
}
