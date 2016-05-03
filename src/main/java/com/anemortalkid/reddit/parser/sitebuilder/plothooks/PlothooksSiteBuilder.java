package com.anemortalkid.reddit.parser.sitebuilder.plothooks;

import java.util.ArrayList;
import java.util.List;

import com.anemortalkid.reddit.parser.sitebuilder.BaseSiteBuilderHelper;
import com.anemortalkid.reddit.parser.sitebuilder.ISiteBuilder;
import com.anemortalkid.reddit.scrubber.StrongParagraphScrubber;
import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class PlothooksSiteBuilder implements ISiteBuilder {

	private static final String OLD_URL = "https://www.reddit.com/r/DnDBehindTheScreen/comments/3fe4x1/lets_make_10000_plot_hooks/";

	private static final String NEW_URL = "https://www.reddit.com/r/DnDBehindTheScreen/comments/4hhfzr/10k_plot_hooks_resurrections_and_chosen_ones/";

	@Override
	public void buildSite() {

		StrongParagraphScrubber oldDataScrubber = new StrongParagraphScrubber(OLD_URL);
		List<ScrubbedDataObject> data = oldDataScrubber.scrubData();
		System.out.println("Old scrubbed plothooks: " + data.size());

		StrongParagraphScrubber newScrubber = new StrongParagraphScrubber(NEW_URL);
		List<ScrubbedDataObject> newData = newScrubber.scrubData();
		System.out.println("New plot hooks: " + newData.size());

		String fileNameAndLocation = "src/main/resources/plothooks";
		List<ScrubbedDataObject> allData = new ArrayList<>(data);
		allData.addAll(newData);

		oldDataScrubber.writeDataToFiles(fileNameAndLocation, allData);

		String tableHeaderHTML = "<tr><th align=\"center\">Plot Hook Name</th><th align=\"center\">Description</th></tr>";
		BaseSiteBuilderHelper baseSiteBuilderHelper = new BaseSiteBuilderHelper(fileNameAndLocation + "/", "Plot Hooks",
				NEW_URL, tableHeaderHTML, allData);
		baseSiteBuilderHelper.buildHTML();
	}

	public static void main(String[] args) {
		new PlothooksSiteBuilder().buildSite();
	}

}
