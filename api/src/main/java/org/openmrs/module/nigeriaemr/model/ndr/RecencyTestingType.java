//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.09.03 at 12:50:15 PM WAT 
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
 * Java class for RecencyTestingType complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RecencyTestingType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TestName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TestDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="RapidRecencyAssay">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="Recent"/>
 *               &lt;enumeration value="Long Term"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ViralLoadConfirmationResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ViralLoadConfirmationTestDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="FinalRecencyTestResult">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="Recent"/>
 *               &lt;enumeration value="Long Term"/>
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
@XmlType(name = "RecencyTestingType", propOrder = { "testName", "testDate", "rapidRecencyAssay",
        "viralLoadConfirmationResult", "viralLoadConfirmationTestDate", "finalRecencyTestResult" })
public class RecencyTestingType {
	
	@XmlElement(name = "TestName", required = true)
	protected String testName;
	
	@XmlElement(name = "TestDate", required = true)
	@XmlSchemaType(name = "date")
	protected XMLGregorianCalendar testDate;
	
	@XmlElement(name = "RapidRecencyAssay", required = true)
	protected String rapidRecencyAssay;
	
	@XmlElement(name = "ViralLoadConfirmationResult")
	protected String viralLoadConfirmationResult;
	
	@XmlElement(name = "ViralLoadConfirmationTestDate")
	@XmlSchemaType(name = "date")
	protected XMLGregorianCalendar viralLoadConfirmationTestDate;
	
	@XmlElement(name = "FinalRecencyTestResult", required = true)
	protected String finalRecencyTestResult;
	
	/**
	 * Gets the value of the testName property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getTestName() {
		return testName;
	}
	
	/**
	 * Sets the value of the testName property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setTestName(String value) {
		this.testName = value;
	}
	
	/**
	 * Gets the value of the testDate property.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 */
	public XMLGregorianCalendar getTestDate() {
		return testDate;
	}
	
	/**
	 * Sets the value of the testDate property.
	 * 
	 * @param value allowed object is {@link XMLGregorianCalendar }
	 */
	public void setTestDate(XMLGregorianCalendar value) {
		this.testDate = value;
	}
	
	/**
	 * Gets the value of the rapidRecencyAssay property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getRapidRecencyAssay() {
		return rapidRecencyAssay;
	}
	
	/**
	 * Sets the value of the rapidRecencyAssay property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setRapidRecencyAssay(String value) {
		this.rapidRecencyAssay = value;
	}
	
	/**
	 * Gets the value of the viralLoadConfirmationResult property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getViralLoadConfirmationResult() {
		return viralLoadConfirmationResult;
	}
	
	/**
	 * Sets the value of the viralLoadConfirmationResult property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setViralLoadConfirmationResult(String value) {
		this.viralLoadConfirmationResult = value;
	}
	
	/**
	 * Gets the value of the viralLoadConfirmationTestDate property.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 */
	public XMLGregorianCalendar getViralLoadConfirmationTestDate() {
		return viralLoadConfirmationTestDate;
	}
	
	/**
	 * Sets the value of the viralLoadConfirmationTestDate property.
	 * 
	 * @param value allowed object is {@link XMLGregorianCalendar }
	 */
	public void setViralLoadConfirmationTestDate(XMLGregorianCalendar value) {
		this.viralLoadConfirmationTestDate = value;
	}
	
	/**
	 * Gets the value of the finalRecencyTestResult property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getFinalRecencyTestResult() {
		return finalRecencyTestResult;
	}
	
	/**
	 * Sets the value of the finalRecencyTestResult property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setFinalRecencyTestResult(String value) {
		this.finalRecencyTestResult = value;
	}
	
}
