package com.anemortalkid.reddit.parser.mysteries;

public class MysteryDataObject {

	private String bold;
	private String regular;

	public MysteryDataObject(String bold, String regular) {
		this.bold = bold;
		this.regular = regular;
	}

	public String getBold() {
		return bold;
	}

	public String getRegular() {
		return regular;
	}

	public String toGooleSpreadsheet() {
		return bold + "\t" + regular;
	}

}
