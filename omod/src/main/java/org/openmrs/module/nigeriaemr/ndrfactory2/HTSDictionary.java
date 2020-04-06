/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.nigeriaemr.ndrfactory2;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.module.nigeriaemr.model.ndr.*;
import org.openmrs.module.nigeriaemr.ndrUtils.Utils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import org.openmrs.EncounterProvider;

import org.openmrs.PatientIdentifier;
import org.openmrs.Provider;
import org.openmrs.module.nigeriaemr.fragment.controller.OBSUtil;
import org.openmrs.module.nigeriaemr.ndrUtils.ConstantsUtil;
import org.openmrs.module.nigeriaemr.ndrUtils.LoggerUtils;
import org.openmrs.module.nigeriaemr.ndrUtils.LoggerUtils.LogFormat;
import org.openmrs.module.nigeriaemr.ndrUtils.LoggerUtils.LogLevel;

import static org.openmrs.module.nigeriaemr.ndrUtils.Utils.extractObs;
import org.openmrs.module.nigeriaemr.xmlgenerator.CustomEncounter;
import org.openmrs.module.nigeriaemr.xmlgenerator.CustomObs;
import org.openmrs.module.nigeriaemr.xmlgenerator.CustomPatient;

public class HTSDictionary {

    //Knowledge assessment
    //private static int Previously_Tested_Hiv_Negative_Concept_Id = 165816;
    // private static int Client_Pregnant_Concept_Id = 166046;
    // private static int Client_Informed_About_Transsmission_Concept_Id = 165801;
    // private static int Client_Informed_About_Risk_Factors_Concept_Id = 165802;
    // private static int Client_Informed_On_Prevention_Concept_Id = 165804;
    // private static int Client_Informed_About_Possible_Concept_Id = 165884;
    //  private static int Informed_Consent_For_Hiv_Testing_Concept_Id = 165805;
    private static int Previously_Tested_Hiv_Negative_Concept_Id = 165799;
    private static int Client_Pregnant_Concept_Id = 1434;
    private static int Client_Informed_About_Transsmission_Concept_Id = 165801;
    private static int Client_Informed_About_Risk_Factors_Concept_Id = 165802;
    private static int Client_Informed_On_Prevention_Concept_Id = 165804;
    private static int Client_Informed_About_Possible_Concept_Id = 165884;
    private static int Informed_Consent_For_Hiv_Testing_Concept_Id = 1710;
    //Risk assessment
    private static int Ever_Had_Sexual_Intercourse_Concept_Id = 165800;
    private static int Blood_Transfussion_In_Last_3_Months = 1063;
    private static int Unprotected_Sex_With_Casual_Partner_In_Last_3_Months = 159218;
    private static int Unprotected_Sex_With_Regular_Partner_In_Last_3_Months = 165803;
    private static int Sti_In_Last_3_Months = 164809;
    private static int More_Than_1_Sex_Partner = 165806;

    //Clinical tb screening
    private static int Current_Cough = 143264;
    private static int Weight_loss = 832;
    private static int Fever = 140238;
    private static int Night_sweats = 133027;
    //Syndronmic Sti
    private static int Complaints_of_vaginal_discharge_or_burning_when_urinating = 165809;
    private static int Complaints_of_lower_abdominal_pains_with_or_without_vaginal_discharge = 165810;
    private static int Complaints_of_urethral_discharge_or_burning_when_urinating = 165811;
    private static int Complaints_of_scrotal_swelling_and_pain = 165812;
    private static int Complaints_of_genital_sore = 165813;
    //Post test councelling
    private static int Have_you_been_tested_for_HIV_before_within_this_year = 165881;
    private static int HIV_Request_and_Result_form_signed_by_tester = 165818;
    private static int HIV_Request_and_Result_form_filled_with_CT_Intake_Form = 165819;
    private static int Client_received_HIV_test_result = 164848;
    private static int Post_test_counseling_done = 159382;
    private static int Risk_reduction_plan_developed = 165820;
    private static int Post_test_disclosure_plan_developed = 165821;
    private static int Will_bring_partner_for_HIV_testing = 165822;
    private static int Will_bring_own_children_Less_5_years_for_HIV_testing = 165823;
    private static int Provided_with_information_on_FP_and_dual_contraception = 1382;
    private static int Client_Partner_use_FP_methods = 165883;
    private static int Client_Partner_use_condoms_as_FP_method = 5571;
    private static int Correct_condom_use_demonstrated = 165824;
    private static int Condoms_provided_to_client = 159777;
    private static int Client_referred_to_other_services = 1648;

    //Client intake form has no concept ids for partner section
    //Partner details
    private static int Partner_Age = 164955;
    private static int Partner_preTest_counselled_ = 164956;
    private static int Partner_accepts_HIV_test = 164957;
    private static int Partner_HIV_test_result = 164958;
    private static int Partner_postTest_counseled = 165571;
    private static int Partner_HBV_status = 165561;
    private static int Partner_HCV_status = 165562;

    private static int Partner_referred_to = 164960;
    //Health facility Visit
    private static int Visit_Date = 1769;
    private static int Visit_Status = 166061;
    private static int Weight = 5089;
    private static int Breast_Feeding = 165876;

    //client intake fields
    public static int HTS_Client_Intake_SETTING_ConceptID = 165839;
    public static int HTS_CLient_Intake_FIRST_TIME_ConceptID = 165790;
    public static int HTS_Client_Intake_SESSION_TYPE_ConceptID = 165793;
    public static int HTS_Client_Intake_REFFERRED_FROM = 165480;
    public static int HTS_Client_Intake_MARITAL_STATUS_ConceptID = 1054;
    public static int HTS_Client_Intake_LESS_THAN_FIVE_CHILDREN = 160312;
    public static int HTS_Client_Intake_NO_OF_COWIFES_ConceptID = 5557;
    public static int HTS_Client_Intake_Client_IDENTIFIED_INDEX = 165794;
    public static int HTS_Client_Intake_INDEX_TYPE = 165798;
    public static int HTS_Client_Intake_INDEX_CLIENT_ID = 165859;
    public static int HTS_Client_Intake_CLIENT_RETESTING = 165976;
    public static int HTS_CLIENT_INTAKE_SETTINGS_OTHERS = 5622;
    public static int HTS_CLIENT_INTAKE_SETTINGS_OTHERS_VALUE = 165966;

    //HIV Testing Result
    public static int SCREENING_TEST_RESULT = 165840;
    public static int CONFIRMATORY_TEST_RESULT = 165841;
    public static int TIE_BREAKER_RESULT = 165842;
    public static int SCREENING_TEST_RESULT_DATE = 165844;
    public static int CONFIRMATORY_TEST_RESULT_date = 165845;
    public static int TIE_BREAKER_RESULT_DATE = 165846;
    public static int FINAL_RESULT = 165843;

    //Index Notification Services
    public static int PARTNER_ELICITATION_GROUPING_CONCEPT = 165858;
    public static int PARTNER_NAME_CONCEPT = 161135;
    public static int PARTNER_GENDER_CONCEPT = 165857;
    public static int PARTNER_INDEX_TYPE_CONCEPT = 165798;
    public static int PARTNER_INDEX_DESCRIPTIVE_ADDRESS_CONCEPT = 166021;
    public static int PARTNER_INDEX_RELATION_PHONE_CONCEPT = 166022;
    
