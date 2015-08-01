package com.anemortalkid.reddit.parser.sitebuilder.plothooks;

import java.util.List;

import com.anemortalkid.reddit.parser.sitebuilder.BaseSiteBuilderHelper;
import com.anemortalkid.reddit.parser.sitebuilder.ISiteBuilder;
import com.anemortalkid.reddit.scrubber.StrongParagraphScrubber;
import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class PlothooksSiteBuilder implements ISiteBuilder {

	@Override
	public void buildSite() {
		String redditURL = "https://www.reddit.com/r/DnDBehindTheScreen/comments/3fe4x1/lets_make_10000_plot_hooks/";

		StrongParagraphScrubber scrubber = new StrongParagraphScrubber(
				redditURL);
		List<ScrubbedDataObject> data = scrubber.scrubData();
		System.out.println("Scrubbed plothooks: " + data.size());

		String fileNameAndLocation = "src/main/resources/plothooks";
		scrubber.writeDataToFiles(fileNameAndLocation, data);

		String tableHeaderHTML = "<tr><th>Plot Hook Name</th><th>Description</th></tr>";
		BaseSiteBuilderHelper baseSiteBuilderHelper = new BaseSiteBuilderHelper(
				fileNameAndLocation + "/", "Plot Hooks", redditURL,
				tableHeaderHTML, data);
		baseSiteBuilderHelper.buildHTML();
	}

	public static void main(String[] args) {
		new PlothooksSiteBuilder().buildSite();
	}

}
