package main;
import java.io.*;
import java.util.*;

public class StartSimulator {
	private static final String JAVA_CMD = "java";
	private static final String JAR = "-jar";
	private static final String DELAY = "-d 1000";
	private static final String Q = "-q";
	private static final String O = "/tmp";

	private static final String CLASS_PATH = "/Users/christian/git/TimHortons/I4Simulation";
	private static final String PROG = "DataAggregator-jar-with-dependencies.jar";
	
	
  public static void main(String args[]) 
     throws InterruptedException,IOException 
  {
	
    ProcessBuilder builder = new ProcessBuilder(JAVA_CMD, "-Dlog4j.configuration=file:classes/log4j.properties", JAR, CLASS_PATH, PROG, DELAY, O, Q);
    Process process = builder.start();
    
    InputStream is = process.getInputStream();
    InputStreamReader isr = new InputStreamReader(is);
    BufferedReader br = new BufferedReader(isr);
    String line;
    while ((line = br.readLine()) != null) {
      System.out.println(line);
    }
   
  }
}