    //HIV Recency Testing
    public static int HIV_RECENCY_TEST_NAME_CONCEPT = 166216;//formerly 165849
    public static int HIV_RECENCY_TEST_DATE_CONCEPT = 165850;
    public static int HIV_RECENCY_ASSAY_CONCEPT = 165853;
    public static int SAMPLE_TEST_DATE_CONCEPT = 165854;
    public static int SAMPLE_TEST_RESULT_CONCEPT = 856;
    public static int FINAL_HIV_RECENCY_INFECTION_TESTING_ALGORITHM_RESULT_CONCEPT = 165856;
    public static int OPT_OUT_OF_RTRI = 165805;
    public static int RECENCY_NUMBER = 166209;
    public static int CONTROL_LINE = 166212;
    public static int VERIFICATION_LINE = 165976;
    public static int LONG_TERM_LINE = 166211;
    public static int RECENCY_INTERPRETATION = 166213;
    public static int VIRALLOAD_REQUEST_MADE = 143264;
    
    
    

    //syphillis and hepatitis
    private static int SYPHILLIS_STATUS_RESULT = 299;
    private static int HEPATITIS_B_RESULT = 159430;
    private static int HEPATITIS_C_RESULT = 161471;

    public HTSDictionary() {
        loadDictionary();
        loadBooleanDictionary();
        loadYNCodeTypeDictionary();
    }

    private Map<Integer, String> htsDictionary = new HashMap<>();
    private Map<Integer, Boolean> htsBooleanDictionary = new HashMap<>();
    private Map<Integer,YNCodeType> htsYNCodeTypeDict = new HashMap<>();

    private void loadDictionary() {
        //Map OpenMRS concepts to corresponding NDR values
        htsDictionary.put(1065, "Y");
        htsDictionary.put(1066, "N");
        htsDictionary.put(1067, "U");
        htsDictionary.put(703, "Pos");
        htsDictionary.put(664, "Neg");
        htsDictionary.put(1228, "R");
        htsDictionary.put(1229, "NR");
        htsDictionary.put(1382, "FP");
        htsDictionary.put(1610, "ART");
        htsDictionary.put(165815, "1");
        htsDictionary.put(165816, "2");
        htsDictionary.put(165817, "3");
        htsDictionary.put(165882, "4");

        //setting
        htsDictionary.put(160539, "1");
        htsDictionary.put(160529, "2");
        htsDictionary.put(160548, "3");
        htsDictionary.put(5271, "4");
        htsDictionary.put(160542, "5");
        htsDictionary.put(161629, "6");
        htsDictionary.put(165788, "7");
        htsDictionary.put(165838, "9");
        htsDictionary.put(5622, "8");

        //session
        htsDictionary.put(165792, "1");
        htsDictionary.put(165789, "2");
        htsDictionary.put(165885, "3");

        //refferred from
        htsDictionary.put(5622, "8");

        //marital status
        htsDictionary.put(1057, "S");
        htsDictionary.put(5555, "M");
        htsDictionary.put(1058, "D");
        htsDictionary.put(1056, "A");
        htsDictionary.put(1060, "G");
        htsDictionary.put(1059, "W");

        //index type
        htsDictionary.put(165796, "1");
        htsDictionary.put(165797, "2");
        htsDictionary.put(165795, "3");

        //recency
        htsDictionary.put(165852, "R");
        htsDictionary.put(165851, "L");
        htsDictionary.put(166215, "AS");
        htsDictionary.put(5622, "OTH");
        htsDictionary.put(165853, "L");
        htsDictionary.put(165855, "Neg");
        htsDictionary.put(165854, "Inv");
        
        
        //gender
        htsDictionary.put(165184, "M");
        htsDictionary.put(165185, "F");

    }

    private void loadBooleanDictionary() {
        //this was added because the class are boolean variable while the data is obs_coded
        htsBooleanDictionary.put(1065, true);
        htsBooleanDictionary.put(1066, false);

    }
    
    private void loadYNCodeTypeDictionary(){
        htsYNCodeTypeDict.put(1065, YNCodeType.YES);
        htsYNCodeTypeDict.put(1066, YNCodeType.NO);
    }

    private YNCodeType getYNCodeTypeValue(int key){
        YNCodeType response = YNCodeType.NO;
                
        if(htsYNCodeTypeDict.containsKey(key)){
        response = htsYNCodeTypeDict.get(key);
        }
        
        return response;
    }
            
    private Boolean getBooleanMappedValue(int key) {
        Boolean uio = htsBooleanDictionary.get(key);
        if (htsBooleanDictionary.containsKey(key)) {
            return htsBooleanDictionary.get(key);
        } else {
            return htsBooleanDictionary.get(key);
        }
    }

