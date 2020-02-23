/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.nigeriaemr.ndrfactory;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.UUID;
import javax.xml.datatype.DatatypeConfigurationException;
import org.openmrs.module.nigeriaemr.model.ndr.FacilityType;
import org.openmrs.module.nigeriaemr.model.ndr.IndividualReportType;
import org.openmrs.module.nigeriaemr.model.ndr.MessageHeaderType;
import org.openmrs.module.nigeriaemr.ndrUtils.Utils;

/**
 *
 * @author The Bright
 */
public class NDRCustomConverter {
    private final static String messageSchemaVersion="1.4";
    private final static String messageStatusCode="INITIAL";
    private final static String facilityTypeCodeForIP="IP";
    
    
    
    private NDRCustomConverter(){
        
    }
    
    
    private MessageHeaderType createMessageHeaderType(String ipName, String ipCode) throws DatatypeConfigurationException {
        MessageHeaderType header = new MessageHeaderType();
        FacilityType facilityType=Utils.createFacilityType(ipName, ipCode, facilityTypeCodeForIP);
        Calendar cal = Calendar.getInstance();
        header.setMessageCreationDateTime(Utils.getXmlDateTime(cal.getTime()));
        header.setMessageStatusCode(messageStatusCode);
        header.setMessageSchemaVersion(new BigDecimal(messageSchemaVersion));
        header.setMessageUniqueID(UUID.randomUUID().toString());
        header.setMessageSendingOrganization(facilityType);
        return header;
    }
    public IndividualReportType createIndividualReportType(){
        IndividualReportType individualReport=new IndividualReportType();
        
        return individualReport;
    }
    
}
