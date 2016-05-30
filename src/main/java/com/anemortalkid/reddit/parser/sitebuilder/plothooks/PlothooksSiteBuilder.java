package com.anemortalkid.reddit.parser.sitebuilder.plothooks;

import java.util.List;

import com.anemortalkid.reddit.parser.sitebuilder.BaseSiteBuilderHelper;
import com.anemortalkid.reddit.parser.sitebuilder.ISiteBuilder;
import com.anemortalkid.reddit.scrubber.StrongParagraphScrubber;
import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class PlothooksSiteBuilder implements ISiteBuilder {

	private static String[] urls = { //
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/3fe4x1/lets_make_10000_plot_hooks/",
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/4hhfzr/10k_plot_hooks_resurrections_and_chosen_ones/",
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/4jwlfc/10k_plot_hooks_betrayals_and_doublecrosses/" };

	private static String tableHeaderHTML = "<tr><th align=\"center\">Plot Hook Name</th><th align=\"center\">Description</th></tr>";

	private StrongParagraphScrubber scrubber;

	private List<ScrubbedDataObject> data;

	public PlothooksSiteBuilder() {
		scrubber = new StrongParagraphScrubber(urls);
	}
	
	@Override
	public String getTitle() {
		return "Plot Hooks";
	}

	@Override
	public String getRedditURL() {
		return urls[urls.length - 1];
	}

	@Override
	public String getTableHeader() {
		return tableHeaderHTML;
	}

	@Override
	public List<ScrubbedDataObject> getScrubbedData() {
		return data;
	}

	@Override
	public int scrubbedCount() {
		return data == null ? -1 : data.size();
	}

	@Override
	public void scrubData() {
		data = scrubber.scrubData();
	}

	public static void main(String[] args) {
		new PlothooksSiteBuilder().buildSite();
	}
}
