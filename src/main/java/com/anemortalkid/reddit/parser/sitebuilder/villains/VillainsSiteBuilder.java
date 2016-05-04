package com.anemortalkid.reddit.parser.sitebuilder.villains;

import java.util.ArrayList;
import java.util.List;

import com.anemortalkid.reddit.parser.sitebuilder.BaseSiteBuilderHelper;
import com.anemortalkid.reddit.parser.sitebuilder.ISiteBuilder;
import com.anemortalkid.reddit.scrubber.StrongParagraphScrubber;
import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class VillainsSiteBuilder implements ISiteBuilder {

	@Override
	public void buildSite() {
		String redditURL = "https://www.reddit.com/r/DnDBehindTheScreen/comments/3fmnhb/10000_villains/";
		StrongParagraphScrubber scrubber = new StrongParagraphScrubber(
				redditURL);
		List<ScrubbedDataObject> oldData = scrubber.scrubData();
		System.out.println("Villains scrubbed Old: " + oldData.size());

		String newURL = "https://www.reddit.com/r/DnDBehindTheScreen/comments/4hn60f/10k_villains_demons_and_devils/";
		StrongParagraphScrubber newDataScrubber = new StrongParagraphScrubber(newURL);
		List<ScrubbedDataObject> newData = newDataScrubber.scrubData();
		System.out.println("New Data Scrubbed: " + newData.size());
		List<ScrubbedDataObject> allData = new ArrayList<>(oldData);
		allData.addAll(newData);
		
		String fileLocationAndName = "src/main/resources/villains";
		scrubber.writeDataToFiles(fileLocationAndName, allData);

		String folder = fileLocationAndName + "/";

		String header = "<tr><th align=\"center\">Villain Name</th><th align=\"center\">Description</th></tr>";
		new BaseSiteBuilderHelper(folder, "Villains", newURL, header, allData)
				.buildHTML();

		scrubber.writeDataToFiles(fileLocationAndName, allData);
	}

	public static void main(String[] args) {
		new VillainsSiteBuilder().buildSite();
	}

}
