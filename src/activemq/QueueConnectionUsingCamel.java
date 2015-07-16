package activemq;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
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

		try {
			context.addRoutes(new RouteBuilder() {

				@Override
				public void configure() throws Exception {
					from("activemq:topic:m_orders").to("stream:out");
					from("activemq:topic:m_opcitems").to("stream:out");
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
