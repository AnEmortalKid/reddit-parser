package com.anemortalkid.reddit.parser.sitebuilder.rooms;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import com.anemortalkid.reddit.parser.sitebuilder.SiteBuilder;
import com.anemortalkid.reddit.scrubber.StrongParagraphEntryScrubber;
import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class RoomSiteBuilder implements SiteBuilder<RoomData> {

	private static final String QUERY_URL = "https://www.reddit.com/r/DnDBehindTheScreen/search?q=flair%3A%2710k+Event%27+and+title%3A%27Room%27&restrict_sr=on&sort=new&t=all";

	private static String tableHeaderHTML = "<tr><th align=\"center\">Room Name</th><th align=\"center\">Room Description</th></tr>";

	private static String[] urls = {
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/4l55a9/addition_to_10k_things_project_lets_make_10000/" };

	private static String[] ignoreHeaders = {
			"It takes a moment for your eyes to adjust to the flickering torchlight, the chamber walls are not at all what you had been expecting", };
	private StrongParagraphEntryScrubber scrubber;
	private List<ScrubbedDataObject> data;

	public RoomSiteBuilder() {
		Set<String> ignores = new HashSet<String>();
		for (String phrase : ignoreHeaders) {
			ignores.add(phrase);
		}
		scrubber = new StrongParagraphEntryScrubber(ignores, urls);
	}

	public RoomSiteBuilder(List<String> newURLs) {

		Set<String> ignores = new HashSet<String>();
		for (String phrase : ignoreHeaders) {
			ignores.add(phrase);
		}
		Collections.reverse(newURLs);
		scrubber = new StrongParagraphEntryScrubber(ignores, newURLs.toArray(new String[newURLs.size()]));
	}

	@Override
	public String getTitle() {
		return "Rooms";
	}

	@Override
	public String getRedditURL() {
		return QUERY_URL;
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
	public void scrubData() {
		data = scrubber.scrubData();
	}

	@Override
	public Function<ScrubbedDataObject, RoomData> getJsonFactory() {
		return RoomData::createFromScrubbedObject;
	}

	@Override
	public int scrubbedCount() {
		return data == null ? -1 : data.size();
	}

	public static void main(String[] args) {
		RoomSiteBuilder builder = new RoomSiteBuilder();
		builder.buildSite();
	}
}
