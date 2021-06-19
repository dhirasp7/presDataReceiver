package com.asprsys.simulator.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asprsys.simulator.db.PresSensorRepository;
import com.asprsys.simulator.model.PresSensor;
import com.asprsys.simulator.service.SensorDataManager;

@Service
public class SensorDataManagerImpl implements SensorDataManager {
	@Autowired
	private PresSensorRepository temperatureSensorRepository;
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    private static final Logger LOGGER =
    	      LoggerFactory.getLogger(SensorDataManagerImpl.class);
	@Override
	public List<PresSensor> findDataByName(String sensorName) {
		return temperatureSensorRepository.findAll();
	}
	@Override
	public PresSensor findDataById(long id) {
		return temperatureSensorRepository.findById(id).get(); //since findById() is optional value we make a call to get()
	}
	@Override
	public PresSensor saveData(String sensorName, String classification, String dateString, String value) {
		Date date = new Date();
		try {
			date = formatter.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
		}
		PresSensor temperatureSensor = new PresSensor(sensorName, classification, date, value);
		return temperatureSensorRepository.save(temperatureSensor); //since findById() is optional value we make a call to get()
	}	

}
