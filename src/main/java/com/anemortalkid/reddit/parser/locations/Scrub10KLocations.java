package com.anemortalkid.reddit.parser.locations;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.anemortalkid.reddit.parser.dataobjects.ScrubbedDataObject;

public class Scrub10KLocations {

	public static final String REDDIT_URL = "http://www.reddit.com/r/DnDBehindTheScreen/comments/3f0lzl/lets_make_10_000_locations/";
	private static final String Out_LOC = "src/main/resources/locations";
	private List<ScrubbedDataObject> dataPoints = new ArrayList<ScrubbedDataObject>();

	private static final int LAST_KNOWN_COUNT = 72;

	public Scrub10KLocations() {
		compileData();
	}

	private void compileData() {

		try {
			Document redditDoc = Jsoup.connect(REDDIT_URL).userAgent("Mozilla")
					.get();
			if (redditDoc != null) {
				Elements comments = redditDoc.getElementsByClass("thing");
				// System.out.println("Comments:" + comments.size());

				List<Element> nonChildOnly = new ArrayList<Element>();

				for (Element element : comments) {
					nonChildOnly.add(element);
				}

				// System.out.println("Top-Level:" + allElements.size());

				for (Element topLevel : nonChildOnly) {
					Element md = topLevel.getElementsByClass("md").first();

					// Process the first one
					Elements mdElems = md.getAllElements();
					String bold, italic, regular = "";
					bold = "";
					italic = "";
					regular = "";

					for (Element elem : mdElems) {

						String tagName = elem.tagName();
						if (tagName.equals("p")) {
							Element p = elem;
							Elements strongElems = p.getElementsByTag("strong");
							Elements emElems = p.getElementsByTag("em");

							if (!strongElems.isEmpty()) {
								Element strong = strongElems.first();
								bold = strong.text();
							} else if (!emElems.isEmpty()) {
								Element firstEm = emElems.first();
								italic = firstEm.text();
							} else {
								regular += " " + p.text();
							}
						} else if (tagName.equals("hr")) {
							constructIfRequiredPartsAreThere(bold, italic,
									regular);
							bold = "";
							italic = "";
							regular = "";
						}
						if (tagName.equals("strong")) {
							bold = elem.text();
						}
						if (tagName.equals("em")) {
							italic = elem.text();
						}
					}

					constructIfRequiredPartsAreThere(bold, italic, regular);
					bold = "";
					italic = "";
					regular = "";
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(dataPoints.size());
		writeToFile();
	}

	private void writeToFile() {
		File outFile_TXT = new File(Out_LOC + ".txt");
		File outFile_CSV = new File(Out_LOC + ".csv");
		File outFile_table = new File(Out_LOC + "-tabledata.txt");

		int dataWritten = 0;
		if (!outFile_TXT.exists()) {
			try {
				outFile_TXT.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (!outFile_CSV.exists()) {
			try {
				outFile_CSV.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (!outFile_table.exists()) {
			try {
				outFile_table.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			PrintWriter textWritter = new PrintWriter(outFile_TXT);
			PrintWriter csvWritter = new PrintWriter(outFile_CSV);
			PrintWriter tableWritter = new PrintWriter(outFile_table);
			for (ScrubbedDataObject data : dataPoints) {
				System.out.println("DataName: " + data.getDataIdentifier());
				dataWritten++;

				textWritter.println(data.toGoogleSpreadsheet());
				csvWritter.println(data.toCSV());
				tableWritter.println(data.toHTMLTableRow());

				textWritter.flush();
				csvWritter.flush();
				tableWritter.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Wrote " + dataWritten + " data");
	}

	private void constructIfRequiredPartsAreThere(String bold, String italic,
			String regular) {
		if (bold == null || bold.isEmpty())
			return;
		if (italic == null || italic.isEmpty())
			return;
		if (regular == null || regular.isEmpty())
			return;

		/*
		 * Excludes the disclaimer
		 */
		if (bold.equals(italic))
			return;
		if (bold.contains("WELCOME") || italic.contains("WELCOME"))
			return;

		LocationDataObject sd = new LocationDataObject(bold, italic, regular);
		if (!dataPoints.contains(sd)) {
			dataPoints.add(sd);
		}
	}

	public static void main(String[] args) {
		new Scrub10KLocations();
	}

	public List<ScrubbedDataObject> getDataPoints() {
		return dataPoints;
	}

}