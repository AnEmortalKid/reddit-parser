package com.anemortalkid.reddit.scrubber;

import java.util.List;

import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

/**
 * An {@link Scrubber} is capable of scrubbing data from a particular URL,
 * based on the data's format
 * 
 * @author JMonterrubio
 *
 */
public interface Scrubber {

	/**
	 * Tells the {@link Scrubber} to scrub the data from the stored url and
	 * return the scrubbed objects
	 * 
	 * @return a List of {@link ScrubbedDataObject} with all the data that was
	 *         scrubbed from the page
	 */
	List<ScrubbedDataObject> scrubData();
}
