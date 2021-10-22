package multiServer;

import java.net.SocketException;

import mqtt.MqttSubSample;

public class iotServer {
	public static void main(String[] args) {
		int httpPort = 9999;
		int socketPort = 8888;
		
		
		HttpConnector httpServer = new HttpConnector(httpPort,"/v1/temperatures");
		SocketConnector socketServer = new SocketConnector(socketPort);		
		MqttConnector mqttConnector = new MqttConnector();
		
	
		
		try {
			CoapConnector coapConnector = new CoapConnector();
			
			System.out.println("Start Socket Server ");
			socketServer.startServer();
			
			System.out.println("Start HTTP Server ");
			httpServer.start();
		
			System.out.println("Start MQTT Connector ");
			mqttConnector.doDemo();
			
			System.out.println("Start CoAP Connector ");
			coapConnector.startServer();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
