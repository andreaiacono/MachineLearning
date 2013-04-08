package org.aitek.ml.core;

public class Item implements Rankable {

	private String description;

	public Item(String description) {

		this.description = description;
	}

	@Override
	public String getDescription() {

		return description;

	}

}
