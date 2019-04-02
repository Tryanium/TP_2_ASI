package com.isep.T2;

//import com.rabbitmq.client.Channel;
//import com.rabbitmq.client.Connection;
//import com.rabbitmq.client.ConnectionFactory;

import java.sql.*;
import org.sqlite.JDBC;


public class GetTweeterFeed {
	private static final String EXCHANGE_NAME = "tweet";
	private final static String QUEUE_NAME = "trending";
	


	public static void main() throws Exception {
		
		/*
		ConnectionFactory factory = new ConnectionFactory();
      	factory.setHost("localhost"); //Set the host
      	Connection connection = factory.newConnection();
   	    Channel channel = connection.createChannel();
   	    channel.queueDelete(QUEUE_NAME); //Delete the queue to avoid multi-answered
   	    channel.exchangeDeclare(EXCHANGE_NAME, "direct", true); //Declare the Exchange 
      	channel.queueDeclare(QUEUE_NAME, true, false, false, null); //Declare the Queue name
      	channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "camel"); //Bind the Queue
      	
      	
      	String tweet = "tweet"; //Recup tweet here
      	channel.basicPublish("", QUEUE_NAME, null, tweet.getBytes("UTF-8")); //Send it
      	*/
      	
		//
	
	}

}
