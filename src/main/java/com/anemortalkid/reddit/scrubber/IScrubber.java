package com.anemortalkid.reddit.scrubber;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

/**
 * An {@link IScrubber} is capable of scrubbing data from a particular URL,
 * based on the data's format
 * 
 * @author JMonterrubio
 *
 */
public interface IScrubber {

	/**
	 * Tells the {@link IScrubber} to scrub the data from the stored url and
	 * return the scrubbed objects
	 * 
	 * @return a List of {@link ScrubbedDataObject} with all the data that was
	 *         scrubbed from the page
	 */
	List<ScrubbedDataObject> scrubData();
}
