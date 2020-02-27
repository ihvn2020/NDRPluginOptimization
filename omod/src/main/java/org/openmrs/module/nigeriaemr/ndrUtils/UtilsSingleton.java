package org.openmrs.module.nigeriaemr.ndrUtils;

import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UtilsSingleton {
    //private static UtilsSingleton ourInstance = new UtilsSingleton();
    private static UtilsSingleton ourInstance = null;

    private final List<Obs> allObs;
    private final Patient patient;

    public static  Map<Integer, Obs> obsByConceptID = new HashMap<>();
    public static  Map<Obs, Obs> ObsByGroupID = new HashMap<>();

    // Types of Encounters
    public static Encounter Client_Tracking_And_Termination_Encounter_Type_Id_Encounter = null;
    public static Encounter HIV_Enrollment_Encounter_Type_Id_Encounter = null;
    public static List<Encounter> allPatientEncounterByPatient = new ArrayList<>();


    public static UtilsSingleton getInstance() {
        if(ourInstance == null) {
            throw new AssertionError("You have to call init first");
        }
        return ourInstance;
    }

    private UtilsSingleton(Patient patient, List<Obs> allObs) {
        this.allObs = allObs;
        this.patient = patient;
    }

    public synchronized static UtilsSingleton init(Patient patient,  List<Obs> obs) {
        if (ourInstance != null)
        {
            // in my opinion this is optional, but for the purists it ensures
            // that you only ever get the same instance when you call getInstance
            throw new AssertionError("You already initialized me");
        }

        ourInstance = new UtilsSingleton(patient, obs);
        obsLoop(obs);
//        encounterLoop(allPatientEncounter);
        patientLoop(patient);
        UtilsCustom.isUtilSingletonInitiated = true;
        return ourInstance;
    }

//    private static  void  encounterLoop(List<Encounter> allPatientEncounter){
//
//    }


    private static void obsLoop(List<Obs> obsList){
        for (Obs obs: obsList){
            //POPULATE MAP WITH CONCEPT ID AS KEY
            obsByConceptID.put(obs.getConcept().getConceptId(), obs);
            //POPULATE MAP WITH GROUP ID AS KEY
            ObsByGroupID.put(obs, obs.getObsGroup());
        }
    }

    private static void patientLoop(Patient patient){
            allPatientEncounterByPatient = Context.getEncounterService().getEncountersByPatient(patient);

         for (Encounter encounter: allPatientEncounterByPatient){
             // getReasonForTerminationObs
             if(encounter.getEncounterType().getEncounterTypeId() == UtilsCustom.Client_Tracking_And_Termination_Encounter_Type_Id){
                 Client_Tracking_And_Termination_Encounter_Type_Id_Encounter = encounter;
             }
             // getHIVEnrollmentObs
             if(encounter.getEncounterType().getEncounterTypeId() == UtilsCustom.HIV_Enrollment_Encounter_Type_Id){
                 HIV_Enrollment_Encounter_Type_Id_Encounter = encounter;
             }
         }
    }

}
