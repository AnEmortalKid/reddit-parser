package com.anemortalkid.reddit.parser.sitebuilder.locations;

import java.util.List;

import com.anemortalkid.reddit.parser.sitebuilder.BaseSiteBuilderHelper;
import com.anemortalkid.reddit.parser.sitebuilder.ISiteBuilder;
import com.anemortalkid.reddit.scrubber.StrongEmphasisParagraphScrubber;
import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

/**
 * 
 * @author JMonterrubio
 *
 */
public class LocationsSiteBuilder implements ISiteBuilder {

	private static String[] urls = { //
			"http://www.reddit.com/r/DnDBehindTheScreen/comments/3f0lzl/lets_make_10_000_locations/",
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/4iuwym/10k_locations_hallowed_ground/",
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/4ousc6/10k_locations_thieves_dens_and_pirates_coves/"};

	private static String header = "<tr><th align=\"center\">Name</th><th align=\"center\">Type</th><th align=\"center\">Description</th></tr>";

	private StrongEmphasisParagraphScrubber scrubber;
	private List<ScrubbedDataObject> data;

	public LocationsSiteBuilder() {
		scrubber = new StrongEmphasisParagraphScrubber(urls);
	}

	@Override
	public String getTitle() {
		return "Locations";
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
		new LocationsSiteBuilder().buildSite();
	}
}
