/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.nigeriaemr.fragment.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientProgram;
import org.openmrs.module.nigeriaemr.ndrUtils.Utils;
import static org.openmrs.module.nigeriaemr.ndrUtils.Utils.extractObs;
import static org.openmrs.module.nigeriaemr.ndrUtils.Utils.getFirstEncounter;
import static org.openmrs.module.nigeriaemr.ndrUtils.Utils.getFirstObsOfConceptByDate;
import org.openmrs.module.nigeriaemr.xmlgenerator.CustomEncounter;
import org.openmrs.module.nigeriaemr.xmlgenerator.CustomObs;

/**
 *
 * @author lordmaul
 */
//put obs in a map using date as key
//organise obs by encounters 
// Map<Integer, List<EncounterObj> encounterList
//Map<Integer, List<CustomObs>obsList
public class OBSUtil1 {
    Map <Integer, CustomObs> allObsMap = new HashMap<>();
    Map <Integer, List <CustomObs>> allObsByEncounter = new HashMap<>();//key is encounter type id, value is the custom obs
    Map <Integer, Integer> obsConceptIds = new HashMap<>();//this is useful to avoid going back to the db. It holds value coded concept id
    Map<Integer, String> patientIdentifies = new HashMap<>();
    Map <Integer, List<CustomObs>> multiValueObsMap = new HashMap<>();//the index is still concept id, this concept has more than one obs
    List<CustomObs> allObs = new ArrayList<>();
    public void setPatientObs(List<CustomObs> allObs)
    {
        
        this.allObs = allObs;
        this.allObsMap.clear();
        for (CustomObs obs : allObs) {
            
            
            int obsConceptId = obs.getConceptID();
            if(allObsMap.containsKey(obsConceptId))//that means this is an obs that has multiple values
            {
                 //get the current list of obs in the 
                 List<CustomObs>subList = new ArrayList<>();
                 subList.add(obs);
                 multiValueObsMap.put(obsConceptId, subList);
                 allObsMap.remove(obsConceptId);//remove this from the normal map because it is a concept that has a list of Obs
            }
            
            if(multiValueObsMap.containsKey(obsConceptId))
            {
                List<CustomObs>currList = multiValueObsMap.get(obsConceptId);
                currList.add(obs);
                multiValueObsMap.put(obsConceptId, currList);
                
            }
            
            if(obs.getValueCoded() != 0)
            {
                int obsValueCodedConceptId = obs.getConceptID();
                this.obsConceptIds.put(obs.getObsID(), obsValueCodedConceptId);
            }
            
            
            int encounterId = obs.getEncounterTypeID();
            if(!this.allObsByEncounter.containsKey(encounterId))
            {
                this.allObsByEncounter.put(encounterId, new ArrayList<>());
            }
            List<CustomObs> currObs = this.allObsByEncounter.get(encounterId);
            currObs.add(obs);
            allObsByEncounter.put(encounterId, currObs);
        }
        
    }
    
    public List<CustomObs> getAllObs()
    {
        return this.allObs;
    }
    
    public void setPatientIdentifies(Patient patient)
    {
        Set<PatientIdentifier> allPatientIdentifies = patient.getIdentifiers();
        for(PatientIdentifier pI: allPatientIdentifies)
        {
            this.patientIdentifies.put(pI.getIdentifierType().getId(), pI.getIdentifier());
        }
    }
    
    public int getValueCodedForObs(int obsId)
    {
        if(this.obsConceptIds.containsKey(obsId))
        {
            return this.obsConceptIds.get(obsId); 
        }
        return 0;
    }
    
    public Date extractARTStartDate() {
        Date artStartDate = null;
        CustomObs obs = null;
        obs = this.getObsForConcept(Utils.ART_START_DATE_CONCEPT);//Utils.extractObs(Utils.ART_START_DATE_CONCEPT, allPatientsObsList);
        if (obs != null) {
                artStartDate = obs.getValueDate();
        } else {
                obs = getLastObsOfConceptByDate(Utils.CURRENT_REGIMEN_LINE_CONCEPT);//getFirstObsOfConceptByDate(allPatientsObsList, Utils.CURRENT_REGIMEN_LINE_CONCEPT);
                if (obs != null) {
                        artStartDate = obs.getObsDatetime();
                }
        }
        return artStartDate;
    }
    
