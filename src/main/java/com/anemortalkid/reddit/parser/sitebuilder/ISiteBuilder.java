package com.anemortalkid.reddit.parser.sitebuilder;

import com.anemortalkid.reddit.scrubber.IScrubber;

public interface ISiteBuilder {

	void buildSite();

	default void buildSiteWithScrubber(IScrubber scrubber) {
		// nothing to avoid comp errors
	}
}
