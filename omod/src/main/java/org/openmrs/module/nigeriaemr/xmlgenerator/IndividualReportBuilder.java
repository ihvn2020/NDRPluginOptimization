/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.nigeriaemr.xmlgenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.xml.datatype.DatatypeConfigurationException;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.module.nigeriaemr.dbmanager.PatientDao;
import org.openmrs.module.nigeriaemr.fragment.controller.OBSUtil;
import org.openmrs.module.nigeriaemr.model.ndr.ClinicalTBScreeningType;
import org.openmrs.module.nigeriaemr.model.ndr.ConditionType;
import org.openmrs.module.nigeriaemr.model.ndr.EncountersType;
import org.openmrs.module.nigeriaemr.model.ndr.FacilityType;
import org.openmrs.module.nigeriaemr.model.ndr.HIVEncounterType;
import org.openmrs.module.nigeriaemr.model.ndr.HIVRiskAssessmentType;
import org.openmrs.module.nigeriaemr.model.ndr.HIVTestResultType;
import org.openmrs.module.nigeriaemr.model.ndr.HIVTestingReportType;
import org.openmrs.module.nigeriaemr.model.ndr.IdentifierType;
import org.openmrs.module.nigeriaemr.model.ndr.IndexNotificationServicesType;
import org.openmrs.module.nigeriaemr.model.ndr.IndividualReportType;
import org.openmrs.module.nigeriaemr.model.ndr.KnowledgeAssessmentType;
import org.openmrs.module.nigeriaemr.model.ndr.LaboratoryReportType;
import org.openmrs.module.nigeriaemr.model.ndr.PartnerDetailsType;
import org.openmrs.module.nigeriaemr.model.ndr.PatientDemographicsType;
import org.openmrs.module.nigeriaemr.model.ndr.PostTestCounsellingType;
import org.openmrs.module.nigeriaemr.model.ndr.PreTestInformationType;
import org.openmrs.module.nigeriaemr.model.ndr.ProgramAreaType;
import org.openmrs.module.nigeriaemr.model.ndr.RegimenType;
import org.openmrs.module.nigeriaemr.model.ndr.SyndromicSTIScreeningType;
import org.openmrs.module.nigeriaemr.ndrUtils.ConstantsUtil;
import org.openmrs.module.nigeriaemr.ndrUtils.LoggerUtils;
import org.openmrs.module.nigeriaemr.ndrUtils.Utils;
import org.openmrs.module.nigeriaemr.ndrfactory2.NDRConverter;
import org.openmrs.module.nigeriaemr.ndrfactory2.NDRMainDictionary;

/**
 * @author lordmaul
 */
public class IndividualReportBuilder {
	
	private CustomPatient patient;
	
	PatientDemographicsType patientDemographicsType;
	
	List<ConditionType> conditiontypes;
	
	IndividualReportType individualReportType = null;
	
	public PatientDao patientDaoObj;
	
	public NDRMainDictionary ndrDictionary;
	
	public Map<Integer, List<CustomEncounter>> allEncounters;//index is encounter type id;
	
	public static String isSurgeSite;
	
	OBSUtil obsUtil;
	
	Map<Integer, IdentifierType> allPatientIdentifiers;
	
	Map<Integer, Set<Date>> uniqueEncounters;
	
	public FacilityType patientFacility;
	
	public IndividualReportBuilder(CustomPatient p, FacilityType pFac, Map<Integer, IdentifierType> allPatientIdentifiers) {
		this.patient = p;
		this.patientFacility = pFac;
		this.allPatientIdentifiers = allPatientIdentifiers;
	}
	
	public IndividualReportType build() {
		try {
			individualReportType = new IndividualReportType();
			patientDaoObj = new PatientDao();
			isSurgeSite = patientDaoObj.getGlobalProperty("surge_site");
			//get all custom obs for patient
			List<CustomObs> obsList = patientDaoObj.getObsForPatient(patient.getPatientId(), null);
			
			obsUtil = new OBSUtil();
			obsUtil.setPatientObs(obsList);
			List<Object> allEncounterObj = patientDaoObj.getPatientEncounters(patient.getPatientId());
			allEncounters = (Map<Integer, List<CustomEncounter>>) allEncounterObj.get(0);
			uniqueEncounters = (Map<Integer, Set<Date>>) allEncounterObj.get(1);
			
			//uniqueEncounters.forEach((key,value) -> System.out.println(key + " = " + value.iterator().next().toString()));
			//allPatientIdentifiers = patientDaoObj.getAllPatientIdentifiers(patient.getPatientId());
			
			ndrDictionary = new NDRMainDictionary();
			//build patient demographics
			patientDemographicsType = ndrDictionary.createPatientDemographicType2(patient, patientFacility, obsUtil,
			    allPatientIdentifiers);
			individualReportType.setPatientDemographics(patientDemographicsType);
			
			ConditionType hivConditionType = createHivConditionType();
			individualReportType.getCondition().add(hivConditionType);
			
			CustomEncounter intakeEncounter = OBSUtil.getLastEncounterForEncounterType(
			    ConstantsUtil.ADMISSION_ENCOUNTER_TYPE, allEncounters);//Utils.getLatestEncounter(this.encounters, ConstantsUtil.ADMISSION_ENCOUNTER_TYPE);
			//CustomEncounter intakeEncounter = OBSUtil.getLastEncounter(allEncounters);//Utils.getLatestEncounter(this.encounters, ConstantsUtil.ADMISSION_ENCOUNTER_TYPE);
			
			if (intakeEncounter != null) {
				Map<Integer, List<CustomObs>> intakeObs = obsUtil
				        .getAllObsByEncounterTypeId(ConstantsUtil.ADMISSION_ENCOUNTER_TYPE);
				//List<Obs> intakeObs = new ArrayList<>(intakeEncounter.getAllObs());
				//  if (htsIdentifier != null) {
				try {
					List<HIVTestingReportType> hivReportTypes = createHIVTestingReport(intakeEncounter, intakeObs);
					individualReportType.getHIVTestingReport().addAll(hivReportTypes);
				}
				catch (Exception ex) {
					LoggerUtils.write(NDRConverter.class.getName(), ex.getMessage(), LoggerUtils.LogFormat.FATAL,
					    LoggerUtils.LogLevel.live);
				}
				//  }
			}
			
			return individualReportType;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			//Logger.getLogger(IndividualReportBuilder.class.getName()).log(Level.SEVERE, null, ex);
			
		}
		return individualReportType;
	}
	
