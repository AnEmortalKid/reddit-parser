package com.anemortalkid.reddit.parser.sitebuilder.dungeons;

import java.util.List;

import com.anemortalkid.reddit.parser.sitebuilder.ISiteBuilder;
import com.anemortalkid.reddit.parser.sitebuilder.BaseSiteBuilderHelper;
import com.anemortalkid.reddit.scrubber.StrongEmphasisParagraphScrubber;
import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class DungeonSiteBuilder implements ISiteBuilder {

	@Override
	public void buildSite() {
		String redditURL = "https://www.reddit.com/r/DnDBehindTheScreen/comments/3fb5w5/lets_make_10000_dungeons/";

		// get scrubber and write to file
		StrongEmphasisParagraphScrubber scrubber = new StrongEmphasisParagraphScrubber(
				redditURL);
		List<ScrubbedDataObject> data = scrubber.scrubData();
		System.out.println("Dungeons scrubbed: " + data.size());

		String fileLocationAndName = "src/main/resources/dungeons";
		scrubber.writeDataToFiles(fileLocationAndName, data);

		String header = "<tr><th align=\"center\">Dungeon Name</th><th>Dungeon Type</th><th align=\"center\">Description</th></tr>";
		new BaseSiteBuilderHelper(fileLocationAndName + "/", "Dungeons",
				redditURL, header, data).buildHTML();
	}

	public static void main(String[] args) {
		new DungeonSiteBuilder().buildSite();
	}

}
