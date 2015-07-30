package com.anemortalkid.reddit.parser.dataobjects;

public interface ScrubbedDataObject {

	String toHTMLTableRow();

	default String wrapInTd(String str) {
		return "\t<td>" + str + "</td>";
	}

	default String wrapInTr(String str) {
		return "<tr>" + str + "\n</tr>";
	}

	default String toTdTr(String... tdElems) {
		StringBuilder bob = new StringBuilder();
		for (String tdElem : tdElems) {
			bob.append("\n" + wrapInTd(tdElem));
		}
		return wrapInTr(bob.toString());
	}
	
	String getDataIdentifier();

	String toGoogleSpreadsheet();

	String toCSV();
}
