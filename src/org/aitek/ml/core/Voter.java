package org.aitek.ml.core;

import java.util.List;

import org.aitek.ml.core.similarity.Measurable;

public interface Voter {

	public String getName();

	public Integer getVote(Rankable item);

	public void setVote(Rankable item, Integer value);

	public Voter getCloserVoter(List<Voter> voters, List<Rankable> rankables, Measurable measurable);

	public Double getWeightedScoreForItem(Rankable item, List<Rankable> items, List<Voter> voters, Measurable measurable);

}
