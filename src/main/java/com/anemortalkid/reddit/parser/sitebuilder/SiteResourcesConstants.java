package com.anemortalkid.reddit.parser.sitebuilder;

/**
 * Lists all the constants for files coming from
 * src/main/resources/site_resources/
 * 
 * @author JMonterrubio
 *
 */
public interface SiteResourcesConstants {

	/**
	 * Root Folder of every site resource file
	 */
	static final String PATH = "src/main/resources/site_resources/";

	/**
	 * <pre>
	 * &lt;div class="progress"&gt;
	 * &lt;div class="progress-bar progress-bar-info progress-bar-striped" role="progressbar" aria-valuenow="{0}" aria-valuemin="0" aria-valuemax="10000" style="width: {1}%"&gt;
	 * &lt;span class="sr-only"&gt;{1}% Complete&lt;/span&gt;
	 * &lt;/div&gt;
	 * &lt;/div&gt;
	 * </pre>
	 * 
	 * <pre>
	 * Fill Values:
	 * 0- aria-valuenow (count of records) 
	 * 1- progress percent (record percent out of 10,000)
	 * </pre>
	 */
	static final String PROGRESS_BAR = PATH + "progressbar.txt";

	/**
	 * <pre>
	 * &lt;input type="text" id="search" placeholder="Type to search" /&gt;
	 * &lt;label&gt;&lt;input type="checkbox" id="casesensitive" onClick="checkFilter()" checked&gt;Case Sensitive Search&lt;/label&gt;
	 * </pre>
	 */
	static final String INPUTS = PATH + "inputelements.txt";

	/**
	 * Contains code for the caseSensitive, caseInsensitive, checkFilter
	 * functions as well as the function on keyUp for the search text field.
	 */
	static final String END_JAVASCRIPT_FUNCTIONS = PATH
			+ "javascriptfunctions.txt";

}
