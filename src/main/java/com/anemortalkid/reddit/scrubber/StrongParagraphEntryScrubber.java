package com.anemortalkid.reddit.scrubber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.anemortalkid.reddit.scrubber.dataobject.MultiDataObject;
import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class StrongParagraphEntryScrubber implements IScrubber {

	private Set<String> ignorePhrases;
	private String[] urls;

	public StrongParagraphEntryScrubber(String... urls) {
		this.urls = urls;
	}

	public StrongParagraphEntryScrubber(Set<String> ignorePhrases, String... urls) {
		this.ignorePhrases = ignorePhrases;
		this.urls = urls;
	}

	private List<ScrubbedDataObject> scrubData(String url) {
		List<ScrubbedDataObject> data = new ArrayList<ScrubbedDataObject>();
		try {
			Document redditDoc = Jsoup.connect(url).userAgent("Mozilla").get();
			if (redditDoc != null) {
				Elements entries = redditDoc.getElementsByClass("entry");

				List<Element> validEntries = new ArrayList<>();
				for (Element entry : entries) {

					if (!shouldSkip(entry)) {
						validEntries.add(entry);
					}

				}

				for (Element entry : validEntries) {
					Element userText = entry.getElementsByClass("usertext-body").first();
					Element md = userText.getElementsByClass("md").first();

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
							constructIfValid(bold, regular, data);
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
								regular += " " + "<li>" + elem.text() + "</li>\n";
							}

						}
						if (tagName.equals("strong")) {
							bold = elem.text();
						}
					}

					constructIfValid(bold, regular, data);
					bold = "";
					regular = "";
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;
	}

	private boolean shouldSkip(Element entry) {
		String allText = entry.text();
		for (String ignorePhrase : ignorePhrases) {
			if (allText.contains(ignorePhrase)) {
				return true;
			}
		}

		return false;
	}

	private void constructIfValid(String bold, String paragraph, List<ScrubbedDataObject> data) {
		if (bold == null || bold.isEmpty())
			return;

		if (paragraph == null || paragraph.isEmpty())
			return;

		MultiDataObject md = new MultiDataObject(bold, paragraph);
		if (!data.contains(md)) {
			data.add(md);
		}
	}

	@Override
	public List<ScrubbedDataObject> scrubData() {
		List<ScrubbedDataObject> allData = new ArrayList<>();
		for (String singleUrl : urls) {
			allData.addAll(scrubData(singleUrl));
		}
		return allData;
	}

}
