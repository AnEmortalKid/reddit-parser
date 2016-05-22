package com.anemortalkid.reddit.parser.sitebuilder.npcs;

import java.util.List;

import com.anemortalkid.reddit.parser.sitebuilder.BaseSiteBuilderHelper;
import com.anemortalkid.reddit.parser.sitebuilder.ISiteBuilder;
import com.anemortalkid.reddit.scrubber.StrongEmphasisParagraphScrubber;
import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class NPCSiteBuilder implements ISiteBuilder {

	private static String[] urls = {
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/3er483/lets_make_10000_npcs/",
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/4ip300/10k_npcs_crusaders_and_exorcists/" };

	@Override
	public void buildSite() {
		String header = "<tr><th>Name</th><th align=\"center\">Gender Race Occupation</th><th align=\"center\">Description</th></tr>";

		// get scrubber and write to file
		StrongEmphasisParagraphScrubber seps = new StrongEmphasisParagraphScrubber(urls);
		List<ScrubbedDataObject> data = seps.scrubData();
		System.out.println("NPCs scrubbed: " + data.size());

		String fileLocationAndName = "src/main/resources/npcs";
		seps.writeDataToFiles(fileLocationAndName, data);

		String folder = fileLocationAndName + "/";

		new BaseSiteBuilderHelper(folder, "NPCs", urls[urls.length - 1], header, data).buildHTML();
	}

	public static void main(String[] args) {
		new NPCSiteBuilder().buildSite();
	}
}
