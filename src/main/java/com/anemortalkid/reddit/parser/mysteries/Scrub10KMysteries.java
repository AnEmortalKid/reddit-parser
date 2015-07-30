package com.anemortalkid.reddit.parser.mysteries;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.anemortalkid.reddit.parser.dataobjects.DataObject;

public class Scrub10KMysteries {

	private static final String Out_LOC = "src/main/resources/mysteries";
	private static final int LAST_KNOWN_COUNT = 200;

	public static final String REDDIT_URL = "https://www.reddit.com/r/DnDBehindTheScreen/comments/3evxgl/lets_make_10000_mysteries/";

	private List<DataObject> dataPoints = new ArrayList<DataObject>();

	public Scrub10KMysteries() {
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
					String bold, regular = "";
					bold = "";
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
							} else {
								regular += " " + p.text();
							}
						} else if (tagName.equals("hr")) {
							constructIfRequiredPartsAreThere(bold, regular);
							bold = "";
							regular = "";
						}
						if (tagName.equals("strong")) {
							bold = elem.text();
						}
					}

					constructIfRequiredPartsAreThere(bold, regular);
					bold = "";
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
			for (DataObject data : dataPoints) {
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

	private void constructIfRequiredPartsAreThere(String bold, String regular) {
		if (bold == null || bold.isEmpty())
			return;

		if (regular == null || regular.isEmpty())
			return;

		/*
		 * Excludes the header
		 */
		if (bold.contains("WELCOME"))
			return;

		MysteryDataObject sd = new MysteryDataObject(bold, regular);
		if (!dataPoints.contains(sd)) {
			dataPoints.add(sd);
		}
	}

	public List<DataObject> getDataPoints() {
		return dataPoints;
	}

	public static void main(String[] args) {
		new Scrub10KMysteries();
	}

}
