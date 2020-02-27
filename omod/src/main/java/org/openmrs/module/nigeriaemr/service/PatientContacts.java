/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.nigeriaemr.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.openmrs.*;
import org.openmrs.api.context.Context;
import org.openmrs.module.htmlformentry.HtmlForm;
import org.openmrs.module.htmlformentry.HtmlFormEntryService;
import org.openmrs.module.nigeriaemr.dbmanager.NdrDBManager;
import org.openmrs.module.nigeriaemr.ndrUtils.ConstantsUtil;
import org.openmrs.module.nigeriaemr.ndrUtils.GeneralMapper;
import org.openmrs.module.nigeriaemr.omodmodels.Client;
import org.openmrs.module.nigeriaemr.omodmodels.PatientContactsModel;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author MORRISON.I
 */
public class PatientContacts {
	
	NdrDBManager dbManageer = null;
	
	public void createContacts(PatientContactsModel pContacts) {
		try {
			dbManageer = new NdrDBManager();
			dbManageer.openConnection();
			dbManageer.createPatientContacts(pContacts);
			dbManageer.closeConnection();
		}
		catch (SQLException ex) {
			Logger.getLogger(PatientContacts.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public List<PatientContactsModel> getAllPatientContactsByIndexId(int indexClientId) {
        List<PatientContactsModel> response = new ArrayList<>();
        try {
            dbManageer = new NdrDBManager();
            dbManageer.openConnection();
            response = dbManageer.getPatientContactsByIndex(indexClientId);
            dbManageer.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(PatientContacts.class.getName()).log(Level.SEVERE, null, ex);
        }

        return response;
    }
	
	public Client pullPatientByReferralCode(String indexClientIndentifier) throws UnirestException {
		Unirest.setObjectMapper(GeneralMapper.getCustomObjectMapper());
		
		HttpResponse<Client> payloads = Unirest.get(ConstantsUtil.BASE_URL + ConstantsUtil.GET_ClIENT)
		        .header("accept", "application/json").queryString("ClientIdentifier", indexClientIndentifier)
		        .asObject(Client.class);
		
		if (payloads != null) {
			return payloads.getBody();
		}
		
		return null;
		
	}
	
	@Transactional
	public void createPatientAndFillHTS(Client client, String indexClientIndentifier, Location location) {

        Patient p = new Patient(GeneralMapper.ClientToPerson(client));
        p.setDateCreated(new Date());
        p.setCreator(Context.getAuthenticatedUser());

        Set<PatientIdentifier> pids = new HashSet<>();
        PatientIdentifier pid_1 = new PatientIdentifier();
        pid_1.setIdentifier(indexClientIndentifier);
        //HTS Identifier type
        pid_1.setIdentifierType(new PatientIdentifierType(ConstantsUtil.PEPFAR_IDENTIFIER_INDEX));
        pid_1.setPatient(p);
        pid_1.setLocation(location);
        pid_1.setPatient(p);
        pid_1.setUuid(UUID.randomUUID().toString());
        pids.add(pid_1);

        p.setIdentifiers(pids);
        p.setUuid(UUID.randomUUID().toString());
        

        Context.getPatientService().savePatient(p);
        
        
           
        
        HtmlFormEntryService service =  Context.getService(HtmlFormEntryService.class);
            HtmlForm hf = new HtmlForm();
             Form f = new Form();
            hf.setForm(f);
           EncounterType et = new EncounterType();
        et.setEncounterTypeId(ConstantsUtil.ADMISSION_ENCOUNTER_TYPE);
        f.setEncounterType(et);
        FormField formField = new FormField();
        
        
       
        
      
    }
}
