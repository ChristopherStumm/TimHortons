package utils;

import java.io.File;
import java.util.ArrayList;

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
		File folder = new File("C:/Users/Lucas.Schlemm/Desktop/Logs");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				System.out.println("File " + listOfFiles[i].getName());
			} else if (listOfFiles[i].isDirectory()) {
				System.out.println("Directory " + listOfFiles[i].getName());
			}
		}
		
		// Datei wird gelöscht
		if(listOfFiles[0].delete()){
			System.out.println(listOfFiles[0].getName() + " is deleted!");
		}else{
			System.out.println("Delete operation is failed.");
		}
	}
}
