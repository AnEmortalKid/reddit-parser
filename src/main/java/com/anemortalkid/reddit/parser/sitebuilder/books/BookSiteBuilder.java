package com.anemortalkid.reddit.parser.sitebuilder.books;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import com.anemortalkid.reddit.parser.sitebuilder.SiteBuilder;
import com.anemortalkid.reddit.scrubber.StrongEmphasisParagraphScrubber;
import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class BookSiteBuilder implements SiteBuilder<BookData> {

	private static String QUERY_URL = "https://www.reddit.com/r/DnDBehindTheScreen/search?q=flair%3A%27Event%27+and+title%3A%27Book%27&restrict_sr=on&sort=new&t=all";

	private static String tableHeaderHTML = "<tr><th align=\"center\">Book Title</th><th align=\"center\">Author</th><th align=\"center\">Information</th></tr>";

	private StrongEmphasisParagraphScrubber scrubber;
	private List<ScrubbedDataObject> data;

	public BookSiteBuilder(List<String> newURLs) {
		Collections.reverse(newURLs);
		scrubber = new StrongEmphasisParagraphScrubber(newURLs.toArray(new String[newURLs.size()]));
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
		return "Books";
	}

	@Override
	public String getTableHeader() {
		return tableHeaderHTML;
	}

	@Override
	public Function<ScrubbedDataObject, BookData> getJsonFactory() {
		return BookData::createFromScrubbedObject;
	}

	@Override
	public String getRedditURL() {
		return QUERY_URL;
	}

}
