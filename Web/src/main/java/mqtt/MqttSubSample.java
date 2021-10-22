package mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

public class MqttSubSample implements MqttCallback {

MqttClient client;
String topic= "esp/temp";

public MqttSubSample() {
}

public static void main(String[] args) {
    new MqttSubSample().doDemo();
}

public void doDemo() {
    try {
    	System.out.println("Subcribe MQTT topic " + topic);
    	
        client = new MqttClient("tcp://127.0.0.1:1883", "Sending");
        client.connect();
        client.setCallback(this);
        client.subscribe(topic);
        MqttMessage message = new MqttMessage();
        message.setPayload("A single message from my computer fff"
                .getBytes());
        client.publish("esp/temp", message);
    } catch (MqttException e) {
        e.printStackTrace();
    }
}

public void connectionLost(Throwable cause) {
	try {
		client.connect();
	} catch (MqttSecurityException e) {		
		e.printStackTrace();
	} catch (MqttException e) {		
		e.printStackTrace();
	}

}

public void messageArrived(String topic, MqttMessage message)
        throws Exception {
 System.out.println(message);   
}

public void deliveryComplete(IMqttDeliveryToken token) {
    // TODO Auto-generated method stub

}

}





//public class PubSub {
//
//	  MqttClient client;
//	  String topics[] = ["foo/#", "bar"];
//	  MqttCallback callback = new MqttCallback() {
//	    public void connectionLost(Throwable t) {
//	      this.connect();
//	    }
//
//	    public void messageArrived(String topic, MqttMessage message) throws Exception {
//	      System.out.println("topic - " + topic + ": " + new String(message.getPayload()));
//	    }
//
//	    public void deliveryComplete(IMqttDeliveryToken token) {
//	    }
//	  };
//
//	  public static void main(String args[]) {
//	    PubSub foo = new PubSub();
//	  }
//
//	  public PubSub(){
//	    this.connect();
//	  }
//
//	  public void connect(){
//	    client = new MqttClient("mqtt://localhost", "pubsub-1");
//	    client.setCallback(callback);
//	    client.connect();
//	    client.subscribe(topics);
//	  }
//
//	}
