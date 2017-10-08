package com.anemortalkid.reddit.parser.sitebuilder.mysteries;

import java.util.Arrays;

import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class MysteryData {

	private String name;
	private String description;

	private MysteryData(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public final String getName() {
		return name;
	}

	public final String getDescription() {
		return description;
	}

	public static MysteryData createFromScrubbedObject(ScrubbedDataObject scrubbedObject) {
		if (scrubbedObject == null) {
			throw new IllegalArgumentException("Scrubbed object was null");
		}

		String[] dataArguments = scrubbedObject.getDataArguments();

		if (dataArguments == null || dataArguments.length != 2) {
			throw new IllegalArgumentException("Cannot create a MysteryData from: " + Arrays.toString(dataArguments));
		}

		return new MysteryData(dataArguments[0], dataArguments[1]);
	}

}
