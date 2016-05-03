package com.anemortalkid.reddit.parser.sitebuilder;

/**
 * An {@link ISiteBuilder} can build an index.html file for a specific reddit
 * url
 * 
 * @author JMonterrubio
 *
 */
public interface ISiteBuilder {

	/**
	 * Builds the index.html file and stores it in a desired location
	 */
	void buildSite();
}
