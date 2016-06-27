package com.anemortalkid;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
		return new File(filePath);
	}

}
