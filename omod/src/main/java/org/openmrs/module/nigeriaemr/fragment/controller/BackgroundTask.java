/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.nigeriaemr.fragment.controller;

import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import org.openmrs.Patient;
import org.openmrs.module.nigeriaemr.model.ndr.Container;
import org.openmrs.module.nigeriaemr.model.ndr.FacilityType;
import org.openmrs.module.nigeriaemr.ndrUtils.LoggerUtils;
import org.openmrs.module.nigeriaemr.ndrUtils.Utils;
import org.openmrs.module.nigeriaemr.ndrfactory.NDRConverter;
import org.openmrs.module.nigeriaemr.omodmodels.DBConnection;

/**
 * @author IHVN
 */
public class BackgroundTask implements Runnable {
	
	Patient patient;
	
	int counter;
	
	int totalPatients;
	
	//NDRConverter generator;
	
	FacilityType facility;
	
	String reportFolder;
	
	Container cn;
	
	String IPShortName;
	
	String DATIMID;
	
	JAXBContext jaxbContext;
	
	String formattedDate;
	
	DBConnection openmrsConn;
	
	public BackgroundTask(Patient pat, int counter, int totalPatients, Container cn, DBConnection openmrsConn,
	    FacilityType fac, String repFolder, String ipShName, String dId, JAXBContext jaxb, String forDate) {
		this.patient = pat;
		this.counter = counter;
		this.totalPatients = totalPatients;
		//this.generator = generator;
		this.facility = fac;
		this.reportFolder = repFolder;
		this.cn = cn;
		this.IPShortName = ipShName;
		this.DATIMID = dId;
		this.jaxbContext = jaxb;
		this.formattedDate = forDate;
		this.openmrsConn = openmrsConn;
		
	}
	
	public void run() {
		Marshaller jaxbMarshaller;
		long startTime = System.currentTimeMillis();
		counter++;
		System.out.println("patient  " + counter + " of " + totalPatients + " with ID " + patient.getId());
		
		try {
			NDRConverter generator = new NDRConverter(Utils.getIPFullName(), IPShortName, openmrsConn);
			jaxbMarshaller = generator.createMarshaller(jaxbContext);
			Container cnt = generator.createContainer(patient, facility);
			System.out.println("did not throw error");
			
			if (cnt != null) {
				
				//String pepFarId = Utils.getPatientPEPFARId(patient);
				String pepFarId = UUID.randomUUID().toString();
				if (pepFarId != null) { //remove forward slashes / from file names
					pepFarId = pepFarId.replace("/", "_").replace(".", "_");
				} else {
					pepFarId = "";
				}
				
				String fileName = IPShortName + "_" + DATIMID + "_" + formattedDate + "_" + pepFarId;
				
				// old implementation		String xmlFile = reportFolder + "\\" + fileName + ".xml";
				String xmlFile = Paths.get(reportFolder, fileName + ".xml").toString();
				
				//  Path xmlFilePath = Paths.get(reportFolder, fileName + ".xml");
				
				File aXMLFile = new File(xmlFile);
				Boolean b;
				if (aXMLFile.exists()) {
					b = aXMLFile.delete();
					System.out.println("deleting file : " + xmlFile + "was successful : " + b);
				}
				b = aXMLFile.createNewFile();
				
				System.out.println("creating xml file : " + xmlFile + "was successful : " + b);
				generator.writeFile(cnt, aXMLFile, jaxbMarshaller);
			}
			
		}
		catch (Exception ex) {
			System.out.println("Could not generate XML");
			ex.printStackTrace();
			//LoggerUtils.write(NdrFragmentController.class.getName(), ex.getMessage(), LoggerUtils.LogFormat.FATAL,
			//  LoggerUtils.LogLevel.live);
		}
		
	}
}
