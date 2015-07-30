package com.anemortalkid.reddit.scrubber.dataobjs;

import java.util.Arrays;

import com.anemortalkid.reddit.parser.dataobjects.ScrubbedDataObject;

public class MultiDataObject implements ScrubbedDataObject {

	private String[] dataArgs;

	/**
	 * Constructs a {@link MultiDataObject} with the given data, note that
	 * dataArgs[0] will be the identifier for this data
	 * 
	 * @param dataArgs
	 */
	public MultiDataObject(String... dataArgs) {
		this.dataArgs = dataArgs;
	}

	@Override
	public String toHTMLTableRow() {
		return toTdTr(dataArgs);
	}

	@Override
	public String getDataIdentifier() {
		return dataArgs[0];
	}

	@Override
	public String toGoogleSpreadsheet() {
		StringBuilder bob = new StringBuilder();
		Arrays.asList(dataArgs).forEach(x -> bob.append(x + "\t"));
		bob.delete(bob.length() - 1, bob.length());
		return bob.toString();
	}

	@Override
	public String toCSV() {
		StringBuilder bob = new StringBuilder();
		Arrays.asList(dataArgs).forEach(x -> bob.append(x + ",\t"));
		bob.delete(bob.length() - 2, bob.length());
		return bob.toString();
	}

}
