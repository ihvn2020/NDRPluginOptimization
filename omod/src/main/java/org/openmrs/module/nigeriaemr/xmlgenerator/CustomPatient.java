/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.nigeriaemr.xmlgenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author lordmaul
 */
public class CustomPatient {
	
	private int patientId;
	
	private String birthDate;
	
	private String gender;
	
	private String firstName;
	
	private String middleName;
	
	private String lastName;
	
	private String phoneNumber;
	
	public CustomPatient(int pId, String bDate, String gender, String fName, String lName, String mName, String pNumber) {
		this.patientId = pId;
		this.birthDate = bDate;
		this.gender = gender;
		this.firstName = fName;
		this.lastName = lName;
		this.middleName = mName;
		this.phoneNumber = pNumber;
	}
	
	public int getAge() {
		//System.out.println("Age: "+this.birthDate);
		if (!birthDate.contentEquals("")) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date date = formatter.parse(this.getBirthDate());
				Date currDate = new Date();
				
				LocalDate patientDob = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				LocalDate currentDate = currDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				
				int age = Period.between(patientDob, currentDate).getYears();
				System.out.println(age);
				return age;
			}
			catch (ParseException ex) {
				ex.printStackTrace();
				//Logger.getLogger(CustomPatient.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return 0;
	}
	
	/**
	 * @return the patientId
	 */
	public int getPatientId() {
		return patientId;
	}
	
	/**
	 * @param patientId the patientId to set
	 */
	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
	
	/**
	 * @return the birthDate
	 */
	public String getBirthDate() {
		return birthDate;
	}
	
	/**
	 * @param birthDate the birthDate to set
	 */
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	
	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}
	
	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}
	
	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public Date getDateOfBirth() {
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(this.getBirthDate());
			return date;
		}
		catch (ParseException ex) {
			//Logger.getLogger(CustomPatient.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
		
	}
	
}
