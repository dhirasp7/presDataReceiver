package com.asprsys.simulator.service;

import java.util.Date;
import java.util.List;

import com.asprsys.simulator.model.PresSensor;

public interface SensorDataManager {

	public List<PresSensor> findDataByName(String sensorName);

	public PresSensor findDataById(long id);

	public PresSensor saveData(String sensorName, String classification, String dateString, String value);

	/*
	 * find sensor data with id
	 * find senor data for name
	 * find sensor data by classification
	 * save sensor data
	 * 
	 */

}
