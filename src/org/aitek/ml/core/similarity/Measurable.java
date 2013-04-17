package org.aitek.ml.core.similarity;

import java.util.List;

import org.aitek.ml.core.Rankable;
import org.aitek.ml.core.Voter;

public interface Measurable {

	double getDistanceBetweenUsers(List<Rankable> items, Voter user1, Voter user2);

	double getDistanceBetweenItems(List<Voter> voters, Rankable item1, Rankable item2);
}
