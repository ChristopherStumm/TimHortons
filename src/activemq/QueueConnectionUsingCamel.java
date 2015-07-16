package activemq;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import model.ERPData;
import model.StatusUpdate;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.w3c.dom.Node;

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
		JaxbDataFormat jaxb = new JaxbDataFormat();
		JaxbDataFormat jaxStatus = new JaxbDataFormat();

		try {
			jaxb.setContext(JAXBContext.newInstance(ERPData.class));
			jaxStatus.setContext(JAXBContext.newInstance(StatusUpdate.class));
		} catch (JAXBException e1) {
			e1.printStackTrace();
		}
        
		
		try {
			context.addRoutes(new RouteBuilder() {

				@Override
				public void configure() throws Exception {
					from("activemq:topic:m_orders").to("stream:out");
					from("activemq:topic:m_opcitems").to("stream:out");
//					Unmarshaller unmarshaller = jaxStatus.getContext().createUnmarshaller();
//					StatusUpdate sUpdate = (StatusUpdate) unmarshaller.unmarshal((Node) from("activemq:topic:m_opcitems"));
//
//					System.out.println(sUpdate);
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
