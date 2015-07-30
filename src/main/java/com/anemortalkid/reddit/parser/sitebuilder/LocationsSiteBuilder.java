package com.anemortalkid.reddit.parser.sitebuilder;

import java.util.List;

import com.anemortalkid.reddit.parser.dataobjects.DataObject;
import com.anemortalkid.reddit.parser.locations.Scrub10KLocations;
import com.anemortalkid.reddit.parser.npcs.Scrub10KNPCS;

public class LocationsSiteBuilder {

	public static void main(String[] args) {
		Scrub10KLocations locations = new Scrub10KLocations();
		List<DataObject> dataPoints = locations.getDataPoints();
		String header = "<tr><th>Name</th><th>Type</th><th>Description</th></tr>";
		String indexLocation = "src/main/resources/locations/";
		new SiteBuilder(indexLocation, "Locations",
				Scrub10KLocations.REDDIT_URL, header, dataPoints).buildHTML();
	}
}
