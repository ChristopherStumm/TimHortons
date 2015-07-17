package utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LogFileReader {

	private static LogFileReader instance = null;

	protected LogFileReader() {
		// Exists only to defeat instantiation.
	}

	public static LogFileReader getInstance() {
		if (instance == null) {
			instance = new LogFileReader();
		}
		return instance;
	}

	public void readLatestFile() {
		// TODO Dynamisch den Ordnerpfad wählen.

		String path = "C:/Users/Lucas.Schlemm/Desktop/Logs";
		File[] files = readFiles(path);

		FileReader reader = null;

		try {
			reader = new FileReader(path + "/" +  files[0].getName());
			final JSONParser parser = new JSONParser();
			final JSONObject json = (JSONObject) parser.parse(reader);

			final String overallStatus = (String) json.get("overallStatus");
			System.out.println("overallStatus: " + overallStatus);

			final String ts_start = (String) json.get("ts_start");
			System.out.println("ts_start: " + ts_start);

			final String ts_stop = (String) json.get("ts_stop");
			System.out.println("ts_stop: " + ts_stop);

		} catch (IOException | ParseException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private File[] readFiles(String path)
	{
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				System.out.println("File " + listOfFiles[i].getName());
			} else if (listOfFiles[i].isDirectory()) {
				System.out.println("Directory " + listOfFiles[i].getName());
			}
		}
		return listOfFiles;
	}
}
