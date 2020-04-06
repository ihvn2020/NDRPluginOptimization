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
import org.openmrs.module.nigeriaemr.ndrfactory.NDRConverterUpdated;
import org.openmrs.module.nigeriaemr.omodmodels.DBConnection;
import org.openmrs.scheduler.tasks.AbstractTask;

/**
 * @author IHVN
 */
public class GenerateXMLFiles2 extends AbstractTask {
	
	Patient patient;
	
	int counter;
	
	int totalPatients;
	
	private UserContext ctx;
	
	private NDRConverterUpdated gen;
	
	private Container cnt;
	
	Marshaller jaxbM;
	
	String fileName;
	
	File aXMLFile;
	
	//NDRConverter generator;
	
	//DBConnection openmrsConn;
	
	public GenerateXMLFiles2() {
		
	}
	
	public GenerateXMLFiles2(NDRConverterUpdated gen, Container cn, Marshaller jaxbM, String xmlFile) {
		this.gen = gen;
		this.cnt = cn;
		this.jaxbM = jaxbM;
		this.fileName = xmlFile;
		//this.generator = generator;
		
	}
	
	@Override
	public void execute() {
		Context.openSession();
		generateXMLFile(gen, cnt, jaxbM, fileName);
		Context.closeSession();
	}
	
	public void generateXMLFile(NDRConverterUpdated gen, Container cn, Marshaller jaxbM, String xmlFile) {
		
		ctx = Context.getUserContext();
		
		new Thread(new Runnable() {
			
			public void run() {
				
				try {
					File aXMLFile = new File(xmlFile);
					Boolean b;
					if (aXMLFile.exists()) {
						b = aXMLFile.delete();
						System.out.println("deleting file : " + xmlFile + "was successful : " + b);
					}
					aXMLFile = new File(xmlFile);
					gen.writeFile(cnt, aXMLFile, jaxbM);
				}
				catch (Exception ex) {
					System.out.println("Could not generate XML");
					ex.printStackTrace();
					
				}
				
			}
		}).start();
		
	}
}
