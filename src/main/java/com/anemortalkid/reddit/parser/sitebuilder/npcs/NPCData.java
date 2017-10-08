package com.anemortalkid.reddit.parser.sitebuilder.npcs;

import java.util.Arrays;

import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class NPCData {

	private String name;
	private String genderRaceOccupation;
	private String description;

	private NPCData(String name, String genderRaceOccupation, String description) {
		this.name = name;
		this.genderRaceOccupation = genderRaceOccupation;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getGenderRaceOccupation() {
		return genderRaceOccupation;
	}

	public String getDescription() {
		return description;
	}

	public static NPCData createFromScrubbedObject(ScrubbedDataObject scrubbedObject) {
		if (scrubbedObject == null) {
			throw new IllegalArgumentException("Scrubbed object was null");
		}

		String[] dataArguments = scrubbedObject.getDataArguments();

		if (dataArguments == null || dataArguments.length != 3) {
			throw new IllegalArgumentException("Cannot create an NPCData from: " + Arrays.toString(dataArguments));
		}

		return new NPCData(dataArguments[0], dataArguments[1], dataArguments[2]);
	}
}
