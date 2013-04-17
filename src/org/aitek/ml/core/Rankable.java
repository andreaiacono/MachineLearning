package org.aitek.ml.core;

import java.util.List;

import org.aitek.ml.core.similarity.Measurable;

public interface Rankable {

	public String getDescription();

	public Double getWeightedScoreForItem(Rankable item, List<Rankable> items, List<Voter> voters, Measurable measurable);

	// public Rankable getCloserRankable(List<Voter> voters, List<Rankable> rankables, Measurable
	// measurable);

	List<Rankable> getClosestRankables(List<Voter> voters, List<Rankable> items, Measurable measurable, int size);

	Rankable getClosestRankable(List<Voter> voters, List<Rankable> rankables, Measurable measurable);
}
