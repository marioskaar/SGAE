package sgae.nucleo.discograficas;
import java.text.ParseException;

import sgae.nucleo.gruposMusicales.GrupoMusical;
import sgae.util.Utils;

/**
 * Clase que recoge las características de un contrato y los métodos para 
 * consultar o modificar dichas características.
 * @author Eduardo Gómez Sánchez y Manuel Rodríguez Cayetano. ETSIT UVa.
 * @version 1.0
 */
public class Contrato {
    /** Un identificador único */
    private String id;
    /** El grupo musica que es contratado */
    private GrupoMusical grupoMusical;
    /** Fecha de inicio */
    private String fechaInicio;
    /** Fecha de finalización */
    private String fechaFin;
    /** El sueldo que se percibe */
    private float sueldo;
    /** Si es false, éste fue un contrato pasado pero está rescindido */
    private boolean activo;

    /**
     * Constructor que recibe como parámetros la discográfica, el grupo musical, 
     * el sueldo, la fecha de inicio del contrato y la fecha de finalización.
     * Por defecto el contrato se crea como activo.
     * @param id un identificador único
     * @param grupoMusical el grupo musical que es contratado
     * @param fechaInicio fecha de inicio del contrato
     * @param fechaFin fecha de finalización del contrato
     * @param sueldo el sueldo que se percibe
     * @throws ParseException si el parámetro id está vacío, contiene sólo espacios o es el puntero null, o 
     * si el sueldo es un número negativo o si los parámetros fechaInicio y fechaFin no tienen el formato dd-MM-yyyy
     */
    public Contrato(String id, GrupoMusical grupoMusical,
		    String fechaInicio, String fechaFin, float sueldo) throws ParseException {
        super();
        // Inicializa según los parámetros
        this.id = Utils.testStringNullOrEmptyOrWhitespaceAndSet(id, "Campo id vacío");
        this.grupoMusical = grupoMusical;
        this.fechaInicio = Utils.testStringNullOrEmptyOrWhitespaceAndSet(fechaInicio, "Campo fecha inicio vacío");
        this.fechaFin = Utils.testStringNullOrEmptyOrWhitespaceAndSet(fechaFin, "Campo fecha fin vacío");
        if (sueldo < 0) {
        	throw new ParseException("El sueldo no puede ser negativo", 0);
        }
        this.sueldo = sueldo;
        // Por defecto un contrato se crea como activo
        this.activo = true;
        // Decimos al  grupo musical que está contratado
        grupoMusical.contrata();
    }

    /**
     * Método que devuelve el id del contrato.
     * @return el identificador del contrato
     */
    public String getId() {
        return id;
    }

    /**
     * Método que devuelve el sueldo del contrato.
     * @return el sueldo del contrato
     */
    public float getSueldo() {
        return sueldo;
    }
    
    /**
     * Método que modifica el sueldo del contrato.
     * @param sueldo el nuevo sueldo del contrato
     * @throws ParseException si el sueldo es un número negativo
     */
    public void setSueldo(float sueldo) throws ParseException {
        if (sueldo < 0) {
        	throw new ParseException("El sueldo no puede ser negativo", 0);
        }
        this.sueldo = sueldo;
    }

   /**
     * Método que devuelve la fecha de inicio del contrato.
     * @return texto con la fecha de inicio del contrato
     */
    public String getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Método que modifica la fecha de inicio del contrato.
     * @param fechaInicio la nueva fecha de inicio del contrato
     * @throws ParseException si el valor de la fecha no tiene el formato dd-MM-yyyy
     */
    public void setFechaInicio(String fechaInicio) throws ParseException {
        this.fechaInicio = Utils.testStringNullOrEmptyOrWhitespaceAndSet(fechaInicio, "Campo fecha inicio vacío");
    }

    /**
     * Método que devuelve la fecha de finalización del contrato.
     * @return texto con la fecha de finalización del contrato
     */
    public String getFechaFinalizacion() {
        return fechaFin;
    }

    /**
     * Método que modifica la fecha de inicio del contrato.
     * @param fechaFinalizacion la nueva fecha de finalización del contrato
     * @throws ParseException si el valor de la fecha no tiene el formato dd-MM-yyyy
     */
    public void setFechaFinalizacion(String fechaFinalizacion) throws ParseException {
        this.fechaFin = Utils.testStringNullOrEmptyOrWhitespaceAndSet(fechaFinalizacion, "Campo fecha fin vacío");
    }

   /** 
     * Método que informa de si el contrato está activo.
     * @return true si el contrato está activo
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * Método que termina el contrato.
     */
    public void terminar() {
        // Decimos al grupoMusical que está parado
        grupoMusical.despide();
        // Cambiamos el estado del contrato
        activo = false;
    }
    
    /**
     * Método que devuelve en una única cadena toda la información de un 
     * contrato.
     * @return la cadena de texto con la información completa del contrato
     */
    public String verDetalles() {
        return "Contrato con grupo musical con CIF " + grupoMusical.getCif() 
            + " por " + String.format("%.2f", sueldo) + " euros."
            + " Situación: " + (activo ? "EN VIGOR" : "TERMINADO")
	    + "Fecha de inicio: " + fechaInicio
	    + "Fecha de finalización " + fechaFin;
    }

    /**
     * Método que comprueba si un contrato tiene a un grupo musical determinado
     * @param cifGrupoMusical identificador del grupo musical
     * @return true si el grupo musical está asociada al contrato
     */
    public boolean tieneGrupoMusical(String cifGrupoMusical) {
        return grupoMusical.getCif().equals(cifGrupoMusical);
    }
    
    /**
     * Método que devuelve el identificador del grupo musical que tiene este contrato
     * @return el identificador del grupo musical que está asociado al contrato
     */
    public String getCifGrupoMusical() {
        // Le pide al grupo musical su id y lo devuelve
        return grupoMusical.getCif();
    }
}
