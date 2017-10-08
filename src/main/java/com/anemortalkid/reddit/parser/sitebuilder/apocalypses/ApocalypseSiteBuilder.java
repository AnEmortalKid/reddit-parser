package com.anemortalkid.reddit.parser.sitebuilder.apocalypses;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import com.anemortalkid.reddit.parser.sitebuilder.SiteBuilder;
import com.anemortalkid.reddit.scrubber.StrongParagraphEntryScrubber;
import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class ApocalypseSiteBuilder implements SiteBuilder<ApocalypseData> {

	private static String QUERY_URL = "https://www.reddit.com/r/DnDBehindTheScreen/search?q=flair%3A%27Event%27+and+title%3A%27Apocalypses%27&restrict_sr=on&sort=new&t=all";
	
	private static String tableHeaderHTML = "<tr><th align=\"center\">Apocalypse Name</th><th align=\"center\">Apocalypse Description</th></tr>";
	
	private static String[] ignoreHeaders = {
			"It takes a moment for your eyes to adjust to the flickering torchlight, the chamber walls are not at all what you had been expecting", };
	
	private StrongParagraphEntryScrubber scrubber;
	private List<ScrubbedDataObject> data;
	
	public ApocalypseSiteBuilder(List<String> newURLs) {

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
		return "Apocalypses";
	}

	@Override
	public String getTableHeader() {
		return tableHeaderHTML;
	}

	@Override
	public Function<ScrubbedDataObject, ApocalypseData> getJsonFactory() {
		return ApocalypseData::createFromScrubbedObject;
	}

	@Override
	public String getRedditURL() {
		return QUERY_URL;
	}
	
}