    public Date extractEnrollmentDate(List<CustomEncounter> allPatientEncounterList) {
        Date enrollmentDate = null;
        CustomObs obs = null;
       
        obs = this.getObsForConcept(Utils.DATE_OF_HIV_DIAGNOSIS_CONCEPT);//extractObs(Utils.DATE_OF_HIV_DIAGNOSIS_CONCEPT, allPatientObs);
        if (obs != null) {
                enrollmentDate = obs.getValueDate();
        } else {
                CustomEncounter firstEncounter = allPatientEncounterList.get(0);
                if (firstEncounter != null) {
                        enrollmentDate = firstEncounter.getEncounterDatetime();
                }
                //}
        }

        return enrollmentDate;
    }
    
    
            
    public CustomObs getObsForConcept(int conceptId)
    {
        if(!allObsMap.isEmpty())//ensure that the map is not empty
        {
            if(this.allObsMap.containsKey(conceptId))
            {
                return allObsMap.get(conceptId);
            }
            
        }
        return null;
    }
    
    public List<CustomObs> getMultiObsForConcept(int conceptId)
    {
        if(!this.multiValueObsMap.isEmpty())//ensure that the map is not empty
        {
            return this.multiValueObsMap.get(conceptId);
        }
        return null;
    }
    
    public List<CustomObs> getAllObsForEncounters(Integer[] encounterArray)
    {
        List<CustomObs> allObs = new ArrayList<>();
        for (Integer encounterId : encounterArray) {
            if(this.allObsByEncounter.containsKey(encounterId))
            {
                allObs.addAll(this.allObsByEncounter.get(encounterId));
            }
            
        }
        
        return allObs;
             
    }
    
    public String getIdentifier(int identifierTypeId)
    {
        if(this.patientIdentifies.containsKey(identifierTypeId))
        {
            return this.patientIdentifies.get(identifierTypeId);
        }  
        else
        {
            return null;
        }
    }
    
    public Date getEnrollmentDate(List<CustomEncounter> encounters) {
        Date enrollmentDate = null;
        CustomObs obs = null;
        PatientProgram patientProgram = null;

        obs = this.getObsForConcept(Utils.DATE_OF_HIV_DIAGNOSIS_CONCEPT);//extractObs(Utils.DATE_OF_HIV_DIAGNOSIS_CONCEPT, allPatientObs);
        if (obs != null) {
                enrollmentDate = obs.getValueDate();
        } else {
            CustomEncounter firstEncounter = encounters.get(0);//getFirstEncounter(patient, allPatientEncounterList);
            if (firstEncounter != null) {
                    enrollmentDate = firstEncounter.getEncounterDatetime();
            }
                //}
        }

        return enrollmentDate;
    }
    
    public CustomObs getLastObsOfConceptByDate(int conceptID) {
        if(multiValueObsMap.containsKey(conceptID))
        {
            List <CustomObs> allObsForConcept = multiValueObsMap.get(conceptID);
            return allObsForConcept.get(allObsForConcept.size() -1 );
        }
        else if(allObsMap.containsKey(conceptID))
        {
            
            return allObsMap.get(conceptID);
        }
        return null;
        
    }
    public CustomObs getFirstObsOfConceptByDate(int conceptID) {
        if(multiValueObsMap.containsKey(conceptID))
        {
            List <CustomObs> allObsForConcept = multiValueObsMap.get(conceptID);
            return allObsForConcept.get(0);
        }
        else if(allObsMap.containsKey(conceptID))
        {
            
            return allObsMap.get(conceptID);
        }
        return null;
        
    }
    
    public CustomObs extractObsByValues(int conceptID, int valueCoded) {
        CustomObs obs = null;
        obs = this.getObsForConcept(conceptID);
        if(obs != null)
        {
            if(obs.getValueCoded() == valueCoded)
            {
                return obs;
            }
        }
        else if(this.multiValueObsMap.containsKey(conceptID))
        {
            List<CustomObs> obsList = this.getMultiObsForConcept(conceptID);
            for(CustomObs ob: obsList)
            {
                if(ob.getValueCoded() == valueCoded)
                {
                    obs = ob;
                }
            }
        }
        return obs;
    }
    
    /*public List<Obs> getAllObsForEncounter()
    {
        
    }*/
    
    //public 
}
