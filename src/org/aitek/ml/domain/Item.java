package org.aitek.ml.domain;

import java.util.List;

import org.aitek.ml.similarity.Similarity;

public interface Item {

	public String getDescription();

	List<Item> getClosestItems(List<Voter> voters, List<Item> items, Similarity similarity, int size);

	Item getClosestItem(List<Voter> voters, List<Item> items, Similarity similarity);
}
