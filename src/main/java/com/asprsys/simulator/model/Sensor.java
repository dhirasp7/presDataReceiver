package com.asprsys.simulator.model;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.UncategorizedJmsException;
import org.springframework.jms.core.JmsTemplate;

/**
 * A callable (so we can invoke in on a executor and join on it) that sends messages
 * to a queue periodically - representing the sensor.
 */
public class Sensor
{
    private static final Logger LOG = LoggerFactory.getLogger(Sensor.class);
	  
	private static final double BASE_TEMP = 80;	
	private static final double BASE_PRES = 12.6;
	

	private String sensorName;
	private JmsTemplate jmsTemplate;
	private String destinationName;
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    public Sensor() {
    	
    }
	
	public Sensor(String sensorName, JmsTemplate jmsTemplate, String queueName) 
	{
		this.sensorName = sensorName;
		this.jmsTemplate = jmsTemplate;
		this.destinationName = queueName;
	}	


	/**
	 * Sends a message to the sensor queue - we've hardcoded this in at present - of course
	 * this needs to be fixed on the course!
	 * @param positionMessage
	 * @throws InterruptedException 
	 */
	private void sendToQueue(Map<String, String> positionMessage) throws InterruptedException {
		boolean messageNotSent = true;
		while (messageNotSent)
		{
			// broadcast this report
			try
			{
				jmsTemplate.convertAndSend(destinationName,positionMessage);
				messageNotSent = false;
			}
			catch (UncategorizedJmsException e)
			{
				// we are going to assume that this is due to downtime - back off and go again
				System.out.println("Queue unavailable - backing off 5000ms before retry");
				delay(5000);
			}
		}
	}
	
	


	private static void delay(double d) throws InterruptedException {
		LOG.info("Sleeping for " + d + " millsecs");
		Thread.sleep((long) d);
	}

	public String getSensorName() {
		return sensorName;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}



	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}


}

