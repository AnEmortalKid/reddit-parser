package com.anemortalkid.reddit.parser.sitebuilder.npcs;

import java.util.List;

import com.anemortalkid.reddit.parser.npcs.Scrub10KNPCS;
import com.anemortalkid.reddit.parser.sitebuilder.ISiteBuilder;
import com.anemortalkid.reddit.parser.sitebuilder.BaseSiteBuilderHelper;
import com.anemortalkid.reddit.scrubber.StrongEmphasisParagraphScrubber;
import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class NPCSiteBuilder implements ISiteBuilder {

	@Override
	public void buildSite() {
		String redditURL = "https://www.reddit.com/r/DnDBehindTheScreen/comments/3er483/lets_make_10000_npcs/";
		String header = "<tr><th>Name</th><th>Gender Race Occupation</th><th>Description</th></tr>";

		// get scrubber and write to file
		StrongEmphasisParagraphScrubber seps = new StrongEmphasisParagraphScrubber(
				redditURL);
		List<ScrubbedDataObject> data = seps.scrubData();
		System.out.println("NPCs scrubbed: " + data.size());

		String fileLocationAndName = "src/main/resources/npcs";
		seps.writeDataToFiles(fileLocationAndName, data);

		String folder = fileLocationAndName + "/";

		new BaseSiteBuilderHelper(folder, "NPCs", redditURL, header, data)
				.buildHTML();
	}

	public static void main(String[] args) {
		new NPCSiteBuilder().buildSite();
	}
}
