/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.nigeriaemr.ndrfactory;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.openmrs.Patient;
import org.openmrs.PersonAddress;
import org.openmrs.api.context.Context;
import org.openmrs.module.nigeriaemr.dbmanager.ObsDAO;
import org.openmrs.module.nigeriaemr.model.ndr.AddressType;
import org.openmrs.module.nigeriaemr.model.ndr.ConditionType;
import org.openmrs.module.nigeriaemr.model.ndr.Container;
import org.openmrs.module.nigeriaemr.model.ndr.FacilityType;
import org.openmrs.module.nigeriaemr.model.ndr.FingerPrintType;
import org.openmrs.module.nigeriaemr.model.ndr.IndividualReportType;
import org.openmrs.module.nigeriaemr.model.ndr.MessageHeaderType;
import org.openmrs.module.nigeriaemr.model.ndr.PatientDemographicsType;
import org.openmrs.module.nigeriaemr.model.ndr.ProgramAreaType;
import org.openmrs.module.nigeriaemr.ndrUtils.CustomErrorHandler;
import org.openmrs.module.nigeriaemr.ndrUtils.LoggerUtils;
import org.openmrs.module.nigeriaemr.ndrUtils.Utils;
import org.openmrs.module.nigeriaemr.ndrUtils.Validator;
import org.openmrs.module.nigeriaemr.omodmodels.CustomObs;
import org.openmrs.module.nigeriaemr.omodmodels.DBConnection;
import org.openmrs.scheduler.tasks.AbstractTask;
import org.xml.sax.SAXException;

/**
 *
 * @author The Bright
 */
public class NDRCustomConverter extends AbstractTask {

    /**
     * @return the obsDAO
     */
    public ObsDAO getObsDAO() {
        return obsDAO;
    }

    /**
     * @param obsDAO the obsDAO to set
     */
    public void setObsDAO(ObsDAO obsDAO) {
        this.obsDAO = obsDAO;
    }
    private final static String messageSchemaVersion = "1.4";
    private final static String messageStatusCode = "INITIAL";
    private final static String facilityTypeCodeForIP = "IP";
    private final static String validation = "Testing new NMRS 2.0 plugin speed";
    
    private final static String conditionCoded="86406008";
    
    private final static String programAreaCode="HIV";

    private ObsDAO obsDAO;

    private NDRCustomConverter() {

    }

    private MessageHeaderType createMessageHeaderType(String ipName, String ipCode) throws DatatypeConfigurationException {
        MessageHeaderType header = new MessageHeaderType();
        FacilityType facilityType = Utils.createFacilityType(ipName, ipCode, facilityTypeCodeForIP);
        Calendar cal = Calendar.getInstance();
        header.setMessageCreationDateTime(Utils.getXmlDateTime(cal.getTime()));
        header.setMessageStatusCode(messageStatusCode);
        header.setMessageSchemaVersion(new BigDecimal(messageSchemaVersion));
        header.setMessageUniqueID(UUID.randomUUID().toString());
        header.setMessageSendingOrganization(facilityType);
        return header;
    }

    public IndividualReportType createIndividualReportType() {
        IndividualReportType individualReport = new IndividualReportType();

        return individualReport;
    }
    
