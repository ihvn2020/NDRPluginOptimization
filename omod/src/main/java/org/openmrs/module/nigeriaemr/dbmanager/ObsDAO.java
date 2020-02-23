/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.nigeriaemr.dbmanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.openmrs.module.nigeriaemr.omodmodels.CustomObs;

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
        this.conn = openmrsConnection;
    }

    public CustomObs convertResultSetToCustomObs(ResultSet rs) throws SQLException {
        CustomObs cobs = new CustomObs();
        cobs.setPatientID(rs.getInt("person_id"));
        cobs.setEncounterID(rs.getInt("encounter_id"));
        cobs.setPepfarID(rs.getString("pepfar_id"));
        cobs.setHospID(rs.getString("hosp_id"));
        cobs.setVisitDate(rs.getDate("encounter_datetime"));
        cobs.setFormName(rs.getString("pmm_form"));
        cobs.setFormID(rs.getInt("form_id"));
        cobs.setConceptID(rs.getInt("concept_id"));
        cobs.setVariableName(rs.getString("variable_name"));
        cobs.setVariableValue(rs.getString("variable_value"));
        cobs.setEnteredBy(rs.getString("entered_by"));
        cobs.setDateEntered(rs.getDate("date_created"));
        cobs.setDateChanged(rs.getDate("date_changed"));
        cobs.setEncounterTypeID(rs.getInt("encounter_type"));
        // cobs.setProvider(rs.getString("PROVIDER"));
        cobs.setUuid(rs.getString("uuid"));
        //cobs.setLocationName(rs.getString("LOCATION"));
        cobs.setLocationID(rs.getInt("location_id"));
        cobs.setCreator(rs.getInt("creator"));
        //cobs.setProviderID(rs.getInt("PROVIDER_ID"));
        cobs.setValueNumeric(rs.getDouble("value_numeric"));
        cobs.setValueCoded(rs.getInt("value_coded"));
        cobs.setValueDate(rs.getDate("value_datetime"));
        cobs.setValueText(rs.getString("value_text"));
        // cobs.setValueBoolean(rs.getBoolean("VALUE_BOOL"));
        cobs.setObsGroupID(rs.getInt("obs_group_id"));
        cobs.setVoided(rs.getInt("voided"));
        cobs.setDateVoided(rs.getDate("date_voided"));
        cobs.setVoidedBy(rs.getInt("voided_by"));
        return cobs;
    }

    public List<CustomObs> getObsForPatient(int patientID, Set<Integer> encounterTypeSet) {
        List<CustomObs> obsList = new ArrayList<>();
        String sql_text = "SELECT\n"
                + "obs.obs_id,\n"
                + "obs.person_id, \n"
                + "obs.obs_datetime, \n"
                + "encounter.encounter_id,\n"
                + "encounter.encounter_datetime,\n"
                + "obs.concept_id, \n"
                + "pid1.identifier as pepfar_id ,\n"
                + "pid2.identifier as hosp_id ,\n"
                + "cn2.name as variable_name,\n"
                + "obs.value_numeric,\n"
                + "obs.value_coded,\n"
                + "obs.value_text,\n"
                + "obs.value_datetime,\n"
                + "cast(coalesce(cn1.`name`,`obs`.`value_numeric`,`obs`.`value_datetime`,`obs`.`value_text`) as char charset utf8)  AS `variable_value`,\n"
                + "obs.date_created,\n"
                + "encounter.date_changed,\n"
                + "encounter.date_voided,\n"
                + "encounter.changed_by,\n"
                + "obs.voided,\n"
                + "obs.date_voided,\n"
                + "encounter.voided_by,\n"
                + "obs.creator,\n"
                + "encounter.encounter_type,\n"
                + "obs.obs_group_id,\n"
                + "obs.uuid ,\n"
                + "encounter.form_id,\n"
                + "form.`name`  as pmm_form,\n"
                + "obs.location_id\n"
                + "from \n"
                + "obs\n"
                + "INNER JOIN  patient_identifier  pid1 on(pid1.patient_id=obs.person_id and pid1.voided=0 and pid1.identifier_type=4)\n"
                + "INNER JOIN patient on(patient.patient_id=obs.person_id and patient.voided=0)\n"
                + "INNER JOIN  patient_identifier  pid2 on(pid2.patient_id=obs.person_id and pid2.voided=0 and pid2.identifier_type=5)\n"
                + "INNER JOIN encounter on(encounter.encounter_id=obs.encounter_id and encounter.voided=0)\n"
                + "INNER JOIN form on(form.form_id=encounter.form_id and encounter.voided=0)\n"
                + "INNER JOIN concept_name cn1 on(cn1.concept_id=obs.value_coded and cn1.locale='en' and cn1.locale_preferred=1)\n"
                + "INNER JOIN concept_name cn2 on(cn2.concept_id=obs.concept_id and cn2.locale='en' and cn2.locale_preferred=1)\n"
                + "WHERE encounter.encounter_type IN ('"+buildString(encounterTypeSet)+"') and encounter.voided=0 and encounter.patient_id='"+patientID+"'";
        
        Statement stmt = null;
        ResultSet rs = null;
        CustomObs obs = null;
        try {
            stmt = conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY,
                    java.sql.ResultSet.CONCUR_READ_ONLY);
            stmt.setFetchSize(Integer.MIN_VALUE);
            rs = stmt.executeQuery(sql_text);
            while(rs.next()) {
                obs = convertResultSetToCustomObs(rs);
                obsList.add(obs);
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            //screen.updateStatus(ex.getMessage());
            ex.printStackTrace();
        } finally {
            cleanUp(rs, stmt);
        }
        return obsList;
        
    }

    public StringBuilder buildString(Set<Integer> ids) {
        StringBuilder sbuilder = new StringBuilder();
        for (int ele : ids) {
            if (sbuilder.length() > 0) {
                sbuilder.append(",");
            }
            sbuilder.append(ele);
        }
        return sbuilder;
    }
    public void cleanUp(ResultSet rs, Statement stmt) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}
