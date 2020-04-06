/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.nigeriaemr.ndrfactory2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.lang3.StringUtils;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.module.nigeriaemr.dbmanager.PatientDao;
import org.openmrs.module.nigeriaemr.fragment.controller.OBSUtil;
import org.openmrs.module.nigeriaemr.model.ndr.CodedSimpleType;
import org.openmrs.module.nigeriaemr.model.ndr.CommonQuestionsType;
import org.openmrs.module.nigeriaemr.model.ndr.ConditionSpecificQuestionsType;
import org.openmrs.module.nigeriaemr.model.ndr.FacilityType;
import org.openmrs.module.nigeriaemr.model.ndr.FingerPrintType;
import org.openmrs.module.nigeriaemr.model.ndr.HIVQuestionsType;
import org.openmrs.module.nigeriaemr.model.ndr.IdentifierType;
import org.openmrs.module.nigeriaemr.model.ndr.IdentifiersType;
import org.openmrs.module.nigeriaemr.model.ndr.LeftHandType;
import org.openmrs.module.nigeriaemr.model.ndr.PatientDemographicsType;
import org.openmrs.module.nigeriaemr.model.ndr.RightHandType;
import org.openmrs.module.nigeriaemr.ndrUtils.LoggerUtils;
import org.openmrs.module.nigeriaemr.ndrUtils.LoggerUtils.LogFormat;
import org.openmrs.module.nigeriaemr.ndrUtils.Utils;
import static org.openmrs.module.nigeriaemr.ndrUtils.Utils.getXmlDate;
import org.openmrs.module.nigeriaemr.omodmodels.DBConnection;
import org.openmrs.module.nigeriaemr.xmlgenerator.CustomEncounter;
import org.openmrs.module.nigeriaemr.xmlgenerator.CustomObs;
import org.openmrs.module.nigeriaemr.xmlgenerator.CustomPatient;
import org.openmrs.module.nigeriaemr.xmlgenerator.IndividualReportBuilder;

/**
 * @author The Bright The goal of this class is to abstract the creation of a CommonQuestionsType
 *         ConditionSpecificQuestionsType HIVQuestionsType
 */
public class NDRCommonQuestionsDictionary {
	
	private Map<Integer, String> map;
	
	private PharmacyDictionary pharmacyDictionary;
	
	public NDRCommonQuestionsDictionary() {
		loadDictionary();
		pharmacyDictionary = new PharmacyDictionary();
	}
	
	private void loadDictionary() {
		map = new HashMap<Integer, String>();
		//map.put(123, "PatientDeceasedIndicator");
		//map.put(124, "DeceasedIndicator");
		//map.put(125, "DeceasedIndicator");
		//map.put(126, "DeceasedIndicator");
		
		//PREGNANCY STATUS
		map.put(165048, "P"); //Pregnant
		map.put(165047, "NP");
		//map.put(128, "NK");
		map.put(165049, "PMTCT");
		
		//EDUCATIONAL_LEVEL MAPPING
		map.put(1107, "1");
		map.put(1713, "2");
		map.put(1714, "3");
		map.put(160292, "6");
		
		/* OCCUPATIONAL CODE */
		map.put(123801, "UNE");
		map.put(1540, "EMP");
		map.put(159465, "STU");
		map.put(159461, "RET");
		map.put(1175, "NA");
		map.put(1067, "UNK");
		
		//MARITAL STATUS CODE
		map.put(1057, "S");
		map.put(5555, "M");
		map.put(1058, "D");
		map.put(1056, "A");
		map.put(1059, "W");
		
		//FUNCTIONAL STATUS
		map.put(159468, "W");
		map.put(162752, "B");
		map.put(160026, "A");
		
		//WHO STAGING
		map.put(1204, "1");
		map.put(1205, "2");
		map.put(1206, "3");
		map.put(1207, "4");
		
		// TB Status
		map.put(1660, "1");
		map.put(142177, "2");
		map.put(166042, "3");
		map.put(1661, "5");
		map.put(1662, "4");
		
		//care entry point
		map.put(160539, "3"); //HTS
		map.put(160538, "9"); //ANC/PMTCT
		map.put(160536, "12"); //In Patient
		map.put(160537, "12");//Current Clinic Patient
		map.put(160542, "2");//OPD
		map.put(160541, "6");//TB DOTS
		map.put(160543, "4");//CBO
		map.put(160545, "4");//Outreaches
		map.put(160546, "1"); //STI
		//  map.put(5622, ""); //Other
		
		//Mode of HIV Test
		map.put(164949, "HIVAb");
		map.put(164948, "HIVPCR");
		
		//Prior ART Exposure
		map.put(1107, "N");
		map.put(165241, "E");
		map.put(165240, "P");
		map.put(165238, "T");
		map.put(165239, "T");
		//Reason Medically Eligible
		map.put(164426, "1");
		map.put(5497, "2");
		map.put(730, "3");
		map.put(164427, "4");
		
	}
	
