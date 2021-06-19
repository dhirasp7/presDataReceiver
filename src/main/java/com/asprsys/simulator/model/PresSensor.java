package com.asprsys.simulator.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PRES_SENSOR")
public class PresSensor {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="CLASSIFICATION")
	private String classification;
	
	@Column(name="CREATE_DATE")
	private Date createDate;
	
	@Column(name="PRES_VALUE")
	private String value;
	
	
	public PresSensor() {
	}

	public PresSensor(String sensorName, String sClassification, Date date, String tValue) {
		this.name = sensorName;
		this.classification = sClassification;
		this.createDate = date;
		this.value = tValue;
	}

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getClassification() {
		return classification;
	}


	public void setClassification(String classification) {
		this.classification = classification;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "PressureSensor [id=" + id + ", name=" + name + ", classification=" + classification + ", createDate="
				+ createDate + ", value=" + value + "]";
	}
	
}
