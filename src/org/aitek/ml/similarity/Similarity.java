package org.aitek.ml.similarity;

import java.util.List;

import org.aitek.ml.domain.Item;
import org.aitek.ml.domain.Voter;

public interface Similarity {

	double getDistanceBetweenVoters(List<Item> items, Voter voter1, Voter voter2);

	double getDistanceBetweenItems(List<Voter> voters, Item item1, Item item2);
}
