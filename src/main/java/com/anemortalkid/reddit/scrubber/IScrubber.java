package com.anemortalkid.reddit.scrubber;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.anemortalkid.reddit.parser.dataobjects.ScrubbedDataObject;

public interface IScrubber {

	List<ScrubbedDataObject> scrubDataFromUrl(String url);

	default public void writeDataToFiles(String fileLocationAndName,
			List<ScrubbedDataObject> data) {
		File outFile_TXT = new File(fileLocationAndName + ".txt");
		File outFile_CSV = new File(fileLocationAndName + ".csv");
		File outFile_table = new File(fileLocationAndName + "-tabledata.txt");

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

		try {
			PrintWriter textWritter = new PrintWriter(outFile_TXT);
			PrintWriter csvWritter = new PrintWriter(outFile_CSV);
			PrintWriter tableWritter = new PrintWriter(outFile_table);

			for (ScrubbedDataObject dataObject : data) {
				System.out.println("DataName: "
						+ dataObject.getDataIdentifier());
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
