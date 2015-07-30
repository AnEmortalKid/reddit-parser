package com.anemortalkid.reddit.parser.npcs;

import com.anemortalkid.reddit.parser.dataobjects.ScrubbedDataObject;

public class NPCDataObject implements ScrubbedDataObject {

	private String name;
	private String raceGenderOccupation;
	private String flavorText;

	/**
	 * Constructs an NPCDataObject with the given name, race gender ocupation
	 * and flavor text
	 */
	public NPCDataObject(String name, String raceGenderOccupation,
			String flavorText) {
		this.name = name;
		this.raceGenderOccupation = raceGenderOccupation;
		this.flavorText = flavorText;
	}

	public String getBoldText() {
		return name;
	}

	public String getItalicText() {
		return raceGenderOccupation;
	}

	public String getRegularText() {
		return flavorText;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime
				* result
				+ ((raceGenderOccupation == null) ? 0 : raceGenderOccupation
						.hashCode());
		result = prime * result
				+ ((flavorText == null) ? 0 : flavorText.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NPCDataObject other = (NPCDataObject) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (raceGenderOccupation == null) {
			if (other.raceGenderOccupation != null)
				return false;
		} else if (!raceGenderOccupation.equals(other.raceGenderOccupation))
			return false;
		if (flavorText == null) {
			if (other.flavorText != null)
				return false;
		} else if (!flavorText.equals(other.flavorText))
			return false;
		return true;
	}

	public String toGoogleSpreadsheet() {
		return name + "\t" + raceGenderOccupation + "\t" + flavorText;
	}

	public String toCSV() {
		return name + "\t," + raceGenderOccupation + "\t," + flavorText;
	}

	@Override
	public String toHTMLTableRow() {
		return toTdTr(name, raceGenderOccupation, flavorText);
	}

	@Override
	public String getDataIdentifier() {
		return name;
	}

}