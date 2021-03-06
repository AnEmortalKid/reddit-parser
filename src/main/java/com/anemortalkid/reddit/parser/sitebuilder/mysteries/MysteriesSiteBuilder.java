package com.anemortalkid.reddit.parser.sitebuilder.mysteries;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import com.anemortalkid.reddit.parser.sitebuilder.SiteBuilder;
import com.anemortalkid.reddit.scrubber.StrongParagraphScrubber;
import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class MysteriesSiteBuilder implements SiteBuilder<MysteryData> {

	private static String[] urls = { //
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/3evxgl/lets_make_10000_mysteries/",
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/4ijjbz/10k_mysteries_the_supernatural_and_the_strange/",
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/4nws6u/10k_mysteries_the_ancient_and_the_arcane/",
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/4st6t2/10k_mysteries_old_war_stories/" };

	private static String header = "<tr><th align=\"center\">Mystery Name</th><th align=\"center\">Mystery Description</th></tr>";

	private StrongParagraphScrubber scrubber;
	private List<ScrubbedDataObject> data;

	public MysteriesSiteBuilder() {
		scrubber = new StrongParagraphScrubber(urls);
	}

	public MysteriesSiteBuilder(List<String> newURLs) {
		Collections.reverse(newURLs);
		scrubber = new StrongParagraphScrubber(newURLs.toArray(new String[newURLs.size()]));
	}

	@Override
	public String getTitle() {
		return "Mysteries";
	}

	@Override
	public String getTableHeader() {
		return header;
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
	public Function<ScrubbedDataObject, MysteryData> getJsonFactory() {
		return MysteryData::createFromScrubbedObject;
	}

	public static void main(String[] args) {
		new MysteriesSiteBuilder().buildSite();
	}
}
