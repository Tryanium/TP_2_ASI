package com.isep.T2;

import javax.annotation.processing.Processor;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;

/**
 * A Camel Java8 DSL Router
 */
public class MyRouteBuilder extends RouteBuilder {
	
    public void configure() {
    	from("file:src/main/resources/test.json")
    		.to("rabbitmq:localhost:5672/test?username=guest&password=guest&autoDelete=false&routingKey=camel&queue=mqpending");
 
    }

}