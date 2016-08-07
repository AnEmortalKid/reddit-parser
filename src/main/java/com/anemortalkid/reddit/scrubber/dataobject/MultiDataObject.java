package com.anemortalkid.reddit.scrubber.dataobject;

import java.util.Arrays;
import java.util.regex.Pattern;

import com.sun.org.apache.xerces.internal.impl.xs.identity.Selector.Matcher;

/**
 * A {@link ScrubbedDataObject} which takes an array of strings, the first
 * string will be the identifier for this data object
 * 
 * @author JMonterrubio
 *
 */
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

	@Override
	public String toString() {
		return Arrays.toString(dataArgs);
	}

	@Override
	public String[] getDataArguments() {
		return dataArgs;
	}

}