    public HIVTestingReportType createClientIntakeTags(CustomPatient patient, CustomEncounter enc, Map<Integer, List<CustomObs>> intakakeObs, Map<Integer, List<CustomEncounter>>allEncounters, Map<Integer, Set<Date>> uniqueEncounters, Map<Integer, IdentifierType> allPatientIdentifiers, OBSUtil obsUtil, HIVTestingReportType hivTestingReport) throws DatatypeConfigurationException {

        CustomObs obs = null;

        //for client Code
        String htsID = allPatientIdentifiers.get(Utils.HTS_IDENTIFIER_INDEX).getIDNumber();//String.valueOf(patient.getPatientIdentifier(Utils.HTS_IDENTIFIER_INDEX));

        if (htsID != null) {
            hivTestingReport.setClientCode(htsID);
        }

        //visit date and ID
        hivTestingReport.setVisitID(String.valueOf(enc.getVisitId()));
        hivTestingReport.setVisitDate(Utils.getXmlDate(enc.getEncounterDatetime()));

        //setting with others been retrieved
        // obs = extractObs(HTS_Client_Intake_SESSION_TYPE_ConceptID, allObs);
        obs = OBSUtil.getObsFromConcept(HTS_Client_Intake_SETTING_ConceptID, intakakeObs);//extractObs(HTS_Client_Intake_SETTING_ConceptID, allObs);
        if (obs != null && obs.getValueCoded() != 0) {
            if (obs.getValueCoded() == HTS_CLIENT_INTAKE_SETTINGS_OTHERS) {
                obs = OBSUtil.getObsFromConcept(HTS_CLIENT_INTAKE_SETTINGS_OTHERS_VALUE, intakakeObs);//extractObs(HTS_CLIENT_INTAKE_SETTINGS_OTHERS_VALUE, allObs);
                if (obs != null && obs.getValueText() != null) {
                    hivTestingReport.setSetting(obs.getValueText());
                }
            } else {
                hivTestingReport.setSetting(getMappedValue(obs.getValueCoded()));
            }

        }

        //first time visit
        obs = OBSUtil.getObsFromConcept(HTS_CLient_Intake_FIRST_TIME_ConceptID, intakakeObs);//extractObs(HTS_CLient_Intake_FIRST_TIME_ConceptID, allObs);
        if (obs != null && obs.getValueCoded() != 0) {
            hivTestingReport.setFirstTimeVisit(getMappedValue(obs.getValueCoded()));
        }

        //put all non required fields inside a try-catch
        try {

            //session
            obs = OBSUtil.getObsFromConcept(HTS_Client_Intake_SESSION_TYPE_ConceptID, intakakeObs);//extractObs(HTS_Client_Intake_SESSION_TYPE_ConceptID, allObs);
            if (obs != null && obs.getValueCoded() != 0) {
                hivTestingReport.setSessionType(getMappedValue(obs.getValueCoded()));
            }

            //refferred from
            obs = OBSUtil.getObsFromConcept(HTS_Client_Intake_REFFERRED_FROM, intakakeObs);//extractObs(HTS_Client_Intake_REFFERRED_FROM, allObs);
            if (obs != null && obs.getValueCoded() != 0) {
                hivTestingReport.setReferredFrom(getMappedValue(obs.getValueCoded()));
            }

            //marital status
            obs = OBSUtil.getObsFromConcept(HTS_Client_Intake_MARITAL_STATUS_ConceptID, intakakeObs); //extractObs(HTS_Client_Intake_MARITAL_STATUS_ConceptID, allObs);
            if (obs != null && obs.getValueCoded() != 0) {
                hivTestingReport.setMaritalStatus(getMappedValue(obs.getValueCoded()));
            }

            //no. of owned children less than five
            obs = OBSUtil.getObsFromConcept(HTS_Client_Intake_LESS_THAN_FIVE_CHILDREN, intakakeObs);//extractObs(HTS_Client_Intake_LESS_THAN_FIVE_CHILDREN, allObs);
            if (obs != null && obs.getValueCoded() != 0) {
                hivTestingReport.setNoOfOwnChildrenLessThan5Years(Integer.parseInt(getMappedValue(obs.getValueCoded())));
            }

            //no. of wives/co-wives
            obs = OBSUtil.getObsFromConcept(HTS_Client_Intake_NO_OF_COWIFES_ConceptID, intakakeObs);//extractObs(HTS_Client_Intake_NO_OF_COWIFES_ConceptID, allObs);
            if (obs != null && obs.getValueText() != null) {
                hivTestingReport.setNoOfAllWives(Integer.parseInt(obs.getValueText()));
            }

            //is index client
            obs = OBSUtil.getObsFromConcept(HTS_Client_Intake_Client_IDENTIFIED_INDEX, intakakeObs);//extractObs(HTS_Client_Intake_Client_IDENTIFIED_INDEX, allObs);
            if (obs != null && obs.getValueCoded() != 0) {
                hivTestingReport.setIsIndexClient(getMappedValue(obs.getValueCoded()));
            }

            //index type
            if (hivTestingReport.getIsIndexClient().equals("Y")) {
                obs = OBSUtil.getObsFromConcept(HTS_Client_Intake_INDEX_TYPE, intakakeObs);//extractObs(HTS_Client_Intake_INDEX_TYPE, allObs);
                if (obs != null && obs.getValueCoded() != 0) {
                    hivTestingReport.setIndexType(getMappedValue(obs.getValueCoded()));
                }

                //index client id
                obs = OBSUtil.getObsFromConcept(HTS_Client_Intake_INDEX_CLIENT_ID, intakakeObs);//extractObs(HTS_Client_Intake_INDEX_CLIENT_ID, allObs);
                if (obs != null && obs.getValueText() != null) {
                    hivTestingReport.setIndexClientId(obs.getValueText());
                }
            }

            //client from retesting
            obs = OBSUtil.getObsFromConcept(HTS_Client_Intake_CLIENT_RETESTING, intakakeObs);//extractObs(HTS_Client_Intake_CLIENT_RETESTING, allObs);
            if (obs != null && obs.getValueCoded() != 0) {
                hivTestingReport.setReTestingForResultVerification(getMappedValue(obs.getValueCoded()));
            }

            //syphillis and hepatitis result
            //syphilis test result
            obs = OBSUtil.getObsFromConcept(SYPHILLIS_STATUS_RESULT, intakakeObs);//extractObs(SYPHILLIS_STATUS_RESULT, allObs);
            if (obs != null && obs.getValueCoded() != 0) {
                hivTestingReport.setSyphilisTestResult(getMappedValue(obs.getValueCoded()));
            }

            //hepatitis B test result
            obs = OBSUtil.getObsFromConcept(HEPATITIS_B_RESULT, intakakeObs);//extractObs(HEPATITIS_B_RESULT, allObs);
            if (obs != null && obs.getValueCoded() != 0) {
                hivTestingReport.setHBVTestResult(getMappedValue(obs.getValueCoded()));
            }

            //hepatiis C test result
            obs = OBSUtil.getObsFromConcept(HEPATITIS_C_RESULT, intakakeObs);//extractObs(HEPATITIS_C_RESULT, allObs);
            if (obs != null && obs.getValueCoded() != 0) {
                hivTestingReport.setHCVTestResult(getMappedValue(obs.getValueCoded()));
            }

            //signature
            try {
                hivTestingReport.setCompletedBy(enc.getProviderName());
            } catch (Exception ex) {
                LoggerUtils.write(HTSDictionary.class.getName(), ex.getMessage(), LogFormat.FATAL, LogLevel.live);
            }

        } catch (Exception ex) {
            LoggerUtils.write(HTSDictionary.class.getName(), ex.getMessage(), LogFormat.FATAL, LogLevel.live);
        }

        return hivTestingReport;

    }

    public HIVTestResultType createHIVTestResult(CustomPatient patient, CustomEncounter enc, Map<Integer, List<CustomObs>> intakeObs, Map<Integer, List<CustomEncounter>>allEncounters, Map<Integer, Set<Date>> uniqueEncounters, Map<Integer, IdentifierType> allPatientIdentifiers, OBSUtil obsUtil) {
        HIVTestResultType hIVTestResultType = new HIVTestResultType();

        RecencyTestingType recencyTestingType = createRecencyType(patient,  enc,  intakeObs, allEncounters, uniqueEncounters, allPatientIdentifiers, obsUtil);
        TestResultType testResultTypes = createTestResultType(patient,  enc,  intakeObs, allEncounters, uniqueEncounters, allPatientIdentifiers, obsUtil);

        if (recencyTestingType != null) {
            hIVTestResultType.setRecencyTesting(recencyTestingType);
        }

        if (testResultTypes != null) {
            hIVTestResultType.setTestResult(testResultTypes);
        }

        if (hIVTestResultType.getRecencyTesting() == null && hIVTestResultType.getTestResult() == null) {
            return null;
        }

        return hIVTestResultType;
    }

