package com.anemortalkid.reddit.parser.sitebuilder;

import java.util.ArrayList;
import java.util.List;

import com.anemortalkid.reddit.parser.dataobjects.DataObject;
import com.anemortalkid.reddit.parser.npcs.NPCDataObject;
import com.anemortalkid.reddit.parser.npcs.Scrub10KNPCS;

public class SiteBuilder {

	private String indexLocation;
	private List<DataObject> tableData;
	private String linkUrl;
	private String headerTag;

	public SiteBuilder(String indexLocation, String linkUrl, String headerTag,
			List<DataObject> tableData) {
		this.indexLocation = indexLocation;
		this.linkUrl = linkUrl;
		this.headerTag = headerTag;
		this.tableData = tableData;
	}

	public void buildHTML() {
		String title = wrapInTitle(linkUrl);
		String titleAndStyle = title + "\n" + getPageStyle();
		String headElement = wrapInHead(titleAndStyle);
		String bodyElement = buildBody();

		System.out.println(wrapInHTML(headElement + "\n" + bodyElement));
	}

	private String buildBody() {
		StringBuilder bob = new StringBuilder();
		String bodyStart = "<body>\n";
		bob.append(bodyStart);
		bob.append("<h1>From:" + linkUrl + "</h1>\n");
		String table = getTable();
		bob.append(table);
		bob.append(getJQueryScript());
		bob.append(getSearchScript());
		String bodyEnd = "</body>\n";
		bob.append(bodyEnd);
		return bob.toString();
	}

	private String getTable() {
		StringBuilder bob = new StringBuilder();
		bob.append("<table>\n");

		// append header
		bob.append(headerTag);

		StringBuilder tableDataBuilder = new StringBuilder();
		for (DataObject datO : tableData)
			tableDataBuilder.append(datO.toHTMLTableRow());
		bob.append(tableDataBuilder.toString());
		bob.append("</table>\n");
		return bob.toString();
	}

	private String getTableData() {
		return "";
	}

	private static String wrapInHTML(String text) {
		return "<html>\n" + text + "\n</html>";
	}

	private static String wrapInHead(String text) {
		return "<head>\n" + text + "\n</head>";
	}

	private static String wrapInTitle(String text) {
		return "<title>" + text + "</title>";
	}

	/**
	 * <pre>
	 * html
	 * head
	 * title
	 * page-style
	 * body
	 * h1. anchor link
	 * input search
	 * table id=table
	 * header row
	 * data
	 * close-table
	 * search script
	 * close-body
	 * close-html
	 * </pre>
	 * 
	 */

	private static String getInputSearch() {
		return "<input type=\"text\" id=\"search\" placeholder=\"Type to search\" />";
	}

	private static String getPageStyle() {
		return "<style type=\"text/css\">\n"
				+ "body {margin:0;padding:0;}"
				+ "input {margin-bottom: 5px; padding: 2px 3px; width: 209px;}"
				+ "table, th, td {border: 1px solid black;border-collapse: collapse;}"
				+ "th, td {padding: 5px;text-align: left;}" + "</style>";
	}

	private static String getJQueryScript() {
		return "<script src=\"https://code.jquery.com/jquery-2.1.0.js\"></script>";
	}

	private static String getSearchScript() {
		return "<script type=\"text/javascript\">\n"
				+ "\tvar $rows = $('#table tr');\n"
				+ "\t$('#search').keyup(function() {\n"
				+ "\tvar val = $.trim($(this).val()).replace(/ +/g, ' ');\n\n"
				+ "\t$rows.show().filter(function() {\n"
				+ "\t\tvar text = $(this).text().replace(/\\s+/g, ' ');\n"
				+ "\t\treturn !~text.indexOf(val);" + "}).hide();" + "});\n"
				+ "</script>\n";
	}

	public static void main(String[] args) {
		Scrub10KNPCS npcs = new Scrub10KNPCS();
		List<DataObject> dataPoints = npcs.getDataPoints();
		String header = "<tr><th>Name</th><th>Gender Race Occupation</th><th>Description</th></tr>";
		new SiteBuilder("HelloWorld", "Hello", header, dataPoints)
				.buildHTML();
	}

}
