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
	static final String PATH = "site_resources/";

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
	 *  &lt;button type="button" onClick="randomRow()"&gt;Random {0}&lt;/button&gt;
	 * </pre>
	 * 
	 * <pre>
	 * Fill Values:
	 * 0 - name of category, singular, ie Villain. For the Random X portion of the button.
	 * </pre>
	 */
	static final String INPUTS = PATH + "inputelements.txt";

	/**
	 * Contains code for the caseSensitive, caseInsensitive, checkFilter
	 * functions as well as the function on keyUp for the search text field.
	 */
	static final String END_JAVASCRIPT_FUNCTIONS = PATH + "javascriptfunctions.txt";

	/**
	 * Contains the link stylesheet declarations for bootstrap
	 */
	static final String BOOT_STRAP_IMPORTS = PATH + "bootstrapimports.txt";

	/**
	 * Contains the script declarations for jquery and jquery.tablesorter
	 */
	static final String JQUERY_IMPORTS = PATH + "jqueryimports.txt";

	/**
	 * A templated index file for the DND 10K Index with the last updated place
	 * holder.
	 */
	static final String DND_INDEX_TEMPLATE = PATH + "dnd-index.html";

}
