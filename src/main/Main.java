package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import marshalling.DemoMarshalling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import ui.MainWindow;
import activemq.QueueConnection;
import activemq.QueueConnectionUsingCamel;
import esper.EsperTest;

/**
 * Just comment out the code pieces you want.
 * 
 * @author julian
 *
 */
public class Main {

	Logger _log = LogManager.getLogger(Main.class);

	public static void main(String[] args) {

		// JAXB Marshalling / Unmarshalling
		// DemoMarshalling demo = new DemoMarshalling();
		// demo.run();
		

	/*	ProcessBuilder build = new ProcessBuilder("java", "-Dlog4j.configuration=file:C:/Users/stumm/Desktop/Toronto Project/Again/I4Simulation/classes","../I4Simulation/DataAggregator.jar","-jar","-d","100","-o","/output","-q");
		try {
			build.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

*/		// Message Queue Connection using JMS
		//QueueConnection q = new QueueConnection();

		// Message Queue Connection using JMS via Apache Camel
		QueueConnectionUsingCamel qc = new QueueConnectionUsingCamel();
		qc.run();

		// State Machine
		// TestMachine t = new TestMachine();
		// t.run();

		// Esper test
		// EsperTest esperTest = new EsperTest();
		// esperTest.run();

		// TODO @Mattes Es ist m�glich, einen Kommandozeilenbefehl aus Java
		// heraus zu starten. Das w�re ganz cool, wenn wir nur das
		// Java-Programm
		// �ffnen m�ssten und dann dort erst alles einstellen k�nnen und
		// dann
		// die Simulation auch von dort aus starten k�nnen. :)

		// new MainWindow();
	}
}
