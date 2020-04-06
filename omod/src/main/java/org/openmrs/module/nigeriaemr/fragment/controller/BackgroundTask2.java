/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.nigeriaemr.fragment.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.nigeriaemr.model.ndr.Container;
import org.openmrs.module.nigeriaemr.model.ndr.FacilityType;
import org.openmrs.module.nigeriaemr.ndrUtils.LoggerUtils;
import org.openmrs.module.nigeriaemr.ndrUtils.Utils;
import org.openmrs.module.nigeriaemr.ndrfactory.NDRConverter;
import org.openmrs.module.nigeriaemr.ndrfactory.NDRConverterUpdated;
import org.xml.sax.SAXException;

/**
 * @author IHVN
 */
public class BackgroundTask2 implements Runnable {
	
	List<Patient> filteredPatients;
	
	NDRConverterUpdated generator;
	
	Container cnt;
	
	FacilityType facility;
	
	String reportFolder;
	
	String IPShortName;
	
	String DATIMID;
	
	File aXMLFile;
	
	JAXBContext jaxBContext;
	
	//Marshaller jaxbMarshaller;
	
	String xmlFile;
	
	Boolean b;
	
	public BackgroundTask2(NDRConverterUpdated gen, Container cn, Marshaller jaxbM, String xmlFile) {
		
		this.generator = gen;
		this.cnt = cn;
		//this.jaxbMarshaller = jaxbM;
		this.xmlFile = xmlFile;
		//this.jaxBContext = jaxBContext;
	}
	
	public void run() {
		
		try {
			Marshaller jaxbMarshaller = generator.createMarshaller(NdrFragmentController.jaxbContext);
			aXMLFile = new File(xmlFile);
			generator.writeFile(cnt, aXMLFile, jaxbMarshaller);
			Context.evictFromSession(cnt);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
