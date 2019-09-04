//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.09.03 at 05:57:32 PM WAT 
//

package org.openmrs.module.nigeriaemr.model.ndr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Java class for DeliveryEncounterType complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeliveryEncounterType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VisitID" type="{}StringType"/>
 *         &lt;element name="VisitDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="TimeOfHIVDiagnosis">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *               &lt;enumeration value="3"/>
 *               &lt;enumeration value="4"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="GestationalAgeAtDelivery" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="HBVStatus">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="Pos"/>
 *               &lt;enumeration value="Neg"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="HCVStatus">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="Pos"/>
 *               &lt;enumeration value="Neg"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="WomanOnART">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="Y"/>
 *               &lt;enumeration value="N"/>
 *               &lt;enumeration value="U"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ARTStartedInLDWard">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="Y"/>
 *               &lt;enumeration value="N"/>
 *               &lt;enumeration value="U"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ROMDeliveryInterval">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ModeOfDelivery">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *               &lt;enumeration value="3"/>
 *               &lt;enumeration value="4"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Episiotomy">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="Y"/>
 *               &lt;enumeration value="N"/>
 *               &lt;enumeration value="U"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="VaginalTear">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="Y"/>
 *               &lt;enumeration value="N"/>
 *               &lt;enumeration value="U"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="FeedingDecision">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="EBF"/>
 *               &lt;enumeration value="ERF"/>
 *               &lt;enumeration value="Mixed"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="MaternalOutcome">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="Alive"/>
 *               &lt;enumeration value="Dead"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeliveryEncounterType", propOrder = { "visitID", "visitDate", "timeOfHIVDiagnosis",
        "gestationalAgeAtDelivery", "hbvStatus", "hcvStatus", "womanOnART", "artStartedInLDWard", "romDeliveryInterval",
        "modeOfDelivery", "episiotomy", "vaginalTear", "feedingDecision", "maternalOutcome" })
public class DeliveryEncounterType {
	
	@XmlElement(name = "VisitID", required = true)
	protected String visitID;
	
	@XmlElement(name = "VisitDate", required = true)
	@XmlSchemaType(name = "date")
	protected XMLGregorianCalendar visitDate;
	
	@XmlElement(name = "TimeOfHIVDiagnosis", required = true)
	protected String timeOfHIVDiagnosis;
	
	@XmlElement(name = "GestationalAgeAtDelivery")
	protected int gestationalAgeAtDelivery;
	
	@XmlElement(name = "HBVStatus", required = true)
	protected String hbvStatus;
	
	@XmlElement(name = "HCVStatus", required = true)
	protected String hcvStatus;
	
	@XmlElement(name = "WomanOnART", required = true)
	protected String womanOnART;
	
	@XmlElement(name = "ARTStartedInLDWard", required = true)
	protected String artStartedInLDWard;
	
	@XmlElement(name = "ROMDeliveryInterval", required = true)
	protected String romDeliveryInterval;
	
	@XmlElement(name = "ModeOfDelivery", required = true)
	protected String modeOfDelivery;
	
	@XmlElement(name = "Episiotomy", required = true)
	protected String episiotomy;
	
	@XmlElement(name = "VaginalTear", required = true)
	protected String vaginalTear;
	
	@XmlElement(name = "FeedingDecision", required = true)
	protected String feedingDecision;
	
	@XmlElement(name = "MaternalOutcome", required = true)
	protected String maternalOutcome;
	
	/**
	 * Gets the value of the visitID property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getVisitID() {
		return visitID;
	}
	
	/**
	 * Sets the value of the visitID property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setVisitID(String value) {
		this.visitID = value;
	}
	
	/**
	 * Gets the value of the visitDate property.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 */
	public XMLGregorianCalendar getVisitDate() {
		return visitDate;
	}
	
	/**
	 * Sets the value of the visitDate property.
	 * 
	 * @param value allowed object is {@link XMLGregorianCalendar }
	 */
	public void setVisitDate(XMLGregorianCalendar value) {
		this.visitDate = value;
	}
	
	/**
	 * Gets the value of the timeOfHIVDiagnosis property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getTimeOfHIVDiagnosis() {
		return timeOfHIVDiagnosis;
	}
	
	/**
	 * Sets the value of the timeOfHIVDiagnosis property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setTimeOfHIVDiagnosis(String value) {
		this.timeOfHIVDiagnosis = value;
	}
	
	/**
	 * Gets the value of the gestationalAgeAtDelivery property.
	 */
	public int getGestationalAgeAtDelivery() {
		return gestationalAgeAtDelivery;
	}
	
	/**
	 * Sets the value of the gestationalAgeAtDelivery property.
	 */
	public void setGestationalAgeAtDelivery(int value) {
		this.gestationalAgeAtDelivery = value;
	}
	
	/**
	 * Gets the value of the hbvStatus property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getHBVStatus() {
		return hbvStatus;
	}
	
	/**
	 * Sets the value of the hbvStatus property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setHBVStatus(String value) {
		this.hbvStatus = value;
	}
	
	/**
	 * Gets the value of the hcvStatus property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getHCVStatus() {
		return hcvStatus;
	}
	
	/**
	 * Sets the value of the hcvStatus property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setHCVStatus(String value) {
		this.hcvStatus = value;
	}
	
	/**
	 * Gets the value of the womanOnART property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getWomanOnART() {
		return womanOnART;
	}
	
	/**
	 * Sets the value of the womanOnART property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setWomanOnART(String value) {
		this.womanOnART = value;
	}
	
	/**
	 * Gets the value of the artStartedInLDWard property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getARTStartedInLDWard() {
		return artStartedInLDWard;
	}
	
	/**
	 * Sets the value of the artStartedInLDWard property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setARTStartedInLDWard(String value) {
		this.artStartedInLDWard = value;
	}
	
	/**
	 * Gets the value of the romDeliveryInterval property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getROMDeliveryInterval() {
		return romDeliveryInterval;
	}
	
	/**
	 * Sets the value of the romDeliveryInterval property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setROMDeliveryInterval(String value) {
		this.romDeliveryInterval = value;
	}
	
	/**
	 * Gets the value of the modeOfDelivery property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getModeOfDelivery() {
		return modeOfDelivery;
	}
	
	/**
	 * Sets the value of the modeOfDelivery property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setModeOfDelivery(String value) {
		this.modeOfDelivery = value;
	}
	
	/**
	 * Gets the value of the episiotomy property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getEpisiotomy() {
		return episiotomy;
	}
	
	/**
	 * Sets the value of the episiotomy property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setEpisiotomy(String value) {
		this.episiotomy = value;
	}
	
	/**
	 * Gets the value of the vaginalTear property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getVaginalTear() {
		return vaginalTear;
	}
	
	/**
	 * Sets the value of the vaginalTear property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setVaginalTear(String value) {
		this.vaginalTear = value;
	}
	
	/**
	 * Gets the value of the feedingDecision property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getFeedingDecision() {
		return feedingDecision;
	}
	
	/**
	 * Sets the value of the feedingDecision property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setFeedingDecision(String value) {
		this.feedingDecision = value;
	}
	
	/**
	 * Gets the value of the maternalOutcome property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getMaternalOutcome() {
		return maternalOutcome;
	}
	
	/**
	 * Sets the value of the maternalOutcome property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setMaternalOutcome(String value) {
		this.maternalOutcome = value;
	}
	
}
