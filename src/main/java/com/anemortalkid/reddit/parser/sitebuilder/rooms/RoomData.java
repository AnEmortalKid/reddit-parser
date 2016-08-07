package com.anemortalkid.reddit.parser.sitebuilder.rooms;

import java.util.Arrays;

import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class RoomData {

	private String name;
	private String description;

	private RoomData(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public static RoomData createFromScrubbedObject(ScrubbedDataObject scrubbedObject) {
		if (scrubbedObject == null) {
			throw new IllegalArgumentException("Scrubbed object was null");
		}

		String[] dataArguments = scrubbedObject.getDataArguments();

		if (dataArguments == null || dataArguments.length != 2) {
			throw new IllegalArgumentException("Cannot create a RoomData from: " + Arrays.toString(dataArguments));
		}

		return new RoomData(dataArguments[0], dataArguments[1]);
	}

}
