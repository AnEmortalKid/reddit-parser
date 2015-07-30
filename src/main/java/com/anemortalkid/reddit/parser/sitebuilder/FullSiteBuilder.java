package com.anemortalkid.reddit.parser.sitebuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains a list of all the sites and lets me run them all at once instead of
 * having to run each at a time
 * 
 * @author JMonterrubio
 *
 */
public class FullSiteBuilder {

	public static void main(String[] args) {
		List<ISiteBuilder> siteBuilders = new ArrayList<ISiteBuilder>();
		siteBuilders.add(new NPCSiteBuilder());
		siteBuilders.add(new MysteriesSiteBuilder());
		siteBuilders.add(new LocationsSiteBuilder());
		siteBuilders.forEach(x -> x.buildSite());
	}
}
