/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.nigeriaemr.fragment.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.joda.time.DateTime;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientProgram;
import org.openmrs.module.nigeriaemr.ndrUtils.Utils;
import static org.openmrs.module.nigeriaemr.ndrUtils.Utils.extractObs;
import static org.openmrs.module.nigeriaemr.ndrUtils.Utils.getFirstEncounter;
import static org.openmrs.module.nigeriaemr.ndrUtils.Utils.getFirstObsOfConceptByDate;
import org.openmrs.module.nigeriaemr.ndrfactory2.LabDictionary;
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
public class OBSUtil {
    Map <Integer, CustomObs> allObsMap = new HashMap<>();//will always hold the last obs for that concept
    Map <Integer, List <CustomObs>> allObsByEncounter = new HashMap<>();//key is encounter type id, value is the custom obs
    Map <Integer, Integer> obsConceptIds = new HashMap<>();//this is useful to avoid going back to the db. It holds value coded concept id
    Map<Integer, String> patientIdentifies = new HashMap<>();
    Map <Integer, List<CustomObs>> multiValueObsMap = new HashMap<>();//the index is still concept id, this concept has more than one obs
    List<CustomObs> allObs = new ArrayList<>();
    Map<Integer, List<CustomObs>> obsByGroupId = new HashMap<>();//here, index is the groupid
    Map<Integer, Map<Integer, List<CustomObs>>> obsByEncounterIdMap = new HashMap<>();//first map - index - encounter type Id  second map index is concept id
    Map<Date, Map<Integer, List<CustomObs>>> obsByEncounterDateMap = new HashMap<>();//first map - index is encounter date, second map index is concept id
    Map <Integer, List<CustomObs>> obsByEncounterId = new HashMap<>();//index = encounter id (not the encounter type id)
    public void setPatientObs(List<CustomObs> allObs)
    {
        
        clearAllMaps();
        this.allObs = allObs;
        
        
        for (CustomObs obs : allObs) {
              
            //System.out.println("setting obs"+obs.getEncounterTypeID());
            int obsConceptId = obs.getConceptID();
            Date encounterDate = obs.getVisitDate();
            //System.out.println(encounterDate);
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
            this.allObsMap.put(obsConceptId, obs);//
            
            if(obs.getValueCoded() != 0)
            {
                int obsValueCodedConceptId = obsConceptId;
                this.obsConceptIds.put(obs.getObsID(), obsValueCodedConceptId);
            }
            
              
            //setup obs by encounter id map
            int encounterTypeId = obs.getEncounterTypeID();
            //System.out.println("EncounterTypeId"+encounterTypeId);
            if(!this.obsByEncounterIdMap.containsKey(encounterTypeId))//if the key is not there, lets create a fresh array list
            {
                Map<Integer, List<CustomObs>> tempMap = new HashMap<>();
                this.obsByEncounterIdMap.put(encounterTypeId, tempMap);
            }
            
            Map<Integer, List<CustomObs>> currMapById = this.obsByEncounterIdMap.get(encounterTypeId);
            if(!currMapById.containsKey(obsConceptId))
            {
               List<CustomObs> obsList = new ArrayList<>();
               currMapById.put(obsConceptId, obsList);
            }
            
            List<CustomObs> currObsList = this.obsByEncounterIdMap.get(encounterTypeId).get(obsConceptId);
            currObsList.add(obs);
            currMapById.put(obsConceptId, currObsList);
            this.obsByEncounterIdMap.put(encounterTypeId, currMapById);
            
            //setup   obs by date map
            if(!this.obsByEncounterDateMap.containsKey(encounterDate))//if the key is not there, lets create a fresh array list
            {
                Map<Integer, List<CustomObs>> tempMap = new HashMap<>();
                this.obsByEncounterDateMap.put(encounterDate, tempMap);
            }
            
            Map<Integer, List<CustomObs>> currMapByDate = this.obsByEncounterDateMap.get(encounterDate);
            if(!currMapByDate.containsKey(obsConceptId))
            {
               List<CustomObs> obsList = new ArrayList<>();
               currMapByDate.put(obsConceptId, obsList);
            }
            
            List<CustomObs> currObsList2 = this.obsByEncounterDateMap.get(encounterDate).get(obsConceptId);
            currObsList2.add(obs);
            currMapByDate.put(obsConceptId, currObsList2);
            
            //setup obs by encounter date map
            this.obsByEncounterDateMap.put(encounterDate, currMapByDate);
            
            
            if(!this.allObsByEncounter.containsKey(encounterTypeId))
            {
                this.allObsByEncounter.put(encounterTypeId, new ArrayList<>());
            }
            List<CustomObs> currObs = this.allObsByEncounter.get(encounterTypeId);
            currObs.add(obs);
            allObsByEncounter.put(encounterTypeId, currObs);
            
            //setup obs by group id
            int obsGroupId = obs.getObsGroupID();
            if(!this.obsByGroupId.containsKey(obsGroupId))
            {
                this.obsByGroupId.put(obsGroupId, new ArrayList<CustomObs>());
            }
            List<CustomObs> currObsByGroup = this.obsByGroupId.get(obsGroupId);
            currObsByGroup.add(obs);
            obsByGroupId.put(obsGroupId, currObsByGroup);
            
            
            //encounter id map
            int encounterId = obs.getEncounterID();
            if(!this.obsByEncounterId.containsKey(encounterId))//if the key is not there, lets create a fresh array list
            {
                List<CustomObs> tempMap = new ArrayList<>();
                this.obsByEncounterId.put(encounterId, tempMap);
            }
            
            List<CustomObs> currList = this.obsByEncounterId.get(encounterId);
            currList.add(obs);
            obsByEncounterId.put(encounterId, currList);
        }
        
    }
    
    
    public List<CustomObs> getObsForEncounterId(int encounterId)
    {
        if(this.obsByEncounterId.containsKey(encounterId))
        {
            return this.obsByEncounterId.get(encounterId);
        }
        return null;
    }
   
