package org.openmrs.module.nigeriaemr.fragment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedWriter;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.nigeriaemr.model.ndr.Container;
import org.openmrs.module.nigeriaemr.model.ndr.FacilityType;
import org.openmrs.module.nigeriaemr.ndrUtils.Utils;
import org.openmrs.module.nigeriaemr.ndrfactory.NDRConverter;
import org.xml.sax.SAXException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.openmrs.Encounter;
import org.openmrs.module.nigeriaemr.ndrUtils.LoggerUtils;
import org.openmrs.module.nigeriaemr.ndrUtils.LoggerUtils.LogFormat;
import org.openmrs.module.nigeriaemr.ndrUtils.LoggerUtils.LogLevel;
import org.openmrs.module.nigeriaemr.ndrfactory.NDRConverterUpdated;
import org.openmrs.module.nigeriaemr.omodmodels.DBConnection;
import org.openmrs.module.nigeriaemr.omodmodels.FacilityLocation;
import org.openmrs.module.nigeriaemr.omodmodels.LocationModel;
import org.openmrs.module.nigeriaemr.omodmodels.PatientLocation;
import org.openmrs.module.nigeriaemr.service.FacilityLocationService;
import org.openmrs.module.nigeriaemr.xmlgenerator.XMLGenerator;
import org.springframework.web.bind.annotation.RequestParam;

public class NdrFragmentController {
	
	public static JAXBContext jaxbContext;
	public static String facName = Utils.getFacilityName();
	public static String DatimId = Utils.getFacilityDATIMId();
        public static String facilityType = "FAC";
	static {
		try {
			jaxbContext = JAXBContext.newInstance("org.openmrs.module.nigeriaemr.model.ndr");
		}
		catch (JAXBException ex) {
			ex.printStackTrace();
		}
	}
        List <Patient>listOfPatients = new ArrayList<>();
	
	public void controller() {
		
	}
	
	public String generateNDRFileByLocation(HttpServletRequest request, @RequestParam(value = "locationId") Integer locationId) throws DatatypeConfigurationException, IOException,
            SAXException, JAXBException, Throwable {

        FacilityLocationService facilityLocationService = new FacilityLocationService();
        //  Integer locationId = 8;

        DBConnection openmrsConn = Utils.getNmrsConnectionDetails();

        List<FacilityLocation> allFacilityLocations = facilityLocationService.getAllFacilityLocations();
        List<PatientLocation> allPatientLocations = facilityLocationService.getAllPatientLocation();

        FacilityLocation facilityLocation = allFacilityLocations.stream()
                .filter(a -> a.getLocation_id().equals(locationId)).findFirst().get();
        List<Integer> filteredPatientByLocation = allPatientLocations.stream()
                .filter(a -> a.getLocation_id().equals(locationId))
                .map(PatientLocation::getPatient_id).collect(Collectors.toList());

        //check if global variable for logging exists
        LoggerUtils.checkLoggerGlobalProperty(openmrsConn);
        LoggerUtils.clearLogFile();


        List<Patient> patients = Context.getPatientService().getAllPatients();
        //filter the patient by location
        List<Patient> filteredPatients = patients.stream().filter(a -> filteredPatientByLocation.contains(a.getId()))
                .collect(Collectors.toList());
        //Patient pts = null;
        //List<Patient> patients = new ArrayList<Patient>();
        //pts = Context.getPatientService().getPatient(28417);
        //patients.add(pts);

        
        String FacilityType = "FAC";
        return "Completed from first version ";
        //return startGenerateFile(request, openmrsConn, filteredPatients, facilityLocation.getFacility_name(), facilityLocation.getDatimCode(), FacilityType);
    }
	
