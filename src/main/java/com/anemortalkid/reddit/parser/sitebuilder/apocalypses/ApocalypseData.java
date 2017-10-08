package com.anemortalkid.reddit.parser.sitebuilder.apocalypses;

import java.util.Arrays;

import com.anemortalkid.reddit.parser.sitebuilder.rooms.RoomData;
import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class ApocalypseData {

	private String name;
	private String description;

	public ApocalypseData(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	
	public static ApocalypseData createFromScrubbedObject(ScrubbedDataObject scrubbedObject) {
		if (scrubbedObject == null) {
			throw new IllegalArgumentException("Scrubbed object was null");
		}

		String[] dataArguments = scrubbedObject.getDataArguments();

		if (dataArguments == null || dataArguments.length != 2) {
			throw new IllegalArgumentException("Cannot create a ApocalypseData from: " + Arrays.toString(dataArguments));
		}

		return new ApocalypseData(dataArguments[0], dataArguments[1]);
	}

	
}