    public static List<CustomObs> getAllObsGroups(List<CustomObs> obsList, Integer groupingConceptId) {
        List<CustomObs> response = obsList.stream().filter(obb -> obb.getConceptID() == groupingConceptId).collect(Collectors.toList());
        return response;
    }
    
    public Map<Integer, CustomObs> getObsForGroup(int groupId)
    {
        
        Map<Integer, CustomObs> map = new HashMap<>();
        List<CustomObs> obsList = obsByGroupId.get(groupId);
        for(CustomObs obs: obsList)
        {
            map.put(obs.getConceptID(), obs);
        }
        return map;
    }
     public List<CustomObs> getObsGroup(int groupId)
     {
         if(obsByGroupId.containsKey(groupId))
         {
             return obsByGroupId.get(groupId);
         }
         return null;
         
     }
    
    
    public Map<Integer, List<CustomObs>> getAllObsForDate(Date date)
    {
        return this.obsByEncounterDateMap.get(date);
    }
    
    
    
    public Map<Integer, List<CustomObs>> getAllObsByEncounterTypeId(int encounterTypeId)
    {
       return this.obsByEncounterIdMap.get(encounterTypeId);
    }
    
    public Map<Integer, List<CustomObs>> getAllObsByEncounterTypeId(Integer[] encounterTypeIdArray)
    {
        Map<Integer, List<CustomObs>> obsMapList = new HashMap<>();
        
        for(int encounterTypeId : encounterTypeIdArray)
        {   
            if(this.obsByEncounterIdMap.containsKey(encounterTypeId))
            {
               
                //obsMapList.put(encounterTypeId, allObs)
                Map<Integer, List<CustomObs>> tempMapList = this.obsByEncounterIdMap.get(encounterTypeId);
                obsMapList.putAll(tempMapList);
                
            }
        }
       return obsMapList;
    }
    //this returns a list
    public static List<CustomObs> getObsListFromConcept(int conceptId, Map<Integer, List<CustomObs>> obsMap)
    {
        if(obsMap.containsKey(conceptId))
        {
            return obsMap.get(conceptId);
        }
        
        return null;
        
    }
         
