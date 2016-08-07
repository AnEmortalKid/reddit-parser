package com.anemortalkid.reddit.parser.sitebuilder.dungeons;

import java.util.Arrays;

import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class DungeonData {

	private String name;
	private String type;
	private String description;

	private DungeonData(String name, String type, String description) {
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

	public static DungeonData createFromScrubbedObject(ScrubbedDataObject scrubbedObject) {
		if (scrubbedObject == null) {
			throw new IllegalArgumentException("Scrubbed object was null");
		}

		String[] dataArguments = scrubbedObject.getDataArguments();

		if (dataArguments == null || dataArguments.length != 3) {
			throw new IllegalArgumentException("Cannot create a DungeonData from: " + Arrays.toString(dataArguments));
		}

		return new DungeonData(dataArguments[0], dataArguments[1], dataArguments[2]);
	}

}
