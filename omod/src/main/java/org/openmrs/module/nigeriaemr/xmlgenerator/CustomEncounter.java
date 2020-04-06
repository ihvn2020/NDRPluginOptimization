/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.nigeriaemr.xmlgenerator;

import java.util.Date;
import java.util.Set;
import org.openmrs.EncounterProvider;
import org.openmrs.EncounterType;
import org.openmrs.Form;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.Visit;
import org.openmrs.annotation.AllowDirectAccess;
import org.openmrs.annotation.DisableHandlers;
import org.openmrs.api.handler.VoidHandler;

/**
 * @author lordmaul
 */
public class CustomEncounter {
	
	private Integer encounterId;
	
	private Date encounterDatetime;
	
	private int patientId;
	
	private Date visitDate;
	
	private int locationId;
	
	private int formId;
	
	private int encounterTypeId;
	
	private int visitId;
	
	private String providerName;
	
	/**
	 * @return the encounterId
	 */
	public int getEncounterId() {
		return encounterId;
	}
	
	/**
	 * @param encounterId the encounterId to set
	 */
	public void setEncounterId(Integer encounterId) {
		this.encounterId = encounterId;
	}
	
	public int getEncounterTypeId() {
		return this.encounterTypeId;
	}
	
	public void setEncounterTypeId(int encounterTypeId) {
		this.encounterTypeId = encounterTypeId;
	}
	
	/**
	 * @return the encounterDatetime
	 */
	public Date getEncounterDatetime() {
		return encounterDatetime;
	}
	
	/**
	 * @param encounterDatetime the encounterDatetime to set
	 */
	public void setEncounterDatetime(Date encounterDatetime) {
		this.encounterDatetime = encounterDatetime;
	}
	
	/**
	 * @return the patientId
	 */
	public Integer getPatientId() {
		return patientId;
	}
	
	/**
	 * @param patientId the patientId to set
	 */
	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}
	
	/**
	 * @return the locationId
	 */
	public int getLocationId() {
		return locationId;
	}
	
	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	
	/**
	 * @return the formId
	 */
	public int getFormId() {
		return formId;
	}
	
	/**
	 * @param formId the formId to set
	 */
	public void setFormId(int formId) {
		this.formId = formId;
	}
	
	public int getVisitId() {
		return this.visitId;
	}
	
	public void setVisitId(int visitId) {
		this.visitId = visitId;
	}
	
	public String getProviderName() {
		return this.providerName;
	}
	
	public void setProviderName(String name) {
		this.providerName = name;
	}
	
	public Date getVisitDate() {
		return this.visitDate;
	}
	
	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}
}
