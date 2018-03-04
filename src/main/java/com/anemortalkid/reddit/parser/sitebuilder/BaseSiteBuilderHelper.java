package com.anemortalkid.reddit.parser.sitebuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import com.anemortalkid.ResourceAssistant;
import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

/**
 * A {@link BaseSiteBuilderHelper} will build the standard index.html files for
 * each set of scrubbed data
 * 
 * @author JMonterrubio
 *
 */
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
	public BaseSiteBuilderHelper(String htmlOutputFolder, String pageTitle, String redditURL, String tableHeaderHTML,
			List<ScrubbedDataObject> tableData) {
		this.indexLocation = htmlOutputFolder;
		this.pageTitle = pageTitle;
		this.redditUrl = redditURL;
		this.headerTag = tableHeaderHTML;
		this.tableData = tableData;
	}

	/**
	 * Writes an index file to the specified location in {@link #indexLocation}.
	 * The index file will follow the same standard format that the other index
	 * files have had within this project.
	 * 
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
	public void buildHTML() {

		StringBuilder headBuilder = new StringBuilder();
		String imports = getDataFromFile(SiteResourcesConstants.JQUERY_IMPORTS);
		headBuilder.append(imports);
		headBuilder.append("\n");

		String meta = "<meta name=viewport content=\"width=device-width, initial-scale=1\">";
		headBuilder.append(meta);
		headBuilder.append("\n");
		String title = wrapInTitle("10K " + pageTitle);
		headBuilder.append(title);
		headBuilder.append("\n");
		headBuilder.append(getDataFromFile(SiteResourcesConstants.BOOT_STRAP_IMPORTS));
		String headElement = wrapInHead(headBuilder.toString());

		String bodyElement = buildBody();

		String html = wrapInHTML(headElement + "\n" + bodyElement);

		File rootDirectory = new File(indexLocation);
		if (rootDirectory.exists()) {
			rootDirectory.mkdirs();
		}

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
		String bodyStart = "<body onload=\"setSorter()\">\n";
		bob.append(bodyStart);

		String containerData = getContainerData();
		bob.append(containerData);

		bob.append("</div>\n");
		bob.append(getDataFromFile(SiteResourcesConstants.END_JAVASCRIPT_FUNCTIONS));
		String bodyEnd = "</body>";
		bob.append(bodyEnd);

		return bob.toString();
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

		// Format the {0} for the inputs to say Random Title
		String inputs = getDataFromFile(SiteResourcesConstants.INPUTS);
		String pageTitleNoPlural = pageTitle.substring(0, pageTitle.length() - 1);
		bob.append(MessageFormat.format(inputs, pageTitleNoPlural));

		bob.append(getProgressBarDiv());

		// generate table
		// we have a silly div class row
		bob.append(DIV_CLASS_ROW + "\n");

		// Append the random div tabble
		bob.append("<div id=\"randomTable\"></div>\n");
		bob.append(getTable());
		bob.append("\n</div>\n");
		return bob.toString();
	}

	private String getColMDImgCircleShit() {
		String someData = "<div class=\"col-md-4 col-md-push-8\">\n"
				+ "<img class=\"img-circle\" src=\"../assets/images/" + getAssetFile()
				+ "\" width=\"200\" height=\"200\">\n" + "</div><!-- ./col-md-4 -->\n"
				+ "<div class=\"col-md-8 col-md-pull-4\">\n";
		return someData;
	}

	private String getAssetFile() {
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
		case "Plot Hooks":
			return "plothooks.jpg";
		case "Villains":
			return "villains.jpg";
		case "Rooms":
			return "rooms.png";
		case "Apocalypses":
			return "rooms.png";
		case "Books":
			return "rooms.png";
		case "Party Foods":
				return "rooms.png";
			//TODO add party foods
		default:
			throw new UnsupportedOperationException("No file matched with: " + pageTitle);
		}
	}

	private String getProgressBarDiv() {
		StringBuilder bob = new StringBuilder();
		bob.append(DIV_CLASS_ROW + "\n");
		String unformattedProgressBar = getDataFromFile(SiteResourcesConstants.PROGRESS_BAR);
		double percent = (tableData.size() / 10000.00) * 100.00;
		DecimalFormat df = new DecimalFormat("##.##");
		bob.append(MessageFormat.format(unformattedProgressBar, tableData.size(), df.format(percent)));
		return bob.toString();
	}

	private String buildDataHeader() {
		StringBuilder bob = new StringBuilder();

		String h1LinkHeader = "<h1><a href=\"..\">10K // </a> " + pageTitle + "</h1>\n";
		String paragraphCopyPasta = "<p>This page is a compilation of the " + pageTitle.toLowerCase()
				+ " from <a href=\"" + redditUrl + "\">/rDnDBehindTheScreen's 10K " + pageTitle
				+ " thread</a>. Be sure to visit and contribute!</p>\n";
		bob.append(h1LinkHeader);
		bob.append(paragraphCopyPasta);

		// Generate currently String

		String currently = "<h2>Currently at " + tableData.size() + "/10000</h2>";
		bob.append(currently);
		bob.append("<h3>Last updated: " + new Date() + "</h3>\n");
		bob.append("</div><!-- ./col-md-8 -->\n</div><!-- ./row -->\n");
		return bob.toString();
	}

	private String getTable() {
		StringBuilder bob = new StringBuilder();
		bob.append("<table class=\"table table-striped tablesorter\" id=\"table\">\n");

		// append header
		bob.append("<thead>\n");
		bob.append(headerTag);
		bob.append("\n");
		bob.append("</thead>\n");

		bob.append("<tbody>");
		StringBuilder tableDataBuilder = new StringBuilder();
		for (ScrubbedDataObject datO : tableData)
			tableDataBuilder.append(datO.toHTMLTableRow());
		bob.append(tableDataBuilder.toString());
		bob.append("</tbody>");
		bob.append("\n</table>\n");
		return bob.toString();
	}

	private static String wrapInHTML(String text) {
		return MessageFormat.format("<html>\n{0}\n</html>", text);
	}

	private static String wrapInHead(String text) {
		return MessageFormat.format("<head>\n{0}\n</head>", text);
	}

	private static String wrapInTitle(String text) {
		return MessageFormat.format("<title>{0}</title>", text);
	}

	/**
	 * Reads the specified file and returns the data within it inside a string
	 * 
	 * @param fileName
	 *            the file to read from
	 * @return a String with the data for the file, preserving new lines
	 */
	private static String getDataFromFile(String fileName) {
		StringBuilder bob = new StringBuilder();

		try {
			List<String> lines = ResourceAssistant.INSTANCE.getLines(fileName);
			lines.forEach(line -> bob.append(line + "\n"));
		} catch (IOException | URISyntaxException e1) {
			e1.printStackTrace();
		}
		return bob.toString();
	}

}
