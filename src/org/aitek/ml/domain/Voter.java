package org.aitek.ml.domain;

import java.util.List;

import org.aitek.ml.similarity.Similarity;

public interface Voter {

	public String getName();

	public Integer getVote(Item item);

	public void setVote(Item item, Integer value);

	public Voter getClosestVoter(List<Voter> voters, List<Item> items, Similarity measurable);

	public Double getWeightedScoreForItem(Item item, List<Item> items, List<Voter> voters, Similarity measurable);

	public List<Voter> getClosestVoters(List<Voter> voters, List<Item> items, Similarity measurable, int size);

}
