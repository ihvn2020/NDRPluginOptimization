/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.nigeriaemr.dbmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.openmrs.module.nigeriaemr.ndrUtils.Utils;
import org.openmrs.module.nigeriaemr.omodmodels.CustomObs;
import org.openmrs.module.nigeriaemr.omodmodels.DBConnection;

/**
 *
 * @author The Bright
 */
public class ObsDAO {

    private Connection conn = null;

    private PreparedStatement pStatement = null;

    private ResultSet resultSet = null;
    

    

    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
            if (pStatement != null) {
                pStatement.close();
            }
        } catch (Exception ex) {

        }
    }

    public ObsDAO(Connection openmrsConnection) {
      this.conn=openmrsConnection;
    }
    
    public CustomObs convertResultSetToCustomObs(ResultSet rs) throws SQLException{
        CustomObs cobs=new CustomObs();
        cobs.setPatientID(rs.getInt("PATIENT_ID"));
        cobs.setEncounterID(rs.getInt("ENCOUNTER_ID"));
        cobs.setPepfarID(rs.getString("PEPFAR_ID"));
        cobs.setHospID(rs.getString("HOSP_ID"));
        cobs.setVisitDate(rs.getDate("VISIT_DATE"));
        cobs.setFormName(rs.getString("PMM_FORM"));
        cobs.setFormID(rs.getInt("FORM_ID"));
        cobs.setConceptID(rs.getInt("CONCEPT_ID"));
        cobs.setVariableName(rs.getString("VARIABLE_NAME"));
        cobs.setVariableValue(rs.getString("VARIABLE_VALUE"));
        cobs.setEnteredBy(rs.getString("ENTERED_BY"));
        cobs.setDateEntered(rs.getDate("DATE_CREATED"));
        cobs.setDateChanged(rs.getDate("DATE_CHANGED"));
        cobs.setProvider(rs.getString("PROVIDER"));
        cobs.setUuid(rs.getString("UUID"));
        cobs.setLocationName(rs.getString("LOCATION"));
        cobs.setLocationID(rs.getInt("LOCATION_ID"));
        cobs.setCreator(rs.getInt("CREATOR_ID"));
        cobs.setProviderID(rs.getInt("PROVIDER_ID"));
        cobs.setValueNumeric(rs.getDouble("VALUE_NUMERIC"));
        cobs.setValueCoded(rs.getInt("VALUE_CODED"));
        cobs.setValueDate(rs.getDate("VALUE_DATETIME"));
        cobs.setValueText(rs.getString("VALUE_TEXT"));
        cobs.setValueBoolean(rs.getBoolean("VALUE_BOOL"));
        cobs.setObsGroupID(rs.getInt("OBS_GROUP_ID"));
        cobs.setVoided(rs.getInt("VOIDED"));
        cobs.setDateVoided(rs.getDate("DATE_VOIDED"));
        cobs.setVoidedBy(rs.getInt("VOIDED_BY"));
        return cobs;
    }
}