    public ConditionType createConditionType(){
        ConditionType conditionType=new ConditionType();
        conditionType.setConditionCode(conditionCoded);
        ProgramAreaType p = new ProgramAreaType();
        p.setProgramAreaCode(programAreaCode);
        conditionType.setProgramArea(p);
        
        return conditionType;
    }
    public Container createContainer(Patient pts, FacilityType facility,DBConnection openmrsConnection) throws SQLException, DatatypeConfigurationException {
        obsDAO=new ObsDAO();
        NDRMainDictionary dictionary=new NDRMainDictionary();
        Container container = new Container();
        container.setValidation(validation);
        /*
          Creating PatientDemographicsType
        */
        Integer[] formEncounterTypeTargets = {Utils.ADULT_INITIAL_ENCOUNTER_TYPE, Utils.PED_INITIAL_ENCOUNTER_TYPE, Utils.INITIAL_ENCOUNTER_TYPE, Utils.HIV_Enrollment_Encounter_Type_Id, Utils.Client_Tracking_And_Termination_Encounter_Type_Id};
        Set<Integer> formEncounterTypeSet =new HashSet<>(Arrays.asList(formEncounterTypeTargets));
        int patientID=pts.getPatientId();
        List<CustomObs> patientDemographicsObs=obsDAO.getObsForPatient(patientID, formEncounterTypeSet);
        PatientDemographicsType patientDemographicsType=dictionary.createCustomPatientDemographicType2(pts, facility, patientDemographicsObs);
        /*
           Creating and setting FingerPrintType
        */
        FingerPrintType fingerPrintType=obsDAO.getPatientsFingerPrint(pts);
        patientDemographicsType.setFingerPrints(fingerPrintType);
        /*
          Create ConditionType        
        */
        ConditionType conditionType= createConditionType();
        
        /*
            Create AddressType
        */
        AddressType addressType=createPatientAddress(pts, openmrsConnection);
        conditionType.setPatientAddress(addressType);
        
        
        return container;
    }