	public PatientDemographicsType createPatientDemographicsType(CustomPatient pts, FacilityType facility, OBSUtil obsUtil,
	        Map<Integer, IdentifierType> allPatientIdentifier) throws DatatypeConfigurationException {
		/*
		    PatientDemographicsType
		      -PatientIdentifier
		       -TreatmentFacility
		-OtherPatientIdentifiers
		-PatientDateOfBirth
		-PatientSexCode
		-PatientDeceasedIndicator
		-PatientDeceasedDate
		-PatientPrimaryLanguageCode
		-PatientEducationLevelCode
		-PatientOccupationCode
		-PatientMaritalStatusCode
		-StateOfNigeriaOriginCode
		-PatientNotes (NoteType)
		-FingerPrints
		-EnrolleeCode
		-TelephoneType
		-Extension
		-EmailAddress
		-TelephoneTypeCode
		
		 */
		PatientDemographicsType demo = new PatientDemographicsType();
		try {
			
			//Identifier 4 is Pepfar ID
			PatientDao patientObj = new PatientDao();
			
			/*pidOthers = pts.getPatientIdentifier(Utils.OTHER_IDENTIFIER_INDEX);
			htsId = pts.getPatientIdentifier(Utils.HTS_IDENTIFIER_INDEX);
			ancId = pts.getPatientIdentifier(Utils.PMTCT_IDENTIFIER_INDEX);
			exposedInfantId = pts.getPatientIdentifier(Utils.EXPOSE_INFANT_IDENTIFIER_INDEX);
			pepId = pts.getPatientIdentifier(Utils.PEP_IDENTIFIER_INDEX);
			pepfarid = pts.getPatientIdentifier(Utils.PEPFAR_IDENTIFIER_INDEX);*/
			
			IdentifierType idt;
			IdentifiersType identifiersType = new IdentifiersType();
			// Use PepfarID as preferred ID if it exist, else use other IDs
			if (allPatientIdentifier.containsKey(Utils.PEPFAR_IDENTIFIER_INDEX)) {
				
				demo.setPatientIdentifier(allPatientIdentifier.get(Utils.PEPFAR_IDENTIFIER_INDEX).getIDNumber());
			} else {
				// demo.setPatientIdentifier(facility.getFacilityID() + "_" + pts.getPatientIdentifier(Utils.OTHER_IDENTIFIER_INDEX).getIdentifier() + "_" + pts.getId());
			}
			if (allPatientIdentifier.containsKey(Utils.HOSPITAL_IDENTIFIER_INDEX)) {
				identifiersType.getIdentifier().add(allPatientIdentifier.get(Utils.HOSPITAL_IDENTIFIER_INDEX));
			}
			if (allPatientIdentifier.containsKey(Utils.OTHER_IDENTIFIER_INDEX)) {
				identifiersType.getIdentifier().add(allPatientIdentifier.get(Utils.OTHER_IDENTIFIER_INDEX));
			}
			if (allPatientIdentifier.containsKey(Utils.HTS_IDENTIFIER_INDEX)) {
				identifiersType.getIdentifier().add(allPatientIdentifier.get(Utils.HTS_IDENTIFIER_INDEX));
			}
			if (allPatientIdentifier.containsKey(Utils.PMTCT_IDENTIFIER_INDEX)) {
				identifiersType.getIdentifier().add(allPatientIdentifier.get(Utils.PMTCT_IDENTIFIER_INDEX));
			}
			if (allPatientIdentifier.containsKey(Utils.EXPOSE_INFANT_IDENTIFIER_INDEX)) {
				identifiersType.getIdentifier().add(allPatientIdentifier.get(Utils.EXPOSE_INFANT_IDENTIFIER_INDEX));
			}
			if (allPatientIdentifier.containsKey(Utils.PEP_IDENTIFIER_INDEX)) {
				identifiersType.getIdentifier().add(allPatientIdentifier.get(Utils.PEP_IDENTIFIER_INDEX));
			}
			demo.setOtherPatientIdentifiers(identifiersType);
			demo.setTreatmentFacility(facility);
			
			demo.setOtherPatientIdentifiers(identifiersType);
			//demo.setTreatmentFacility(facility);
			
			String gender = pts.getGender();
			if (gender.equals("M") || gender.equalsIgnoreCase("Male")) {
				demo.setPatientSexCode("M");
			} else if (gender.equals("F") || gender.equalsIgnoreCase("Female")) {
				demo.setPatientSexCode("F");
			}
			if (gender.equals("M") || gender.equalsIgnoreCase("Male")) {
				demo.setPatientSexCode("M");
			} else if (gender.equals("F") || gender.equalsIgnoreCase("Female")) {
				demo.setPatientSexCode("F");
			}
			System.out.println(pts.getDateOfBirth());
			demo.setPatientDateOfBirth(getXmlDate(pts.getDateOfBirth()));
			/*
			 * Edited By Johnbosco
			 * */
			//check Finger Print if available
			demo.setFingerPrints(patientObj.getPatientsFingerPrint(pts.getPatientId()));
			
			//collect data for SURGE
			if (IndividualReportBuilder.isSurgeSite == "true") {
				demo.setFamilyName(pts.getLastName());
				demo.setFirstName(pts.getFirstName());
				demo.setOtherName(pts.getMiddleName());
				demo.setPhoneNumber(pts.getPhoneNumber());
			}
			try {
				String testCode = pts.getLastName() + " " + pts.getFirstName() + "" + pts.getMiddleName();
				Soundex soundex = new Soundex();
				demo.setEnrolleeCode(soundex.encode(testCode));
			}
			catch (Exception ex) {
				//
			}
			
			String ndrCodedValue;
			Integer[] formEncounterTypeTargets = { Utils.ADULT_INITIAL_ENCOUNTER_TYPE, Utils.PED_INITIAL_ENCOUNTER_TYPE,
			        Utils.INITIAL_ENCOUNTER_TYPE, Utils.HIV_Enrollment_Encounter_Type_Id,
			        Utils.Client_Tracking_And_Termination_Encounter_Type_Id };
			
			//List<Obs> obsListForEncounterTypes = Utils.extractObsListForEncounterType(allObsListForPatient,
			// formEncounterTypeTargets);
			Map<Integer, List<CustomObs>> obsListForEncounterTypes = obsUtil
			        .getAllObsByEncounterTypeId(formEncounterTypeTargets);
			CustomObs obs = null;
			if (obsListForEncounterTypes != null && !obsListForEncounterTypes.isEmpty()) {
				//check for disease indicator
				obs = OBSUtil.getObsFromConcept(Utils.REASON_FOR_TERMINATION_CONCEPT, obsListForEncounterTypes);//Utils.extractObs(Utils.REASON_FOR_TERMINATION_CONCEPT, obsListForEncounterTypes);
				if (obs != null && obs.getValueCoded() != 0) {
					if (obs.getValueCoded() == Utils.DEAD_CONCEPT) {
						demo.setPatientDeceasedIndicator(true);
						obs = OBSUtil.getObsFromConcept(Utils.DATE_OF_TERMINATION_CONCEPT, obsListForEncounterTypes);//Utils.extractObs(Utils.DATE_OF_TERMINATION_CONCEPT, obsListForEncounterTypes);
						//set date
						if (obs != null) {
							demo.setPatientDeceasedDate(getXmlDate(obs.getObsDatetime()));
						}
					} else {
						demo.setPatientDeceasedIndicator(false);
					}
				}
				//check Educational level
				obs = OBSUtil.getObsFromConcept(Utils.EDUCATIONAL_LEVEL_CONCEPT, obsListForEncounterTypes);
				
				//System.out.println(obsListForEncounterTypes.toString());
				if (obs != null && obs.getValueCoded() != 0) {
					ndrCodedValue = getMappedValue(obs.getValueCoded());
					if (ndrCodedValue.equals("N")) {
						demo.setPatientEducationLevelCode("1");
					} else {
						demo.setPatientEducationLevelCode(ndrCodedValue);
					}
					
				}
				//check primary Concept Id
				//obs = Utils.extractObs(Utils.PRIMARY_LANGUAGE_CONCEPT, obsListForEncounterTypes);
				//if (obs != null) {
				//ndrCodedValue = getMappedValue(obs.getValueCoded().getConceptId());
				//demo.setPatientPrimaryLanguageCode(ndrCodedValue);
				// }
				//check Occupational Code
				obs = OBSUtil.getObsFromConcept(Utils.OCCUPATIONAL_STATUS_CONCEPT, obsListForEncounterTypes);
				if (obs != null && obs.getValueCoded() != 0) {
					ndrCodedValue = getMappedValue(obs.getValueCoded());
					demo.setPatientOccupationCode(ndrCodedValue);
				}
				//check Marital Status Code
				obs = OBSUtil.getObsFromConcept(Utils.MARITAL_STATUS_CONCEPT, obsListForEncounterTypes);
				if (obs != null && obs.getValueCoded() != 0) {
					ndrCodedValue = getMappedValue(obs.getValueCoded());
					demo.setPatientMaritalStatusCode(ndrCodedValue);
				}
			}
			
			return demo;
		}
		catch (Exception ex) {
			//LoggerUtils.write(NDRMainDictionary.class.getName(), ex.getMessage(), LoggerUtils.LogFormat.FATAL, LoggerUtils.LogLevel.live);
			//throw new DatatypeConfigurationException(Arrays.toString(ex.getStackTrace()));
		}
		
		return demo;
		
	}
	
