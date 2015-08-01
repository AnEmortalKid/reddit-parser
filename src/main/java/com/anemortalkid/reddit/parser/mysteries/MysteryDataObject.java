package com.anemortalkid.reddit.parser.mysteries;

import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class MysteryDataObject implements ScrubbedDataObject {

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

	public String toGoogleSpreadsheet() {
		return bold + "\t" + regular;
	}

	public String toCSV() {
		return bold + "\t," + regular;
	}
	
	@Override
	public String getDataIdentifier() {
		return bold;
	}

	@Override
	public String toHTMLTableRow() {
		return toTdTr(bold, regular);
	}

	public static void main(String[] args) {
		MysteryDataObject mdo = new MysteryDataObject("Big Data", "Small Data");
		System.out.println(mdo.toHTMLTableRow());
	}

}
