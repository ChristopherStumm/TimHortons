package activemq;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import model.ERPData;
import model.LogFile;
import model.OPCDataItem;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.dataformat.JsonLibrary;

import logic.Identifier;
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
							Identifier.getInstance().createProduct(tempERPData);						
						}
					}); 
					
					
					from("activemq:topic:m_opcitems")
					.unmarshal(jaxbOPC)
					.process(new Processor() {
						
						@Override
						public void process(Exchange arg0) throws Exception {
							@SuppressWarnings("rawtypes")
							OPCDataItem tempStatus = arg0.getIn().getBody(OPCDataItem.class);
							Output.showStatusUpdate(tempStatus);
							Identifier.getInstance().processEvent(tempStatus);
							
						}
					});
					
					
					//FileWriter
					//Get OutPutPath
					Path path = Paths.get(QueueConnectionUsingCamel.class.getResource(".").toURI());
					String dir = path.getParent().getParent().getParent()+"/output";
					dir = dir.replace("\\", "/");
					System.out.println(dir);
					from("file://"+dir+"?delete=true&antInclude=*.erp")
					.unmarshal().json(JsonLibrary.Gson, LogFile.class)
					.process(new Processor() {
						@Override
						public void process(Exchange arg0) throws Exception {
							LogFile tempStatus = arg0.getIn().getBody(LogFile.class);
							Identifier.getInstance().finishProduct(tempStatus);
							System.out.println(tempStatus.getOverallStatus());
							System.out.println("Delete file");
						}
					});
					
					//Esper 
					from("activemq:topic:m_orders")
					.unmarshal(jaxbOPC)
					.to("esper:heat");
					

					  from("esper:test?eql=select avg(materialNumber) as avg from model.ERPData")
					  .process(new Processor() {

					  public void process(Exchange arg0) throws Exception {
					  com.espertech.esper.event.map.MapEventBean ev = (com.espertech.esper.event.map.MapEventBean) arg0
					  .getIn().getBody();

					  Map map = (Map)ev.getUnderlying();
					  System.out.println(map.get("avg"));
					  }
					 });					  
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
