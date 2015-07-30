package com.anemortalkid.reddit.parser.sitebuilder;

import java.util.List;

import com.anemortalkid.reddit.parser.dataobjects.ScrubbedDataObject;
import com.anemortalkid.reddit.parser.npcs.Scrub10KNPCS;
import com.anemortalkid.reddit.scrubber.StrongEmphasisParagraphScrubber;

public class NPCSiteBuilder implements ISiteBuilder {

	public static void main(String[] args) {
		new NPCSiteBuilder().buildSite();
	}

	@Override
	public void buildSite() {
		String redditURL = "https://www.reddit.com/r/DnDBehindTheScreen/comments/3er483/lets_make_10000_npcs/";
		String header = "<tr><th>Name</th><th>Gender Race Occupation</th><th>Description</th></tr>";

		// get scrubber and write to file
		StrongEmphasisParagraphScrubber seps = new StrongEmphasisParagraphScrubber();
		List<ScrubbedDataObject> data = seps.scrubDataFromUrl(redditURL);
		String fileLocationAndName = "src/main/resources/npcs";
		seps.writeDataToFiles(fileLocationAndName, data);

		new SiteBuilder(fileLocationAndName + "/", "NPCs",
				Scrub10KNPCS.REDDIT_URL, header, data).buildHTML();
	}
}