	public String generateNDRFile(HttpServletRequest request) throws DatatypeConfigurationException, IOException,
	        SAXException, JAXBException, Throwable {
		
		DBConnection openmrsConn = Utils.getNmrsConnectionDetails();
		
		//check if global variable for logging exists
		LoggerUtils.checkLoggerGlobalProperty(openmrsConn);
		LoggerUtils.clearLogFile();
		
		//List<Patient> patients = Context.getPatientService().getAllPatients();
		String startDate = request.getParameter("startDate")+" 00:00:00";
                String endDate = request.getParameter("endDate")+" 23:59:59";
                System.out.println("start date: "+startDate);
		//Patient pts = null;
		//List<Patient> patients = new ArrayList<Patient>();
		//pts = Context.getPatientService().getPatient(28417);
		//patients.add(pts);
		
		String facilityName = Utils.getFacilityName();
		String DATIMID = Utils.getFacilityDATIMId();
		String FacilityType = "FAC";
		int counter = 1;
		Utils util = new Utils();
                String reportType = "NDR";
                String reportFolder = util.ensureReportFolderExist(request, reportType);
		
                XMLGenerator xml = new XMLGenerator(reportFolder, openmrsConn, startDate, endDate);
		xml.startGenerating();
                
                
               // XMLGenerator generator = new XMLGenerator();
                //generator.startGenerating();
                //return "hello";
                
                String formattedDate = new SimpleDateFormat("ddMMyy").format(new Date());
                //String zipFileName = IPShortName + "_ " + facilityName + "_" + DATIMID + "_" + formattedDate + ".zip";
                String zipFileName =   formattedDate + ".zip";
                //String response = "Files Exported successfully, view uploaded files here: \n"
                  //      + util.ZipFolder(request, reportFolder, zipFileName, reportType);*/
                String response = util.ZipFolder(request, reportFolder, zipFileName, reportType);
                //return response;
                ///return "file.zip";
                //String zipFileName = IPShortName + "_ " + facilityName + "_" + DATIMID + "_" + formattedDate + ".zip";
                return request.getContextPath() + "/downloads/NDR/" + zipFileName;
		//return startGenerateFile(request, openmrsConn, patients, facilityName, DATIMID, FacilityType);
		
	}
	
