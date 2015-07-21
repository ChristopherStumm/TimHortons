package activemq;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import model.ERPData;
import model.OPCDataItem;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * This demonstrates how to connect to the queue using apache camel. NOTE:
 * Apache Camel is a mighty library and able to route and process messages from
 * different sources to different targets (also supports marshalling with JAXB)
 * e.g. from(activemq:topic:m_orders).process(...) or
 * from(activemq:topic:m_orders).unmarshal(...) or
 * from(activemq:topic:m_orders).to(esper:myName) from(esper:myName?eql=select
 * ....).to(file://...) or from(file://...).to(activemq:topic:...) or
 * from(file://...).marshal().json(JsonLibrary.Gson).to(activemq:...)
 * 
 * @author julian
 *
 */
public class QueueConnectionUsingCamel {

	public QueueConnectionUsingCamel() {

	}

	public void run() {
		CamelContext context = new DefaultCamelContext();
		JaxbDataFormat jaxbERP = new JaxbDataFormat();
		JaxbDataFormat jaxbOPC = new JaxbDataFormat();
		try {
			jaxbERP.setContext(JAXBContext.newInstance(ERPData.class));
			jaxbOPC.setContext(JAXBContext.newInstance(OPCDataItem.class));
		} catch (JAXBException e1) {
			e1.printStackTrace();
		}
		
		try {
			context.addRoutes(new RouteBuilder() {

				@Override
				public void configure() throws Exception {
					from("activemq:topic:m_orders")
					.unmarshal(jaxbERP)
					.process(new Processor() {
						
						@Override
						public void process(Exchange arg0) throws Exception {
							ERPData tempERPData = arg0.getIn().getBody(ERPData.class); 
							System.out.println();
							System.out.println("Kunde: " + tempERPData.getCustomerNumber());
							System.out.println("Material: " + tempERPData.getMaterialNumber());
							System.out
									.println("Bestellnummer: " + tempERPData.getOrderNumber());
							System.out.println("Zeitpunkt der Bestellung: "
									+ tempERPData.getTimeStamp());
							//identifier.createProduct(tempERPData);
							System.out.println("ID: " + tempERPData.getOrderNumber());
							
							System.out.println("---------------");

							// push into database
							// Connection conn = main.DatabaseConn.getDatabaseConn();
							// writeToDatabase(conn, tempERPDate);
													
						}
					})
					.to("esper:test1")
					.to("esper:test2"); 
					
					//from(file://).
					
					
					from("activemq:topic:m_opcitems").to("mock:status");
					
					
					//Esper 
					//from("activemq:topic:m_orders").unmarshal(someUnmarshallingObjectGoesHere).to("esper:test");
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			context.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
