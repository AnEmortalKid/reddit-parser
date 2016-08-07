package com.anemortalkid.reddit.parser.sitebuilder.treasures;

import java.util.Arrays;

import com.anemortalkid.reddit.parser.sitebuilder.npcs.NPCData;
import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class TreasureData {

	private String name;
	private String type;
	private String description;

	private TreasureData(String name, String type, String description) {
		this.name = name;
		this.type = type;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getDescription() {
		return description;
	}

	public static TreasureData createFromScrubbedObject(ScrubbedDataObject scrubbedObject) {
		if (scrubbedObject == null) {
			throw new IllegalArgumentException("Scrubbed object was null");
		}

		String[] dataArguments = scrubbedObject.getDataArguments();

		if (dataArguments == null || dataArguments.length != 3) {
			throw new IllegalArgumentException("Cannot create an TreasureData from: " + Arrays.toString(dataArguments));
		}

		return new TreasureData(dataArguments[0], dataArguments[1], dataArguments[2]);
	}

}
