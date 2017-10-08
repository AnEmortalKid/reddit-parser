package com.anemortalkid.reddit.parser.sitebuilder.books;

import java.util.Arrays;

import com.anemortalkid.reddit.parser.sitebuilder.rooms.RoomData;
import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class BookData {

	private String title;
	private String author;
	private String description;

	public BookData(String name, String author, String description) {
		this.title = name;
		this.author = author;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getDescription() {
		return description;
	}

	public static BookData createFromScrubbedObject(ScrubbedDataObject scrubbedObject) {
		if (scrubbedObject == null) {
			throw new IllegalArgumentException("Scrubbed object was null");
		}

		String[] dataArguments = scrubbedObject.getDataArguments();

		if (dataArguments == null || dataArguments.length != 3) {
			throw new IllegalArgumentException(
					"Cannot create a BookData from: " + Arrays.toString(dataArguments));
		}

		return new BookData(dataArguments[0], dataArguments[1], dataArguments[2]);
	}

}
