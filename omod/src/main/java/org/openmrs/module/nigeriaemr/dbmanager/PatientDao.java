/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.nigeriaemr.dbmanager;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import org.openmrs.Patient;
import org.openmrs.PersonAddress;
import org.openmrs.module.nigeriaemr.model.ndr.AddressType;
import org.openmrs.module.nigeriaemr.model.ndr.FingerPrintType;
import org.openmrs.module.nigeriaemr.model.ndr.IdentifierType;
import org.openmrs.module.nigeriaemr.model.ndr.LeftHandType;
import org.openmrs.module.nigeriaemr.model.ndr.RightHandType;
import org.openmrs.module.nigeriaemr.ndrUtils.Utils;
import org.openmrs.module.nigeriaemr.xmlgenerator.CustomEncounter;
import org.openmrs.module.nigeriaemr.xmlgenerator.CustomObs;
import org.openmrs.module.nigeriaemr.xmlgenerator.CustomPatient;

/**
 * @author lordmaul
 */
public class PatientDao {
	
	public List<CustomPatient> getAllPatients(int offset, int limit, String startDate, String endDate) {//we are supposed to get only patients who have had something done after the ndr last updated
            String query = "SELECT person.person_id, person.birthdate, person.birthdate_estimated, person.gender, person.dead, person.death_date, ";
            query += " person.cause_of_death, person_name.given_name, person_name.middle_name, person_name.family_name,  ";
            query += " person_attribute.value AS phone FROM patient ";
            query += " JOIN  person ON person.person_id=patient.patient_id ";
            query += " LEFT JOIN person_name ON person_name.person_id=person.person_id ";
            query += " LEFT JOIN person_attribute ON  person_attribute.person_id=person.person_id AND person_attribute.person_attribute_type_id=8 ";
            query += " JOIN (SELECT MAX(obs_datetime) AS max_datetime, person_id \n" +
                     " FROM obs \n" +
                     " GROUP BY  person_id ) max_obs ON (max_obs.person_id=patient.patient_id) \n";
            query += " WHERE person.voided=0 AND max_obs.max_datetime BETWEEN '"+startDate+"' AND '"+endDate+"' GROUP BY person.person_id  LIMIT "+offset+", "+limit;
            //query += " WHERE person.voided=0 AND person.person_id=14121 GROUP BY person.person_id LIMIT "+offset+", "+limit;  
            //System.out.println(query);  
            Statement stmt = null;
            ResultSet rs = null;
            Connection con = null;
            try {
                
                con = Database.connectionPool.getConnection();
                //stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
                stmt = con.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
                stmt.setFetchSize(Integer.MIN_VALUE);
                rs = stmt.executeQuery(query);
                List<CustomPatient> allPatients = new ArrayList<>();
                while (rs.next()) {
                    int patientId = rs.getInt("person_id"); 
                    String gender = rs.getString("gender");
                    String fName = rs.getString("given_name");
                    String lName = rs.getString("family_name");
                    String mName = rs.getString("middle_name");
                    if(rs.wasNull())
                        mName = "";
                    String pNumber = rs.getString("phone");
                    
                    String bDate = rs.getString("birthdate");
                    if(rs.wasNull())
                    {
                        bDate = "";
                    }
                        
                    
                    allPatients.add(new CustomPatient(patientId, bDate, gender, fName, lName, mName, pNumber));
                    
                }
                rs.close();
                stmt.close();
                Database.connectionPool.free(con);
                return allPatients;
            } catch (SQLException ex) {
                //screen.updateStatus(ex.getMessage());
                ex.printStackTrace();
                return null;
                
            } finally {
                try {
                        rs.close();
                        stmt.close();
                        Database.connectionPool.free(con);
                    } catch (SQLException ex) {
                        //Logger.getLogger(PatientDao.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
	}
	
	public int getTotalPatients() {
		String query = "SELECT COUNT(patient_id) AS count  FROM patients ";
		
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			stmt = con.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			
			//stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			stmt.setFetchSize(Integer.MIN_VALUE);
			rs = stmt.executeQuery(query);
			
			rs.next();
			int totalCount = rs.getInt("count");
			
			return totalCount;
		}
		catch (SQLException ex) {
			//screen.updateStatus(ex.getMessage());
			ex.printStackTrace();
			return 0;
		}
		finally {
			try {
				rs.close();
				stmt.close();
				Database.connectionPool.free(con);
			}
			catch (SQLException ex) {
				//Logger.getLogger(PatientDao.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
	
	public static String getGlobalProperty(String propertyName) {
		String query = "SELECT global_property.* FROM global_property where global_property.property= '" + propertyName
		        + "' LIMIT 1";
		
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = Database.connectionPool.getConnection();
			stmt = con.createStatement();
			//stmt = Database.conn.createStatement();
			//stmt.setString(1, propertyName);
			rs = stmt.executeQuery(query);
			String value = "";
			if (rs.next()) {
				value = rs.getString("property_value");
			}
			
			//rs.close();
			//stmt.close();
			return value;
		}
		catch (SQLException ex) {
			//screen.updateStatus(ex.getMessage());
			ex.printStackTrace();
			return "";
		}
		finally {
			try {
				if (rs != null && stmt != null) {
					rs.close();
					stmt.close();
					Database.connectionPool.free(con);
				}
				
			}
			catch (SQLException ex) {
				ex.printStackTrace();
				//Logger.getLogger(PatientDao.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		//return "";
	}
	
	/*public List<CustomPatient> getObsForPatient(int patietnId) {
	    String query = "SELECT person.person_id, person.birthdate, person.birthdate_estimated, person.gender, person.dead, person.death_date, ";
	    query += " person.cause_of_death, person_name.given_name, person_name.middle_name, person_name.family_name,  ";
	    query += " person_attribute_phone.value AS phone FROM patients ";
	    query += " JOIN  person ON person.person_id=patient.patient_id ";
	    query += " LEFT JOIN person_name As person_name.person_id=person.person_id ";
	    query += " LEFT JOIN person_attribute person_attribute_phone.person_id=person.person_id AND person_attribute_phone.person_attribute_type_id=8 ";
	      
	    Statement stmt = null;
	    ResultSet rs = null;
	    try {
	        stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
	        stmt.setFetchSize(Integer.MIN_VALUE);
	        rs = stmt.executeQuery(query);
	        List<CustomPatient> allPatients = new ArrayList<>();
	        while (rs.next()) {
	            int patientId = rs.getInt("person_id");
	            String bDate = rs.getString("birthdate");
	            String gender = rs.getString("gender");
	            String fName = rs.getString("given_name");
	            String lName = rs.getString("family_name");
	            String mName = rs.getString("middle_name");
	            String pNumber = rs.getString("phone");
	            allPatients.add(new CustomPatient(patientId, bDate, gender, fName, lName, mName, pNumber));
	            
	        }
	        rs.close();
	        stmt.close();
	        return allPatients;
	    } catch (SQLException ex) {
	        //screen.updateStatus(ex.getMessage());
	        ex.printStackTrace();
	        return null;
	        
	    } finally {
	        
	    }
	}*/
	
	public List<CustomObs> getObsForPatient(int patientID, Set<Integer> encounterTypeSet) {
        List<CustomObs> obsList = new ArrayList<>();
        String sql_text = "SELECT\n"
                + "obs.obs_id,\n"
                + "obs.person_id, \n"
                + "obs.obs_datetime, \n"
                + "encounter.encounter_id,\n"
                + "encounter.encounter_datetime,\n"
                + "visit.date_started, \n"
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
                + "obs_group.concept_id AS group_concept_id,\n"
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
                + "LEFT  JOIN concept_name cn1 on(cn1.concept_id=obs.value_coded and cn1.locale='en' and cn1.locale_preferred=1)\n"
                + "LEFT  JOIN concept_name cn2 on(cn2.concept_id=obs.concept_id and cn2.locale='en' and cn2.locale_preferred=1)\n"
                + " JOIN visit ON visit.visit_id=encounter.visit_id \n"
               
                + "LEFT JOIN obs obs_group on(obs_group.obs_id=obs.obs_group_id)\n"
                
                + "WHERE encounter.voided=0 and encounter.patient_id='" + patientID + "' GROUP BY obs.obs_id ORDER BY obs.obs_datetime ASC ";
                //+ "WHERE encounter.encounter_type IN ('" + buildString(encounterTypeSet) + "') and encounter.voided=0 and encounter.patient_id='" + patientID + "'";
        
        Statement stmt = null;
        ResultSet rs = null;
        CustomObs obs = null;
         Connection con = null;
        try {
           con = Database.connectionPool.getConnection();
           stmt = con.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY,
                    java.sql.ResultSet.CONCUR_READ_ONLY);
            //stmt = Database.getConnection().createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY,
              //      java.sql.ResultSet.CONCUR_READ_ONLY);
            stmt.setFetchSize(50);
            rs = stmt.executeQuery(sql_text);
            while (rs.next()) {
                obs = convertResultSetToCustomObs(rs);
                obsList.add(obs);
                if(obs.getConceptID() == 5090)
                {
                    System.out.println("it is indeed here "+obs.getVisitDate());
                }
            }
           // rs.close();
           // stmt.close();
        } catch (SQLException ex) {
            //screen.updateStatus(ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                Database.connectionPool.free(con);
            } catch (SQLException ex) {
                //Logger.getLogger(PatientDao.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
        return obsList;
    }
	
	public Map<Integer, IdentifierType> getAllPatientIdentifiers(int patientId)
    {
        String query = "SELECT patient_identifier.* FROM patient_identifier WHERE patient_id="+patientId;  
              
            Statement stmt = null;
            ResultSet rs = null;
            Connection con = null;
            try {
                con = Database.connectionPool.getConnection();
                stmt = con.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
                
               // stmt = Database.conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
                
                stmt.setFetchSize(Integer.MIN_VALUE);
                rs = stmt.executeQuery(query);
                Map<Integer, IdentifierType> allIdentifiers = new HashMap<>();
                while (rs.next()) {
                    IdentifierType idType = buildIdentifier(rs);
                    int identifierType = rs.getInt("identifier_type");
                    allIdentifiers.put(identifierType, idType);
                    
                }
                //rs.close();
                //stmt.close();
                return allIdentifiers;
            } catch (SQLException ex) {
                //screen.updateStatus(ex.getMessage());
                ex.printStackTrace();
                return null;
                
            } finally {
                try {
                    rs.close();
                    stmt.close();
                    Database.connectionPool.free(con);
                } catch (SQLException ex) {
                    //Logger.getLogger(PatientDao.class.getName()).log(Level.SEVERE, null, ex);
                 }
            }
    }
	
	private IdentifierType buildIdentifier(ResultSet rs) throws SQLException {
		int identifierType = rs.getInt("identifier_type");
		String identifier = rs.getString("identifier");
		String prefered = rs.getString("preferred");
		
		IdentifierType temp = new IdentifierType();
		if (identifierType == Utils.PEPFAR_IDENTIFIER_INDEX)//pepfarid
		{
			temp.setIDNumber(identifier);
			temp.setIDTypeCode("PEPFARID");
		} else if (identifierType == Utils.HOSPITAL_IDENTIFIER_INDEX) {
			temp.setIDNumber(identifier);
			temp.setIDTypeCode("PI");
		} else if (identifierType == Utils.OTHER_IDENTIFIER_INDEX) {
			temp.setIDNumber(identifier);
			temp.setIDTypeCode("PE");
		} else if (identifierType == Utils.HTS_IDENTIFIER_INDEX) {
			temp.setIDNumber(identifier);
			temp.setIDTypeCode("HTS");
		} else if (identifierType == Utils.PMTCT_IDENTIFIER_INDEX) {
			temp.setIDNumber(identifier);
			temp.setIDTypeCode("ANC");
		} else if (identifierType == Utils.EXPOSE_INFANT_IDENTIFIER_INDEX) {
			temp.setIDNumber(identifier);
			temp.setIDTypeCode("EI");
		} else if (identifierType == Utils.PEP_IDENTIFIER_INDEX) {
			temp.setIDNumber(identifier);
			temp.setIDTypeCode("PEP");
		} else if (identifierType == Utils.PEP_IDENTIFIER_INDEX) {
			temp.setIDNumber(identifier);
			temp.setIDTypeCode("PEP");
		}
		return temp;
		
	}
	
	public FingerPrintType getPatientsFingerPrint(int patientId) {
		int id = patientId;
		//Connection connection;
		Connection con = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			
			con = Database.connectionPool.getConnection();
			//  DBConnection connResult = Utils.getNmrsConnectionDetails();
			//connection = DriverManager.getConnection(connResult.getUrl(), connResult.getUsername(), connResult.getPassword());
			statement = con.createStatement();//Database.getConnection().createStatement();
			String sqlStatement = ("SELECT template, fingerPosition, date_created,creator FROM biometricinfo WHERE patient_Id = " + id);
			result = statement.executeQuery(sqlStatement);
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
				//connection.close();
				return null;
			}
			//connection.close();
			return fingerPrintsType;
		}
		catch (SQLException e) {
			e.printStackTrace();
			//LoggerUtils.write(NDRMainDictionary.class.getName(), e.getMessage(), LogFormat.FATAL, LoggerUtils.LogLevel.live.live);
		}
		catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		finally {
			//cleanUp(resultSet, pStatement);
			
			try {
				result.close();
				statement.close();
				Database.connectionPool.free(con);
			}
			catch (SQLException ex) {
				//Logger.getLogger(PatientDao.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return null;
	}
	
	public CustomObs convertResultSetToCustomObs(ResultSet rs) throws SQLException {
		CustomObs cobs = new CustomObs();
		try {
			
			cobs.setObsID(rs.getInt("obs_id"));
			cobs.setPatientID(rs.getInt("person_id"));
			cobs.setEncounterID(rs.getInt("encounter_id"));
			cobs.setPepfarID(rs.getString("pepfar_id"));
			cobs.setHospID(rs.getString("hosp_id"));
			
			cobs.setFormName(rs.getString("pmm_form"));
			cobs.setFormID(rs.getInt("form_id"));
			cobs.setConceptID(rs.getInt("concept_id"));
			cobs.setVariableName(rs.getString("variable_name"));
			cobs.setVariableValue(rs.getString("variable_value"));
			//cobs.setEnteredBy(rs.getString("entered_by"));
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
			cobs.setObsGroupConceptId(rs.getInt("group_concept_id"));
			SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			cobs.setObsDatetime(rs.getDate("obs_datetime"));
			//cobs.setVisitDate(d.parse(rs.getString("encounter_datetime")));
			cobs.setVisitDate(d.parse(rs.getString("date_started")));
			
			//cobs.setObsDatetime(d.parse(rs.getString("obs_datetime")));
			//System.out.println(cobs.getObsDatetime())
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return cobs;
		
	}
	
	public AddressType createPatientAddress(int patientId) {
		AddressType p = new AddressType();
		p.setAddressTypeCode("H");
		p.setCountryCode("NGA");
		Statement statement = null;
		ResultSet result = null;
		Connection con = null;
		//PersonAddress pa = patient.getPersonAddress();
		
		try {
			
			String query = "SELECT state.user_generated_id AS stateGenerated, lga.user_generated_id AS lgaGenerated FROM person_address ";
			query += " LEFT JOIN (SELECT user_generated_id, name FROM address_hierarchy_entry WHERE level_id=2) state ON state.name=person_address.state_province  ";
			query += " LEFT JOIN (SELECT user_generated_id, name FROM address_hierarchy_entry WHERE level_id=3) lga ON lga.name=person_address.city_village  ";
			query += " WHERE person_address.person_id= " + patientId;
			/*String sql = String
			        .format(
			            "SELECT `name`, user_generated_id, 'STATE' AS 'Location' "
			                    + "FROM address_hierarchy_entry WHERE level_id =2 AND NAME = '%s' "
			                    + "UNION "
			                    + "SELECT `name`, user_generated_id, 'LGA' AS 'Location' FROM address_hierarchy_entry "
			                    + " WHERE level_id =3 AND NAME ='%s' AND parent_id = (SELECT address_hierarchy_entry_id FROM address_hierarchy_entry\n"
			                    + " WHERE level_id =2 AND NAME = '%s')", state, lga, state);*/
			con = Database.connectionPool.getConnection();
			statement = con.createStatement();//Database.conn.createStatement();
			result = statement.executeQuery(query);
			while (result.next()) {
				//String name = result.getString("name");
				String stateValue = result.getString("stateGenerated");
				String lgaValue = result.getString("lgaGenerated");
				p.setStateCode(stateValue);
				p.setLGACode(lgaValue);
				
			}
			result.close();
			;
			statement.close();
		}
		catch (SQLException e) {
			try {
				e.printStackTrace();
				result.close();
				statement.close();
				//LoggerUtils.write(NDRMainDictionary.class.getName(), e.getMessage(), LoggerUtils.LogFormat.FATAL,
				//  LogLevel.live);
			}
			catch (SQLException ex) {
				//Logger.getLogger(PatientDao.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		finally {
			//cleanUp(resultSet, statement);
			try {
				result.close();
				statement.close();
				Database.connectionPool.free(con);
			}
			catch (SQLException ex) {
				//Logger.getLogger(PatientDao.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		
		return p;
	}
	
	public List<Object> getPatientEncounters(int patientId) {
		
             List<Object> allEncounterObj = new ArrayList<>();
                Map<Integer, List<CustomEncounter>> encounters = new HashMap<>();//list of encounters
                Map<Integer, Set<java.util.Date>> uniqueEncounterDates = new HashMap<>();//list of unique dates for encounter;
		Statement statement = null;
		ResultSet result = null ;
                Connection con = null;
		//PersonAddress pa = patient.getPersonAddress();
			
                    try {

                        String query = "SELECT encounter.*, visit.date_started, person_name.given_name, person_name.family_name FROM encounter ";
                        query += " LEFT JOIN encounter_provider ON encounter_provider.encounter_id=encounter.encounter_id";
                        query += " JOIN visit ON visit.visit_id=encounter.visit_id ";
                        query += " LEFT JOIN provider ON provider.provider_id=encounter_provider.provider_id";
                        query += " LEFT JOIN person ON person.person_id=provider.person_id";
                        query += " LEFT JOIN person_name ON person_name.person_id=person.person_id";
                        query += " WHERE encounter.patient_id= "+patientId+" GROUP BY encounter.encounter_id ORDER BY encounter_datetime ASC ";
                            
                        con = Database.connectionPool.getConnection();
                        statement = con.createStatement();//Database.conn.createStatement();
                        result = statement.executeQuery(query);
                        while (result.next()) {
                            
                            CustomEncounter temp = new CustomEncounter();
                            SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            int encounterTypeId = result.getInt("encounter_type");
                            java.util.Date encounterDateTime = d.parse(result.getString("encounter_datetime"));
                            temp.setEncounterDatetime(encounterDateTime);
                            temp.setVisitDate(d.parse(result.getString("date_started")));
                            temp.setPatientId(patientId);
                            temp.setFormId(result.getInt("form_id"));
                            temp.setEncounterId(result.getInt("encounter_id"));
                            temp.setEncounterTypeId(encounterTypeId);
                            temp.setLocationId(result.getInt("location_id"));
                            temp.setVisitId(result.getInt("visit_id"));
                            temp.setProviderName(result.getString("given_name")+ " "+result.getString("family_name"));
                            if(!encounters.containsKey(encounterTypeId))
                            {
                                List<CustomEncounter> newList = new ArrayList<>();
                                encounters.put(encounterTypeId, newList);
                            }
                            
                            List<CustomEncounter> currList = encounters.get(encounterTypeId);
                            currList.add(temp);
                            encounters.put(encounterTypeId, currList);
                            
                            
                            if(!uniqueEncounterDates.containsKey(encounterTypeId))
                            {
                                Set<java.util.Date> newSet = new HashSet<>();
                                uniqueEncounterDates.put(encounterTypeId, newSet);
                            }
                            
                            Set<java.util.Date> currSet = uniqueEncounterDates.get(encounterTypeId);
                            //currSet.add(temp.getEncounterDatetime());
                            currSet.add(temp.getVisitDate());
                            uniqueEncounterDates.put(encounterTypeId, currSet);
                            
                        }
                        
                        allEncounterObj.add(encounters);
                        allEncounterObj.add(uniqueEncounterDates);
                        
                        result.close();
                        statement.close();
                        return allEncounterObj;
                    }
                    catch (Exception e) {
                        try {
                            e.printStackTrace();
                            result.close();
                            statement.close();
                            //LoggerUtils.write(NDRMainDictionary.class.getName(), e.getMessage(), LoggerUtils.LogFormat.FATAL,
                            //  LogLevel.live);
                        } catch (SQLException ex) {
                            //Logger.getLogger(PatientDao.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    finally {
                            //cleanUp(resultSet, statement);
                       try {
                            result.close();
                            statement.close();
                            Database.connectionPool.free(con);
                        } catch (SQLException ex) {
                            //Logger.getLogger(PatientDao.class.getName()).log(Level.SEVERE, null, ex);
                         }
                    }
		
		return null;
	}
}
