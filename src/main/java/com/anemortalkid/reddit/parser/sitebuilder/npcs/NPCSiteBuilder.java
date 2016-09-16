package com.anemortalkid.reddit.parser.sitebuilder.npcs;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import com.anemortalkid.reddit.parser.sitebuilder.ISiteBuilder;
import com.anemortalkid.reddit.scrubber.StrongEmphasisParagraphScrubber;
import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class NPCSiteBuilder implements ISiteBuilder<NPCData> {

	private static String[] urls = {
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/3er483/lets_make_10000_npcs/",
			"https://www.reddit.com/r/DnDBehindTheScreen/comments/4ip300/10k_npcs_crusaders_and_exorcists/",

			"https://www.reddit.com/r/DnDBehindTheScreen/comments/4odi6q/10k_npcs_witches_and_seers/" };

	private static String header = "<tr><th>Name</th><th align=\"center\">Gender Race Occupation</th><th align=\"center\">Description</th></tr>";

	private List<ScrubbedDataObject> data;
	private StrongEmphasisParagraphScrubber scrubber;

	public NPCSiteBuilder() {
		scrubber = new StrongEmphasisParagraphScrubber(urls);
	}

	public NPCSiteBuilder(List<String> newURLs) {
		Collections.reverse(newURLs);
		scrubber = new StrongEmphasisParagraphScrubber(newURLs.toArray(new String[newURLs.size()]));
	}

	@Override
	public String getTitle() {
		return "NPCs";
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
	public Function<ScrubbedDataObject, NPCData> getJsonFactory() {
		return NPCData::createFromScrubbedObject;
	}

	public static void main(String[] args) {
		new NPCSiteBuilder().buildSite();
	}
}
