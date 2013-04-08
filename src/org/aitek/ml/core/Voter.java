package org.aitek.ml.core;

public interface Voter {

	public String getName();

	public Integer getVote(Rankable item);

	public void setVote(Rankable item, Integer value);
}