    public static CustomObs getObsFromConcept(int conceptId, Map<Integer, List<CustomObs>>obsMap)
    {
        
        if(obsMap != null && obsMap.containsKey(conceptId))
        {
            //return obsMap.get(conceptId).get(obsMap.get(conceptId).size() -1);//get the most recent. incase it is more than one
            return obsMap.get(conceptId).get(0);//get the most recent. incase it is more than one
        }
        return null;   
    }
    
    public static CustomObs getObsFromConcept2(int encounterId, int conceptId, Map<Integer, List<CustomObs>>obsMap)
    {
        
        if(obsMap != null && obsMap.containsKey(conceptId))
        {
            
            //return obsMap.get(conceptId).get(obsMap.get(conceptId).size() -1);//get the most recent. incase it is more than one
            List<CustomObs> obsList = obsMap.get(conceptId);//get the most recent. incase it is more than one
            for(int i=0; i<obsList.size(); i++)
            {
                if(obsList.get(i).getEncounterID() == encounterId)
                {
                    return obsList.get(i);
                }
            }
        }
        return null;   
    }
    
    
   
    
    public Map<Integer, List<CustomObs>> getAllObsForVisit(Date visitDate)
    {
        return this.obsByEncounterDateMap.get(visitDate);
    }
    
    
    
