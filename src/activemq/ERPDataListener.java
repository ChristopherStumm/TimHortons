package activemq;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import model.ERPData;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import utils.LogFileReader;

/**
 * The listener class takes new messages and unmarshalls them to Java Objects.
 * 
 * @author julian
 *
 */
public class ERPDataListener implements MessageListener {

	private Logger _log = LogManager.getLogger(ERPDataListener.class);

	private JAXBContext _ctx;

	private Unmarshaller _unmarshaller;

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
		_log.debug("New data listener created.");
	}

	@Override
	public void onMessage(Message arg0) {
		_log.debug("New ERP message arrived!");

		System.out.println();
		TextMessage tmpMessage = null;
		if (arg0 instanceof TextMessage) {
			tmpMessage = (TextMessage) arg0;
		} else {
			_log.warn("Unknown format, marshalling aborted.");
			return;
		}

		try {
			_log.debug(tmpMessage.getText());
		} catch (JMSException e) {
			e.printStackTrace();
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
			System.out.println("---------------");

			// push into database
			// Connection conn = main.DatabaseConn.getDatabaseConn();
			// writeToDatabase(conn, tempERPDate);

		} catch (JMSException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		LogFileReader lfr = LogFileReader.getInstance();
		lfr.readLatestFile();
	}

	private void writeToDatabase(Connection conn, ERPData data) {
		if (conn != null) {
			try {
				conn.createStatement().executeQuery("BLABLA");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Error writing to database");
		}
	}
}
