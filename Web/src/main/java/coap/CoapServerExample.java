package coap;


import java.net.SocketException;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.server.resources.CoapExchange;



/**
 * The Class CoAPServer.
 * @author Yasith Lokuge
 */
public class CoapServerExample extends CoapServer {

	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {		
		
		try {
			// create server
			CoapServerExample server = new CoapServerExample();
			server.start();
		} catch (SocketException e) {
			System.err.println("Failed to initialize server: " + e.getMessage());
		}
	}

	/**
	 * Instantiates a new co ap server.
	 * @param port 
	 *
	 * @throws SocketException the socket exception
	 */
	public CoapServerExample() throws SocketException {
	
		// provide an instance of a resource
		add(new PublishResource());
	}

	/**
	 * The Class PublishResource.
	 */
	class PublishResource extends CoapResource {

		
		/**
		 * Instantiates a new publish resource.
		 */
		public PublishResource() {
			// set resource identifier
			super("temperatures");
			// set display name
			getAttributes().setTitle("temperatures");
		}

		/* (non-Javadoc)
		 * @see org.eclipse.californium.core.CoapResource#handlePOST(org.eclipse.californium.core.server.resources.CoapExchange)
		 */		
		public void handlePOST(CoapExchange exchange) {			
			System.out.println(exchange.getRequestText());			
			exchange.respond("POST_REQUEST_SUCCESS");			
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.californium.core.CoapResource#handleGET(org.eclipse.californium.core.server.resources.CoapExchange)
		 */
		public void handleGET(CoapExchange exchange) {						
			exchange.respond("GET_REQUEST_SUCCESS");			
		}
	}

}