package com.asprsys.simulator.config.queue;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.activemq.selector.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;

import com.asprsys.simulator.model.PresSensor;
import com.asprsys.simulator.service.SensorDataManager;

public class Receiver {

	private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

	@Autowired
	private SensorDataManager tSensorManager;

	@JmsListener(destination = "${destination.pres.sensor.queue}")
	public void receive(Map<String, String> message) throws ParseException {
		PresSensor presSensor = processIncomingMessage(message);
		LOGGER.info("---------'  received message='{}'", presSensor.toString());
	}

	private PresSensor processIncomingMessage(Map<String, String> message) {
		String sensorName = message.get("sensor");
		String value = message.get("value");
		if (value.length() > 15)
			value = value.substring(0, 15);
		String reportDate = message.get("time");
		if (Double.parseDouble(value) > 12.9)
			postToAnalomyQueue(sensorName, value, reportDate);
		PresSensor tempSensor = tSensorManager.saveData(sensorName, null, reportDate, value);

		return tempSensor;
	}

	@Autowired
	private JmsTemplate template;
	@Value("${anomaly.pres.sensor.queue}")
	private String queueName;

	private void postToAnalomyQueue(String sensorName, String value, String reportDate) {
		HashMap<String, String> sensorMessage = new HashMap<>();
		sensorMessage.put("sensor", sensorName);
		sensorMessage.put("value", value);
		sensorMessage.put("time", reportDate);
		template.convertAndSend(queueName, sensorMessage);
	}

	/*
	 * Receive the data
	 * 
	 * process it to create an object add more information like if it crosses a
	 * threshold generate an alert message. put the message on another queue
	 */
}