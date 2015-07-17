package activemq;

import java.io.StringReader;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import model.ERPData;
import utils.LogFileReader;

import logic.Identifier;

/**
 * This Listener listens for new ERPData messages.
 * 
 * @author lucas.schlemm
 *
 */
public class ERPDataListener implements MessageListener {

	private JAXBContext _ctx;

	private Unmarshaller _unmarshaller;
	
	Identifier identifier;

	/**
	 * Default Constructor
	 */
	public ERPDataListener() {
		try {
			_ctx = JAXBContext.newInstance(ERPData.class);
			_unmarshaller = _ctx.createUnmarshaller();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onMessage(Message arg0) {

		System.out.println();
		TextMessage tmpMessage = null;
		if (arg0 instanceof TextMessage) {
			tmpMessage = (TextMessage) arg0;
		} else {
			System.err.println("Unknown format, marshalling aborted.");
			return;
		}

		// Creating ERP-Object
		ERPData tempERPDate = null;
		try {
			StringReader reader = new StringReader(tmpMessage.getText());
			tempERPDate = (ERPData) _unmarshaller.unmarshal(reader);
			System.out.println();
			System.out.println("Kunde: " + tempERPDate.getCustomerNumber());
			System.out.println("Material: " + tempERPDate.getMaterialNumber());
			System.out
					.println("Bestellnummer: " + tempERPDate.getOrderNumber());
			System.out.println("Zeitpunkt der Bestellung: "
					+ tempERPDate.getTimeStamp());
			identifier.createProduct(tempERPDate.getOrderNumber());
			System.out.println("ID: " + tempERPDate.getOrderNumber());
			
			System.out.println("---------------");

			// push into database
			// Connection conn = main.DatabaseConn.getDatabaseConn();
			// writeToDatabase(conn, tempERPDate);

		} catch (JMSException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		// Reading Log Files after a new order get's submitted.
		// TODO @Lucas Pr�fung ob es der erste Durchlauf ist.
		LogFileReader lfr = LogFileReader.getInstance();
		lfr.readLatestFile();

	}
	
	public void setIdentifier(Identifier identifier){
		this.identifier = identifier;
	}
}
