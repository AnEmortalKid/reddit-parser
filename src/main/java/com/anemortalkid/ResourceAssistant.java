package com.anemortalkid;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.anemortalkid.reddit.scrubber.dataobject.ScrubbedDataObject;

public class ResourceAssistant {

	public static final ResourceAssistant INSTANCE = new ResourceAssistant();

	private ResourceAssistant() {
		// nope;
	}

	public List<String> getLines(String fileName) throws IOException, URISyntaxException {

		List<String> allLines = new ArrayList<>();
		File file = getFile(fileName);

		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				allLines.add(scanner.nextLine());
			}
		}

		return allLines;
	}

	public File getFile(String fileName) {
		ClassLoader classLoader = getClass().getClassLoader();
		URL resource = classLoader.getResource(fileName);
		if (resource == null) {
			return null;
		}
		String filePath = resource.getFile();
		File existingFile = new File(filePath);
		if (!existingFile.exists()) {
			System.out.println("File did not exist: " + fileName + ", trying with Paths.get");
			Path path = Paths.get(fileName);
			return path.toFile();
		}

		return existingFile;
	}

	/**
	 * Reads the specified file and returns the data within it inside a string
	 * 
	 * @param fileName
	 *            the file to read from
	 * @return a String with the data for the file, preserving new lines
	 */
	public String getDataFromFile(String fileName) {
		StringBuilder bob = new StringBuilder();

		try {
			List<String> lines = ResourceAssistant.INSTANCE.getLines(fileName);
			lines.forEach(line -> bob.append(line + "\n"));
		} catch (IOException | URISyntaxException e1) {
			e1.printStackTrace();
		}
		return bob.toString();
	}

	public void writeToFile(String fileName, String data) {
		System.out.println("Writing to file " + fileName);
		File file = new File(fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try (PrintWriter textWritter = new PrintWriter(file);) {
				textWritter.write(data);
				textWritter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
