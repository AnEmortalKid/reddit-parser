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
 * &lt;em&gt;Subdata&lt;/em&gt;
 * &lt;p&gt;More Data &lt;/p&gt;
 * &lt;hr&gt;
 * </pre>
 * 
 * @author JMonterrubio
 *
 */
public class StrongEmphasisParagraphScrubber implements IScrubber {

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
				// System.out.println("Comments:" + comments.size());

				List<Element> nonChildOnly = new ArrayList<Element>();

				for (Element element : comments) {
					nonChildOnly.add(element);
				}

				// System.out.println("Top-Level:" + allElements.size());

				for (Element topLevel : nonChildOnly) {
					Element md = topLevel.getElementsByClass("md").first();
					if(md == null)
						continue;
					
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

		MultiDataObject mdo = new MultiDataObject(bold, italic, regular);
		if (!dataPoints.contains(mdo)) {
			dataPoints.add(mdo);
		}
	}

}
