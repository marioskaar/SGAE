package sgae.nucleo.gruposMusicales;

import java.text.ParseException;

import sgae.util.Utils;

/**
 * Clase que almacena información sobre las pistas de un álbum.
 * @author Manuel Rodríguez Cayetano. ETSIT UVa.
 * @version 1.0
 */
public class Pista {
	/** El identificador de la pista */
	private String idPista;
	/** El nombre de la pista */
	private String nombre;
	/** La duración de la pista */
	private int duracion;

	/**
	 * Constructor con los campos básicos
	 * 
	 * @param idPista
	 *            el identificador de la pista
	 * @param nombre
	 *            el nombre de la pista
	 * @param duracion
	 *            la duración de la pista
	 * @throws ParseException
	 *             si los parámetros idPista o nombre están vacíos, contienen sólo espacios
	 *             o son null, o si el valor del parámetro duracion no es positivo
	 */
	public Pista(String idPista, String nombre, int duracion) throws ParseException {
		super();
		// Asigna campos básicos
		this.idPista = Utils.testStringNullOrEmptyOrWhitespaceAndSet(idPista, "Campo idPista vacío");
		this.nombre = Utils.testStringNullOrEmptyOrWhitespaceAndSet(nombre, "Campo nombre vacío");
		if (duracion <= 0) {
			throw new ParseException("La duración de la pista no puede ser negativa", 0);
		}
		this.duracion = duracion;
	}

	/**
	 * Método que permite leer el identificador de la pista. NOTA: el
	 * identificador no se puede cambiar.
	 * 
	 * @return el valor del identificador de la pista
	 */
	public String getIdPista() {
		return idPista;
	}

	/**
	 * Método que permite leer el nombre.
	 * 
	 * @return el nombre de la pista
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método que permite cambiar el nombre.
	 * 
	 * @param nuevoNombre
	 *            el nuevo nombre de la pista
	 * @throws ParseException
	 *             si el parámetro nuevoNombre está vacío, contiene sólo espacios
	 *             o es null
	 */
	public void setNombre(String nuevoNombre) throws ParseException {
		this.nombre = Utils.testStringNullOrEmptyOrWhitespaceAndSet(nuevoNombre, "Campo nombre vacío");
	}

	/**
	 * Método que permite leer la duración de la pista
	 * 
	 * @return la duración de la pista en segundos
	 */
	public int getDuracion() {
		return duracion;
	}

	/**
	 * Método que permite cambiar la duración.
	 * 
	 * @param nuevaDuracion
	 *            la nueva duración de la pista
	 * @throws ParseException si el valor del parámetro nuevaDuracion no es positivo
	 */
	public void setDuracion(int nuevaDuracion) throws ParseException {
		if (duracion <= 0) {
			throw new ParseException("La duración de la pista no puede ser negativa", 0);
		}
		duracion = nuevaDuracion;
	}

	/**
	 * Método que permite recuperar una descripción breve de la pista.
	 * 
	 * @return descripción textual breve de la pista
	 */
	public String verDescripcionBreve() {
		return "nombre: " + nombre + "\n";
	}

	/**
	 * Método que devuelve en una única cadena la información completa de la
	 * pista.
	 * 
	 * @return la descripción textual completa de la pista
	 */
	public String verDescripcionCompleta() {
		return "Id: " + idPista + "\nNombre: " + nombre + "\nDuración: " + duracion + "segundos \n";
	}
}