	//set unique encounters in a set
	
	public ConditionType createHivConditionType() throws Exception {
		ConditionType condition = new ConditionType();
		condition.setConditionCode("86406008");
		try {
			//create address
			condition.setPatientAddress(patientDaoObj.createPatientAddress(patient.getPatientId()));
			
			//create program area
			condition.setProgramArea(createProgramArea());
			
			//create common question tags by calling the factory method and passing the encounter, patient and obs list
			
			condition.setCommonQuestions(ndrDictionary.createCommonQuestionType2(this.patient, allEncounters,
			    uniqueEncounters, obsUtil, allPatientIdentifiers));
			
			condition.setConditionSpecificQuestions(ndrDictionary.createCommConditionSpecificQuestionsType(this.patient,
			    allEncounters, uniqueEncounters, obsUtil, allPatientIdentifiers));
			
			//create hiv encounter
			
			List<HIVEncounterType> hivEncounter = ndrDictionary.createHIVEncounterType(this.patient, allEncounters,
			    uniqueEncounters, obsUtil, allPatientIdentifiers);
			if (hivEncounter != null && hivEncounter.size() > 0) {
				EncountersType encType = new EncountersType();
				encType.getHIVEncounter().addAll(hivEncounter);
				condition.setEncounters(encType);
			}
			
			//List<Encounter> tempEncs = encounters.stream().filter(e -> e.getEncounterType().getEncounterTypeId() == Utils.Laboratory_Encounter_Type_Id).collect(Collectors.toList());
			List<CustomEncounter> labEncounters = this.allEncounters.get(Utils.Laboratory_Encounter_Type_Id);
			if (labEncounters != null && !labEncounters.isEmpty()) {
				Date artStartdate = obsUtil.extractARTStartDate();//Utils.extractARTStartDate(patient, allobs);
				for (CustomEncounter customEncounter : labEncounters) {
					System.out.println(customEncounter.getEncounterId());
					LaboratoryReportType laboratoryReport = ndrDictionary.createLaboratoryOrderAndResult(patient,
					    customEncounter, allEncounters, uniqueEncounters, obsUtil, allPatientIdentifiers, artStartdate);
					if (laboratoryReport != null) {
						condition.getLaboratoryReport().add(laboratoryReport);
					}
				}
				
			}
			
			//Partner details
			
			List<PartnerDetailsType> partnerDetailsType = ndrDictionary.createPartnerDetails(patient, obsUtil,
			    allEncounters, uniqueEncounters, allPatientIdentifiers);
			if (!partnerDetailsType.isEmpty()) {
				condition.getPartnerDetails().addAll(partnerDetailsType);
			}
			
			List<RegimenType> arvRegimenTypeList = ndrDictionary.createRegimenTypeList(patient, obsUtil, allEncounters,
			    uniqueEncounters, allPatientIdentifiers);
			if (arvRegimenTypeList != null && arvRegimenTypeList.size() > 0) {
				condition.getRegimen().addAll(arvRegimenTypeList);
			}
		}
		catch (Exception ex) {
			LoggerUtils.write(NDRMainDictionary.class.getName(), ex.getMessage(), LoggerUtils.LogFormat.FATAL,
			    LoggerUtils.LogLevel.live);
		}
		//
		/*List<HealthFacilityVisitsType> healthFacilityVisitsType = mainDictionary.createHealthFacilityVisit(patient,
		    this.encounters, this.allobs);
		if (healthFacilityVisitsType != null) {
			condition.getHealthFacilityVisits().addAll(healthFacilityVisitsType);
		}
		/*long endTime = System.currentTimeMillis();
		if ((endTime - startTime) > 1000) {
		    System.out.println("took too long to get obs : " + (endTime - startTime) + " milli secs : ");
		}*/
		
		return condition;
	}
	