    public IndexNotificationServicesType createIndexNotificationServicesTypes(CustomPatient patient, CustomEncounter enc, Map<Integer, List<CustomObs>> intakeObs, Map<Integer, List<CustomEncounter>>allEncounters, Map<Integer, Set<Date>> uniqueEncounters, Map<Integer, IdentifierType> allPatientIdentifiers, OBSUtil obsUtil) {
        List<PartnerNotificationType> partnerNotificationTypes = new ArrayList<>();
        IndexNotificationServicesType indexNotificationServicesType = new IndexNotificationServicesType();

        try {
            List<CustomObs> allIndexGroupObs = OBSUtil.getAllObsGroups(obsUtil.getAllObs(), PARTNER_NAME_CONCEPT);
                    //OBSUtil.getObsGroup(PARTNER_ELICITATION_GROUPING_CONCEPT, intakeObs);//Utils.getAllObsGroups(allObs, PARTNER_ELICITATION_GROUPING_CONCEPT);

            allIndexGroupObs.stream().forEach(gObs -> {

                PartnerNotificationType partnerNotificationType = new PartnerNotificationType();

                Map<Integer, CustomObs> allMembers = OBSUtil.getObsGroup2(gObs.getObsGroupID(), intakeObs);//gObs.getGroupMembers());
                //extract all the members using the concept

                //partner name
                CustomObs obs = allMembers.get(PARTNER_NAME_CONCEPT);//extractObs(PARTNER_NAME_CONCEPT, allMembers);
                if (obs != null && obs.getValueText() != null) {
                    partnerNotificationType.setPartnername(obs.getValueText());
                }

                //partner gender
                obs = allMembers.get(PARTNER_GENDER_CONCEPT);//extractObs(PARTNER_GENDER_CONCEPT, allMembers);
                if (obs != null && obs.getValueCoded() != 0) {
                    partnerNotificationType.setPartnerGender(getMappedValue(obs.getValueCoded()));
                }

                //partner index type
                obs = allMembers.get(PARTNER_INDEX_TYPE_CONCEPT);//extractObs(PARTNER_INDEX_TYPE_CONCEPT, allMembers);
                if (obs != null && obs.getValueCoded() != 0) {
                    partnerNotificationType.setIndexRelation(getMappedValue(obs.getValueCoded()));
                }

                //partner address
                obs = allMembers.get(PARTNER_INDEX_DESCRIPTIVE_ADDRESS_CONCEPT);//extractObs(PARTNER_INDEX_DESCRIPTIVE_ADDRESS_CONCEPT, allMembers);
                if (obs != null && obs.getValueText() != null) {
                    partnerNotificationType.setDescriptiveAddress(obs.getValueText());
                }

                //partner phone
                obs = allMembers.get(PARTNER_INDEX_RELATION_PHONE_CONCEPT);//extractObs(PARTNER_INDEX_RELATION_PHONE_CONCEPT, allMembers);
                if (obs != null && obs.getValueText() != null) {
                    partnerNotificationType.setPhoneNumber(obs.getValueText());
                }

                partnerNotificationTypes.add(partnerNotificationType);

            });
        } catch (Exception ex) {
            LoggerUtils.write(HTSDictionary.class.getName(), ex.getMessage(), LogFormat.FATAL, LogLevel.live);
        }

        if (!partnerNotificationTypes.isEmpty()) {
            indexNotificationServicesType.getPartner().addAll(partnerNotificationTypes);
        } else {
            return null;
        }

        return indexNotificationServicesType;
    }

    //Note returns only one recencyType object
    public RecencyTestingType createRecencyType(CustomPatient patient, CustomEncounter enc, Map<Integer, List<CustomObs>> intakeObs, Map<Integer, List<CustomEncounter>>allEncounters, Map<Integer, Set<Date>> uniqueEncounters, Map<Integer, IdentifierType> allPatientIdentifiers, OBSUtil obsUtil) {

        RecencyTestingType recencyTestingType = new RecencyTestingType();

        //test name
        CustomObs obs = OBSUtil.getObsFromConcept(HIV_RECENCY_TEST_NAME_CONCEPT, intakeObs);//extractObs(HIV_RECENCY_TEST_NAME_CONCEPT, allObs);
        if (obs != null && obs.getValueCoded() != 0) {
            recencyTestingType.setTestName(getMappedValue(obs.getValueCoded()));
        }
  
        //test date
        obs = OBSUtil.getObsFromConcept(HIV_RECENCY_TEST_DATE_CONCEPT, intakeObs);//extractObs(HIV_RECENCY_TEST_DATE_CONCEPT, allObs);
        if (obs != null && obs.getValueDate() != null) {
            try {
                recencyTestingType.setTestDate(Utils.getXmlDate(obs.getValueDate()));
            } catch (DatatypeConfigurationException ex) {
                LoggerUtils.write(HTSDictionary.class.getName(), ex.getMessage(), LogFormat.FATAL, LogLevel.live);
            }
        }

        
          //consent
        obs = OBSUtil.getObsFromConcept(OPT_OUT_OF_RTRI, intakeObs);//extractObs(OPT_OUT_OF_RTRI, allObs);
        if(obs != null && obs.getValueCoded() != 0){
            recencyTestingType.setConsent(getYNCodeTypeValue(obs.getValueCoded()));
        }
        
        //recency number
        obs = OBSUtil.getObsFromConcept(RECENCY_NUMBER, intakeObs);//extractObs(RECENCY_NUMBER, allObs);
        if(obs != null && obs.getValueText() != null){
        recencyTestingType.setRecencyNumber(obs.getValueText());
        }
        
        //control line
        obs = OBSUtil.getObsFromConcept(CONTROL_LINE, intakeObs);//extractObs(CONTROL_LINE, allObs);
        if(obs != null && obs.getValueCoded() != 0){
            recencyTestingType.setControlLine(getYNCodeTypeValue(obs.getValueCoded()));
        }
        
        //verification line
        obs = OBSUtil.getObsFromConcept(VERIFICATION_LINE, intakeObs);//extractObs(VERIFICATION_LINE, allObs);
        if(obs != null && obs.getValueCoded() != 0){
        recencyTestingType.setVerificationLine(getYNCodeTypeValue(obs.getValueCoded()));
        }
        
        //long term line
        obs = OBSUtil.getObsFromConcept(LONG_TERM_LINE, intakeObs);//extractObs(LONG_TERM_LINE, allObs);
        if(obs != null && obs.getValueCoded() != 0){
            recencyTestingType.setLongTermLine(getYNCodeTypeValue(obs.getValueCoded()));
        }
        
        //recency interpretation
        obs = OBSUtil.getObsFromConcept(RECENCY_INTERPRETATION, intakeObs);//extractObs(RECENCY_INTERPRETATION, allObs);
        if(obs != null && obs.getValueCoded() != 0){
            recencyTestingType.setRecencyInterpretation(getMappedValue(obs.getValueCoded()));
        }
        
        
        
        //rapid recency assay
        obs = OBSUtil.getObsFromConcept(HIV_RECENCY_ASSAY_CONCEPT, intakeObs);//extractObs(HIV_RECENCY_ASSAY_CONCEPT, allObs);
        if (obs != null && obs.getValueCoded() != 0) {
            recencyTestingType.setRapidRecencyAssay(getMappedValue(obs.getValueCoded()));
        }

        //VL Sample test date
        obs = OBSUtil.getObsFromConcept(SAMPLE_TEST_DATE_CONCEPT, intakeObs);//extractObs(SAMPLE_TEST_DATE_CONCEPT, allObs);
        if (obs != null && obs.getValueDate() != null) {
            try {
                recencyTestingType.setViralLoadConfirmationTestDate(Utils.getXmlDate(obs.getValueDate()));
            } catch (DatatypeConfigurationException ex) {
                LoggerUtils.write(HTSDictionary.class.getName(), ex.getMessage(), LogFormat.FATAL, LogLevel.live);
            }
        }

        //result copies
        obs = OBSUtil.getObsFromConcept(SAMPLE_TEST_RESULT_CONCEPT, intakeObs);//extractObs(SAMPLE_TEST_RESULT_CONCEPT, allObs);
        if (obs != null && obs.getValueNumeric() != 0) {
            recencyTestingType.setViralLoadConfirmationResult(obs.getValueNumeric());
        }

        //final HIV Result
        obs = OBSUtil.getObsFromConcept(FINAL_HIV_RECENCY_INFECTION_TESTING_ALGORITHM_RESULT_CONCEPT, intakeObs);//extractObs(FINAL_HIV_RECENCY_INFECTION_TESTING_ALGORITHM_RESULT_CONCEPT, allObs);
        if (obs != null && obs.getValueCoded() != 0) {
            recencyTestingType.setFinalRecencyTestResult(getMappedValue(obs.getValueCoded()));
        }

        if (recencyTestingType.isEmpty()) {
            return null;
        }

        return recencyTestingType;

    }

