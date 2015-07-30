package com.anemortalkid.reddit.scrubber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.anemortalkid.reddit.parser.dataobjects.ScrubbedDataObject;
import com.anemortalkid.reddit.scrubber.dataobjs.MultiDataObject;

/**
 * A PageScrubber that parses data in the format
 * 
 * <pre>
 * &lt;strong&gt;Some data - will be used as the identifier&lt;/strong&gt;
 * &lt;p&gt;More Data &lt;/p&gt;
 * &lt;hr&gt;
 * </pre>
 * 
 * @author JMonterrubio
 *
 */
public class StrongParagraphScrubber implements IScrubber {

	private List<ScrubbedDataObject> dataPoints;

	@Override
	public List<ScrubbedDataObject> scrubDataFromUrl(String url) {
		dataPoints = new ArrayList<ScrubbedDataObject>();
		scrubData(url);
		return dataPoints;
	}

	private void scrubData(String url) {
		try {
			Document redditDoc = Jsoup.connect(url).userAgent("Mozilla").get();
			if (redditDoc != null) {
				Elements comments = redditDoc.getElementsByClass("thing");

				// Copying to a list in case we wanted to do some filtering
				List<Element> thingElems = new ArrayList<Element>();
				for (Element element : comments) {
					thingElems.add(element);
				}

				for (Element topLevel : thingElems) {
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
	}

	private void constructIfRequiredPartsAreThere(String part1, String part2) {
		if (part1 == null || part1.isEmpty())
			return;

		if (part2 == null || part2.isEmpty())
			return;

		/*
		 * Excludes the header that some people put
		 */
		if (part1.contains("WELCOME"))
			return;

		MultiDataObject md = new MultiDataObject(part1, part2);
		if (!dataPoints.contains(md)) {
			dataPoints.add(md);
		}
	}

	public static void main(String[] args) {
		StrongParagraphScrubber sps = new StrongParagraphScrubber();
		String outLocation = "src/main/resources/mysteries";
		String redditURL = "https://www.reddit.com/r/DnDBehindTheScreen/comments/3evxgl/lets_make_10000_mysteries/";
		List<ScrubbedDataObject> dataFromUrl = sps.scrubDataFromUrl(redditURL);
		sps.writeDataToFiles(outLocation, dataFromUrl);
	}

}
