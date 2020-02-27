//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.09.05 at 07:44:04 AM WAT 
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
 * Java class for HealthFacilityVisitsType complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HealthFacilityVisitsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VisitDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="VisitStatus">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="A"/>
 *               &lt;enumeration value="TI"/>
 *               &lt;enumeration value="TO"/>
 *               &lt;enumeration value="L"/>
 *               &lt;enumeration value="DC"/>
 *               &lt;enumeration value="X"/>
 *               &lt;enumeration value="LTFU"/>
 *               &lt;enumeration value="D"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Cotrimoxazole">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="Y"/>
 *               &lt;enumeration value="N"/>
 *               &lt;enumeration value="U"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Weight" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="BreastFeeding">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="Y"/>
 *               &lt;enumeration value="N"/>
 *               &lt;enumeration value="U"/>
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
@XmlType(name = "HealthFacilityVisitsType", propOrder = { "visitDate", "visitStatus", "cotrimoxazole", "weight",
        "breastFeeding" })
public class HealthFacilityVisitsType {
	
	@XmlElement(name = "VisitDate", required = true)
	@XmlSchemaType(name = "date")
	protected XMLGregorianCalendar visitDate;
	
	@XmlElement(name = "VisitStatus", required = true)
	protected String visitStatus;
	
	@XmlElement(name = "Cotrimoxazole", required = true)
	protected String cotrimoxazole;
	
	@XmlElement(name = "Weight")
	protected int weight;
	
	@XmlElement(name = "BreastFeeding", required = true)
	protected String breastFeeding;
	
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
	 * Gets the value of the visitStatus property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getVisitStatus() {
		return visitStatus;
	}
	
	/**
	 * Sets the value of the visitStatus property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setVisitStatus(String value) {
		this.visitStatus = value;
	}
	
	/**
	 * Gets the value of the cotrimoxazole property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getCotrimoxazole() {
		return cotrimoxazole;
	}
	
	/**
	 * Sets the value of the cotrimoxazole property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setCotrimoxazole(String value) {
		this.cotrimoxazole = value;
	}
	
	/**
	 * Gets the value of the weight property.
	 */
	public int getWeight() {
		return weight;
	}
	
	/**
	 * Sets the value of the weight property.
	 */
	public void setWeight(int value) {
		this.weight = value;
	}
	
	/**
	 * Gets the value of the breastFeeding property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getBreastFeeding() {
		return breastFeeding;
	}
	
	/**
	 * Sets the value of the breastFeeding property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setBreastFeeding(String value) {
		this.breastFeeding = value;
	}
	
}