	private  String startGenerateFile(HttpServletRequest request, DBConnection openmrsConn,
	        List<Patient> filteredPatients, String facilityName, String DATIMID, String FacilityType) throws JAXBException,
	        SAXException {
		
		//create report download folder at the server. skip if already exist
		Utils util = new Utils();
		String reportType = "NDR";
		String reportFolder = util.ensureReportFolderExist(request, reportType);
		
		String IPShortName = Utils.getIPShortName();
		
		//Create an xml file and save in today's folder
		NDRConverterUpdated generator = new NDRConverterUpdated(Utils.getIPFullName(), IPShortName, openmrsConn);
		//JAXBContext jaxbContext = JAXBContext.newInstance("org.openmrs.module.nigeriaemr.model.ndr");
		Marshaller jaxbMarshaller = generator.createMarshaller(jaxbContext);
		
		String formattedDate = new SimpleDateFormat("ddMMyy").format(new Date());
		
		FacilityType facility = Utils.createFacilityType(facilityName, DATIMID, FacilityType);
		
		try {
			
			long loop_start_time = System.currentTimeMillis();
			int counter = 0;
			//Container cnt = null;
			ExecutorService executor = Executors.newFixedThreadPool(10);
			//BufferedWriter writer = null;
			for (Patient patient : filteredPatients) {
				String pepFarId = Utils.getPatientPEPFARId(patient);
				if (pepFarId != null) { //remove forward slashes / from file names
					pepFarId = pepFarId.replace("/", "_").replace(".", "_");
				} else {
					pepFarId = "";
				}
                                String fileName = IPShortName + "_" + DATIMID + "_" + formattedDate + "_" + pepFarId;
                                listOfPatients.add(patient);
                                List<Encounter> encs = Context.getEncounterService().getEncountersByPatient(patient);//I expect that this brought all the encounters
                                List<Encounter> filteredEncs = new ArrayList<>();
                                Date lastDate = Utils.getLastNDRDate();

                                try {

                                    //only run if patient has had any encounter between the last run date and now
                                    //LocalDate firstDate = LocalDate.of
                                    encs.forEach((enc) -> {
                                        int dateCreatedComp = enc.getDateCreated().compareTo(lastDate);
                                        int dateModifiedComp = -1;
                                        if (enc.getDateChanged() != null) {
                                            dateModifiedComp = enc.getDateChanged().compareTo(lastDate);
                                        }
                                        if (dateCreatedComp > -1 || dateModifiedComp > -1) {
                                            filteredEncs.add(enc);
                                        }
                                    });//                if (lastDate != null) {
                    //                    encs = encs.stream()
                    //                            .filter(e -> e.getDateCreated().after(lastDate)
                    //                            || e.getDateChanged().after(lastDate) || e.getDateCreated().equals(lastDate) || e.getDateChanged().equals(lastDate)).collect(Collectors.toList());
                    //                }

                                } catch (Exception ex) {
                                    System.out.println(ex.getMessage());
                                }
                                Container cnt = null;
                                OBSUtil obsObj = new OBSUtil();
                                ///obsObj.setPatientObs(Utils.extractObsfromEncounter(filteredEncs));
                                obsObj.setPatientIdentifies(patient);
                                //cnt = generator.createContainer(patient, facility, obsObj, filteredEncs);
                                System.out.println("processing "+counter+" out of "+filteredPatients.size());
				//new GenerateXMLFiles().generateXMLFile(patient, pepFarId, counter, filteredPatients.size(), generator,
				//  facility, reportFolder, IPShortName, x, jaxbContext, formattedDate);
				counter++; 
				if (counter > 4000)
					break;
				
                                
				//for (int i = 0; i < 10; i++) {
				//Patient p = patient;
				//Runnable worker = new BackgroundTask(patient, counter, filteredPatients.size(), cnt, openmrsConn, facility,
				//reportFolder, IPShortName, DATIMID, jaxbContext, formattedDate);
				/*Runnable worker = new BackgroundTask2(generator, cnt, jaxbMarshaller, fileName);
				executor.execute(worker);
                                Context.evictFromSession(patient);
                                Context.evictFromSession(cnt);
                                //Context.flushSession();
                                Context.clearSession();
                                if (counter % 100 == 0) {
                                        System.gc();
                                }*/
				//System.out.println(patient);
				//if (counter > 50)
				//	break;
				
				//if (counter > 1)
				//	break;
				//}
				
				long startTime = System.currentTimeMillis();
				//counter++;
				//System.out.println("pateint  " + counter + " of " + filteredPatients.size() + " with ID " + patient.getId());
				
				//	if (patient.getId() == 497) {
				try {
					//LoggerUtils.write(NdrFragmentController.class.getName(),
					//"#################### #################### ####################", LogFormat.FATAL, LogLevel.live);
					//LoggerUtils.write(NdrFragmentController.class.getName(), "Started Export for patient with id: "
					// + patient.getId(), LoggerUtils.LogFormat.INFO, LogLevel.live);
					
					///cnt = generator.createContainer(patient, facility);
                                        cnt = generator.createContainer(patient, facility, obsObj, filteredEncs);
					System.out.println("did not throw error");
					
				}
				catch (Exception ex) {
					System.out.println("threw error");
					//LoggerUtils.write(
					// NdrFragmentController.class.getName(),
					//MessageFormat.format("Could not parse patient with id: {0},{1},{2} ",
					//  Integer.toString(patient.getId()), "\r\n", ex.getMessage()), LogFormat.FATAL, LogLevel.live);
					cnt = null;
				}
				
				if (cnt != null) {
					//LoggerUtils.write(NdrFragmentController.class.getName(),
					//  "Got data for patient with ID: " + patient.getId(), LogFormat.INFO, LogLevel.live);
					try {
						
						//String pepFarId = Utils.getPatientPEPFARId(patient);
						
						if (pepFarId != null) { //remove forward slashes / from file names
							pepFarId = pepFarId.replace("/", "_").replace(".", "_");
						} else {
							pepFarId = "";
						}
						System.out.println("Pepfar "+pepFarId);
						//String fileName = IPShortName + "_" + DATIMID + "_" + formattedDate + "_" + pepFarId;
						
						// old implementation		String xmlFile = reportFolder + "\\" + fileName + ".xml";
						//String xmlFile = Paths.get(reportFolder, fileName + ".xml").toString();
						
						//  Path xmlFilePath = Paths.get(reportFolder, fileName + ".xml");
						String xmlFile = Paths.get(reportFolder, fileName + ".xml").toString();
						/*File aXMLFile = new File(xmlFile);
						Boolean b;
						if (aXMLFile.exists()) {
							b = aXMLFile.delete();
							System.out.println("deleting file : " + xmlFile + "was successful : " + b);
						}*/
						//b = aXMLFile.createNewFile();
						
						//System.out.println("creating xml file : " + xmlFile + "was successful : " + b);
						Runnable worker = new BackgroundTask2(generator, cnt, jaxbMarshaller, xmlFile);
						executor.execute(worker);
						//generator.writeFile(cnt, aXMLFile, jaxbMarshaller);
                                                //GenerateXMLFiles2 f = new GenerateXMLFiles2(generator, cnt, jaxbMarshaller, xmlFile);
                                                //f.generateXMLFile(generator, cnt, jaxbMarshaller, xmlFile);
						Context.evictFromSession(patient);
						//Context.evictFromSession(cnt);
						//Context.flushSession();
						//Context.clearSession();
						if (counter % 100 == 0) {
							System.gc();
						}
					}
					catch (Exception ex) {
						//LoggerUtils.write(NdrFragmentController.class.getName(), ex.getMessage(), LogFormat.FATAL,
                                            ex.printStackTrace();;
					}
				} //comments end here*/
                                else{
                                    System.out.println("container is null");
                                }
				
				/*long endTime = System.currentTimeMillis();
				LoggerUtils.write(NdrFragmentController.class.getName(),
				    "Finished Export for patient with id: " + patient.getId() + " Time Taken: " + (endTime - startTime)
				            + " milliseconds", LoggerUtils.LogFormat.INFO, LogLevel.live);
				System.out.println("generating ndr took : " + (endTime - startTime) + " milli secs : ");
				
				long loop_end_time = System.currentTimeMillis();
				System.out.println("generating ndr took : " + (loop_end_time - loop_start_time) + " milli secs : ");*/
				//	}
			}
			//executor.shutdown();
			
			//Update ndr last run date
			//Utils.updateLast_NDR_Run_Date(new Date());
			
			String zipFileName = IPShortName + "_ " + facilityName + "_" + DATIMID + "_" + formattedDate + ".zip";
			/*String response = "Files Exported successfully, view uploaded files here: \n"
			        + util.ZipFolder(request, reportFolder, zipFileName, reportType);*/
			//String response = util.ZipFolder(request, reportFolder, zipFileName, reportType);
			//return response;
			///return "file.zip";
			//String zipFileName = IPShortName + "_ " + facilityName + "_" + DATIMID + "_" + formattedDate + ".zip";
			return request.getContextPath() + "/downloads/" + zipFileName;
			//return "Completed here";
		}
		catch (Exception ex) {
			LoggerUtils.write(NdrFragmentController.class.getName(), ex.getMessage(), LoggerUtils.LogFormat.FATAL,
			    LogLevel.live);
			//Update ndr last run date
			Utils.updateLast_NDR_Run_Date(new Date());
			
			String zipFileName = IPShortName + "_" + DATIMID + "_" + formattedDate + ".zip";
			util.ZipFolder(request, reportFolder, zipFileName, reportType);
			
			//throw new Throwable(LoggerUtils.getExportPath());
			String response = "Some files exported with errors, view error log here: \n" + LoggerUtils.getExportPath();
			return response;
		}
		
	}
	
