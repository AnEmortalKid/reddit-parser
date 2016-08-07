package com.anemortalkid.reddit.parser.sitebuilder.locations;

import java.util.Arrays;

import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class LocationData {

	private String name;
	private String type;
	private String description;

	private LocationData(String name, String type, String description) {
		this.name = name;
		this.type = type;
		this.description = description;
	}

	public final String getName() {
		return name;
	}

	public final String getType() {
		return type;
	}

	public final String getDescription() {
		return description;
	}

	public static LocationData createFromScrubbedObject(ScrubbedDataObject scrubbedObject) {
		if (scrubbedObject == null) {
			throw new IllegalArgumentException("Scrubbed object was null");
		}

		String[] dataArguments = scrubbedObject.getDataArguments();

		if (dataArguments == null || dataArguments.length != 3) {
			throw new IllegalArgumentException("Cannot create a LocationData from: " + Arrays.toString(dataArguments));
		}

		return new LocationData(dataArguments[0], dataArguments[1], dataArguments[2]);
	}
}