    public TestResultType createTestResultType(CustomPatient patient, CustomEncounter enc, Map<Integer, List<CustomObs>> intakeObs, Map<Integer, List<CustomEncounter>>allEncounters, Map<Integer, Set<Date>> uniqueEncounters, Map<Integer, IdentifierType> allPatientIdentifiers, OBSUtil obsUtil) {

        TestResultType testResultType = new TestResultType();

        //screening test result
        CustomObs obs = OBSUtil.getObsFromConcept(SCREENING_TEST_RESULT, intakeObs);//extractObs(SCREENING_TEST_RESULT, allObs);
        if (obs != null && obs.getValueCoded() != 0) {
            testResultType.setScreeningTestResult(getMappedValue(obs.getValueCoded()));
        }

        //confirmatory test result
        obs = OBSUtil.getObsFromConcept(CONFIRMATORY_TEST_RESULT, intakeObs);//extractObs(CONFIRMATORY_TEST_RESULT, allObs);
        if (obs != null && obs.getValueCoded() != 0) {
            testResultType.setConfirmatoryTestResult(getMappedValue(obs.getValueCoded()));
        }

        //tie breaker test
        obs = OBSUtil.getObsFromConcept(TIE_BREAKER_RESULT, intakeObs);//extractObs(TIE_BREAKER_RESULT, allObs);
        if (obs != null && obs.getValueCoded() != 0) {
            testResultType.setTieBreakerTestResult(getMappedValue(obs.getValueCoded()));
        }

        //screening test result date
        obs = OBSUtil.getObsFromConcept(SCREENING_TEST_RESULT_DATE, intakeObs);//extractObs(SCREENING_TEST_RESULT_DATE, allObs);
        if (obs != null && obs.getValueDate() != null) {
            try {
                testResultType.setScreeningTestResultDate(Utils.getXmlDate(obs.getValueDate()));
            } catch (DatatypeConfigurationException ex) {
                LoggerUtils.write(HTSDictionary.class.getName(), ex.getMessage(), LogFormat.FATAL, LogLevel.live);
            }
        }

        //confirmatory test result data
        obs = OBSUtil.getObsFromConcept(CONFIRMATORY_TEST_RESULT_date, intakeObs);//extractObs(CONFIRMATORY_TEST_RESULT_date, allObs);
        if (obs != null && obs.getValueDate() != null) {
            try {
                testResultType.setConfirmatoryTestResultDate(Utils.getXmlDate(obs.getValueDate()));
            } catch (DatatypeConfigurationException ex) {
                LoggerUtils.write(HTSDictionary.class.getName(), ex.getMessage(), LogFormat.FATAL, LogLevel.live);
            }
        }

        //tie test result data
        obs = OBSUtil.getObsFromConcept(TIE_BREAKER_RESULT_DATE, intakeObs);//extractObs(TIE_BREAKER_RESULT_DATE, allObs);
        if (obs != null && obs.getValueDate() != null) {
            try {
                testResultType.setTieBreakerTestResultDate(Utils.getXmlDate(obs.getValueDate()));
            } catch (DatatypeConfigurationException ex) {
                LoggerUtils.write(HTSDictionary.class.getName(), ex.getMessage(), LogFormat.FATAL, LogLevel.live);
            }
        }

        //final result
        obs = OBSUtil.getObsFromConcept(FINAL_RESULT, intakeObs);//extractObs(FINAL_RESULT, allObs);
        if (obs != null && obs.getValueCoded() != 0) {
            testResultType.setFinalTestResult(getMappedValue(obs.getValueCoded()));
        }

        if (testResultType.isEmpty()) {
            return null;
        }

        return testResultType;
    }

    public KnowledgeAssessmentType createKnowledgeAssessmentType(CustomPatient patient, CustomEncounter enc, Map<Integer, List<CustomObs>> intakeObs, Map<Integer, List<CustomEncounter>>allEncounters, Map<Integer, Set<Date>> uniqueEncounters, Map<Integer, IdentifierType> allPatientIdentifiers, OBSUtil obsUtil) throws DatatypeConfigurationException {
        IdentifierType htsIdentifier = allPatientIdentifiers.get(Utils.HTS_IDENTIFIER_INDEX);//pts.getPatientIdentifier(ConstantsUtil.HTS_IDENTIFIER_INDEX);
        KnowledgeAssessmentType knowledgeAssessmentType = null;

        if (htsIdentifier != null) {
            LoggerUtils.write(HTSDictionary.class.getName(), "htsIdentifier is not null", LogFormat.INFO, LogLevel.live);
            knowledgeAssessmentType = new KnowledgeAssessmentType();

            CustomObs obs = OBSUtil.getObsFromConcept(Previously_Tested_Hiv_Negative_Concept_Id, intakeObs);//extractObs(Previously_Tested_Hiv_Negative_Concept_Id, obsList);
            if (obs != null && obs.getValueCoded() != 0) {
                knowledgeAssessmentType.setPreviouslyTestedHIVNegative(getBooleanMappedValue(obs.getValueCoded()));
            }
            obs = OBSUtil.getObsFromConcept(Client_Pregnant_Concept_Id, intakeObs);//extractObs(Client_Pregnant_Concept_Id, obsList);
            if (obs != null) {
                knowledgeAssessmentType.setClientPregnant(obs.isValueBoolean());
            }
            obs = OBSUtil.getObsFromConcept(Client_Informed_About_Transsmission_Concept_Id, intakeObs); //extractObs(Client_Informed_About_Transsmission_Concept_Id, obsList);
            if (obs != null && obs.getValueCoded() != 0) {
                knowledgeAssessmentType.setClientInformedAboutHIVTransmissionRoutes(getBooleanMappedValue(obs.getValueCoded()));
            }
            obs = OBSUtil.getObsFromConcept(Client_Informed_About_Risk_Factors_Concept_Id, intakeObs);//extractObs(Client_Informed_About_Risk_Factors_Concept_Id, obsList);
            if (obs != null && obs.getValueCoded() != 0) {
                knowledgeAssessmentType.setClientInformedOfHIVTransmissionRiskFactors(getBooleanMappedValue(obs.getValueCoded()));
            }
            obs = OBSUtil.getObsFromConcept(Client_Informed_On_Prevention_Concept_Id, intakeObs);//extractObs(Client_Informed_On_Prevention_Concept_Id, obsList);
            if (obs != null && obs.getValueCoded() != 0) {
                knowledgeAssessmentType.setClientInformedAboutPreventingHIV(getBooleanMappedValue(obs.getValueCoded()));
            }
            obs = OBSUtil.getObsFromConcept(Client_Informed_About_Possible_Concept_Id, intakeObs);///extractObs(Client_Informed_About_Possible_Concept_Id, obsList);
            if (obs != null && obs.getValueCoded() != 0) {
                knowledgeAssessmentType.setClientInformedAboutPossibleTestResults(getBooleanMappedValue(obs.getValueCoded()));
            }
            obs = OBSUtil.getObsFromConcept(Informed_Consent_For_Hiv_Testing_Concept_Id, intakeObs);//extractObs(Informed_Consent_For_Hiv_Testing_Concept_Id, obsList);
            if (obs != null ) {
                knowledgeAssessmentType.setInformedConsentForHIVTestingGiven(obs.isValueBoolean());
            }

            if (knowledgeAssessmentType.isEmpty()) {
                return null;
            }
        }

        return knowledgeAssessmentType;
    }

