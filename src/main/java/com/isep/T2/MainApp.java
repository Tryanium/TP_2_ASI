package com.isep.T2;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.camel.main.Main;
import org.apache.camel.util.json.JsonObject;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import twitter4j.JSONArray;

import com.rabbitmq.client.Channel;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * A Camel Application
 */
public class MainApp {

	private final static String QUEUE_NAME = "mqpending";
	private static final String EXCHANGE_NAME = "test";
	
    protected static String dbName = "Tweeter.db";
    protected static String TableName = "FEED";
	
    public static void main(String... args) throws Exception {
    	FindFileInDirectory FindFiles = new FindFileInDirectory();
    	ArrayList<String> Files = FindFiles.findFile("src/main/resources/articlesPending");
    	
    	ConnectionFactory factory = new ConnectionFactory();
      	factory.setHost("localhost"); //Set the host
      	Connection connection = factory.newConnection();
   	    Channel channel = connection.createChannel();
   	    channel.queueDelete(QUEUE_NAME); //Delete the queue to avoid multi-answered
   	    channel.exchangeDeclare(EXCHANGE_NAME, "direct", true); //Declare the Exchange 
      	channel.queueDeclare(QUEUE_NAME, true, false, false, null); //Declare the Queue name
      	channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "camel"); //Bind the Queue
      	
    	
    	
      	Files.forEach(name -> {
      			JSONObject file = ReadJsonFile.read(name); //Put the file in a JSON Object
            	try {
    				channel.basicPublish("", QUEUE_NAME, null, file.toString().getBytes("UTF-8")); //Send the message
    			} catch (UnsupportedEncodingException e) {
    				e.printStackTrace();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
      		});
      	
    	DeliverCallback deliverCallback = (RecupFile, messageRecup) -> { //Recevied the msg
    	    String message = new String(messageRecup.getBody(), "UTF-8");
    	    com.google.gson.JsonObject jsonObject = new JsonParser().parse(message).getAsJsonObject();
    	    
    	    Date date = new Date(); //Create a date
    	    SimpleDateFormat ft = new SimpleDateFormat ("y-MM-d"); //Format the date
  
    	    jsonObject.addProperty("accepted_date", ft.format(date)); //Add the acceptedDate to the JSON
    	    
    	    String reviewer_name = "Computer_1"; //Create the Reviewer name
    	    jsonObject.addProperty("reviewer", reviewer_name); //Add it to the JSON
    	    
    	    String title = jsonObject.get("title").toString(); //Find the title
    	    title = title.substring(1, title.length()-1); //Erase quotation mark
    	    
    	    //Write the file
    	    try (FileWriter file = new FileWriter("src/main/resources/articlesAccepted/" + title + ".json")) {
                file.write(jsonObject.toString());
                file.flush();
     
            } catch (IOException e) {
                e.printStackTrace();
            }
    	    
    	};
    	
    	channel.basicConsume(QUEUE_NAME, true, deliverCallback, RecupFile -> { }); //Allow the delivering
    	
    	//Schedule every Hour
    	ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
    	ses.scheduleAtFixedRate(new Runnable() {
    	    @Override
    	    public void run() {
    	    	try {
					TrendsHour();
				} catch (Exception e) {
					e.printStackTrace();
				}
    	    }
    	}, 0, 1, TimeUnit.HOURS);
    	
    }
    
    @SuppressWarnings("unchecked")
	private static void TrendsHour() throws Exception {
    	try {
			ArrayList<String> Trends = GetTweeterFeed.GetFeed();
			SQLite.connect(dbName);
	    	if(!SQLite.CheckTableExist(TableName)) {
	    		SQLite.CreateTable(TableName);
	    	}
	    	JSONObject TrendsJson = new JSONObject();
	    	TrendsJson.put("Classement", new JSONArray(Trends));
	    	String arrayList = TrendsJson.get("Classement").toString();
	    	
	    	SQLite.SendData(arrayList, TableName);
	    	
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
}

