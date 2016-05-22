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

	private static String[] urls = {//
			"http://www.reddit.com/r/DnDBehindTheScreen/comments/3f0lzl/lets_make_10_000_locations/",
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/4iuwym/10k_locations_hallowed_ground/"
	};
	
	@Override
	public void buildSite() {
		StrongEmphasisParagraphScrubber scrubber = new StrongEmphasisParagraphScrubber(
				urls);
		List<ScrubbedDataObject> data = scrubber.scrubData();
		System.out.println("Locations scrubbed: " + data.size());

		String fileLocationAndName = "src/main/resources/locations";
		scrubber.writeDataToFiles(fileLocationAndName, data);

		String header = "<tr><th align=\"center\">Name</th><th align=\"center\">Type</th><th align=\"center\">Description</th></tr>";
		String indexLocation = "src/main/resources/locations/";

		BaseSiteBuilderHelper baseBuilder = new BaseSiteBuilderHelper(
				indexLocation, "Locations", urls[urls.length-1], header, data);
		baseBuilder.buildHTML();
	}

	public static void main(String[] args) {
		new LocationsSiteBuilder().buildSite();
	}
}
