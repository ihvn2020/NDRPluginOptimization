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
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.nigeriaemr.model.ndr.Container;
import org.openmrs.module.nigeriaemr.model.ndr.FacilityType;
import org.openmrs.module.nigeriaemr.ndrUtils.LoggerUtils;
import org.openmrs.module.nigeriaemr.ndrUtils.Utils;
import org.openmrs.module.nigeriaemr.ndrfactory.NDRConverter;
import org.openmrs.module.nigeriaemr.omodmodels.DBConnection;
import org.openmrs.scheduler.tasks.AbstractTask;

/**
 * @author IHVN
 */
public class GenerateXMLFiles extends AbstractTask {
	
	Patient patient;
	
	int counter;
	
	int totalPatients;
	
	private UserContext ctx;
	
	//NDRConverter generator;
	
	FacilityType facility;
	
	String reportFolder;
	
	NDRConverter generator;
	
	String IPShortName;
	
	String DATIMID;
	
	JAXBContext jaxbContext;
	
	String formattedDate;
	
	String patientPepFarId;
	
	//DBConnection openmrsConn;
	
	public GenerateXMLFiles() {
		
	}
	
	public GenerateXMLFiles(Patient pat, String patientPepFarId, int counter, int totalPatients, NDRConverter generator,
	    FacilityType fac, String repFolder, String ipShName, String dId, JAXBContext jaxb, String forDate) {
		this.patient = pat;
		this.counter = counter;
		this.totalPatients = totalPatients;
		//this.generator = generator;
		this.facility = fac;
		this.reportFolder = repFolder;
		this.generator = generator;
		this.IPShortName = ipShName;
		this.DATIMID = dId;
		this.jaxbContext = jaxb;
		this.formattedDate = forDate;
		this.patientPepFarId = patientPepFarId;
		
	}
	
	@Override
	public void execute() {
		Context.openSession();
		generateXMLFile(patient, patientPepFarId, counter, totalPatients, generator, facility, reportFolder, IPShortName,
		    DATIMID, jaxbContext, formattedDate);
		Context.closeSession();
	}
	
	public void generateXMLFile(Patient patient, String pepFarId, int counter, int totalPatients, NDRConverter generator,
	        FacilityType facility, String repFolder, String IPShortName, String dId, JAXBContext jaxb, String forDate) {
		
		ctx = Context.getUserContext();
		
		new Thread(new Runnable() {
			
			public void run() {
				
				try {
					Context.openSession();
					UserContext ctxInThread = ctx;
					Context.setUserContext(ctxInThread);
					Container cnt = generator.createContainer(patient, facility);
					Marshaller jaxbMarshaller;
					long startTime = System.currentTimeMillis();
					GenerateXMLFiles.this.counter++;
					System.out.println("patient  " + counter + " of " + totalPatients + " with ID " + patient.getId());
					//generator = new NDRConverter(Utils.getIPFullName(), IPShortName, openmrsConn);
					jaxbMarshaller = generator.createMarshaller(jaxb);
					
					System.out.println("did not throw error");
					
					if (cnt != null) {
						
						//String pepFarId = Utils.getPatientPEPFARId(patient);
						//String pepFarId = UUID.randomUUID().toString();
						
						String fileName = IPShortName + "_" + dId + "_" + forDate + "_" + pepFarId;
						
						// old implementation		String xmlFile = reportFolder + "\\" + fileName + ".xml";
						String xmlFile = Paths.get(repFolder, fileName + ".xml").toString();
						
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
					Context.closeSession();
				}
				catch (Exception ex) {
					System.out.println("Could not generate XML");
					ex.printStackTrace();
					//LoggerUtils.write(NdrFragmentController.class.getName(), ex.getMessage(), LoggerUtils.LogFormat.FATAL,
					//  LoggerUtils.LogLevel.live);
				}
				
			}
		}).start();
		
	}
}
