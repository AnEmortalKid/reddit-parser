package com.anemortalkid.reddit.parser.sitebuilder.villains;

import java.util.List;

import com.anemortalkid.reddit.parser.sitebuilder.BaseSiteBuilderHelper;
import com.anemortalkid.reddit.parser.sitebuilder.ISiteBuilder;
import com.anemortalkid.reddit.scrubber.StrongParagraphScrubber;
import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class VillainsSiteBuilder implements ISiteBuilder {

	private static String[] urls = { //
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/3fmnhb/10000_villains/",
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/4hn60f/10k_villains_demons_and_devils/", 
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/4knuyj/10k_villains_monstrosities/"};

	private static String header = "<tr><th align=\"center\">Villain Name</th><th align=\"center\">Description</th></tr>";

	private StrongParagraphScrubber scrubber;
	private List<ScrubbedDataObject> data;

	public VillainsSiteBuilder() {
		scrubber = new StrongParagraphScrubber(urls);
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

	public static void main(String[] args) {
		new VillainsSiteBuilder().buildSite();
	}

}
