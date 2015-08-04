package com.anemortalkid.reddit.scrubber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.anemortalkid.reddit.scrubber.dataobject.MultiDataObject;
import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

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

	private String url;

	public StrongParagraphScrubber(String url) {
		this.url = url;
	}

	@Override
	public List<ScrubbedDataObject> scrubData() {
		return scrubData(url);
	}

	private List<ScrubbedDataObject> scrubData(String url) {
		List<ScrubbedDataObject> data = new ArrayList<ScrubbedDataObject>();
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

							if (!strongElems.isEmpty()) {
								Element strong = strongElems.first();
								bold = strong.text();
							} else {
								regular += " " + p.text();
							}
						} else if (tagName.equals("hr")) {
							constructIfRequiredPartsAreThere(bold, regular,
									data);
							bold = "";
							regular = "";
						} else {
							// TODO: Put as Scrubber with OL LI handle in it /
							// refactor for modularity mayhaps
							if (tagName.equals("ol")) {
								regular += " " + "<ol>\n";
								continue;
							}
							if (tagName.equals("/ol")) {
								regular += " " + "</ol>\n";
							}
							if (tagName.equals("li")) {
								regular += " " + "<li>" + elem.text()
										+ "</li>\n";
							}

						}
						if (tagName.equals("strong")) {
							bold = elem.text();
						}
					}

					constructIfRequiredPartsAreThere(bold, regular, data);
					bold = "";
					regular = "";
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;
	}

	private void constructIfRequiredPartsAreThere(String bold,
			String paragraph, List<ScrubbedDataObject> data) {
		if (bold == null || bold.isEmpty())
			return;

		if (paragraph == null || paragraph.isEmpty())
			return;

		/*
		 * Excludes the header that some people put
		 */
		if (bold.contains("WELCOME") || bold.contains("Welcome") || bold.contains("The goal"))
			return;

		MultiDataObject md = new MultiDataObject(bold, paragraph);
		if (!data.contains(md)) {
			data.add(md);
		}
	}

}
