package com.anemortalkid.reddit.parser.sitebuilder.villains;

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
		List<ScrubbedDataObject> data = scrubber.scrubData();

		System.out.println("Villains scrubbed: " + data.size());

		String fileLocationAndName = "src/main/resources/villains";
		scrubber.writeDataToFiles(fileLocationAndName, data);

		String folder = fileLocationAndName + "/";

		String header = "<tr><th align=\"center\">Villain Name</th><th align=\"center\">Description</th></tr>";
		new BaseSiteBuilderHelper(folder, "Villains", redditURL, header, data)
				.buildHTML();

		scrubber.writeDataToFiles(fileLocationAndName, data);
	}

	public static void main(String[] args) {
		new VillainsSiteBuilder().buildSite();
	}

}
