/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.nigeriaemr.xmlgenerator;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import org.openmrs.module.nigeriaemr.fragment.controller.NdrFragmentController;
import org.openmrs.module.nigeriaemr.model.ndr.FacilityType;
import org.openmrs.module.nigeriaemr.model.ndr.MessageHeaderType;
import org.openmrs.module.nigeriaemr.ndrUtils.Utils;

/**
 * @author lordmaul
 */
public class MessageHeaderTypeBuilder {
	
	MessageHeaderType messageHeaderType;
	
	public MessageHeaderType build(String facilityId, String facilityName, String facilityTypeCode) {
		
		try {
			messageHeaderType = new MessageHeaderType();
			Calendar cal = Calendar.getInstance();
			
			messageHeaderType.setMessageCreationDateTime(Utils.getXmlDateTime(cal.getTime()));
			messageHeaderType.setMessageStatusCode("INITIAL");
			messageHeaderType.setMessageSchemaVersion(new BigDecimal("1.4"));
			messageHeaderType.setMessageUniqueID(UUID.randomUUID().toString());
			messageHeaderType.setMessageSendingOrganization(this.buildSendingOrganization(facilityName, facilityId,
			    facilityTypeCode));
			return messageHeaderType;
		}
		catch (DatatypeConfigurationException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	private FacilityType buildSendingOrganization(String facilityName, String facilityID, String facilityTypeCode) {
		FacilityType facilityType = new FacilityType();
		facilityType.setFacilityName(facilityName);
		facilityType.setFacilityID(facilityID);
		facilityType.setFacilityTypeCode(facilityTypeCode);
		return facilityType;
	}
}