	private List<HIVTestingReportType> createHIVTestingReport(CustomEncounter encounter, Map<Integer, List<CustomObs>> intakeObs) {

        //TODO: pull hivtestReport as a list
        //NDRMainDictionary mainDictionary = new NDRMainDictionary();

        List<HIVTestingReportType> hivTestingReportList = new ArrayList<>();

        try {

            //for each testing to the following
            HIVTestingReportType hivTestingReport = new HIVTestingReportType();
            PreTestInformationType preTestInfo = new PreTestInformationType();
            PostTestCounsellingType postTestType = new PostTestCounsellingType();
           // (CustomPatient patient, CustomEncounter enc, Map<Integer, List<CustomObs>> intakeObs, Map<Integer, List<CustomEncounter>>allEncounters, Map<Integer, Set<Date>> uniqueEncounters, Map<Integer, IdentifierType> allPatientIdentifiers, HIVTestingReportType hivTestingReport
            hivTestingReport = ndrDictionary.createHIVTestIntake(patient, encounter, intakeObs, this.allEncounters, this.uniqueEncounters, this.allPatientIdentifiers,  obsUtil,  hivTestingReport);

            //complete
            HIVTestResultType hIVTestResultType = ndrDictionary.createHIVTestResult(patient, encounter, intakeObs, this.allEncounters, this.uniqueEncounters, this.allPatientIdentifiers,  obsUtil);
            
            
            //complete
            IndexNotificationServicesType indexNotificationServicesType = ndrDictionary.createIndexNotificationServicesTypes(patient, encounter, intakeObs, this.allEncounters, this.uniqueEncounters, this.allPatientIdentifiers,  obsUtil);

            if (hIVTestResultType != null) {
                hivTestingReport.setHIVTestResult(hIVTestResultType);
            }

            if (indexNotificationServicesType != null) {
                hivTestingReport.setIndexNotificationServices(indexNotificationServicesType);
            }

            //create TB screening. // Complete
            List<ClinicalTBScreeningType> clinicalTBScreeningType = ndrDictionary.createClinicalTbScreening(patient, encounter, intakeObs, this.allEncounters, this.uniqueEncounters, this.allPatientIdentifiers,  obsUtil);

            if (clinicalTBScreeningType != null && !clinicalTBScreeningType.isEmpty()) {

                preTestInfo.setClinicalTBScreening(clinicalTBScreeningType.get(0));
            }

            //HIV Risk assessment. //complete
            List<HIVRiskAssessmentType> hivRiskAssessmentType = ndrDictionary.createHivRiskAssessment(patient, encounter, intakeObs, this.allEncounters, this.uniqueEncounters, this.allPatientIdentifiers,  obsUtil);
            if (hivRiskAssessmentType != null && !hivRiskAssessmentType.isEmpty()) {
                preTestInfo.setHIVRiskAssessment(hivRiskAssessmentType.get(0));
            }

            //knowledge assessment
            //Knowledge Assessment Type. //Complete
            List<KnowledgeAssessmentType> knowledgeAssessmentType = ndrDictionary.createKnowledgeAssessmentType(patient, encounter, intakeObs, this.allEncounters, this.uniqueEncounters, this.allPatientIdentifiers,  obsUtil);
            if (knowledgeAssessmentType != null && !knowledgeAssessmentType.isEmpty()) {
                preTestInfo.setKnowledgeAssessment(knowledgeAssessmentType.get(0));
            }

            //Syndromic STI.// Complete
            List<SyndromicSTIScreeningType> syndromicSTIScreeningType = ndrDictionary.createSyndromicsStiType(patient, encounter, intakeObs, this.allEncounters, this.uniqueEncounters, this.allPatientIdentifiers,  obsUtil);
            if (syndromicSTIScreeningType != null && syndromicSTIScreeningType.size() > 0) {
                preTestInfo.setSyndromicSTIScreening(syndromicSTIScreeningType.get(0));
            }

            //Post Test Counselling. // Complete
            List<PostTestCounsellingType> postTestCounsellingType = ndrDictionary.createPostTestCounsellingType(patient, encounter, intakeObs, this.allEncounters, this.uniqueEncounters, this.allPatientIdentifiers,  obsUtil);
            if (postTestCounsellingType != null && postTestCounsellingType.size() > 0) {
                postTestType = postTestCounsellingType.get(0);
            }

            hivTestingReport.setPreTestInformation(preTestInfo);
            hivTestingReport.setPostTestCounselling(postTestType);

            hivTestingReportList.add(hivTestingReport);

        } catch (Exception ex) {
            LoggerUtils.write(NDRConverter.class.toString(), ex.getMessage(), LoggerUtils.LogFormat.FATAL, LoggerUtils.LogLevel.live);
        }

        return hivTestingReportList;

    }
	
	private ProgramAreaType createProgramArea() {
		ProgramAreaType p = new ProgramAreaType();
		p.setProgramAreaCode("HIV");
		return p;
	}
}
