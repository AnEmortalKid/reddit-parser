package com.anemortalkid.reddit.parser.sitebuilder.treasures;

import java.util.List;

import com.anemortalkid.reddit.parser.sitebuilder.BaseSiteBuilderHelper;
import com.anemortalkid.reddit.parser.sitebuilder.ISiteBuilder;
import com.anemortalkid.reddit.scrubber.StrongEmphasisParagraphScrubber;
import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class TreasureSiteBuilder implements ISiteBuilder {

	@Override
	public void buildSite() {
		String redditURL = "https://www.reddit.com/r/DnDBehindTheScreen/comments/3f633m/lets_make_10000_treasures/";
		String header = "<tr><th align=\"center\">Treasure Name</th><th align=\"center\">Treasure Type</th><th align=\"center\">Description</th></tr>";

		// get scrubber and write to file
		StrongEmphasisParagraphScrubber seps = new StrongEmphasisParagraphScrubber(
				redditURL);
		List<ScrubbedDataObject> data = seps.scrubData();
		System.out.println("Treasures scrubbed: " + data.size());

		String fileLocationAndName = "src/main/resources/treasures";
		seps.writeDataToFiles(fileLocationAndName, data);

		new BaseSiteBuilderHelper(fileLocationAndName + "/", "Treasures",
				redditURL, header, data).buildHTML();
	}

	public static void main(String[] args) {
		new TreasureSiteBuilder().buildSite();
	}

}
