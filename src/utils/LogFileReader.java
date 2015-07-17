package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import model.LogFile;

import com.google.gson.Gson;

/**
 * This file reader get's the latest log file, reads it and deletes it
 * afterwards.
 * 
 * @author lucas.schlemm
 *
 */
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

	/**
	 * Gets a list of the files in the log folder. Reads the first file of the
	 * list and deletes it afterwards.
	 */
	public void readLatestFile() {

		// TODO @Mattes Dynamisch den Ordnerpfad wählen.
		
		String path = "C:/Users/Lucas.Schlemm/Desktop/Logs";
		File[] files = readFiles(path);

		Gson gson = new Gson();

		try {
			if (files[0].exists()) {
				System.out.println("Reading JSON from a file");
				System.out.println("----------------------------");

				BufferedReader br = new BufferedReader(new FileReader(files[0]));

				// convert the json string back to object
				LogFile logFile = gson.fromJson(br, LogFile.class);

				System.out.println("a1: " + logFile.getA1());
				System.out.println("a2: " + logFile.getA2());
				System.out.println("b1: " + logFile.getB1());
				System.out.println("b2: " + logFile.getB2());
				System.out.println("em1: " + logFile.getEm1());
				System.out.println("em2: " + logFile.getEm2());
				System.out.println("overallStatus: " + logFile.getOverallStatus());
				System.out.println("ts_start: " + logFile.getTs_start());
				System.out.println("ts_stop: " + logFile.getTs_stop());

				// Closing of the BufferedReader
				br.close();
			}
			else
			{
				System.out.println("No log files to read from.");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		// Deleting the read file.
		if (files[0].delete()) {
			System.out.println("Datei erfolgreich gelöscht..");
			System.out.println();
		} else {
			System.out
					.println("nicht erfolgreich gelöscht. Die kleine Hure...");
		}
	}

	private File[] readFiles(String path) {
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
