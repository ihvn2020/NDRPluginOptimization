/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.nigeriaemr.xmlgenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.openmrs.Patient;
import org.openmrs.module.nigeriaemr.dbmanager.PatientDao;
import org.openmrs.module.nigeriaemr.model.ndr.Container;

/**
 * @author lordmaul
 */
public class ContainerBuilder {
	
	private int offset;
	
	private int limit = 100000;
	
	PatientDao patientObj = new PatientDao();
	
	public static String facName = "Facility Name";//PatientDao;
	
	public static String DatimId = "DATim ID";//Utils.getFacilityDATIMId();
	
	public static String facilityType = "FAC";
	
	public String facilityName = "";
	
	public String facilityTypeCode;
	
	//public String DATIMID = "";
	public String facilityID = "";
        public Map<Integer, List<Object>> allPatientsMap = new HashMap<>();
	
	ExecutorService executor = Executors.newFixedThreadPool(5);
	String startDate;
        String endDate;
	public ContainerBuilder(int offset, String sDate, String eDate) {
		this.offset = offset;
                this.startDate = sDate;
                this.endDate = eDate;
	}
	
	public void startBuilding() {
		
		buildFacility();
		System.out.println("Starting Date " + new Date().toString());
		List<CustomPatient> allPatients = patientObj.getAllPatients(offset, limit, startDate, endDate);
		System.out.println("After get all Patient " + new Date().toString());
		int counter = 0;
                PatientDao patientDaoObj = new PatientDao();
                //loop through all the patients and collect them in a map of lists
                //first item in the list is the patient, second is the obs and third is the list of encounters
		String isSurgeSite = patientDaoObj.getGlobalProperty("surge_site");
                for (CustomPatient p : allPatients) {
                         
                        /*List<Object> patientList = new ArrayList<Object>();
                        patientList.add(p);
                        
                        List<CustomObs> obsList = patientDaoObj.getObsForPatient(p.getPatientId(), null);
			patientList.add(obsList);
                        
                        List<Object> allEncounterObj = patientDaoObj.getPatientEncounters(p.getPatientId());
			patientList.add(allEncounterObj);
                        
                        allPatientsMap.put(p.getPatientId(), patientList);
                        
                        */
			XMLBuilder b = new XMLBuilder(counter, p, facilityID, facilityTypeCode, facilityName, facilityID);
			b.start();
			//p = null;
			//Runnable worker = new XMLBuilder(counter, p, facilityID, facilityTypeCode, facilityName, facilityID);
			//executor.execute(worker);
			counter++;
                        System.out.println("processed "+counter);
			/*if (counter > 1000) {
				System.out.println("Break out");
				break;
			}*/
			
		}
		System.out.println("After Loop  " + new Date().toString());
	}
	
	public void buildFacility() {
		//facilityName = PatientDao.getGlobalProperty("Facility_Name");//Utils.getFacilityName();
		//DATIMID = PatientDao.getGlobalProperty("facility_datim_code");//Utils.getFacilityDATIMId();
		facilityTypeCode = "IP";//PatientDao.getGlobalProperty("partner_short_name");;
		facilityName = PatientDao.getGlobalProperty("partner_full_name");
		facilityID = PatientDao.getGlobalProperty("partner_short_name");
		//DATIMID = PatientDao.getGlobalProperty("facility_datim_code");
		
	}
	
}
