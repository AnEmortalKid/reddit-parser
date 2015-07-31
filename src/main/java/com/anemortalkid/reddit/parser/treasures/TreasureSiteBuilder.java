package com.anemortalkid.reddit.parser.treasures;

import java.util.List;

import com.anemortalkid.reddit.parser.dataobjects.ScrubbedDataObject;
import com.anemortalkid.reddit.parser.npcs.Scrub10KNPCS;
import com.anemortalkid.reddit.parser.sitebuilder.ISiteBuilder;
import com.anemortalkid.reddit.parser.sitebuilder.SiteBuilder;
import com.anemortalkid.reddit.scrubber.StrongEmphasisParagraphScrubber;

public class TreasureSiteBuilder implements ISiteBuilder {

	@Override
	public void buildSite() {
		String redditURL = "https://www.reddit.com/r/DnDBehindTheScreen/comments/3f633m/lets_make_10000_treasures/";
		String header = "<tr><th>Treasure Name</th><th>Treasure Type</th><th>Description</th></tr>";

		// get scrubber and write to file
		StrongEmphasisParagraphScrubber seps = new StrongEmphasisParagraphScrubber();
		List<ScrubbedDataObject> data = seps.scrubDataFromUrl(redditURL);
		String fileLocationAndName = "src/main/resources/treasures";
		seps.writeDataToFiles(fileLocationAndName, data);

		new SiteBuilder(fileLocationAndName + "/", "Treasures",
				Scrub10KNPCS.REDDIT_URL, header, data).buildHTML();
	}

	public static void main(String[] args) {
		new TreasureSiteBuilder().buildSite();
	}

}
