/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.nigeriaemr.xmlgenerator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.nigeriaemr.dbmanager.PatientDao;
import org.openmrs.module.nigeriaemr.model.ndr.Container;
import org.openmrs.module.nigeriaemr.model.ndr.FacilityType;
import org.openmrs.module.nigeriaemr.model.ndr.IdentifierType;
import org.openmrs.module.nigeriaemr.model.ndr.IndividualReportType;
import org.openmrs.module.nigeriaemr.model.ndr.MessageHeaderType;
import org.openmrs.module.nigeriaemr.ndrUtils.CustomErrorHandler;
import org.openmrs.module.nigeriaemr.ndrUtils.Utils;
import org.openmrs.module.nigeriaemr.ndrUtils.Validator;
import org.xml.sax.SAXException;

/**
 * @author lordmaul
 */
public class XMLBuilder implements Runnable {
	
	private int offset;
	
	private int currCount;
	
	private Container container;
	
	private MessageHeaderType messageHeaderType;
	
	private IndividualReportType individualReportType;
	
	private IndividualReportBuilder individualReportBuilder;
	
	private CustomPatient patient;
	
	public static JAXBContext jaxbContext;
	
	File aXMLFile;
	
	public String facilityName = "";
	
	public String facilityTypeCode;
	
	public String facilityType = "FAC";
	
	public String facilityID = "";
	
	public FacilityType patientFacility = null;
	
	public String ipShortName;
	
	public String DATIMID = "";
	
	public Map<Integer, IdentifierType> allPatientIdentifiers;
	
	static {
		try {
			jaxbContext = JAXBContext.newInstance("org.openmrs.module.nigeriaemr.model.ndr");
		}
		catch (JAXBException ex) {
			ex.printStackTrace();
		}
	}
	
	public XMLBuilder(int currCount, CustomPatient p, String ipShortName, String facilityTypeCode, String facilityName,
	    String facilityID) {
		this.currCount = currCount;
		this.patient = p;
		this.facilityTypeCode = facilityTypeCode;
		this.facilityName = facilityName;
		this.facilityID = facilityID;
		this.ipShortName = ipShortName;
		
		//lastRunDateString = Context.getAdministrationService().getGlobalProperty("ndr_last_run_date");
		
	}
	