	private String getMappedValue(int conceptID) {
		if (map.containsKey(conceptID)) {
			return map.get(conceptID);
		}
		return "";
	}
	
	public FingerPrintType getPatientsFingerPrint(int id, DBConnection connResult) {
		Connection connection;
		try {
			
			//  DBConnection connResult = Utils.getNmrsConnectionDetails();
			connection = DriverManager
			        .getConnection(connResult.getUrl(), connResult.getUsername(), connResult.getPassword());
			Statement statement = connection.createStatement();
			String sqlStatement = ("SELECT template, fingerPosition, date_created,creator FROM biometricinfo WHERE patient_Id = " + id);
			ResultSet result = statement.executeQuery(sqlStatement);
			FingerPrintType fingerPrintsType = new FingerPrintType();
			if (result.next()) {
				RightHandType rightFingerType = new RightHandType();
				LeftHandType leftFingerType = new LeftHandType();
				XMLGregorianCalendar dataCaptured = null;
				Integer creator = null;
				while (result.next()) {
					String fingerPosition = result.getString("fingerPosition");
					creator = result.getInt("creator");
					dataCaptured = Utils.getXmlDateTime(result.getDate("date_created"));
					switch (fingerPosition) {
						case "RightThumb":
							rightFingerType.setRightThumb(result.getString("template"));
							break;
						case "RightIndex":
							rightFingerType.setRightIndex(result.getString("template"));
							break;
						case "RightMiddle":
							rightFingerType.setRightMiddle(result.getString("template"));
							break;
						case "RightWedding":
							rightFingerType.setRightWedding(result.getString("template"));
							break;
						case "RightSmall":
							rightFingerType.setRightSmall(result.getString("template"));
							break;
						case "LeftThumb":
							leftFingerType.setLeftThumb(result.getString("template"));
							break;
						case "LeftIndex":
							leftFingerType.setLeftIndex(result.getString("template"));
							break;
						case "LeftMiddle":
							leftFingerType.setLeftMiddle(result.getString("template"));
							break;
						case "LeftWedding":
							leftFingerType.setLeftWedding(result.getString("template"));
							break;
						case "LeftSmall":
							leftFingerType.setLeftSmall(result.getString("template"));
							break;
					}
				}
				/*do{

				} while(result.next());*/
				if (creator == 0) {
					fingerPrintsType.setSource("N");
				} else if (creator == 1) {
					fingerPrintsType.setSource("M");
				} else {
					fingerPrintsType.setSource("UNK");
				}
				fingerPrintsType.setDateCaptured(dataCaptured);
				
				fingerPrintsType.setPresent(true);
				//fingerPrintsType.setLeftHand(leftHand);
				//fingerPrintsType.setRightHand(rightHand);
				fingerPrintsType.setRightHand(rightFingerType);
				fingerPrintsType.setLeftHand(leftFingerType);
			} else {
				connection.close();
				return null;
			}
			connection.close();
			return fingerPrintsType;
		}
		catch (SQLException e) {
			e.printStackTrace();
			// LoggerUtils.write(NDRMainDictionary.class.getName(), e.getMessage(), LogFormat.FATAL, LoggerUtils.LogLevel.live.live);
		}
		catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public CommonQuestionsType createCommonQuestionType(CustomPatient patient,
	        Map<Integer, List<CustomEncounter>> allPatientEncounterList, Map<Integer, Set<Date>> uniqueEncounters,
	        OBSUtil obsUtil, Map<Integer, IdentifierType> allPatientIdentifier) throws DatatypeConfigurationException {
		CustomObs obs = null;
		Date valueDateTime = null;
		int valueCoded = 0;
		String ndrCode = "";
		Boolean ndrBooleanCode = null;
		try {
			IdentifierType pepFarId = allPatientIdentifier.get(Utils.PEPFAR_IDENTIFIER_INDEX);
			CommonQuestionsType common = new CommonQuestionsType();
			//List<Obs> hivEnrollmentObs = Utils.FilterObsByEncounterTypeId(allObs, Utils.HIV_Enrollment_Encounter_Type_Id); // Utils.getHIVEnrollmentObs(pts);
			
			if (pepFarId != null) {
				
				try {
					common.setHospitalNumber(allPatientIdentifier.get(Utils.HOSPITAL_IDENTIFIER_INDEX).getIDNumber());
				}
				catch (Exception e) {
					common.setHospitalNumber(allPatientIdentifier.get(Utils.PEPFAR_IDENTIFIER_INDEX).getIDNumber());
					
				}
				/*  Assuming Hospital No is 3*/
				//old code commented for throwing error change by the try and catch code abowe
				//common.setHospitalNumber(pts.getPatientIdentifier(3).getIdentifier());
			}
			
			try {
				//Encounter lastEncounterDate = Utils.getLastEncounter(encounters); //(pts);
				CustomEncounter lastEncounter = OBSUtil.getLastEncounter(allPatientEncounterList);
				if (lastEncounter != null) {
					common.setDateOfLastReport(getXmlDate(lastEncounter.getEncounterDatetime()));
				}
				
				Date EnrollmentDate = obsUtil.getEnrollmentDate(allPatientEncounterList);//Utils.extractEnrollmentDate(pts, allObs, encounters);
				if (EnrollmentDate != null) {
					common.setDateOfFirstReport(getXmlDate(EnrollmentDate));
					common.setDiagnosisDate(getXmlDate(EnrollmentDate));
				}
				obs = obsUtil.getObsForConcept(Utils.DATE_OF_HIV_DIAGNOSIS_CONCEPT);////Utils.extractObs(Utils.DATE_OF_HIV_DIAGNOSIS_CONCEPT, allObs);
				if (obs != null) {
					valueDateTime = obs.getValueDate();
					common.setDiagnosisDate(getXmlDate(valueDateTime));
				}
			}
			catch (Exception ex) {
				
			}
			
			if (patient.getGender().equalsIgnoreCase("F")) {
				
				//set estimated delivery date concept id
				//obs = Utils.extractObs(Estimated_Delivery_Date_Concept_Id, hivEnrollmentObs);
				obs = obsUtil.getLastObsOfConceptByDate(Utils.PREGNANCY_BREASTFEEDING_STATUS);//Utils.getLastObsOfConceptByDate(allObs, Utils.PREGNANCY_BREASTFEEDING_STATUS);
				
				if (obs != null) {
					System.out.println("not null");
					//ndrBooleanCode = obs.isValueBoolean();
					if (obs.getValueCoded() == Utils.NOT_PREGNANT_CONCEPT) {
						common.setPatientPregnancyStatusCode("NP");
					} else if (ndrBooleanCode == false) {
						common.setPatientPregnancyStatusCode("P");
					}
					
				}
				
			}
			
			common.setPatientAge(patient.getAge());
			
			//set Patient Die From This Illness tag
			obs = obsUtil.extractObsByValues(Utils.REASON_FOR_TERMINATION_CONCEPT, valueCoded);//Utils.extractObsByValues(Utils.REASON_FOR_TERMINATION_CONCEPT, Utils.DEAD_CONCEPT, allObs);
			if (obs != null) {
				common.setPatientDieFromThisIllness(Boolean.TRUE);
			} else {
				common.setPatientDieFromThisIllness(Boolean.FALSE);
			}
			//LoggerUtils.write(NDRMainDictionary.class.getName(), "About to pull Patient_Died_From_Illness_Concept_Id", LoggerUtils.LogFormat.FATAL, LoggerUtils.LogLevel.debug);
			//if (obs != null && obs.getValueCoded().getConceptId() == Patient_Died_From_Illness_Value_Concept_Id) {
			// common.setPatientDieFromThisIllness(true);
			//}
			//LoggerUtils.write(NDRMainDictionary.class.getName(), "Finished pulling Patient_Died_From_Illness_Concept_Id", LoggerUtils.LogFormat.FATAL, LoggerUtils.LogLevel.debug);
			
			return common;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			// LoggerUtils.write(NDRMainDictionary.class.getName(), ex.getMessage(), LoggerUtils.LogFormat.FATAL, LoggerUtils.LogLevel.live);
			throw new DatatypeConfigurationException(ex.getMessage());
		}
	}
	
	/*
	      
	HIVQuestionsType
	-CareEntryPoint (160540)  
	-FirstConfirmedHIVTestDate(160554)
	-FirstHIVTestMode(164947)
	-WhereFirstHIVTest
	-PriorArt (165242)
	-MedicallyEligibleDate (162227)
	-ReasonMedicallyEligible (162225)
	-InitialAdherenceCounselingCompletedDate (165038)
	-TransferredInDate (160534)
	-TransferredInFrom (160535)
	-TransferredInFromPatId
	-FirstARTRegimen (165708)(164506,164513,165702,164507,164514,165703)
	-ARTStartDate (159599)
	-WHOClinicalStageARTStart (5356)
	-WeightAtARTStart (165582)
	-ChildHeightAtARTStart (165581)
	-FunctionalStatusStartART (165039)
	-CD4AtStartOfART (164429)
	-PatientTransferredOut (165470)(159492)
	-TransferredOutStatus(165470)(159492)
	-TransferredOutDate (165469)
	-FacilityReferredTo
	-PatientHasDied (165470)(165889)
	-StatusAtDeath
	-DeathDate(165469)
	-SourceOfDeathInformation (162568)
	-CauseOfDeathHIVRelated (165889,(165886,165887)) (163534,Yes(1065),No(1066))
	-DrugAllergies (165419)
	-EnrolledInHIVCareDate
	-InitialTBStatus (1659)
	 */
	public HIVQuestionsType createHIVQuestionType(CustomPatient patient,
	        Map<Integer, List<CustomEncounter>> allPatientEncounterList, Map<Integer, Set<Date>> uniqueEncounters,
	        OBSUtil obsUtil, Map<Integer, IdentifierType> allPatientIdentifier) throws DatatypeConfigurationException {
		Integer[] targetEncounterTypes = { Utils.ADULT_INITIAL_ENCOUNTER_TYPE, Utils.PED_INITIAL_ENCOUNTER_TYPE,
		        Utils.HIV_Enrollment_Encounter_Type_Id, Utils.ART_COMMENCEMENT_ENCOUNTER_TYPE,
		        Utils.Client_Tracking_And_Termination_Encounter_Type_Id };
		
		HIVQuestionsType hivQuestionsType = null;
		//List<Obs> obsList = Utils.extractObsListForEncounterType(allObsList, targetEncounterTypes);
		Map<Integer, List<CustomObs>> obsListForEncounterTypes = obsUtil.getAllObsByEncounterTypeId(targetEncounterTypes);
		
		CustomObs obs = null;
		int valueCoded = 0, valueNumericInt = 0;
		
		Date valueDateTime = null;
		String ndrCode = "";
		FacilityType facilityType = null;
		CodedSimpleType cst = null;
		if (obsListForEncounterTypes != null && !obsListForEncounterTypes.isEmpty()) {
			hivQuestionsType = new HIVQuestionsType();
			obs = OBSUtil.getObsFromConcept(Utils.CARE_ENTRY_POINT_CONCEPT, obsListForEncounterTypes);//Utils.extractObs(Utils.CARE_ENTRY_POINT_CONCEPT, obsList);
			if (obs != null && obs.getValueCoded() != 0) {
				valueCoded = obs.getValueCoded();
				ndrCode = getMappedValue(valueCoded);
				if (!ndrCode.equals("")) {
					hivQuestionsType.setCareEntryPoint(ndrCode);
				}
				
			}
			obs = OBSUtil.getObsFromConcept(Utils.DATE_OF_HIV_DIAGNOSIS_CONCEPT, obsListForEncounterTypes);
			if (obs != null && obs.getValueDate() != null) {
				valueDateTime = obs.getValueDate();
				hivQuestionsType.setFirstConfirmedHIVTestDate(getXmlDate(valueDateTime));
			}
			obs = OBSUtil.getObsFromConcept(Utils.MODE_OF_HIV_TEST, obsListForEncounterTypes);
			if (obs != null && obs.getValueCoded() != 0) {
				valueCoded = obs.getValueCoded();
				ndrCode = getMappedValue(valueCoded);
				if (!ndrCode.equals("")) {
					hivQuestionsType.setFirstHIVTestMode(ndrCode);
				}
			}
			// Where first tested positive missing
			
			obs = OBSUtil.getObsFromConcept(Utils.PRIOR_ART_CONCEPT, obsListForEncounterTypes);
			if (obs != null && obs.getValueCoded() != 0) {
				valueCoded = obs.getValueCoded();
				ndrCode = getMappedValue(valueCoded);
				hivQuestionsType.setPriorArt(ndrCode);
			}
			obs = OBSUtil.getObsFromConcept(Utils.MEDICAL_ELIGIBLE_DATE_CONCEPT, obsListForEncounterTypes);
			if (obs != null) {
				valueDateTime = obs.getValueDate();
				hivQuestionsType.setMedicallyEligibleDate(getXmlDate(valueDateTime));
			}
			obs = OBSUtil.getObsFromConcept(Utils.REASON_MEDICALLY_ELIGIBLE_CONCEPT, obsListForEncounterTypes);
			if (obs != null && obs.getValueCoded() != 0) {
				valueCoded = obs.getValueCoded();
				ndrCode = getMappedValue(valueCoded);
				hivQuestionsType.setReasonMedicallyEligible(ndrCode);
			}
			obs = OBSUtil.getObsFromConcept(Utils.DATE_INITIAL_ADHERENCE_COUNCELING_CONCEPT, obsListForEncounterTypes);
			if (obs != null) {
				valueDateTime = obs.getValueDate();
				hivQuestionsType.setInitialAdherenceCounselingCompletedDate(Utils.getXmlDate(valueDateTime));
			}
			obs = OBSUtil.getObsFromConcept(Utils.TRANSFERRED_IN_DATE, obsListForEncounterTypes);
			if (obs != null) {
				valueDateTime = obs.getValueDate();
				hivQuestionsType.setTransferredInDate(Utils.getXmlDate(valueDateTime));
			}
			obs = OBSUtil.getObsFromConcept(Utils.TRANSFERRED_IN_FROM, obsListForEncounterTypes);
			if (obs != null) {
				String transferredInFromFacility = "";
				transferredInFromFacility = obs.getValueText();
				facilityType = new FacilityType();
				facilityType.setFacilityName(transferredInFromFacility);
				facilityType.setFacilityTypeCode("FAC");
				facilityType.setFacilityID(transferredInFromFacility.toUpperCase());//facilityType.setFacilityID(StringUtils.upperCase(transferredInFromFacility));
				hivQuestionsType.setTransferredInFrom(facilityType);
			}
			//Need to create a transferred in patient id
			obs = OBSUtil.getObsFromConcept(Utils.CURRENT_REGIMEN_LINE_CONCEPT, obsListForEncounterTypes);
			if (obs != null && obs.getValueCoded() != 0) {
				valueCoded = obs.getValueCoded();
				obs = OBSUtil.getObsFromConcept(valueCoded, obsListForEncounterTypes);
				if (obs != null && obs.getValueCoded() != 0) {
					valueCoded = obs.getValueCoded();
					ndrCode = pharmacyDictionary.getRegimenMapValue(valueCoded);
					cst = new CodedSimpleType();
					cst.setCode(ndrCode);
					cst.setCodeDescTxt(obs.getVariableName());
					hivQuestionsType.setFirstARTRegimen(cst);
				}
			}
			Date artStartDate = obsUtil.extractARTStartDate();//Utils.extractARTStartDate(patient, allObsList);//Obs(Utils.ART_START_DATE_CONCEPT, obsList);
			if (artStartDate != null) {
				//valueDateTime=obs.getValueDate();
				hivQuestionsType.setARTStartDate(Utils.getXmlDate(artStartDate));
			}
			obs = OBSUtil.getObsFromConcept(Utils.WHO_CLINICAL_STAGGING_AT_START_CONCEPT, obsListForEncounterTypes);//Utils.extractObs(Utils.WHO_CLINICAL_STAGGING_AT_START_CONCEPT, obsList);
			if (obs != null && obs.getValueCoded() != 0) {
				valueCoded = obs.getValueCoded();
				ndrCode = getMappedValue(valueCoded);
				hivQuestionsType.setWHOClinicalStageARTStart(ndrCode);
			}
			obs = OBSUtil.getObsFromConcept(Utils.WEIGHT_AT_START_CONCEPT, obsListForEncounterTypes);//Utils.extractObs(Utils.WEIGHT_AT_START_CONCEPT, obsList);
			if (obs != null && obs.getValueNumeric() != 0) {
				valueNumericInt = (int) obs.getValueNumeric();
				hivQuestionsType.setWeightAtARTStart(valueNumericInt);
			}
			obs = OBSUtil.getObsFromConcept(Utils.CHILD_HEIGHT_AT_START, obsListForEncounterTypes);//Utils.extractObs(Utils.CHILD_HEIGHT_AT_START, obsList);
			if (obs != null && obs.getValueNumeric() != 0) {
				valueNumericInt = (int) obs.getValueNumeric();
				hivQuestionsType.setChildHeightAtARTStart(valueNumericInt);
			}
			obs = OBSUtil.getObsFromConcept(Utils.FUNCTIONAL_STATUS_ART_START, obsListForEncounterTypes);//Utils.extractObs(Utils.FUNCTIONAL_STATUS_ART_START, obsList);
			if (obs != null && obs.getValueCoded() != 0) {
				valueCoded = obs.getValueCoded();
				ndrCode = getMappedValue(valueCoded);
				hivQuestionsType.setFunctionalStatusStartART(ndrCode);
			}
			obs = OBSUtil.getObsFromConcept(Utils.CD4_AT_START, obsListForEncounterTypes);//Utils.extractObs(Utils.CD4_AT_START, obsList);
			if (obs != null && obs.getValueNumeric() != 0) {
				valueNumericInt = (int) obs.getValueNumeric();
				hivQuestionsType.setCD4AtStartOfART(String.valueOf(valueNumericInt));
			}
			obs = OBSUtil.extractObsByValuesForMap(Utils.REASON_FOR_TERMINATION_CONCEPT, Utils.TRANSFERRED_OUT_CONCEPT,
			    obsListForEncounterTypes);//obsUtil.extractObsByValues(Utils.REASON_FOR_TERMINATION_CONCEPT, Utils.TRANSFERRED_OUT_CONCEPT);//Utils.extractObsByValues(Utils.REASON_FOR_TERMINATION_CONCEPT, Utils.TRANSFERRED_OUT_CONCEPT, obsList);
			if (obs != null) {
				hivQuestionsType.setPatientTransferredOut(Boolean.TRUE);
			}
			obs = OBSUtil.getObsFromConcept(Utils.TRANSFER_OUT_DATE, obsListForEncounterTypes);//Utils.extractObs(Utils.TRANSFER_OUT_DATE, obsList);
			if (obs != null) {
				valueDateTime = obs.getValueDate();
				hivQuestionsType.setTransferredOutDate(getXmlDate(valueDateTime));
			}
			if (artStartDate != null) {
				hivQuestionsType.setTransferredOutStatus("A");
			} else {
				hivQuestionsType.setTransferredOutStatus("P");
			}
			/*
			    Use date confirmed positve or visit date of the HIVEnrollmentForm
			 */
			Date enrollmentDate = obsUtil.getEnrollmentDate(allPatientEncounterList);//Utils.extractEnrollmentDate(patient, allObsList, allEncounterList);
			if (enrollmentDate != null) {
				hivQuestionsType.setEnrolledInHIVCareDate(Utils.getXmlDate(enrollmentDate));
			}
			obs = OBSUtil.getObsFromConcept(Utils.INITIAL_TB_STATUS, obsListForEncounterTypes);//Utils.extractObs(Utils.INITIAL_TB_STATUS, obsList);
			if (obs != null && obs.getValueCoded() != 0) {
				valueCoded = obs.getValueCoded();
				ndrCode = getMappedValue(valueCoded);
				hivQuestionsType.setInitialTBStatus(ndrCode);
			}
			
		}
		return hivQuestionsType;
		
	}
	
	public ConditionSpecificQuestionsType createConditionSpecificQuestionType(CustomPatient patient,
	        Map<Integer, List<CustomEncounter>> allPatientEncounterList, Map<Integer, Set<Date>> uniqueEncounters,
	        OBSUtil allPatientObsList, Map<Integer, IdentifierType> allPatientIdentifier)
	        throws DatatypeConfigurationException {
		ConditionSpecificQuestionsType conditionSpecificQuestion = new ConditionSpecificQuestionsType();
		HIVQuestionsType hivQuestionType = createHIVQuestionType(patient, allPatientEncounterList, uniqueEncounters,
		    allPatientObsList, allPatientIdentifier);
		conditionSpecificQuestion.setHIVQuestions(hivQuestionType);
		return conditionSpecificQuestion;
	}
}
