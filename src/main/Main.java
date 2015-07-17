package main;

import marshalling.DemoMarshalling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ui.MainWindow;
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

		// Message Queue Connection using JMS
		QueueConnection q = new QueueConnection();

		// Message Queue Connection using JMS via Apache Camel
		QueueConnectionUsingCamel qc = new QueueConnectionUsingCamel();
		qc.run();

		// State Machine
		// TestMachine t = new TestMachine();
		// t.run();

		// Esper test
		// EsperTest esperTest = new EsperTest();
		// esperTest.run();

		// TODO @Mattes Es ist möglich, einen Kommandozeilenbefehl aus Java
		// heraus zu starten. Das wäre ganz cool, wenn wir nur das Java-Programm
		// öffnen müssten und dann dort erst alles einstellen können und dann
		// die Simulation auch von dort aus starten können. :)

		new MainWindow();
	}
}
