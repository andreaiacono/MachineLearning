package org.aitek.ml.core.similarity;

import java.util.List;

import org.aitek.ml.core.Item;
import org.aitek.ml.core.Voter;

public interface Similarity {

	double getDistanceBetweenVoters(List<Item> items, Voter voter1, Voter voter2);

	double getDistanceBetweenItems(List<Voter> voters, Item item1, Item item2);
}
