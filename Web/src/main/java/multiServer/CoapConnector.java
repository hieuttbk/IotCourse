package multiServer;


import java.net.SocketException;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.server.resources.CoapExchange;



/**
 * The Class CoAPServer.
 * @author Yasith Lokuge
 */
public class CoapConnector extends CoapServer {

	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public void startServer() {		
		
		try {
			// create server
			CoapConnector server = new CoapConnector();
			server.start();
			
			System.out.println(">CoAP Server: start at path " + "/temperatures");
		} catch (SocketException e) {
			System.err.println("Failed to initialize server: " + e.getMessage());
		}
	}

	/**
	 * Instantiates a new co ap server.
	 *
	 * @throws SocketException the socket exception
	 */
	public CoapConnector() throws SocketException {
		//addEndpoint(null);
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
			System.out.println(">CoAP Server: request: " + exchange.getRequestText());			
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