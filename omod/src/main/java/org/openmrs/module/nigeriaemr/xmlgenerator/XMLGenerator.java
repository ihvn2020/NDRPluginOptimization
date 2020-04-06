package org.openmrs.module.nigeriaemr.xmlgenerator;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.openmrs.module.nigeriaemr.dbmanager.Database;
import org.openmrs.module.nigeriaemr.dbmanager.PatientDao;
import org.openmrs.module.nigeriaemr.omodmodels.DBConnection;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author lordmaul
 */
public class XMLGenerator {
	
	//get total patients
	PatientDao patientObj = new PatientDao();
	
	//int totalPatients = patientObj.getTotalPatients();
	
	//int numberOfPages = totalPatients / 1000;
	public static String reportFolder;
	
	public DBConnection openmrsConn = null;
	
	private String startDate;
	
	private String endDate;
	
	public XMLGenerator(String repFolder, DBConnection con, String sDate, String eDate) {
		XMLGenerator.reportFolder = repFolder;
		this.openmrsConn = con;
		this.startDate = sDate;
		this.endDate = eDate;
	}
	
	public void startGenerating() {
		
		Database.initConnection(this.openmrsConn);
		
		//for (int i = 0; i <= numberOfPages; i++) {
		// ContainerBuilder builder = new ContainerBuilder(i * 1000);
		ContainerBuilder builder = new ContainerBuilder(0, startDate, endDate);
		builder.startBuilding();
		//}
	}
	
}
