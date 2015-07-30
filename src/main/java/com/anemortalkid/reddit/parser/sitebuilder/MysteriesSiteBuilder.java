package com.anemortalkid.reddit.parser.sitebuilder;

import java.util.List;

import com.anemortalkid.reddit.parser.dataobjects.ScrubbedDataObject;
import com.anemortalkid.reddit.parser.mysteries.Scrub10KMysteries;
import com.anemortalkid.reddit.parser.npcs.Scrub10KNPCS;

public class MysteriesSiteBuilder implements ISiteBuilder {

	public static void main(String[] args) {
		new MysteriesSiteBuilder().buildSite();
	}

	@Override
	public void buildSite() {
		Scrub10KMysteries mysteries = new Scrub10KMysteries();
		List<ScrubbedDataObject> dataPoints = mysteries.getDataPoints();
		String header = "<tr><th>Mystery Name</th><th>Mystery Description</th></tr>";
		String indexLocation = "src/main/resources/mysteries/";
		new SiteBuilder(indexLocation, "NPCs", Scrub10KMysteries.REDDIT_URL,
				header, dataPoints).buildHTML();
	}
}
