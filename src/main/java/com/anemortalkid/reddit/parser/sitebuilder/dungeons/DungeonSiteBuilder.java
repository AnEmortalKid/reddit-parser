package com.anemortalkid.reddit.parser.sitebuilder.dungeons;

import java.util.ArrayList;
import java.util.List;

import com.anemortalkid.reddit.parser.sitebuilder.ISiteBuilder;
import com.anemortalkid.reddit.parser.sitebuilder.BaseSiteBuilderHelper;
import com.anemortalkid.reddit.scrubber.StrongEmphasisParagraphScrubber;
import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class DungeonSiteBuilder implements ISiteBuilder {

	@Override
	public void buildSite() {
		String oldURL = "https://www.reddit.com/r/DnDBehindTheScreen/comments/3fb5w5/lets_make_10000_dungeons/";

		// get scrubber and write to file
		StrongEmphasisParagraphScrubber scrubber = new StrongEmphasisParagraphScrubber(
				oldURL);
		List<ScrubbedDataObject> oldData = scrubber.scrubData();
		System.out.println("Old Dungeons scrubbed: " + oldData.size());

		String newURL = "https://www.reddit.com/r/DnDBehindTheScreen/comments/4huas3/10k_dungeons_unholy_places/";
		StrongEmphasisParagraphScrubber newScrubber = new StrongEmphasisParagraphScrubber(newURL);
		List<ScrubbedDataObject> newData = newScrubber.scrubData();
		System.out.println("New Dungeons scrubbed: " + newData.size());
		
		List<ScrubbedDataObject> allData = new ArrayList<>();
		allData.addAll(oldData);
		allData.addAll(newData);
		String fileLocationAndName = "src/main/resources/dungeons";
		scrubber.writeDataToFiles(fileLocationAndName, allData);

		String header = "<tr><th align=\"center\">Dungeon Name</th><th>Dungeon Type</th><th align=\"center\">Description</th></tr>";
		new BaseSiteBuilderHelper(fileLocationAndName + "/", "Dungeons",
				newURL, header, allData).buildHTML();
	}

	public static void main(String[] args) {
		new DungeonSiteBuilder().buildSite();
	}

}
