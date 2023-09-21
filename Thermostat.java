/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package thermostat;

import org.eclipse.paho.client.mqttv3.*;

/**
 *
 * @author e2304799
 */
public class Thermostat {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MqttException, InterruptedException {
      
        
           // Creation un client
        MqttClient client = new MqttClient("tcp://localhost:1883","Thermostat");  	// Nom du client (tous les clients doivent 
                                                // avoir des noms differents)
         client.setCallback(new MqttCallback() {
                        @Override
                        public void connectionLost(Throwable cause) {}
                       // Callback pour la reception des messages
                        @Override
                        public void messageArrived(String topic, MqttMessage message)throws Exception {

                            
                            Thermostat.connect();
                            
                            System.out.printf("Le topic : %s\n ",topic);
                            System.out.printf("Le message : %s\n ",new String(message.getPayload()));
                            
                            String Msg1 = new String(message.getPayload());
                            
                            
                            if(Integer.parseInt(Msg1) < 15 ){
                 
                              MqttMessage newMessage = new MqttMessage();
                               message.setPayload("ON".getBytes());
                               Thermostat.publish("cuisine/thermometre", message);
                                
                            }else{
                         
                 MqttMessage newMessage = new MqttMessage();
                               message.setPayload("OFF".getBytes());
                 
                 }
                                
                            
                        }
                        @Override
                        public void deliveryComplete(IMqttDeliveryToken token) {}
                    });
        // Connexion du client
        
        // Definition des topics pour la reception des messages, un thread est cree
        Thermostat.subscribe("#");
        for (int i=0; i<100000; i++) {
            Thread.sleep(1000);
        }
        Thermostat.disconnect();
    
}
}
