package coap;

import java.io.IOException;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.elements.exception.ConnectorException;

public class CoapClientExample {
public static void main(String[] args) throws ConnectorException, IOException {
	CoapClient client = new CoapClient("coap://localhost:5683/temperatures");
	System.out.println( client.get().getResponseText() );
	
	String payload = "{\"name\":\"httpNode1\",\"value\":\"27\"}";
	CoapResponse response = client.post(payload,  MediaTypeRegistry.TEXT_PLAIN);
	System.out.println(response.getResponseText());
}
}