	public String getAllFacilityLocation() {
		ObjectMapper mapper = new ObjectMapper();
		FacilityLocationService facilityLocationService = new FacilityLocationService();
		String response = "";
		try {
			response = mapper.writeValueAsString(facilityLocationService.getAllFacilityLocations());
		}
		catch (JsonProcessingException ex) {
			Logger.getLogger(NdrFragmentController.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return response;
	}
	
	//get host for openmrs instance
	public String retrieveBiometricServer(String msg) {
		System.out.println("This is from the UI: " + msg);
		return Utils.getBiometricServer();
	}
	
	public String createFacilityLocation(@RequestParam(value = "falicityLocationString") String falicityLocationString) {
		
		ObjectMapper mapper = new ObjectMapper();
		FacilityLocationService facilityLocationService = new FacilityLocationService();
		int response = 0;
		
		try {
			FacilityLocation facilityLocation = mapper.readValue(falicityLocationString, FacilityLocation.class);
			facilityLocation.setCreator(Context.getAuthenticatedUser().toString());
			response = facilityLocationService.createFacilityLocation(facilityLocation);
			
		}
		catch (IOException ex) {
			Logger.getLogger(NdrFragmentController.class.getName()).log(Level.SEVERE, null, ex);
			return "Error occurred, try again";
		}
		
		if (response != -1) {
			return "Successfully created facility location";
		}
		
		return "Error occurred, try again";
		
	}
	
	public String editFacilityLocation(@RequestParam(value = "facilityLocationString") String facilityLocationString) {
		ObjectMapper mapper = new ObjectMapper();
		FacilityLocationService facilityLocationService = new FacilityLocationService();
		int response = 0;
		try {
			FacilityLocation facilityLocation = mapper.readValue(facilityLocationString, FacilityLocation.class);
			response = facilityLocationService.editFacilityLocation(facilityLocation);
		}
		catch (Exception ex) {
			Logger.getLogger(NdrFragmentController.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		if (response > 0) {
			return "Successfully updated facility location";
		}
		
		return "Error occurred, try again";
	}
	
	public void deleteFacilityLocation(@RequestParam(value = "facilityLocationUUID") String facilityLocationUUID) {
		FacilityLocationService facilityLocationService = new FacilityLocationService();
		try {
			facilityLocationService.deleteFacilityLocation(facilityLocationUUID);
			
		}
		catch (Exception ex) {
			Logger.getLogger(NdrFragmentController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public String getAllLocation() {
        List<LocationModel> locationModels = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        String locationString = "";

        try {

            Context.getLocationService().getAllLocations().stream().forEach(a -> {
                locationModels.add(new LocationModel(a.getName(), a.getLocationId()));
            });

            locationString = mapper.writeValueAsString(locationModels);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(NdrFragmentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return locationString;
    }
	
	public String getPatientLocationAggregate() {
		ObjectMapper mapper = new ObjectMapper();
		FacilityLocationService facilityLocationService = new FacilityLocationService();
		String responseString = "";
		
		try {
			responseString = mapper.writeValueAsString(facilityLocationService.getPatientLocationAggregate());
		}
		catch (JsonProcessingException ex) {
			Logger.getLogger(NdrFragmentController.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return responseString;
	}
}
