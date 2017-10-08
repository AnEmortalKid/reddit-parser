package com.anemortalkid.sitebuilder.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;

public class JsonUtils {

	public static <T> void writeJsonDataToFiles(String fileLocationAndName, List<T> jsonData) {
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

}
