package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import model.LogFile;

import com.google.gson.Gson;

import logic.Identifier;

/**
 * This file reader get's the latest log file, reads it and deletes it
 * afterwards.
 * 
 * @author lucas.schlemm
 *
 */
public class LogFileReader {

	Identifier identifier;

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
	public void readFiles(String path) {

		File file = new File(path);

		Gson gson = new Gson();

		try {
			if (file.exists()) {
				BufferedReader br = null;

				br = new BufferedReader(new FileReader(file));

				System.out.println("Reading JSON from a file: " + file);
				System.out.println("----------------------------");

				// convert the json string back to object
				LogFile logFile = gson.fromJson(br, LogFile.class);

				System.out.println("a1: " + logFile.getA1());
				System.out.println("a2: " + logFile.getA2());
				System.out.println("b1: " + logFile.getB1());
				System.out.println("b2: " + logFile.getB2());
				System.out.println("em1: " + logFile.getEm1());
				System.out.println("em2: " + logFile.getEm2());
				System.out.println("overallStatus: "
						+ logFile.getOverallStatus());
				System.out.println("ts_start: " + logFile.getTs_start());
				System.out.println("ts_stop: " + logFile.getTs_stop());

				System.out
						.println("\nReading of the log file was successfull \n");
				// Closing of the BufferedReader
				Identifier id = Identifier.getInstance();
				id.finishProduct(logFile);
				br.close();
			} else {
				System.out.println("No log files to read from. \n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

//		if (file.delete()) {
//			System.out.println("Datei erfolgreich gel�scht. \n");
//			System.out.println();
//		} else {
//			System.out
//					.println("nicht erfolgreich gel�scht. Die kleine Hure...");
//		}

	}

	public void setIdentifier(Identifier identifier) {
		if (identifier == null) {
			this.identifier = identifier;
		}
	}
}
