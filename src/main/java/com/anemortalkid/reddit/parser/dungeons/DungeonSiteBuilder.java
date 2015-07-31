package com.anemortalkid.reddit.parser.dungeons;

import java.util.List;

import com.anemortalkid.reddit.parser.dataobjects.ScrubbedDataObject;
import com.anemortalkid.reddit.parser.sitebuilder.ISiteBuilder;
import com.anemortalkid.reddit.parser.sitebuilder.SiteBuilder;
import com.anemortalkid.reddit.scrubber.StrongEmphasisParagraphScrubber;

public class DungeonSiteBuilder implements ISiteBuilder {

	@Override
	public void buildSite() {
		String redditURL = "https://www.reddit.com/r/DnDBehindTheScreen/comments/3fb5w5/lets_make_10000_dungeons/";
		String header = "<tr><th>Dungeon Name</th><th>Dungeon Type</th><th>Description</th></tr>";

		// get scrubber and write to file
		StrongEmphasisParagraphScrubber seps = new StrongEmphasisParagraphScrubber();
		List<ScrubbedDataObject> data = seps.scrubDataFromUrl(redditURL);
		String fileLocationAndName = "src/main/resources/dungeons";
		seps.writeDataToFiles(fileLocationAndName, data);

		new SiteBuilder(fileLocationAndName + "/", "Dungeons", redditURL,
				header, data).buildHTML();
	}

	public static void main(String[] args) {
		new DungeonSiteBuilder().buildSite();
	}

}
