package com.anemortalkid.reddit.parser.sitebuilder;

import java.util.List;

import com.anemortalkid.reddit.parser.dataobjects.DataObject;
import com.anemortalkid.reddit.parser.npcs.Scrub10KNPCS;

public class NPCSiteBuilder {

	public static void main(String[] args) {
		Scrub10KNPCS npcs = new Scrub10KNPCS();
		List<DataObject> dataPoints = npcs.getDataPoints();
		String header = "<tr><th>Name</th><th>Gender Race Occupation</th><th>Description</th></tr>";
		String indexLocation = "src/main/resources/npcs/";
		new SiteBuilder(indexLocation, "NPCs", Scrub10KNPCS.REDDIT_URL, header,
				dataPoints).buildHTML();
	}
}
