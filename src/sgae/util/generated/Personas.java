//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantaci�n de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perder�n si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.04.18 a las 12:50:52 PM CEST 
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
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.ptpd.tel.uva.es/ns/sgaerest/util/esquemaRepresentacionesRecursos}PersonaInfoBreve" maxOccurs="unbounded" minOccurs="0"/>
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
    "personaInfoBreve"
})
@XmlRootElement(name = "Personas")
public class Personas {

    @XmlElement(name = "PersonaInfoBreve", namespace = "http://www.ptpd.tel.uva.es/ns/sgaerest/util/esquemaRepresentacionesRecursos")
    protected List<PersonaInfoBreve> personaInfoBreve;

    /**
     * Gets the value of the personaInfoBreve property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the personaInfoBreve property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPersonaInfoBreve().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PersonaInfoBreve }
     * 
     * 
     */
    public List<PersonaInfoBreve> getPersonaInfoBreve() {
        if (personaInfoBreve == null) {
            personaInfoBreve = new ArrayList<PersonaInfoBreve>();
        }
        return this.personaInfoBreve;
    }

}
