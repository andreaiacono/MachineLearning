package org.aitek.ml.core.similarity;

import java.util.List;

import org.aitek.ml.core.Rankable;
import org.aitek.ml.core.Voter;

public interface Similarity {

	double getScore(List<Rankable> items, Voter user1, Voter user2);
}
