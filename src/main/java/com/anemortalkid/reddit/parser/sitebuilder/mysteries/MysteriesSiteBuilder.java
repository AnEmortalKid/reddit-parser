package com.anemortalkid.reddit.parser.sitebuilder.mysteries;

import java.util.List;

import com.anemortalkid.reddit.parser.sitebuilder.BaseSiteBuilderHelper;
import com.anemortalkid.reddit.parser.sitebuilder.ISiteBuilder;
import com.anemortalkid.reddit.scrubber.StrongParagraphScrubber;
import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class MysteriesSiteBuilder implements ISiteBuilder {

	private static String[] urls = { //
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/3evxgl/lets_make_10000_mysteries/",
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/4ijjbz/10k_mysteries_the_supernatural_and_the_strange/" };

	@Override
	public void buildSite() {
		StrongParagraphScrubber scrubber = new StrongParagraphScrubber(urls);
		List<ScrubbedDataObject> data = scrubber.scrubData();
		System.out.println("Mysteries scrubbed: " + data.size());

		String fileLocationAndName = "src/main/resources/mysteries";
		scrubber.writeDataToFiles(fileLocationAndName, data);

		String header = "<tr><th align=\"center\">Mystery Name</th><th align=\"center\">Mystery Description</th></tr>";
		String indexLocation = "src/main/resources/mysteries/";
		new BaseSiteBuilderHelper(indexLocation, "Mysteries", urls[urls.length - 1], header, data).buildHTML();
	}

	public static void main(String[] args) {
		new MysteriesSiteBuilder().buildSite();
	}
}