    private void clearAllMaps()
    {
        this.obsByEncounterDateMap.clear();
        this.obsByEncounterIdMap.clear();
        this.allObs.clear();
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
    
    public static int getOBSDataType(CustomObs obs)
    {
        if(obs.getValueCoded() > 0)
        {
            return 2;
        }
        else if(obs.getValueText() != null && !obs.getValueText().equals(""))
        {
            return 3;
        }
        else if(obs.getValueNumeric() > 0)
        {
            return 1;
        }
        else
        {
            return 1;
        }
            
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
    
    public Date getEnrollmentDate(Map<Integer, List<CustomEncounter>> encounters) {
        Date enrollmentDate = null;
        CustomObs obs = null;
        PatientProgram patientProgram = null;

        obs = this.getObsForConcept(Utils.DATE_OF_HIV_DIAGNOSIS_CONCEPT);//extractObs(Utils.DATE_OF_HIV_DIAGNOSIS_CONCEPT, allPatientObs);
        if (obs != null) {
                enrollmentDate = obs.getValueDate();
        } else {
            for(Map.Entry<Integer, List<CustomEncounter>> entry: encounters.entrySet())
            {
                Date currDate = entry.getValue().get(0).getEncounterDatetime();
                if(enrollmentDate == null)
                {
                    enrollmentDate = currDate;
                    
                }
                if(currDate.before(enrollmentDate))
                {
                    enrollmentDate = currDate;
                }
               
            }
            
            return enrollmentDate;
                //}
        }

        return enrollmentDate;
    }
    
    
    public static CustomEncounter getFirstEncounter(Map<Integer, List<CustomEncounter>> encounters) {
        CustomEncounter firstEncounter = null;
        Date encounterDate = null;
        
       
            for(Map.Entry<Integer, List<CustomEncounter>> entry: encounters.entrySet())
            {
                Date currDate = entry.getValue().get(0).getEncounterDatetime();
                if(encounterDate == null)
                {
                    encounterDate = currDate;
                    firstEncounter = entry.getValue().get(0);
                    
                }
                if(currDate.before(encounterDate))
                {
                    firstEncounter = entry.getValue().get(0);
                }
               
            }
            
            return firstEncounter;
                //}
        
    }
    
    public static CustomEncounter getLastEncounter(Map<Integer, List<CustomEncounter>> encounters) {
        CustomEncounter firstEncounter = null;
        Date encounterDate = null;
        
       
            for(Map.Entry<Integer, List<CustomEncounter>> entry: encounters.entrySet())
            {
                Date currDate = entry.getValue().get(0).getEncounterDatetime();
                if(encounterDate == null)
                {
                    encounterDate = currDate;
                    firstEncounter = entry.getValue().get(0);
                    
                }
                if(currDate.after(encounterDate))
                {
                    firstEncounter = entry.getValue().get(0);
                }
               
            }
            
            return firstEncounter;
                //}
        
    }
    public static CustomEncounter getLastEncounterForEncounterType(int encounterType, Map<Integer, List<CustomEncounter>> encounters) {
        CustomEncounter lastEncounter = null;
        Date encounterDate = null;
        if(encounters.containsKey(encounterType))
        {
            List<CustomEncounter> encounterList = encounters.get(encounterType);

            for(CustomEncounter encounter: encounterList)
            {
                Date currDate = encounter.getEncounterDatetime();
                if(encounterDate == null)
                {
                    encounterDate = currDate;
                    lastEncounter = encounter;

                }
                if(currDate.before(encounterDate))
                {
                    lastEncounter = encounter;
                }

            }
        }

            
        return lastEncounter;
                //}
        
    }
    
    
    
    public CustomObs getLastObsOfConceptByDate(int conceptID) {
        /*if(multiValueObsMap.containsKey(conceptID))
        {
            List <CustomObs> allObsForConcept = multiValueObsMap.get(conceptID);
            return allObsForConcept.get(allObsForConcept.size() -1 );
        }*/
        if(allObsMap.containsKey(conceptID))
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
    
    
    
    public CustomObs getARTStateDate()
    {
        if(allObsMap.containsKey(Utils.ART_START_DATE_CONCEPT))
        {
            
            return allObsMap.get(Utils.ART_START_DATE_CONCEPT);
        }
        else if(multiValueObsMap.containsKey(Utils.CURRENT_REGIMEN_LINE_CONCEPT))
        {
            List <CustomObs> allObsForConcept = multiValueObsMap.get(Utils.CURRENT_REGIMEN_LINE_CONCEPT);
            return allObsForConcept.get(0);
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
    public static CustomObs extractObsByValuesForMap(int conceptID, int valueCoded, Map<Integer, List<CustomObs>> obsList) {
        CustomObs obs = null;
        List<CustomObs> tempList = obsList.get(conceptID);
        if(tempList != null && !tempList.isEmpty())
        for(CustomObs ob: tempList)
        {
            if(ob.getValueCoded() == valueCoded && ob.getConceptID() == conceptID)
            {
                obs = ob;
            }
        }
        return obs;
    }
    public static CustomObs extractObsByValuesForMap(int conceptID, int valueCoded,  List<CustomObs> obsList) {
        CustomObs obs = null;
       // List<CustomObs> tempList = obsList.get(conceptID);
        for(CustomObs ob: obsList)
        {
            
            if(ob.getValueCoded() == valueCoded && ob.getConceptID() == conceptID)
            {
                obs = ob;
            }
        }
        return obs;
    }
    
     public static CustomObs extractObsByValuesForMap2(int conceptID, int valueCoded, Map<Integer, CustomObs> obsList) {
        //CustomObs obs = null;
        return obsList.get(conceptID);
       
    }
    
    public static DateTime extractMedicationDuration(Date visitDate, Map<Integer, List<CustomObs>> obsMap) {
               DateTime stopDateTime = null;
               DateTime startDateTime = null;
               int durationDays = 0;
               CustomObs obs = null;
              
               
               //obsList.addAll( new ArrayList<List<CustomObs>>(obsMap.values()));
               //List<CustomObs> obsList = (ArrayList<CustomObs>)obsMap.values();
               
               //List<CustomObs> obList = (Arrays.asList((T[]) obsMap.values().toArray()));
               //CustomObs obsGroup = obsMap.get(Utils.ARV_DRUGS_GROUPING_CONCEPT_SET).get(0);//Utils.extractObs(Utils.ARV_DRUGS_GROUPING_CONCEPT_SET, obsList);
               //obs = obsMap.get(obs)//Utils.extractObsGroupMemberWithConceptID(Utils.MEDICATION_DURATION_CONCEPT, obsList, obsGroup);
               /*if (obs != null) {
                       durationDays = (int) obs.getValueNumeric();
                       startDateTime = new DateTime(visitDate);
                       stopDateTime = startDateTime.plusDays(durationDays);
               }*/
               
                //CustomObs obsGroup = OBSUtil.getObsFromConcept(Utils.ARV_DRUGS_GROUPING_CONCEPT_SET, obsList);//Utils.extractObs(Utils.ARV_DRUGS_GROUPING_CONCEPT_SET, obsList);
		obs = OBSUtil.getObsFromConcept(Utils.MEDICATION_DURATION_CONCEPT, obsMap);//Utils.extractObsGroupMemberWithConceptID(Utils.MEDICATION_DURATION_CONCEPT, obsList, obsGroup);
		//System.out.println("Visit Date "+visitDate);
                if (obs != null) {
                    
                  
			durationDays = (int) obs.getValueNumeric();
			startDateTime = new DateTime(visitDate);
			stopDateTime = startDateTime.plusDays(durationDays);
                       
		}
		if (stopDateTime == null) {
                   
			obs = OBSUtil.getObsFromConcept(Utils.NEXT_APPOINTMENT_DATE_CONCEPT, obsMap);//Utils.extractObs(Utils.NEXT_APPOINTMENT_DATE_CONCEPT, obsList);
			if (obs != null) {
				stopDateTime = new DateTime(obs.getValueDate());
			}
		}
                
               
                    //System.out.println("Next App "+stopDateTime.toString());
               return stopDateTime;
     }
    
    public  DateTime extractMedicationDuration2(Date visitDate) {
               DateTime stopDateTime = null;
               DateTime startDateTime = null;
               int durationDays = 0;
               CustomObs obs = null;
              
               
               //obsList.addAll( new ArrayList<List<CustomObs>>(obsMap.values()));
               //List<CustomObs> obsList = (ArrayList<CustomObs>)obsMap.values();
               
               //List<CustomObs> obList = (Arrays.asList((T[]) obsMap.values().toArray()));
               //CustomObs obsGroup = obsMap.get(Utils.ARV_DRUGS_GROUPING_CONCEPT_SET).get(0);//Utils.extractObs(Utils.ARV_DRUGS_GROUPING_CONCEPT_SET, obsList);
               //obs = obsMap.get(obs)//Utils.extractObsGroupMemberWithConceptID(Utils.MEDICATION_DURATION_CONCEPT, obsList, obsGroup);
               /*if (obs != null) {
                       durationDays = (int) obs.getValueNumeric();
                       startDateTime = new DateTime(visitDate);
                       stopDateTime = startDateTime.plusDays(durationDays);
               }*/
               
                //CustomObs obsGroup = OBSUtil.getObsFromConcept(Utils.ARV_DRUGS_GROUPING_CONCEPT_SET, obsList);//Utils.extractObs(Utils.ARV_DRUGS_GROUPING_CONCEPT_SET, obsList);
		obs = this.getObsForConcept(Utils.MEDICATION_DURATION_CONCEPT);//.getObsFromConcept(Utils.MEDICATION_DURATION_CONCEPT, obsMap);//Utils.extractObsGroupMemberWithConceptID(Utils.MEDICATION_DURATION_CONCEPT, obsList, obsGroup);
		
                if (obs != null) {
                    
                  
			durationDays = (int) obs.getValueNumeric();
			startDateTime = new DateTime(visitDate);
			stopDateTime = startDateTime.plusDays(durationDays);
                       
		}
		if (stopDateTime == null) {
                   
			obs = this.getObsForConcept(Utils.NEXT_APPOINTMENT_DATE_CONCEPT);//OBSUtil.getObsFromConcept(Utils.NEXT_APPOINTMENT_DATE_CONCEPT, obsMap);//Utils.extractObs(Utils.NEXT_APPOINTMENT_DATE_CONCEPT, obsList);
			if (obs != null) {
				stopDateTime = new DateTime(obs.getValueDate());
			}
		}
                
                
               return stopDateTime;
     }
    
    public  static DateTime extractMedicationDuration3(Date visitDate, Map<Integer, List<CustomObs>>obsMap, CustomEncounter encounter) {
               DateTime stopDateTime = null;
               DateTime startDateTime = null;
               int durationDays = 0;
               CustomObs obs = null;
              
               try{
               
                    //CustomObs obsGroup = OBSUtil.getObsFromConcept(Utils.ARV_DRUGS_GROUPING_CONCEPT_SET, obsList);//Utils.extractObs(Utils.ARV_DRUGS_GROUPING_CONCEPT_SET, obsList);
                    
                    obs = (obsMap.containsKey(Utils.MEDICATION_DURATION_CONCEPT)) ? obsMap.get(Utils.MEDICATION_DURATION_CONCEPT).get(0) : null;//this.getObsForConcept(Utils.MEDICATION_DURATION_CONCEPT);//.getObsFromConcept(Utils.MEDICATION_DURATION_CONCEPT, obsMap);//Utils.extractObsGroupMemberWithConceptID(Utils.MEDICATION_DURATION_CONCEPT, obsList, obsGroup);
                    /*for(int i=0; i<obsList.size(); i++)
                    {
                        if(obsList.get(i).getEncounterID() == encounter.getEncounterId())
                        {
                            obs = obsList.get(i);
                        }
                    }*/
                    if (obs != null) {


                            durationDays = (int) obs.getValueNumeric();
                            startDateTime = new DateTime(visitDate);
                            stopDateTime = startDateTime.plusDays(durationDays);

                    }
                    if (stopDateTime == null) {

                            obs = (obsMap.containsKey(Utils.NEXT_APPOINTMENT_DATE_CONCEPT)) ? obsMap.get(Utils.NEXT_APPOINTMENT_DATE_CONCEPT).get(0) : null;//this.getObsForConcept(Utils.NEXT_APPOINTMENT_DATE_CONCEPT);//OBSUtil.getObsFromConcept(Utils.NEXT_APPOINTMENT_DATE_CONCEPT, obsMap);//Utils.extractObs(Utils.NEXT_APPOINTMENT_DATE_CONCEPT, obsList);
                            if (obs != null) {
                                    stopDateTime = new DateTime(obs.getValueDate());
                            }
                    }
               }
               catch(Exception e)
               {
                   e.printStackTrace();
               }
                
               return stopDateTime;
     }
    
    public static List<CustomObs> getObsGroup(int groupId, Map<Integer, List<CustomObs>> allObs)
    {
        return allObs.get(groupId);
    }
    
    public static Map<Integer, CustomObs> getObsGroup2(int groupId, Map<Integer, List<CustomObs>> allObs)
    {
        Map<Integer, CustomObs> obsGroup = new HashMap<>();
        List<CustomObs> tempObsList =  allObs.get(groupId);
        for(CustomObs obs: tempObsList)
        {
            obsGroup.put(obs.getConceptID(), obs);
        }
        return obsGroup;
    }
    
    public static CustomObs extractObsGroupMemberWithConceptID(int conceptID, List<CustomObs> obsList, CustomObs obsGrouping) {
		CustomObs obs = null;
		for (CustomObs ele : obsList) {
			if (ele.getConceptID() == conceptID && ele.getObsGroupID() == obsGrouping.getObsGroupID()) {
				obs = ele;
			}
		}
		return obs;
	}
    
    
    
    public static CustomObs extractObs(int conceptID, List<CustomObs> obsList) {

        if (obsList == null) {
            return null;
        }
        return obsList.stream().filter(ele -> ele.getConceptID() == conceptID).findFirst().orElse(null);
    }
    
    public static Set<Date> getUniqueDatesForEncounterTypes(Map<Integer, Set<Date>> uniqueSet, Integer[] encounterTypeArr)
    {
        Set<Date> uniqueSetList = new HashSet<>();
        for(int typeId : encounterTypeArr)
        {
            if(uniqueSet.containsKey(typeId))
            {
                uniqueSetList.addAll(uniqueSet.get(typeId));
            }
        }
        return uniqueSetList;
    }
    
    public Map<Integer, CustomObs> getObsForEncounterId2(int encounterId)
    {
        if(this.obsByEncounterId.containsKey(encounterId))
        {
            List<CustomObs> obsList = this.obsByEncounterId.get(encounterId);
            Map<Integer, CustomObs> obsMap = new HashMap<>();
            for(CustomObs obs : obsList)
            {
                obsMap.put(obs.getConceptID(), obs);
            }
            return obsMap;
        }
        return null;
         
    }
}
