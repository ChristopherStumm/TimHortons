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

import utils.Output;

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
							Output.showERP(tempERPData);
													
						}
					}); 
					
					//from(file://).
					
					
					from("activemq:topic:m_opcitems")
					.unmarshal(jaxbOPC)
					.process(new Processor() {
						
						@Override
						public void process(Exchange arg0) throws Exception {
							@SuppressWarnings("rawtypes")
							OPCDataItem tempStatus = arg0.getIn().getBody(OPCDataItem.class);
							Output.showStatusUpdate(tempStatus);
						}
					});
					
					
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
