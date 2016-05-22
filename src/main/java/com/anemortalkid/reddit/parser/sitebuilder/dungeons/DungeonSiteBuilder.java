package com.anemortalkid.reddit.parser.sitebuilder.dungeons;

import java.util.List;

import com.anemortalkid.reddit.parser.sitebuilder.BaseSiteBuilderHelper;
import com.anemortalkid.reddit.parser.sitebuilder.ISiteBuilder;
import com.anemortalkid.reddit.scrubber.StrongEmphasisParagraphScrubber;
import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class DungeonSiteBuilder implements ISiteBuilder {

	
	private static String[] urls = {
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/3fb5w5/lets_make_10000_dungeons/",
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/4huas3/10k_dungeons_unholy_places/",
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/4k9k33/10k_dungeons_fortresses_keeps_and_castles/"
	};
	
	@Override
	public void buildSite() {

		// get scrubber and write to file
		StrongEmphasisParagraphScrubber scrubber = new StrongEmphasisParagraphScrubber(
				urls);
		List<ScrubbedDataObject> allData = scrubber.scrubData();
		System.out.println("Dungeons scrubbed: " + allData.size());

		String fileLocationAndName = "src/main/resources/dungeons";
		scrubber.writeDataToFiles(fileLocationAndName, allData);

		String header = "<tr><th align=\"center\">Dungeon Name</th><th>Dungeon Type</th><th align=\"center\">Description</th></tr>";
		new BaseSiteBuilderHelper(fileLocationAndName + "/", "Dungeons",
				urls[urls.length-1], header, allData).buildHTML();
	}

	public static void main(String[] args) {
		new DungeonSiteBuilder().buildSite();
	}

}
