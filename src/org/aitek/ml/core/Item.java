package org.aitek.ml.core;

import java.util.List;

import org.aitek.ml.core.similarity.Similarity;

public interface Item {

	public String getDescription();

	List<Item> getClosestItems(List<Voter> voters, List<Item> items, Similarity similarity, int size);

	Item getClosestItem(List<Voter> voters, List<Item> items, Similarity similarity);
}
