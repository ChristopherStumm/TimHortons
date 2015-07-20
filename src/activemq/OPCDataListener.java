<<<<<<< HEAD
package activemq;

import java.io.StringReader;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import model.OPCDataItem;
import logic.*;

public class OPCDataListener implements MessageListener {

	private JAXBContext _ctx;
	private Unmarshaller _unmarshaller;
	int counter = 1;
	
	Identifier identifier = new Identifier();
	

	/**
	 * Default Constructor
	 */
	public OPCDataListener() {
		try {
			_ctx = JAXBContext.newInstance(OPCDataItem.class);
			_unmarshaller = _ctx.createUnmarshaller();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onMessage(Message arg0) {
		TextMessage tmpMessage = null;
		if (arg0 instanceof TextMessage) {
			tmpMessage = (TextMessage) arg0;
		} else {
			System.out.println("Unknown format, marshalling aborted.");
			return;
		}

		// Creating ERP-Object
		OPCDataItem tempStatus = null;
		try {
			StringReader reader = new StringReader(tmpMessage.getText());
			tempStatus = (OPCDataItem) _unmarshaller.unmarshal(reader);
			System.out.println("Item: " + tempStatus.getItemName());
			System.out.println("Status: " + tempStatus.getStatus());
			System.out.println("Zeitpunkt der Meldung: "
					+ tempStatus.getTimestamp());
			System.out.println("Wert: " + tempStatus.getValue());
			if (tempStatus.getItemName().contains("Lichtschranke")) 
			{
				//opticalFeedback(tempStatus);
			}
			
				if (tempStatus.getValue() instanceof Boolean){
					System.out.println("ID: " +
							identifier.processEventWithBoolean(tempStatus.getItemName(),
							(boolean) tempStatus.getValue(),
							tempStatus));
				}
				if (tempStatus.getValue() instanceof Integer){
					System.out.println("ID: " +
							identifier.processEventWithoutBoolean
							(tempStatus.getItemName(), tempStatus)
							);
				}
				if (tempStatus.getValue() instanceof Double){
					System.out.println("ID: " +
							identifier.processEventWithoutBoolean
							(tempStatus.getItemName(), tempStatus));
				}
			
			
			System.out.println("-----");
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void opticalFeedback(OPCDataItem tempStatus) {
		String[] tempArray = tempStatus.getItemName().split(" ");
		
	}
	
	public void setIdentifier(Identifier identifier){
		this.identifier = identifier;
	}

}
=======
package activemq;

import java.io.StringReader;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import model.OPCDataItem;
import logic.*;

public class OPCDataListener implements MessageListener {

	private JAXBContext _ctx;
	private Unmarshaller _unmarshaller;

	private int drillHeatCount;
	private int drillSpeedCount;
	private int millHeatCount;
	private int millSpeedCount;

	private double avgDrHeat;
	private double avgDrSpeed;

	private double avgMiHeat;
	private double avgMiSpeed;

	int counter = 1;

	Identifier identifier = new Identifier();

	/**
	 * Default Constructor
	 */
	public OPCDataListener() {
		try {
			_ctx = JAXBContext.newInstance(OPCDataItem.class);
			_unmarshaller = _ctx.createUnmarshaller();

			drillHeatCount = 0;
			drillSpeedCount = 0;
			millHeatCount = 0;
			millSpeedCount = 0;

			avgDrHeat = 0.0;
			avgDrSpeed = 0.0;

			avgMiHeat = 0.0;
			avgMiSpeed = 0.0;

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onMessage(Message arg0) {
		TextMessage tmpMessage = null;
		if (arg0 instanceof TextMessage) {
			tmpMessage = (TextMessage) arg0;
		} else {
			System.out.println("Unknown format, marshalling aborted.");
			return;
		}

		// Creating ERP-Object
		OPCDataItem tempStatus = null;
		try {
			StringReader reader = new StringReader(tmpMessage.getText());
			tempStatus = (OPCDataItem) _unmarshaller.unmarshal(reader);
			opticalFeedback(tempStatus);
			System.out.println("Item: " + tempStatus.getItemName());
			System.out.println("Status: " + tempStatus.getStatus());
			System.out.println("Zeitpunkt der Meldung: "
					+ tempStatus.getTimestamp());
			System.out.println("\tWert: " + tempStatus.getValue());
			System.out.println();
			System.out.println("Wert: " + tempStatus.getValue());
			if (tempStatus.getItemName().contains("Lichtschranke")) {
				// opticalFeedback(tempStatus);
			}

			if (tempStatus.getValue() instanceof Boolean) {
				System.out.println("ID: "
						+ identifier.processEventWithBoolean(
								tempStatus.getItemName(),
								(boolean) tempStatus.getValue()));
			}
			if (tempStatus.getValue() instanceof Integer) {
				System.out.println("ID: "
						+ identifier.processEventWithoutBoolean(tempStatus
								.getItemName()));
			}
			if (tempStatus.getValue() instanceof Double) {
				System.out.println("ID: "
						+ identifier.processEventWithoutBoolean(tempStatus
								.getItemName()));
			}

			System.out.println("-----");
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void opticalFeedback(OPCDataItem update) {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~");
		if (update.getItemName().contains("Lichtschranke")) {
			if (update.getValue().toString().equals("false")) {
				System.out.println("Product enters: " + update.getItemName());
			} else {
				System.out.println("Product leaves: " + update.getItemName());
			}
		} else if (update.getItemName().contains("Milling")
				|| update.getItemName().contains("Drilling")) {
			System.out.println(update.getItemName() + " updated: "
					+ update.getValue().toString());
			double temp;

			// Calculating average Drilling Heat
			if (update.getItemName().contains("Drilling Heat")) {
				temp = (avgDrHeat * drillHeatCount + Double.valueOf(update
						.getValue().toString())) / (drillHeatCount + 1);
				drillHeatCount += 1;
				avgDrHeat = temp;
				System.out.println("Average: " + avgDrHeat);

			} // Calculating average Drilling Speed
			else if (update.getItemName().contains("Drilling Speed")
					&& !update.getValue().toString().equals("0")) {

				temp = (avgDrSpeed * drillSpeedCount + Double.valueOf(update
						.getValue().toString())) / (drillSpeedCount + 1);
				drillSpeedCount += 1;
				avgDrSpeed = temp;
				System.out.println("Average: " + avgDrSpeed);

			}
			// Calculating average Milling Heat
			else if (update.getItemName().contains("Milling Heat")) {

				temp = (avgMiHeat * millHeatCount + Double.valueOf(update
						.getValue().toString())) / (millHeatCount + 1);
				millHeatCount += 1;
				avgMiHeat = temp;
				System.out.println("Average: " + avgMiHeat);

			} else if (update.getItemName().contains("Milling Speed")
					&& !update.getValue().toString().equals("0")) {

				temp = (avgMiSpeed * millSpeedCount + Double.valueOf(update
						.getValue().toString())) / (millSpeedCount + 1);
				millSpeedCount += 1;
				avgMiSpeed = temp;
				System.out.println("Average: " + avgMiSpeed);
			}

		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~");

	}

	public void setIdentifier(Identifier identifier) {
		this.identifier = identifier;
	}

}
>>>>>>> origin/master
