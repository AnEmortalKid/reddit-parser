package com.anemortalkid.reddit.parser.treasures;

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
import com.anemortalkid.reddit.parser.npcs.NPCDataObject;
import com.anemortalkid.reddit.scrubber.IScrubber;

public class TreasureScrubber implements IScrubber {

	public static final String REDDIT_URL = "https://www.reddit.com/r/DnDBehindTheScreen/comments/3er483/lets_make_10000_npcs/";
	private List<ScrubbedDataObject> dataPoints;

	private static final int LAST_KNOWN_COUNT = 360;

	public TreasureScrubber() {
		dataPoints = new ArrayList<ScrubbedDataObject>();
	}

	public List<ScrubbedDataObject> getDataPoints() {
		return dataPoints;
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

		NPCDataObject sd = new NPCDataObject(bold, italic, regular);
		if (!dataPoints.contains(sd)) {
			dataPoints.add(sd);
		}
	}

	@Override
	public List<ScrubbedDataObject> scrubDataFromUrl(String url) {
		compileData();
		return dataPoints;
	}

	public static void main(String[] args) {
		TreasureScrubber ts = new TreasureScrubber();
		List<ScrubbedDataObject> data = ts.scrubDataFromUrl(REDDIT_URL);
		ts.writeDataToFiles("src/main/resources/treasures", data);
	}

}
