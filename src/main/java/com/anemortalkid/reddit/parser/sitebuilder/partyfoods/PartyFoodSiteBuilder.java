package com.anemortalkid.reddit.parser.sitebuilder.partyfoods;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import com.anemortalkid.reddit.parser.sitebuilder.SiteBuilder;
import com.anemortalkid.reddit.scrubber.StrongParagraphEntryScrubber;
import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class PartyFoodSiteBuilder implements SiteBuilder<PartyFoodData> {

	private static String QUERY_URL = "https://www.reddit.com/r/DnDBehindTheScreen/search?q=flair%3A%27Event%27+and+title%3A%27Party%20Foods%27&restrict_sr=on&sort=new&t=all";

	private static String tableHeaderHTML = "<tr><th align=\"center\">Party Food Name</th><th align=\"center\">Food Description</th></tr>";

	private static String[] ignoreHeaders = {
			"As you walk through the festival in the square", };

	private StrongParagraphEntryScrubber scrubber;
	private List<ScrubbedDataObject> data;

	public PartyFoodSiteBuilder(List<String> newURLs) {

		Set<String> ignores = new HashSet<String>();
		for (String phrase : ignoreHeaders) {
			ignores.add(phrase);
		}
		Collections.reverse(newURLs);
		scrubber = new StrongParagraphEntryScrubber(ignores, newURLs.toArray(new String[newURLs.size()]));
	}

	@Override
	public List<ScrubbedDataObject> getScrubbedData() {
		return data;
	}

	@Override
	public void scrubData() {
		data = scrubber.scrubData();
	}

	@Override
	public int scrubbedCount() {
		return data == null ? -1 : data.size();
	}

	@Override
	public String getTitle() {
		return "Party Foods";
	}

	@Override
	public String getTableHeader() {
		return tableHeaderHTML;
	}

	@Override
	public Function<ScrubbedDataObject, PartyFoodData> getJsonFactory() {
		return PartyFoodData::createFromScrubbedObject;
	}

	@Override
	public String getRedditURL() {
		return QUERY_URL;
	}

}
