//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.04.28 at 07:23:23 PM CEST 
//


package sgae.util.generated;

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
 *         &lt;element name="cif" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fechaCreacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="uri1" type="{http://www.ptpd.tel.uva.es/ns/sgaerest/util/link}link"/>
 *         &lt;element name="uri2" type="{http://www.ptpd.tel.uva.es/ns/sgaerest/util/link}link"/>
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
    "cif",
    "nombre",
    "fechaCreacion",
    "uri1",
    "uri2"
})
@XmlRootElement(name = "GrupoMusical")
public class GrupoMusical {

    @XmlElement(required = true)
    protected String cif;
    @XmlElement(required = true)
    protected String nombre;
    @XmlElement(required = true)
    protected String fechaCreacion;
    @XmlElement(required = true)
    protected Link uri1;
    @XmlElement(required = true)
    protected Link uri2;

    /**
     * Gets the value of the cif property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCif() {
        return cif;
    }

    /**
     * Sets the value of the cif property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCif(String value) {
        this.cif = value;
    }

    /**
     * Gets the value of the nombre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the value of the nombre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombre(String value) {
        this.nombre = value;
    }

    /**
     * Gets the value of the fechaCreacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Sets the value of the fechaCreacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaCreacion(String value) {
        this.fechaCreacion = value;
    }

    /**
     * Gets the value of the uri1 property.
     * 
     * @return
     *     possible object is
     *     {@link Link }
     *     
     */
    public Link getUri1() {
        return uri1;
    }

    /**
     * Sets the value of the uri1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Link }
     *     
     */
    public void setUri1(Link value) {
        this.uri1 = value;
    }

    /**
     * Gets the value of the uri2 property.
     * 
     * @return
     *     possible object is
     *     {@link Link }
     *     
     */
    public Link getUri2() {
        return uri2;
    }

    /**
     * Sets the value of the uri2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Link }
     *     
     */
    public void setUri2(Link value) {
        this.uri2 = value;
    }

}
