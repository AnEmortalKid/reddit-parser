package com.anemortalkid.reddit.parser.sitebuilder.dungeons;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import com.anemortalkid.reddit.parser.sitebuilder.ISiteBuilder;
import com.anemortalkid.reddit.scrubber.StrongEmphasisParagraphScrubber;
import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class DungeonSiteBuilder implements ISiteBuilder<DungeonData> {

	private static final String QUERY_URL = "https://www.reddit.com/r/DnDBehindTheScreen/search?q=flair%3A%2710K+Event%27+%2B+title%3A%27Locations%27&restrict_sr=on&sort=new&t=all";
	
	private static String[] urls = {
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/3fb5w5/lets_make_10000_dungeons/",
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/4huas3/10k_dungeons_unholy_places/",
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/4k9k33/10k_dungeons_fortresses_keeps_and_castles/",
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/4q0321/10k_dungeons_coastal_caches_and_island_intrigue/" };

	private static String header = "<tr><th align=\"center\">Dungeon Name</th><th>Dungeon Type</th><th align=\"center\">Description</th></tr>";

	private StrongEmphasisParagraphScrubber scrubber;
	private List<ScrubbedDataObject> data;

	public DungeonSiteBuilder() {
		scrubber = new StrongEmphasisParagraphScrubber(urls);
	}

	public DungeonSiteBuilder(List<String> newURLs) {
		Collections.reverse(newURLs);
		scrubber = new StrongEmphasisParagraphScrubber(newURLs.toArray(new String[newURLs.size()]));
	}

	@Override
	public String getTitle() {
		return "Dungeons";
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

	@Override
	public Function<ScrubbedDataObject, DungeonData> getJsonFactory() {
		return DungeonData::createFromScrubbedObject;
	}

	public static void main(String[] args) {
		new DungeonSiteBuilder().buildSite();
	}

}
