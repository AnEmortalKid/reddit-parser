package com.anemortalkid.reddit.parser.sitebuilder.villains;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import com.anemortalkid.reddit.parser.sitebuilder.ISiteBuilder;
import com.anemortalkid.reddit.scrubber.StrongParagraphScrubber;
import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class VillainsSiteBuilder implements ISiteBuilder<VillainData> {

	private static String[] urls = { //
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/3fmnhb/10000_villains/",
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/4hn60f/10k_villains_demons_and_devils/",
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/4knuyj/10k_villains_monstrosities/",
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/4rxgns/10k_villains_warlords_and_military_commanders/" };

	private static String header = "<tr><th align=\"center\">Villain Name</th><th align=\"center\">Description</th></tr>";

	private StrongParagraphScrubber scrubber;
	private List<ScrubbedDataObject> data;

	public VillainsSiteBuilder() {
		scrubber = new StrongParagraphScrubber(urls);
	}

	public VillainsSiteBuilder(List<String> newURLs) {
		Collections.reverse(newURLs);
		scrubber = new StrongParagraphScrubber(newURLs.toArray(new String[newURLs.size()]));
	}
	
	@Override
	public String getTitle() {
		return "Villains";
	}

	@Override
	public String getRedditURL() {
		return urls[urls.length - 1];
	}

	@Override
	public String getTableHeader() {
		return header;
	}

	@Override
	public List<ScrubbedDataObject> getScrubbedData() {
		return data;
	}

	@Override
	public void scrubData() {
		data = scrubber.scrubData();
	}

	@Override
	public int scrubbedCount() {
		return data == null ? -1 : data.size();
	}

	@Override
	public Function<ScrubbedDataObject, VillainData> getJsonFactory() {
		return VillainData::createFromScrubbedObject;
	}

	public static void main(String[] args) {
		new VillainsSiteBuilder().buildSite();
	}

}
