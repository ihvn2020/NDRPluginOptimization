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
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for TelephoneType complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TelephoneType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Number" type="{}StringType" minOccurs="0"/>
 *         &lt;element name="Extension" type="{}StringType" minOccurs="0"/>
 *         &lt;element name="EmailAddress" type="{}StringType" minOccurs="0"/>
 *         &lt;element name="TelephoneTypeCode" type="{}CodeType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TelephoneType", propOrder = { "number", "extension", "emailAddress", "telephoneTypeCode" })
public class TelephoneType {
	
	@XmlElement(name = "Number")
	protected String number;
	
	@XmlElement(name = "Extension")
	protected String extension;
	
	@XmlElement(name = "EmailAddress")
	protected String emailAddress;
	
	@XmlElement(name = "TelephoneTypeCode", required = true)
	protected String telephoneTypeCode;
	
	/**
	 * Gets the value of the number property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getNumber() {
		return number;
	}
	
	/**
	 * Sets the value of the number property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setNumber(String value) {
		this.number = value;
	}
	
	/**
	 * Gets the value of the extension property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getExtension() {
		return extension;
	}
	
	/**
	 * Sets the value of the extension property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setExtension(String value) {
		this.extension = value;
	}
	
	/**
	 * Gets the value of the emailAddress property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getEmailAddress() {
		return emailAddress;
	}
	
	/**
	 * Sets the value of the emailAddress property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setEmailAddress(String value) {
		this.emailAddress = value;
	}
	
	/**
	 * Gets the value of the telephoneTypeCode property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getTelephoneTypeCode() {
		return telephoneTypeCode;
	}
	
	/**
	 * Sets the value of the telephoneTypeCode property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setTelephoneTypeCode(String value) {
		this.telephoneTypeCode = value;
	}
	
}
