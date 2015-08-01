package com.anemortalkid.reddit.parser.sitebuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.anemortalkid.reddit.parser.npcs.NPCDataObject;
import com.anemortalkid.reddit.parser.npcs.Scrub10KNPCS;
import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class BaseSiteBuilderHelper {

	private static final String DIV_CLASS_ROW = "<div class=\"row\">";
	private String indexLocation;
	private List<ScrubbedDataObject> tableData;
	private String redditUrl;
	private String headerTag;
	private String pageTitle;

	/**
	 * 
	 * @param htmlOutputFolder
	 *            one of "src/main/resources/THEME/"
	 * @param pageTitle
	 *            the theme name, ie: Locations
	 * @param redditURL
	 *            the reddit url ie:
	 *            http://www.reddit.com/r/DnDBehindTheScreen/comments
	 *            /3f0lzl/lets_make_10_000_locations/
	 * @param tableHeaderHTML
	 *            the tr th combo for the header for the data
	 * @param tableData
	 *            the data objects from which to create the table data rows
	 */
	public BaseSiteBuilderHelper(String htmlOutputFolder, String pageTitle,
			String redditURL, String tableHeaderHTML,
			List<ScrubbedDataObject> tableData) {
		this.indexLocation = htmlOutputFolder;
		this.pageTitle = pageTitle;
		this.redditUrl = redditURL;
		this.headerTag = tableHeaderHTML;
		this.tableData = tableData;
	}

	public void buildHTML() {

		String meta = "<meta name=viewport content=\"width=device-width, initial-scale=1\">";
		String title = wrapInTitle("10K " + pageTitle);
		String bootstrapLinks = "\n<link href=\"//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css\" rel=\"stylesheet\">\n"
				+ "<link href=\"//maxcdn.bootstrapcdn.com/bootswatch/3.3.5/slate/bootstrap.min.css\" rel=\"stylesheet\">";
		String headElement = wrapInHead(meta + "\n" + title + bootstrapLinks);

		String bodyElement = buildBody();

		String html = wrapInHTML(headElement + "\n" + bodyElement);

		File indexFile = new File(indexLocation + "index.html");
		if (!indexFile.exists()) {
			try {
				indexFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		PrintWriter pw = null;
		try {
			pw = new PrintWriter(indexFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		pw.write(html);
		pw.flush();
		pw.close();
	}

	private String buildBody() {
		StringBuilder bob = new StringBuilder();
		String bodyStart = "<body>\n";
		bob.append(bodyStart);

		String containerData = getContainerData();
		bob.append(containerData);

		bob.append(getJavascript());
		// bob.append(getBootstrapJavaScript() + "\n");
		// bob.append(getJQueryScript() + "\n");
		// bob.append(getSearchScript());

		String bodyEnd = "</body>";
		bob.append(bodyEnd);

		return bob.toString();
	}

	private String getJavascript() {
		return getDataFromFile("src/main/resources/site_resources/javascriptfunctions.txt");
	}

	private String getBootstrapJavaScript() {
		return "<script src=\"//ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js\"></script>";
	}

	private String getContainerData() {

		StringBuilder bob = new StringBuilder();
		bob.append("<div class=\"container\">\n");
		String threeBR = "<br/>\n<br/>\n<br/>\n";
		bob.append(threeBR);
		bob.append(divClassRowData());
		bob.append("</div>\n");
		return bob.toString();
	}

	private String divClassRowData() {
		StringBuilder bob = new StringBuilder();
		bob.append("<div class=\"row\">\n");
		bob.append(getColMDImgCircleShit());
		bob.append(buildDataHeader());

		bob.append(getProgressBarDiv());

		// generate table
		// we have a silly div class row
		bob.append(DIV_CLASS_ROW + "\n");
		bob.append(getTable());
		bob.append("\n</div>\n");
		return bob.toString();
	}

	private String getColMDImgCircleShit() {
		String someData = "<div class=\"col-md-4 col-md-push-8\">\n"
				+ "<img class=\"img-circle\" src=\"../assets/images/"
				+ getAssetFile() + "\" width=\"200\" height=\"200\">\n"
				+ "</div><!-- ./col-md-4 -->\n"
				+ "<div class=\"col-md-8 col-md-pull-4\">\n";
		return someData;
	}

	private String getAssetFile() {
		// TODO Update this with search strings
		switch (pageTitle) {
		case "NPCs":
			return "npcs.min.jpg";
		case "Locations":
			return "locations.min.jpg";
		case "Mysteries":
			return "mysteries.min.jpg";
		case "Treasures":
			return "treasures.jpg";
		case "Dungeons":
			return "dungeons.jpg";
		default:
			throw new UnsupportedOperationException("No file matched with: "
					+ pageTitle);
		}
	}

	private String getProgressBarDiv() {
		StringBuilder bob = new StringBuilder();
		bob.append(DIV_CLASS_ROW + "\n");
		bob.append("<div class=\"progress\">");

		String aria_value_now = tableData.size() + "";

		double percent = (tableData.size() / 10000.00) * 100.00;
		DecimalFormat df = new DecimalFormat("##.##");
		String percentString = df.format(percent) + "%";
		String style_value = "width: " + percentString;

		String divClass = "\n<div class=\"progress-bar progress-bar-info progress-bar-striped\" role=\"progressbar\"";
		String ariaValueNow = "aria-valuenow=\"" + aria_value_now + "\"";
		String ariaValueMinMax = "aria-valuemin=\"0\" aria-valuemax=\"10000\"";
		String style = "style=\"" + style_value + "\">";
		bob.append(divClass + ariaValueNow + ariaValueMinMax + style);

		// build span
		bob.append("<span class=\"sr-only\">" + percentString
				+ " Complete</span>");
		bob.append("</div></div>");
		bob.append(getInputSearch());
		bob.append(getCaseSensitiveCheckBox());
		String featuretteDivider = "<hr class=\"featurette-divider\">";
		bob.append(featuretteDivider);
		bob.append("\n</div>\n");

		return bob.toString();
	}

	private String getCaseSensitiveCheckBox() {
		StringBuilder bob = new StringBuilder();

		File checkboxFile = new File(
				"src/main/resources/site_resources/input_checkbox.txt");
		try (BufferedReader br = new BufferedReader(
				new FileReader(checkboxFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				bob.append(line + "\n");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bob.toString();
	}

	private String buildDataHeader() {
		StringBuilder bob = new StringBuilder();

		String h1LinkHeader = "<h1><a href=\"..\">10K // </a> " + pageTitle
				+ "</h1>\n";
		String paragraphCopyPasta = "<p>This page is a compilation of the locations from <a href=\""
				+ redditUrl
				+ "\">/rDnDBehindTheScreen's 10K "
				+ pageTitle
				+ " thread</a>. Be sure to visit and contribute!</p>\n";
		bob.append(h1LinkHeader);
		bob.append(paragraphCopyPasta);

		// Generate currently String

		String currently = "<h2>Currently at " + tableData.size()
				+ "/10000</h2>";
		bob.append(currently);
		bob.append("<h3>Last updated: " + new Date() + "</h3>\n");
		bob.append("</div><!-- ./col-md-8 -->\n</div><!-- ./row -->\n");
		return bob.toString();
	}

	private String getTable() {
		StringBuilder bob = new StringBuilder();
		bob.append("<table class=\"table table-striped\" id=\"table\">\n");

		// append header
		bob.append(headerTag);
		bob.append("\n");

		StringBuilder tableDataBuilder = new StringBuilder();
		for (ScrubbedDataObject datO : tableData)
			tableDataBuilder.append(datO.toHTMLTableRow());
		bob.append(tableDataBuilder.toString());
		bob.append("\n</table>\n");
		return bob.toString();
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
		return "<input type=\"text\" id=\"search\" placeholder=\"Type to search\" />\n";
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

	private static String getDataFromFile(String fileName) {
		StringBuilder bob = new StringBuilder();

		File checkboxFile = new File(fileName);
		try (BufferedReader br = new BufferedReader(
				new FileReader(checkboxFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				bob.append(line + "\n");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bob.toString();
	}

	public static void main(String[] args) {
		Scrub10KNPCS npcs = new Scrub10KNPCS();
		List<ScrubbedDataObject> dataPoints = npcs.getDataPoints();
		String header = "<tr><th>Name</th><th>Gender Race Occupation</th><th>Description</th></tr>";
		String indexLocation = "src/main/resources/npcs/";
		new BaseSiteBuilderHelper(indexLocation, "NPCs", npcs.REDDIT_URL,
				header, dataPoints).buildHTML();
	}

}