    public Marshaller createjaxbMarshaller() throws JAXBException, JAXBException, SAXException {
        JAXBContext jaxbContext = JAXBContext.newInstance("org.openmrs.module.nigeriaemr.model.ndr");
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

    public void runNDRThread(final Class showProgressToClass, DBConnection conn, HttpServletRequest request) throws JAXBException, SAXException {
        DBConnection openmrsConn = Utils.getNmrsConnectionDetails();

        //check if global variable for logging exists
        LoggerUtils.checkLoggerGlobalProperty(openmrsConn);
        LoggerUtils.clearLogFile();

        List<Patient> patients = Context.getPatientService().getAllPatients();
        String facilityName = Utils.getFacilityName();
        String DATIMID = Utils.getFacilityDATIMId();
        String FacilityType = "FAC";
        Utils util = new Utils();
        String reportType = "NDR";
        String reportFolder = util.ensureReportFolderExist(request, reportType);

        String IPShortName = Utils.getIPShortName();

        //Create an xml file and save in today's folder
        //NDRConverter generator = new NDRConverter(Utils.getIPFullName(), IPShortName, openmrsConn);
        Marshaller jaxbMarshaller = createjaxbMarshaller();
        String formattedDate = new SimpleDateFormat("ddMMyy").format(new Date());
        FacilityType facility = Utils.createFacilityType(facilityName, DATIMID, FacilityType);
        new Thread(new Runnable() {
            @Override
            public void run() {

            }

        }).start();
        try {

            long loop_start_time = System.currentTimeMillis();
            int counter = 0;
            Container cnt = null;
            for (Patient patient : patients) {
                long startTime = System.currentTimeMillis();
                counter++;
                System.out.println("pateint  " + counter + " of " + patients.size() + " with ID " + patient.getId());

                try {
                    //cnt = createContainer(patient, facility);

                } catch (Exception ex) {

                }

                if (cnt != null) {

                    try {

                        String pepFarId = Utils.getPatientPEPFARId(patient);

                        if (pepFarId != null) { //remove forward slashes / from file names
                            pepFarId = pepFarId.replace("/", "_").replace(".", "_");
                        } else {
                            pepFarId = "";
                        }

                        String fileName = IPShortName + "_" + DATIMID + "_" + formattedDate + "_" + pepFarId;

                        // old implementation		String xmlFile = reportFolder + "\\" + fileName + ".xml";
                        String xmlFile = Paths.get(reportFolder, fileName + ".xml").toString();

                        File aXMLFile = new File(xmlFile);
                        Boolean b;
                        if (aXMLFile.exists()) {
                            b = aXMLFile.delete();
                            System.out.println("deleting file : " + xmlFile + "was successful : " + b);
                        }
                        b = aXMLFile.createNewFile();

                        System.out.println("creating xml file : " + xmlFile + "was successful : " + b);
                        writeFile(cnt, aXMLFile, jaxbMarshaller);
                    } catch (Exception ex) {

                    }
                }

                long endTime = System.currentTimeMillis();

                System.out.println("generating ndr took : " + (endTime - startTime) + " milli secs : ");

                long loop_end_time = System.currentTimeMillis();
                System.out.println("generating ndr took : " + (loop_end_time - loop_start_time) + " milli secs : ");
                //	}
            }

            //Update ndr last run date
            Utils.updateLast_NDR_Run_Date(new Date());

            String zipFileName = IPShortName + "_ " + facilityName + "_" + DATIMID + "_" + formattedDate + ".zip";

            String response = util.ZipFolder(request, reportFolder, zipFileName, reportType);

        } catch (Exception ex) {
            Utils.updateLast_NDR_Run_Date(new Date());
            String zipFileName = IPShortName + "_" + DATIMID + "_" + formattedDate + ".zip";
            util.ZipFolder(request, reportFolder, zipFileName, reportType);
            String response = "Some files exported with errors, view error log here: \n" + LoggerUtils.getExportPath();

        }
    }

    public void writeFile(Container container, File file, Marshaller jaxbMarshaller) throws SAXException, JAXBException,
            IOException {

        //CustomErrorHandler errorHandler = new CustomErrorHandler();

        try {
            //javax.xml.validation.Validator validator = jaxbMarshaller.getSchema().newValidator();
            jaxbMarshaller.marshal(container, file);
            //validator.setErrorHandler(errorHandler);
            //validator.validate(new StreamSource(file));
        } catch (Exception ex) {
            System.out.println("File " + file.getName() + " throw an exception \n" + ex.getMessage());
            //	throw ex;
        }
    }

    @Override
    public void execute() {
        Context.openSession();

        Context.closeSession();
    }
    private AddressType createPatientAddress(Patient patient, DBConnection openmrsConn) {
        AddressType p = new AddressType();
        p.setAddressTypeCode("H");
        p.setCountryCode("NGA");
        Connection connection = null;
        Statement statement = null;

        PersonAddress pa = patient.getPersonAddress();
        if (pa != null) {
            //p.setTown(pa.getAddress1());
            String lga = pa.getCityVillage();
            String state = pa.getStateProvince();

            try {
                String sql = String
                        .format(
                                "SELECT `name`, user_generated_id, 'STATE' AS 'Location' "
                                + "FROM address_hierarchy_entry WHERE level_id =2 AND NAME = '%s' "
                                + "UNION "
                                + "SELECT `name`, user_generated_id, 'LGA' AS 'Location' FROM address_hierarchy_entry "
                                + " WHERE level_id =3 AND NAME ='%s' AND parent_id = (SELECT address_hierarchy_entry_id FROM address_hierarchy_entry\n"
                                + " WHERE level_id =2 AND NAME = '%s')", state, lga, state);

                connection = DriverManager.getConnection(openmrsConn.getUrl(), openmrsConn.getUsername(),
                        openmrsConn.getPassword());
                statement = connection.createStatement();
                ResultSet result = statement.executeQuery(sql);
                while (result.next()) {
                    //String name = result.getString("name");
                    String coded_value = result.getString("user_generated_id");

                    if (result.getString("Location").contains("STATE")) {
                        p.setStateCode(coded_value);
                    } else {
                        p.setLGACode(coded_value);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                //LoggerUtils.write(NDRMainDictionary.class.getName(), e.getMessage(), LoggerUtils.LogFormat.FATAL,
                //  LogLevel.live);
            } finally {

                try {
                    if (connection != null) {
                        connection.close();
                    }

                    if (statement != null) {
                        statement.close();
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        }
        return p;
    }


}