	@Override
	public void run() {
		try {
			buildPatientFacility();
			container = new Container();
			//build header type
			MessageHeaderTypeBuilder mhBuilder = new MessageHeaderTypeBuilder();
			messageHeaderType = mhBuilder.build(facilityID, facilityName, facilityTypeCode);
			container.setMessageHeader(messageHeaderType);//messageHeaderType is complete
			
			PatientDao patientDaoObj = new PatientDao();
			this.allPatientIdentifiers = patientDaoObj.getAllPatientIdentifiers(patient.getPatientId());
			System.out.println("Identifier_count" + this.allPatientIdentifiers.size());
			individualReportBuilder = new IndividualReportBuilder(patient, patientFacility, allPatientIdentifiers);
			//System.out.println("Identifier_"+this.allPatientIdentifiers.size());
			
			individualReportType = individualReportBuilder.build();
			container.setIndividualReport(individualReportType);
			
			// moment of truth
			Marshaller jaxbMarshaller = createMarshaller(jaxbContext);
			String formattedDate = new SimpleDateFormat("ddMMyy").format(new Date());
			IdentifierType pepFarIdType = this.allPatientIdentifiers.get(Utils.Patient_PEPFAR_Id);
			String pepFarId = "";
			if (pepFarIdType != null) { //remove forward slashes / from file names
				pepFarId = pepFarIdType.getIDNumber();
				pepFarId = pepFarId.replace("/", "_").replace(".", "_");
				
				System.out.println("Pepfar ID" + pepFarId);
			} else {
				pepFarId = "" + this.patient.getPatientId();
			}
			String fileName = ipShortName + "_" + DATIMID + "_" + formattedDate + "_" + pepFarId;
			
			String xmlFile = Paths.get(XMLGenerator.reportFolder, fileName + ".xml").toString();
			//ensureDownloadFolderExist("reports");
			//String xmlFile = Paths.get("reports", "test_file"+this.patient.getPatientId() + ".xml").toString();
			System.out.println("File Name ID" + xmlFile);
			
			aXMLFile = new File(xmlFile);
			//Runnable t = new Generator(aXMLFile);
			//t.run();
			writeFile(container, aXMLFile, jaxbMarshaller);
			
			//container.setIndividualReport(individualReportType);
			System.out.println("processed patient " + currCount);
		}
		catch (Exception ex) {
			ex.printStackTrace();//Logger.getLogger(XMLBuilder.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void start() {
		try {
			buildPatientFacility();
			container = new Container();
			//build header type
			MessageHeaderTypeBuilder mhBuilder = new MessageHeaderTypeBuilder();
			messageHeaderType = mhBuilder.build(facilityID, facilityName, facilityTypeCode);
			container.setMessageHeader(messageHeaderType);//messageHeaderType is complete
			
			PatientDao patientDaoObj = new PatientDao();
			this.allPatientIdentifiers = patientDaoObj.getAllPatientIdentifiers(patient.getPatientId());
			System.out.println("Identifier_count" + this.allPatientIdentifiers.size());
			individualReportBuilder = new IndividualReportBuilder(patient, patientFacility, allPatientIdentifiers);
			//System.out.println("Identifier_"+this.allPatientIdentifiers.size());
			
			individualReportType = individualReportBuilder.build();
			container.setIndividualReport(individualReportType);
			
			// moment of truth
			Marshaller jaxbMarshaller = createMarshaller(jaxbContext);
			String formattedDate = new SimpleDateFormat("ddMMyy").format(new Date());
			IdentifierType pepFarIdType = this.allPatientIdentifiers.get(Utils.Patient_PEPFAR_Id);
			String pepFarId = "";
			if (pepFarIdType != null) { //remove forward slashes / from file names
				pepFarId = pepFarIdType.getIDNumber();
				pepFarId = pepFarId.replace("/", "_").replace(".", "_");
				
				System.out.println("Pepfar ID" + pepFarId);
			} else {
				pepFarId = "" + this.patient.getPatientId();
			}
			String fileName = ipShortName + "_" + DATIMID + "_" + formattedDate + "_" + pepFarId;
			
			String xmlFile = Paths.get(XMLGenerator.reportFolder, fileName + ".xml").toString();
			//ensureDownloadFolderExist("reports");
			//String xmlFile = Paths.get("reports", "test_file"+this.patient.getPatientId() + ".xml").toString();
			System.out.println("File Name ID" + xmlFile);
			
			aXMLFile = new File(xmlFile);
			//Runnable t = new Generator(aXMLFile);
			//t.run();
			writeFile(container, aXMLFile, jaxbMarshaller);
			//container = null;
			//aXMLFile = null;
			//container.setIndividualReport(individualReportType);
			System.out.println("processed patient " + currCount);
		}
		catch (Exception ex) {
			ex.printStackTrace();//Logger.getLogger(XMLBuilder.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}
	
	public void buildPatientFacility() {
		//facilityTypeCode = "IP";//PatientDao.getGlobalProperty("partner_short_name");;
		String patientFacilityName = PatientDao.getGlobalProperty("Facility_Name");
		DATIMID = PatientDao.getGlobalProperty("facility_datim_code");
		
		this.patientFacility = Utils.createFacilityType(patientFacilityName, DATIMID, facilityType);
		
	}
	
	public Marshaller createMarshaller(JAXBContext jaxbContext) throws JAXBException, SAXException {
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		
		java.net.URL xsdFilePath = Thread.currentThread().getContextClassLoader().getResource("NDR 1.4.xsd");
		
		assert xsdFilePath != null;
		
		Schema schema = sf.newSchema(xsdFilePath);
		
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		
		jaxbMarshaller.setSchema(schema);
		
		//Call Validator class to perform the validation
		jaxbMarshaller.setEventHandler(new Validator());
		return jaxbMarshaller;
	}
	
	public void writeFile(Container container, File file, Marshaller jaxbMarshaller) throws SAXException, JAXBException,
	        IOException {
		
		CustomErrorHandler errorHandler = new CustomErrorHandler();
		
		try {
			javax.xml.validation.Validator validator = jaxbMarshaller.getSchema().newValidator();
			jaxbMarshaller.marshal(container, file);
			//validator.setErrorHandler(errorHandler);
			//validator.validate(new StreamSource(file));
			
		}
		catch (Exception ex) {
			System.out.println("Could not generate file" + ex.getMessage());
			//	throw ex;
		}
	}
	
	class Generator implements Runnable {
		
		File filePath;
		
		public Generator(File fPath) {
			filePath = fPath;
		}
		
		public void run() {
			CustomErrorHandler errorHandler = new CustomErrorHandler();
			
			try {
				Marshaller jaxbMarshaller = createMarshaller(jaxbContext);
				javax.xml.validation.Validator validator = jaxbMarshaller.getSchema().newValidator();
				jaxbMarshaller.marshal(container, filePath);
				//validator.setErrorHandler(errorHandler);
				//validator.validate(new StreamSource(file));
				
			}
			catch (Exception ex) {
				System.out.println("Could not generate file" + ex.getMessage());
				//	throw ex;
			}
		}
		
		public Marshaller createMarshaller(JAXBContext jaxbContext) throws JAXBException, SAXException {
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			
			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			
			java.net.URL xsdFilePath = Thread.currentThread().getContextClassLoader().getResource("NDR 1.4.xsd");
			
			assert xsdFilePath != null;
			
			Schema schema = sf.newSchema(xsdFilePath);
			
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			
			jaxbMarshaller.setSchema(schema);
			
			//Call Validator class to perform the validation
			jaxbMarshaller.setEventHandler(new Validator());
			return jaxbMarshaller;
		}
	}
	
}
