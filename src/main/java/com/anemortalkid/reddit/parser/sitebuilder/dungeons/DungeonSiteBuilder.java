package com.anemortalkid.reddit.parser.sitebuilder.dungeons;

import java.util.List;

import com.anemortalkid.reddit.parser.sitebuilder.BaseSiteBuilderHelper;
import com.anemortalkid.reddit.parser.sitebuilder.ISiteBuilder;
import com.anemortalkid.reddit.scrubber.StrongEmphasisParagraphScrubber;
import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class DungeonSiteBuilder implements ISiteBuilder {

	private static String[] urls = {
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/3fb5w5/lets_make_10000_dungeons/",
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/4huas3/10k_dungeons_unholy_places/",
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/4k9k33/10k_dungeons_fortresses_keeps_and_castles/",
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/4q0321/10k_dungeons_coastal_caches_and_island_intrigue/"};

	private static String header = "<tr><th align=\"center\">Dungeon Name</th><th>Dungeon Type</th><th align=\"center\">Description</th></tr>";

	private StrongEmphasisParagraphScrubber scrubber;
	private List<ScrubbedDataObject> data;

	public DungeonSiteBuilder() {
		scrubber = new StrongEmphasisParagraphScrubber(urls);
	}

	@Override
	public String getTitle() {
		return "Dungeons";
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
	public int scrubbedCount() {
		return data == null ? -1 : data.size();
	}

	@Override
	public void scrubData() {
		data = scrubber.scrubData();
	}

	public static void main(String[] args) {
		new DungeonSiteBuilder().buildSite();
	}

}