    public HIVRiskAssessmentType createHivRiskAssessment(CustomPatient patient, CustomEncounter enc, Map<Integer, List<CustomObs>> intakeObs, Map<Integer, List<CustomEncounter>>allEncounters, Map<Integer, Set<Date>> uniqueEncounters, Map<Integer, IdentifierType> allPatientIdentifiers, OBSUtil obsUtil) throws DatatypeConfigurationException {
        IdentifierType htsIdentifier = allPatientIdentifiers.get(Utils.HTS_IDENTIFIER_INDEX);//pts.getPatientIdentifier(ConstantsUtil.HTS_IDENTIFIER_INDEX);
        HIVRiskAssessmentType hivRiskAssessmentType = new HIVRiskAssessmentType();

        if (htsIdentifier != null) {
            CustomObs obs = OBSUtil.getObsFromConcept(Ever_Had_Sexual_Intercourse_Concept_Id, intakeObs);//extractObs(Ever_Had_Sexual_Intercourse_Concept_Id, obsList);
            if (obs != null && obs.getValueCoded() != 0) {
                hivRiskAssessmentType.setEverHadSexualIntercourse(getBooleanMappedValue(obs.getValueCoded()));
            }
            obs = OBSUtil.getObsFromConcept(Blood_Transfussion_In_Last_3_Months, intakeObs);//extractObs(Blood_Transfussion_In_Last_3_Months, obsList);
            if (obs != null ) {
                hivRiskAssessmentType.setBloodTransfussionInLast3Months(obs.isValueBoolean());
            }
            obs = OBSUtil.getObsFromConcept(Unprotected_Sex_With_Casual_Partner_In_Last_3_Months, intakeObs);//extractObs(Unprotected_Sex_With_Casual_Partner_In_Last_3_Months, obsList);
            if (obs != null && obs.getValueCoded() != 0) {
                hivRiskAssessmentType.setUnprotectedSexWithCasualPartnerinLast3Months(getBooleanMappedValue(obs.getValueCoded()));
            }
            obs = OBSUtil.getObsFromConcept(Unprotected_Sex_With_Regular_Partner_In_Last_3_Months, intakeObs);//extractObs(Unprotected_Sex_With_Regular_Partner_In_Last_3_Months, obsList);
            if (obs != null && obs.getValueCoded() != 0) {
                hivRiskAssessmentType.setUnprotectedSexWithRegularPartnerInLast3Months(getBooleanMappedValue(obs.getValueCoded()));
            }
            obs = OBSUtil.getObsFromConcept(Sti_In_Last_3_Months, intakeObs);//extractObs(Sti_In_Last_3_Months, obsList);
            if (obs != null && obs.getValueCoded() != 0) {
                hivRiskAssessmentType.setSTIInLast3Months(getBooleanMappedValue(obs.getValueCoded()));
            }
            obs = OBSUtil.getObsFromConcept(More_Than_1_Sex_Partner, intakeObs);//extractObs(More_Than_1_Sex_Partner, obsList);
            if (obs != null && obs.getValueCoded() != 0) {
                hivRiskAssessmentType.setMoreThan1SexPartnerDuringLast3Months(getBooleanMappedValue(obs.getValueCoded()));
            }
        }
        if (hivRiskAssessmentType.isEmpty()) {
            return null;
        }

        return hivRiskAssessmentType;
    }

    public SyndromicSTIScreeningType createSyndromicsStiType(CustomPatient patient, CustomEncounter enc, Map<Integer, List<CustomObs>> intakeObs, Map<Integer, List<CustomEncounter>>allEncounters, Map<Integer, Set<Date>> uniqueEncounters, Map<Integer, IdentifierType> allPatientIdentifiers, OBSUtil obsUtil) throws DatatypeConfigurationException {
        IdentifierType htsIdentifier = allPatientIdentifiers.get(Utils.HTS_IDENTIFIER_INDEX);//pts.getPatientIdentifier(ConstantsUtil.HTS_IDENTIFIER_INDEX);
        SyndromicSTIScreeningType syndromicSTIScreeningType = null;

        if (htsIdentifier != null) {
            syndromicSTIScreeningType = new SyndromicSTIScreeningType();
            CustomObs obs = OBSUtil.getObsFromConcept(Complaints_of_vaginal_discharge_or_burning_when_urinating, intakeObs);//extractObs(Complaints_of_vaginal_discharge_or_burning_when_urinating, obsList);
            if (obs != null && obs.getValueCoded() != 0) {

                syndromicSTIScreeningType.setVaginalDischargeOrBurningWhenUrinating(getBooleanMappedValue(obs.getValueCoded()));

            } else {
                //handles male
                syndromicSTIScreeningType.setVaginalDischargeOrBurningWhenUrinating(false);
            }
            obs = OBSUtil.getObsFromConcept(Complaints_of_lower_abdominal_pains_with_or_without_vaginal_discharge, intakeObs);;//extractObs(Complaints_of_lower_abdominal_pains_with_or_without_vaginal_discharge, obsList);
            if (obs != null && obs.getValueCoded() != 0) {

                syndromicSTIScreeningType.setLowerAbdominalPainsWithOrWithoutVaginalDischarge(getBooleanMappedValue(obs.getValueCoded()));
            } else {
                //handles male
                syndromicSTIScreeningType.setLowerAbdominalPainsWithOrWithoutVaginalDischarge(false);
            }
            obs = OBSUtil.getObsFromConcept(Complaints_of_urethral_discharge_or_burning_when_urinating, intakeObs);//extractObs(Complaints_of_urethral_discharge_or_burning_when_urinating, obsList);
            if (obs != null && obs.getValueCoded() != 0) {
                syndromicSTIScreeningType.setUrethralDischargeOrBurningWhenUrinating(getBooleanMappedValue(obs.getValueCoded()));
            } else {
                //handles female
                syndromicSTIScreeningType.setUrethralDischargeOrBurningWhenUrinating(false);

            }
            obs = OBSUtil.getObsFromConcept(Complaints_of_scrotal_swelling_and_pain, intakeObs);//extractObs(Complaints_of_scrotal_swelling_and_pain, obsList);
            if (obs != null && obs.getValueCoded() != 0) {
                syndromicSTIScreeningType.setScrotalSwellingAndPain(getBooleanMappedValue(obs.getValueCoded()));
            } else {
                //handles female
                syndromicSTIScreeningType.setScrotalSwellingAndPain(false);
            }
            obs = OBSUtil.getObsFromConcept(Complaints_of_genital_sore, intakeObs);//extractObs(Complaints_of_genital_sore, obsList);
            if (obs != null && obs.getValueCoded() != 0) {
                syndromicSTIScreeningType.setGenitalSoreOrSwollenInguinalLymphNodes(getBooleanMappedValue(obs.getValueCoded()));
            }
        }

        return syndromicSTIScreeningType;
    }

