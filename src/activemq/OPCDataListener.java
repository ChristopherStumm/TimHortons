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
			System.out.println("Item: " + tempStatus.getItemName());
			System.out.println("Status: " + tempStatus.getStatus());
			System.out.println("Zeitpunkt der Meldung: "
					+ tempStatus.getTimestamp());
			System.out.println("Wert: " + tempStatus.getValue());
			System.out.println("-----");
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
