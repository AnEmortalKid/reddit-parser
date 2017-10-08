package com.anemortalkid.reddit.parser.sitebuilder.treasures;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import com.anemortalkid.reddit.parser.sitebuilder.SiteBuilder;
import com.anemortalkid.reddit.scrubber.StrongEmphasisParagraphScrubber;
import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class TreasureSiteBuilder implements SiteBuilder<TreasureData> {

	private static String[] urls = { //
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/3f633m/lets_make_10000_treasures/",
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/4hzx3a/10k_treasure_trinkets_talismans_and_prayer_beads/",
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/4n4yvf/10k_treasure_books_and_scrolls/",
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/4paizo/10k_treasures_maritime_plunder/", };

	private static String header = "<tr><th align=\"center\">Treasure Name</th><th align=\"center\">Treasure Type</th><th align=\"center\">Description</th></tr>";

	private StrongEmphasisParagraphScrubber scrubber;

	private List<ScrubbedDataObject> data;

	public TreasureSiteBuilder() {
		scrubber = new StrongEmphasisParagraphScrubber(urls);
	}

	public TreasureSiteBuilder(List<String> newURLs) {
		Collections.reverse(newURLs);
		scrubber = new StrongEmphasisParagraphScrubber(newURLs.toArray(new String[newURLs.size()]));
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
		new TreasureSiteBuilder().buildSite();
	}

	@Override
	public String getTitle() {
		return "Treasures";
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
	public Function<ScrubbedDataObject, TreasureData> getJsonFactory() {
		return TreasureData::createFromScrubbedObject;
	}

}
