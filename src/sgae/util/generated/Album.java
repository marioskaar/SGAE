//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.04.18 a las 12:50:52 PM CEST 
//


package sgae.util.generated;

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
 *         &lt;element name="idAlbum" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="titulo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fechaPublicacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ejemplaresVendidos" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "idAlbum",
    "titulo",
    "fechaPublicacion",
    "ejemplaresVendidos"
})
@XmlRootElement(name = "Album")
public class Album {

    @XmlElement(required = true)
    protected String idAlbum;
    @XmlElement(required = true)
    protected String titulo;
    @XmlElement(required = true)
    protected String fechaPublicacion;
    protected int ejemplaresVendidos;

    /**
     * Obtiene el valor de la propiedad idAlbum.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdAlbum() {
        return idAlbum;
    }

    /**
     * Define el valor de la propiedad idAlbum.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdAlbum(String value) {
        this.idAlbum = value;
    }

    /**
     * Obtiene el valor de la propiedad titulo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Define el valor de la propiedad titulo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitulo(String value) {
        this.titulo = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaPublicacion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    /**
     * Define el valor de la propiedad fechaPublicacion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaPublicacion(String value) {
        this.fechaPublicacion = value;
    }

    /**
     * Obtiene el valor de la propiedad ejemplaresVendidos.
     * 
     */
    public int getEjemplaresVendidos() {
        return ejemplaresVendidos;
    }

    /**
     * Define el valor de la propiedad ejemplaresVendidos.
     * 
     */
    public void setEjemplaresVendidos(int value) {
        this.ejemplaresVendidos = value;
    }

}
