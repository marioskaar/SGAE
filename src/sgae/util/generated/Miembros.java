//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.04.30 at 11:41:05 AM CEST 
//


package sgae.util.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.ptpd.tel.uva.es/ns/sgaerest/util/esquemaRepresentacionesRecursos}MiembroInfoBreve" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "miembroInfoBreve"
})
@XmlRootElement(name = "Miembros")
public class Miembros {

    @XmlElement(name = "MiembroInfoBreve", namespace = "http://www.ptpd.tel.uva.es/ns/sgaerest/util/esquemaRepresentacionesRecursos")
    protected List<MiembroInfoBreve> miembroInfoBreve;

    /**
     * Gets the value of the miembroInfoBreve property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the miembroInfoBreve property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMiembroInfoBreve().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MiembroInfoBreve }
     * 
     * 
     */
    public List<MiembroInfoBreve> getMiembroInfoBreve() {
        if (miembroInfoBreve == null) {
            miembroInfoBreve = new ArrayList<MiembroInfoBreve>();
        }
        return this.miembroInfoBreve;
    }

}
