package com.isep.T2;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.camel.main.Main;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Channel;
import org.json.simple.JSONObject;

/**
 * A Camel Application
 */
public class MainApp {

	private final static String QUEUE_NAME = "mqpending";
	private static final String EXCHANGE_NAME = "test";
	
    public static void main(String... args) throws Exception {
    	
    	FindFileInDirectory FindFiles = new FindFileInDirectory();
    	ArrayList<String> Files = FindFiles.findFile("src/main/resources/articles");
    	
    	ConnectionFactory factory = new ConnectionFactory();
      	factory.setHost("localhost"); //Set the host
      	Connection connection = factory.newConnection();
   	    Channel channel = connection.createChannel();
   	    channel.exchangeDeclare(EXCHANGE_NAME, "direct", true); //Declare the Exchange 
      	channel.queueDeclare(QUEUE_NAME, true, false, false, null); //Declare the Queue name
      	channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "camel"); //Bind the Queue
    	
    	ArrayList<JSONObject> ListFile = new ArrayList<JSONObject>() ; //Créer l'objet à envoyer
    	
    	
    	Files.forEach((name) -> {
        	JSONObject file = ReadJsonFile.read(name); //Put the file in a JSON Object
        	try {
				channel.basicPublish("", QUEUE_NAME, null, file.toString().getBytes("UTF-8")); //Send the message
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	});
    }
}

