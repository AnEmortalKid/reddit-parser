package com.anemortalkid.reddit.parser.sitebuilder;

import java.util.List;

import com.anemortalkid.reddit.parser.dataobjects.DataObject;
import com.anemortalkid.reddit.parser.mysteries.Scrub10KMysteries;
import com.anemortalkid.reddit.parser.npcs.Scrub10KNPCS;

public class MysteriesSiteBuilder {

	public static void main(String[] args) {
		Scrub10KMysteries mysteries = new Scrub10KMysteries();
		List<DataObject> dataPoints = mysteries.getDataPoints();
		String header = "<tr><th>Mystery Name</th><th>Mystery Description</th></tr>";
		String indexLocation = "src/main/resources/mysteries/";
		new SiteBuilder(indexLocation, "NPCs", Scrub10KMysteries.REDDIT_URL,
				header, dataPoints).buildHTML();
	}
}
