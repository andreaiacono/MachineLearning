package org.aitek.ml.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.aitek.ml.similarity.Similarity;

public class Book implements Item {

	private final String description;

	public Book(String description) {

		this.description = description;
	}

	@Override
	public List<Item> getClosestItems(List<Voter> voters, List<Item> items, Similarity similarity, int size) {

		List<ScoredItem> scoredItems = new ArrayList<ScoredItem>();

		for (int j = 0; j < items.size(); j++) {

			Item item = items.get(j);
			if (item != this) {

				double distance = similarity.getDistanceBetweenItems(voters, item, this);
				ScoredItem scoredItem = new ScoredItem(distance, item);
				scoredItems.add(scoredItem);
			}
		}

		Collections.sort(scoredItems);
		List<Item> sortedItems = new ArrayList<Item>();
		size = size < scoredItems.size() ? scoredItems.size() : size;
		for (int j = 0; j < size; j++) {
			sortedItems.add(scoredItems.get(j).item);
		}
		return sortedItems;
	}

	@Override
	public Item getClosestItem(List<Voter> voters, List<Item> items, Similarity similarity) {

		double max = -1;
		Item closerItem = null;
		for (int j = 0; j < items.size(); j++) {

			Item item = items.get(j);
			if (item != this) {

				double distance = similarity.getDistanceBetweenItems(voters, item, this);
				if (max < distance) {
					max = distance;
					closerItem = item;
				}
			}
		}

		return closerItem;
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
		final Item item;

		public ScoredItem(Double score, Item item) {

			this.score = score;
			this.item = item;
		}

		@Override
		public int compareTo(ScoredItem o) {

			return score.compareTo(o.score);
		}
	}
}
