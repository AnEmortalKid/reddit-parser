package com.anemortalkid.reddit.parser.sitebuilder.plothooks;

import java.util.Arrays;

import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class PlothookData {

	private String name;
	private String description;

	private PlothookData(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public static PlothookData createFromScrubbedObject(ScrubbedDataObject scrubbedObject) {
		if (scrubbedObject == null) {
			throw new IllegalArgumentException("Scrubbed object was null");
		}

		String[] dataArguments = scrubbedObject.getDataArguments();

		if (dataArguments == null || dataArguments.length != 2) {
			throw new IllegalArgumentException("Cannot create a PlothookData from: " + Arrays.toString(dataArguments));
		}

		return new PlothookData(dataArguments[0], dataArguments[1]);
	}

}
