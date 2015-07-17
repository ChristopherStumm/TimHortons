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

public class OPCDataListener implements MessageListener {

	private JAXBContext _ctx;
	private Unmarshaller _unmarshaller;

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
			opticalFeedback(tempStatus);
			System.out.println("Details:");
			System.out.println("\tItem: " + tempStatus.getItemName());
			System.out.println("\tStatus: " + tempStatus.getStatus());
			System.out.println("\tZeitpunkt der Meldung: "
					+ tempStatus.getTimestamp());
			System.out.println("\tWert: " + tempStatus.getValue());
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
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~");
	}

}
