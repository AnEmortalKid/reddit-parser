package com.anemortalkid.reddit.parser.sitebuilder.plothooks;

import java.util.List;

import com.anemortalkid.reddit.parser.sitebuilder.BaseSiteBuilderHelper;
import com.anemortalkid.reddit.parser.sitebuilder.ISiteBuilder;
import com.anemortalkid.reddit.scrubber.StrongParagraphScrubber;
import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class PlothooksSiteBuilder implements ISiteBuilder {

	private static String[] urls = {//
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/3fe4x1/lets_make_10000_plot_hooks/",
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/4hhfzr/10k_plot_hooks_resurrections_and_chosen_ones/",
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/4jwlfc/10k_plot_hooks_betrayals_and_doublecrosses/"
	};
		
	@Override
	public void buildSite() {

		StrongParagraphScrubber scrubber = new StrongParagraphScrubber(urls);
		List<ScrubbedDataObject> data = scrubber.scrubData();
		System.out.println("Scrubbed plothooks: " + data.size());

		String fileNameAndLocation = "src/main/resources/plothooks";
		scrubber.writeDataToFiles(fileNameAndLocation, data);

		String tableHeaderHTML = "<tr><th align=\"center\">Plot Hook Name</th><th align=\"center\">Description</th></tr>";
		BaseSiteBuilderHelper baseSiteBuilderHelper = new BaseSiteBuilderHelper(fileNameAndLocation + "/", "Plot Hooks",
				urls[urls.length-1], tableHeaderHTML, data);
		baseSiteBuilderHelper.buildHTML();
	}

	public static void main(String[] args) {
		new PlothooksSiteBuilder().buildSite();
	}

}
