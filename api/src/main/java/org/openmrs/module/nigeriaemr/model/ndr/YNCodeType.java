//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.01.09 at 04:49:13 PM WAT 
//

package org.openmrs.module.nigeriaemr.model.ndr;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for YNCodeType.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * <p>
 * 
 * <pre>
 * &lt;simpleType name="YNCodeType">
 *   &lt;restriction base="{}CodeType">
 *     &lt;enumeration value="Yes"/>
 *     &lt;enumeration value="No"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "YNCodeType")
@XmlEnum
public enum YNCodeType {
	
	@XmlEnumValue("Yes")
	YES("Yes"), @XmlEnumValue("No")
	NO("No");
	
	private final String value;
	
	YNCodeType(String v) {
		value = v;
	}
	
	public String value() {
		return value;
	}
	
	public static YNCodeType fromValue(String v) {
		for (YNCodeType c : YNCodeType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
	
}
