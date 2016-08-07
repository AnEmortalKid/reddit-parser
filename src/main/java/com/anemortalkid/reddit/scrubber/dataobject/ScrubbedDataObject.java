package com.anemortalkid.reddit.scrubber.dataobject;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public interface ScrubbedDataObject {

	static String PATTERN_STRING = ".*\\d+d\\d+.*";
	static Pattern PATTERN = Pattern.compile(PATTERN_STRING);
	static AtomicInteger INNER_TABLE_COUNT = new AtomicInteger(0);

	String toHTMLTableRow();

	default boolean containsInnerTable() {
		return PATTERN.matcher(getDataIdentifier()).matches();
	}

	default String wrapInTd(String str) {
		boolean closedTable = false;
		String closingTag = "";
		if (containsInnerTable()) {
			closedTable = str.endsWith("</ol>") || str.endsWith("</ul>");
			boolean ordered = str.contains("<ol>");
			if (!closedTable) {
				closingTag = ordered ? "</ol>" : "</ul>";
			}
			// hopefully it's the only one so we'll add the paragraph at the end
			closingTag += "<p id=\"table_" + INNER_TABLE_COUNT + "_data\"></p>";
		}

		return "<td>" + str + closingTag + "</td>";
	}

	default String wrapInTdWithRoll(String data) {
		String inputElem = "<input type=\"button\" value=\"roll\" onclick=\"rollTable('table_" + INNER_TABLE_COUNT
				+ "')\">";
		return "<td>" + data + inputElem + "</td>";
	}

	default String wrapInTr(String str) {
		String tr = "<tr>";
		if (containsInnerTable()) {
			tr = "<tr id=\"table_" + INNER_TABLE_COUNT + "\">";
		}
		return tr + str + "\n</tr>";
	}

	default String toTdTr(String... tdElems) {
		boolean containsInnerTable = containsInnerTable();
		if (containsInnerTable) {
			INNER_TABLE_COUNT.incrementAndGet();
		}

		StringBuilder bob = new StringBuilder();
		int elemCount = 0;
		for (String tdElem : tdElems) {
			if (containsInnerTable && elemCount == 0) {
				bob.append("\n" + wrapInTdWithRoll(tdElem));
			} else {
				bob.append("\n" + wrapInTd(tdElem));
			}
			elemCount++;
		}
		String elem = wrapInTr(bob.toString());

		return elem;
	}

	String getDataIdentifier();

	String toGoogleSpreadsheet();

	String toCSV();

	/**
	 * @return an array of Strings containing the pieces of data for this
	 *         {@link ScrubbedDataObject}
	 */
	String[] getDataArguments();
}
