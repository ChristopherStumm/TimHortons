package activemq;

import javax.jms.Message;
import javax.jms.MessageListener;

public class OPCDataListener implements MessageListener {

	@Override
	public void onMessage(Message arg0) {
		System.out.println("Got it");
	}

}
