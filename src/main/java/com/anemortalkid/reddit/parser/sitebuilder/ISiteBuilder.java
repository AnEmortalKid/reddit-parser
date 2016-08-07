package com.anemortalkid.reddit.parser.sitebuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;
import com.google.gson.Gson;

/**
 * An {@link ISiteBuilder} can build an index.html file for a specific reddit
 * url
 * 
 * @author JMonterrubio
 *
 */
public interface ISiteBuilder<T> {

	static String OUTPUT_DIR_PROPERTY_NAME = "output.dir";
	static String OUTPUT_DIR = System.getProperty(OUTPUT_DIR_PROPERTY_NAME);
	static String BASE_RESOURCES = OUTPUT_DIR == null ? "src/main/resources/" : OUTPUT_DIR;

	/**
	 * Scrubs data and stores it in the site builder
	 */
	void scrubData();

	/**
	 * @return
	 */
	int scrubbedCount();

	/**
	 * @return
	 */
	String getTitle();

	String getRedditURL();

	String getTableHeader();

	/**
	 * @return a list of {@link ScrubbedDataObject} for the results of scrubbing
	 *         data for a site.
	 */
	List<ScrubbedDataObject> getScrubbedData();

	/**
	 * @return a List of Json objects
	 */
	default List<T> getJsonData() {
		return getScrubbedData().stream().map(getJsonFactory()).collect(Collectors.toList());
	}

	/**
	 * @return a function that can create a Json representation for this site
	 *         based on a ScrubbedDataObject
	 */
	Function<ScrubbedDataObject, T> getJsonFactory();

	default void buildSite() {
		System.out.println("Building " + getTitle());
		if (scrubbedCount() == -1) {
			scrubData();
		}
		List<ScrubbedDataObject> data = getScrubbedData();

		String title = getTitle().replace(" ", "").toLowerCase();
		String rootDirectory = BASE_RESOURCES + title + "/";
		File directory = new File(rootDirectory);
		if (!directory.exists()) {
			System.out.println("Making dir: " + directory);
			directory.mkdirs();
		}

		writeDataToFiles(rootDirectory, data);

		List<T> jsonData = getJsonData();
		if (jsonData != null) {
			writeJsonDataToFiles(rootDirectory + "data", jsonData);
		}
		new BaseSiteBuilderHelper(rootDirectory, getTitle(), getRedditURL(), getTableHeader(), data).buildHTML();
	}

	default void writeJsonDataToFiles(String fileLocationAndName, List<T> jsonData) {
		File jsonFile = new File(fileLocationAndName + ".json");
		if (!jsonFile.exists()) {
			try {
				jsonFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try (FileOutputStream fos = new FileOutputStream(jsonFile)) {
			System.out.println("Writing json file: " + fileLocationAndName + ".json");
			Gson gson = new Gson();
			String jsonText = gson.toJson(jsonData);
			fos.write(jsonText.getBytes());
			fos.flush();
		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	default void writeDataToFiles(String directory, List<ScrubbedDataObject> data) {
		File outFile_TXT = new File(directory + "data.txt");
		File outFile_CSV = new File(directory + "data.csv");
		File outFile_table = new File(directory + "tabledata.txt");

		int dataWritten = 0;
		if (!outFile_TXT.exists()) {
			try {
				outFile_TXT.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (!outFile_CSV.exists()) {
			try {
				outFile_CSV.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (!outFile_table.exists()) {
			try {
				outFile_table.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try (PrintWriter textWritter = new PrintWriter(outFile_TXT);
				PrintWriter csvWritter = new PrintWriter(outFile_CSV);
				PrintWriter tableWritter = new PrintWriter(outFile_table);) {
			for (ScrubbedDataObject dataObject : data) {
				System.out.println("DataName: " + dataObject.getDataIdentifier());
				dataWritten++;

				textWritter.println(dataObject.toGoogleSpreadsheet());
				csvWritter.println(dataObject.toCSV());
				tableWritter.println(dataObject.toHTMLTableRow());

				textWritter.flush();
				csvWritter.flush();
				tableWritter.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Wrote " + dataWritten + " data");
	}
}