    public PostTestCounsellingType createPostTestCouncellingType(CustomPatient patient, CustomEncounter enc, Map<Integer, List<CustomObs>> intakeObs, Map<Integer, List<CustomEncounter>>allEncounters, Map<Integer, Set<Date>> uniqueEncounters, Map<Integer, IdentifierType> allPatientIdentifiers, OBSUtil obsUtil) throws DatatypeConfigurationException {
        IdentifierType htsIdentifier = allPatientIdentifiers.get(Utils.HTS_IDENTIFIER_INDEX);//pts.getPatientIdentifier(ConstantsUtil.HTS_IDENTIFIER_INDEX);
        PostTestCounsellingType postTestCounsellingType = null;

        if (htsIdentifier != null) {
            postTestCounsellingType = new PostTestCounsellingType();
            CustomObs obs = OBSUtil.getObsFromConcept(Have_you_been_tested_for_HIV_before_within_this_year, intakeObs);//extractObs(Have_you_been_tested_for_HIV_before_within_this_year, obsList);
            if (obs != null && obs.getValueCoded() != 0) {
                //LoggerUtils.write(HTSDictionary.class.getName(), "About to pull Have_you_been_tested_for_HIV_before_within_this_year", LogFormat.FATAL, LogLevel.debug);
                postTestCounsellingType.setTestedForHIVBeforeWithinThisYear(getMappedValue(obs.getValueCoded()));
                //LoggerUtils.write(HTSDictionary.class.getName(), "Finished pulling Have_you_been_tested_for_HIV_before_within_this_year", LogFormat.FATAL, LogLevel.debug);
            }
            obs = OBSUtil.getObsFromConcept(HIV_Request_and_Result_form_signed_by_tester, intakeObs);//extractObs(HIV_Request_and_Result_form_signed_by_tester, obsList);
            if (obs != null && obs.getValueCoded() != 0) {
                postTestCounsellingType.setHIVRequestAndResultFormSignedByTester(getBooleanMappedValue(obs.getValueCoded()));
            }
            obs = OBSUtil.getObsFromConcept(HIV_Request_and_Result_form_filled_with_CT_Intake_Form, intakeObs);//extractObs(HIV_Request_and_Result_form_filled_with_CT_Intake_Form, obsList);
            if (obs != null && obs.getValueCoded() != 0) {
                postTestCounsellingType.setHIVRequestAndResultFormFilledWithCTIForm(getBooleanMappedValue(obs.getValueCoded()));
            }
            obs = OBSUtil.getObsFromConcept(Client_received_HIV_test_result, intakeObs);//extractObs(Client_received_HIV_test_result, obsList);
            if (obs != null && obs.getValueCoded() != 0) {
                postTestCounsellingType.setClientRecievedHIVTestResult(getBooleanMappedValue(obs.getValueCoded()));
            }
            obs = OBSUtil.getObsFromConcept(Post_test_counseling_done, intakeObs);//extractObs(Post_test_counseling_done, obsList);
            if (obs != null && obs.getValueCoded() != 0) {
                postTestCounsellingType.setPostTestCounsellingDone(getBooleanMappedValue(obs.getValueCoded()));
            }
            obs = OBSUtil.getObsFromConcept(Risk_reduction_plan_developed, intakeObs);//extractObs(Risk_reduction_plan_developed, obsList);
            if (obs != null && obs.getValueCoded() != 0) {
                postTestCounsellingType.setRiskReductionPlanDeveloped(getBooleanMappedValue(obs.getValueCoded()));
            }
            obs = OBSUtil.getObsFromConcept(Post_test_disclosure_plan_developed, intakeObs);///extractObs(Post_test_disclosure_plan_developed, obsList);
            if (obs != null && obs.getValueCoded() != 0) {
                postTestCounsellingType.setPostTestDisclosurePlanDeveloped(getBooleanMappedValue(obs.getValueCoded()));
            }
            obs = OBSUtil.getObsFromConcept(Will_bring_partner_for_HIV_testing, intakeObs);//extractObs(Will_bring_partner_for_HIV_testing, obsList);
            if (obs != null && obs.getValueCoded() != 0) {
                postTestCounsellingType.setWillBringPartnerForHIVTesting(getBooleanMappedValue(obs.getValueCoded()));
            }
            obs = OBSUtil.getObsFromConcept(Will_bring_own_children_Less_5_years_for_HIV_testing, intakeObs);//extractObs(Will_bring_own_children_Less_5_years_for_HIV_testing, obsList);
            if (obs != null && obs.getValueCoded() != 0) {
                postTestCounsellingType.setWillBringOwnChildrenForHIVTesting(getBooleanMappedValue(obs.getValueCoded()));
            }
            obs = OBSUtil.getObsFromConcept(Provided_with_information_on_FP_and_dual_contraception, intakeObs);////extractObs(Provided_with_information_on_FP_and_dual_contraception, obsList);
            if (obs != null && obs.getValueCoded() != 0) {
                postTestCounsellingType.setProvidedWithInformationOnFPandDualContraception(getBooleanMappedValue(obs.getValueCoded()));
            }
            obs = OBSUtil.getObsFromConcept(Client_Partner_use_FP_methods, intakeObs);//extractObs(Client_Partner_use_FP_methods, obsList);
            if (obs != null && obs.getValueCoded() != 0) {
                postTestCounsellingType.setClientOrPartnerUseFPMethodsOtherThanCondoms(getBooleanMappedValue(obs.getValueCoded()));
            }
            obs = OBSUtil.getObsFromConcept(Client_Partner_use_condoms_as_FP_method, intakeObs);//extractObs(Client_Partner_use_condoms_as_FP_method, obsList);
            if (obs != null ) {
                postTestCounsellingType.setClientOrPartnerUseCondomsAsOneFPMethods(obs.isValueBoolean());
            }
            obs = OBSUtil.getObsFromConcept(Correct_condom_use_demonstrated, intakeObs);///extractObs(Correct_condom_use_demonstrated, obsList);
            if (obs != null && obs.getValueCoded() != 0) {
                postTestCounsellingType.setCorrectCondomUseDemonstrated(getBooleanMappedValue(obs.getValueCoded()));
            }
            obs = OBSUtil.getObsFromConcept(Condoms_provided_to_client, intakeObs);//extractObs(Condoms_provided_to_client, obsList);
            if (obs != null && obs.getValueCoded() != 0) {
                postTestCounsellingType.setCondomsProvidedToClient(getBooleanMappedValue(obs.getValueCoded()));
            }
            obs = OBSUtil.getObsFromConcept(Client_referred_to_other_services, intakeObs);//extractObs(Client_referred_to_other_services, obsList);
            if (obs != null ) {
                postTestCounsellingType.setClientReferredToOtherServices(obs.isValueBoolean());
            }
        }

        return postTestCounsellingType;
    }

    public ClinicalTBScreeningType createClinicalTbScreening(CustomPatient patient, CustomEncounter enc, Map<Integer, List<CustomObs>> intakeObs, Map<Integer, List<CustomEncounter>>allEncounters, Map<Integer, Set<Date>> uniqueEncounters, Map<Integer, IdentifierType> allPatientIdentifiers, OBSUtil obsUtil) throws DatatypeConfigurationException {
        IdentifierType htsIdentifier = allPatientIdentifiers.get(ConstantsUtil.HTS_IDENTIFIER_INDEX);//pts.getPatientIdentifier(ConstantsUtil.HTS_IDENTIFIER_INDEX);
        
        if (htsIdentifier == null) {
            return null;
        }
        ClinicalTBScreeningType clinicalTBScreeningType = new ClinicalTBScreeningType();

        CustomObs obs = OBSUtil.getObsFromConcept(Current_Cough, intakeObs);//extractObs(Current_Cough, obsList);
        if (obs != null && obs.getValueCoded() != 0) {
            clinicalTBScreeningType.setCurrentlyCough(getBooleanMappedValue(obs.getValueCoded()));
        }
        obs = OBSUtil.getObsFromConcept(Weight_loss, intakeObs);//extractObs(Weight_loss, obsList);
        if (obs != null) {
            clinicalTBScreeningType.setWeightLoss(obs.isValueBoolean());
        }
        obs = OBSUtil.getObsFromConcept(Fever, intakeObs);//extractObs(Fever, obsList);
        if (obs != null && obs.getValueCoded() != 0) {
            clinicalTBScreeningType.setFever(getBooleanMappedValue(obs.getValueCoded()));
        }
        obs = OBSUtil.getObsFromConcept(Night_sweats, intakeObs);//extractObs(Night_sweats, obsList);
        if (obs != null) {
            clinicalTBScreeningType.setNightSweats(obs.isValueBoolean());
        }

        return clinicalTBScreeningType;
    }

