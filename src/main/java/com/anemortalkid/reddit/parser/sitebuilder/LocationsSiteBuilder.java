package com.anemortalkid.reddit.parser.sitebuilder;

import java.util.List;

import com.anemortalkid.reddit.parser.dataobjects.ScrubbedDataObject;
import com.anemortalkid.reddit.parser.locations.Scrub10KLocations;
import com.anemortalkid.reddit.parser.npcs.Scrub10KNPCS;

/**
 * 
 * @author JMonterrubio
 *
 */
public class LocationsSiteBuilder implements ISiteBuilder {

	public static void main(String[] args) {
		new LocationsSiteBuilder().buildSite();
	}

	@Override
	public void buildSite() {
		Scrub10KLocations locations = new Scrub10KLocations();
		List<ScrubbedDataObject> dataPoints = locations.getDataPoints();
		String header = "<tr><th>Name</th><th>Type</th><th>Description</th></tr>";
		String indexLocation = "src/main/resources/locations/";
		SiteBuilder baseBuilder = new SiteBuilder(indexLocation, "Locations",
				Scrub10KLocations.REDDIT_URL, header, dataPoints);
		baseBuilder.buildHTML();
	}
}