    public PartnerDetailsType createPartnerDetails(CustomPatient pts, OBSUtil obsUtil,  Map<Integer, List<CustomEncounter>> allPatientEncounterList, Map<Integer, Set<Date>> uniqueEncounters,  Map<Integer, IdentifierType>allPatientIdentifier) throws DatatypeConfigurationException {
        //PatientIdentifier htsIdentifier = pts.getPatientIdentifier(ConstantsUtil.HTS_IDENTIFIER_INDEX);
        IdentifierType htsIdentifier = allPatientIdentifier.get(Utils.HTS_IDENTIFIER_INDEX);
        PartnerDetailsType partnerDetailsType = new PartnerDetailsType();
        int value_numeric;

        if (htsIdentifier != null) {

            CustomObs obs = obsUtil.getObsForConcept(Partner_Age);//extractObs(Partner_Age, obsList);
            if (obs != null && obs.getValueNumeric() != 0) {
                value_numeric = (int) Math.round(obs.getValueNumeric());
                partnerDetailsType.setPartnerAge(value_numeric);
            }
            obs = obsUtil.getObsForConcept(Partner_preTest_counselled_);//extractObs(Partner_preTest_counselled_, obsList);
            if (obs != null && obs.getValueCoded() != 0) {
                //LoggerUtils.write(HTSDictionary.class.getName(), "About to pull Partner_preTest_counselled_", LogFormat.FATAL, LogLevel.debug);
                partnerDetailsType.setPartnerPreTestCounseled(getMappedValue(obs.getValueCoded()));
                //LoggerUtils.write(HTSDictionary.class.getName(), "Finished pulling Partner_preTest_counselled_", LogFormat.FATAL, LogLevel.debug);
            }
            obs = obsUtil.getObsForConcept(Partner_accepts_HIV_test);//extractObs(Partner_accepts_HIV_test, obsList);
            if (obs != null && obs.getValueCoded() != 0) {
                //LoggerUtils.write(HTSDictionary.class.getName(), "About to pull Partner_accepts_HIV_test", LogFormat.FATAL, LogLevel.debug);
                partnerDetailsType.setPartnerAcceptsHIVTest(getMappedValue(obs.getValueCoded()));
                //LoggerUtils.write(HTSDictionary.class.getName(), "Finished pulling Partner_accepts_HIV_test", LogFormat.FATAL, LogLevel.debug);
            }
            obs = obsUtil.getObsForConcept(Partner_HIV_test_result);//extractObs(Partner_HIV_test_result, obsList);
            if (obs != null && obs.getValueCoded() != 0) {
                //LoggerUtils.write(HTSDictionary.class.getName(), "About to pull Partner_HIV_test_result", LogFormat.FATAL, LogLevel.debug);
                partnerDetailsType.setPartnerHIVTestResult(getMappedValue(obs.getValueCoded()));
                //LoggerUtils.write(HTSDictionary.class.getName(), "Finished pulling Partner_HIV_test_result", LogFormat.FATAL, LogLevel.debug);
            }
            obs = obsUtil.getObsForConcept(Partner_postTest_counseled);//extractObs(Partner_postTest_counseled, obsList);
            if (obs != null && obs.getValueCoded() != 0) {
                //LoggerUtils.write(HTSDictionary.class.getName(), "About to pull Partner_postTest_counseled", LogFormat.FATAL, LogLevel.debug);
                partnerDetailsType.setPartnerPostTestCounseled(getMappedValue(obs.getValueCoded()));
                //LoggerUtils.write(HTSDictionary.class.getName(), "Finished pulling Partner_postTest_counseled", LogFormat.FATAL, LogLevel.debug);
            }
            obs = obsUtil.getObsForConcept(Partner_HBV_status);//extractObs(Partner_HBV_status, obsList);
            if (obs != null && obs.getValueCoded() != 0) {
                //LoggerUtils.write(HTSDictionary.class.getName(), "About to pull Partner_HBV_status", LogFormat.FATAL, LogLevel.debug);
                partnerDetailsType.setPartnerHBVStatus(getMappedValue(obs.getValueCoded()));
                //LoggerUtils.write(HTSDictionary.class.getName(), "Finished pulling Partner_HBV_status", LogFormat.FATAL, LogLevel.debug);
            }
            obs = obsUtil.getObsForConcept(Partner_HCV_status);//extractObs(Partner_HCV_status, obsList);
            if (obs != null && obs.getValueCoded() != 0) {
               // LoggerUtils.write(HTSDictionary.class.getName(), "About to pull Partner_HCV_status", LogFormat.FATAL, LogLevel.debug);
                partnerDetailsType.setPartnerHCVStatus(getMappedValue(obs.getValueCoded()));
                //LoggerUtils.write(HTSDictionary.class.getName(), "Finished pulling Partner_HCV_status", LogFormat.FATAL, LogLevel.debug);
            }
            obs = obsUtil.getObsForConcept(SYPHILLIS_STATUS_RESULT);//extractObs(SYPHILLIS_STATUS_RESULT, obsList);
            if (obs != null && obs.getValueCoded() != 0) {
                //LoggerUtils.write(HTSDictionary.class.getName(), "About to pull Partner_syphilis_status", LogFormat.FATAL, LogLevel.debug);
                partnerDetailsType.setPartnerSyphilisStatus(getMappedValue(obs.getValueCoded()));
                //LoggerUtils.write(HTSDictionary.class.getName(), "Finished pulling Partner_syphilis_status", LogFormat.FATAL, LogLevel.debug);
            }

            obs = obsUtil.getObsForConcept(Partner_referred_to);//extractObs(Partner_referred_to, obsList);
            if (obs != null && obs.getValueCoded() != 0) {
                //LoggerUtils.write(HTSDictionary.class.getName(), "About to pull Partner_referred_to", LogFormat.FATAL, LogLevel.debug);
                partnerDetailsType.setPartnerReferredTo(getMappedValue(obs.getValueCoded()));
                //LoggerUtils.write(HTSDictionary.class.getName(), "Finished pulling Partner_referred_to", LogFormat.FATAL, LogLevel.debug);
            }

        }

        return partnerDetailsType;
    }

    public HealthFacilityVisitsType createHealthFacilityVisit(Patient pts, Encounter enc, List<Obs> obsList) throws DatatypeConfigurationException {
        HealthFacilityVisitsType healthFacilityVisitsType = new HealthFacilityVisitsType();

        return healthFacilityVisitsType;
    }

    private String getMappedValue(int conceptID) {
        try {
            return htsDictionary.get(conceptID);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return "";
        }
    }
}
