package sgae.nucleo.personas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import sgae.util.Utils;

/**
 * Clase que recoge las características de una persona y los métodos para
 * consultar o modificar dichas características.
 * 
 * @author Eduardo Gómez Sánchez y Manuel Rodríguez Cayetano. ETSIT. UVa
 * @version 1.0
 */
public class Persona {
	/** El DNI de la persona */
	private String dni;
	/** El nombre de la persona */
	private String nombre;
	/** Los apellidos de la persona */
	private String apellidos;
	/** La fecha de nacimiento de la persona */
	private Date fechaNacimiento;

	/**
	 * Constructor con los atributos que se pueden inicializar de partida. Nota:
	 * la fecha de nacimiento se pasa como una cadena con el formato dd-MM-yyyy
	 * .
	 *
	 * @param dni
	 *            El DNI de la persona
	 * @param nombre
	 *            El nombre de la persona
	 * @param apellidos
	 *            Los apellidos de la persona
	 * @param fechaNacimiento
	 *            La fecha de nacimiento de la persona, se pasa como una cadena
	 *            dd-MM-yyyy
	 * @throws ParseException
	 *             si el parámetro <i>fechaNacimiento</i> no tiene el formato
	 *             dd-MM-yyyy o si los parámetros dni, nombre o apellidos están vacíos, contienen sólo espacios
	 *             o son null
	 */
	public Persona(String dni, String nombre, String apellidos, String fechaNacimiento) throws ParseException {
		super();
		// Inicializa con valores pasados como parámetros
		this.dni = Utils.testStringNullOrEmptyOrWhitespaceAndSet(dni, "Campo DNI vacío");		
		this.nombre = Utils.testStringNullOrEmptyOrWhitespaceAndSet(nombre, "Campo nombre vacío");
		this.apellidos = Utils.testStringNullOrEmptyOrWhitespaceAndSet(apellidos, "Campo apellidos vacío");
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		if (Utils.isStringNullOrEmptyOrWhitespace(fechaNacimiento)) {
			throw new ParseException("Campo fecha vacío", 0);
		}
		this.fechaNacimiento = dateFormat.parse(fechaNacimiento);
	}

	/**
	 * Método que lee el DNI. NOTA: El DNI sólo se puede leer, no escribir.
	 * 
	 * @return el valor del DNI
	 */
	public String getDni() {
		return dni;
	}

	/**
	 * Método que lee el nombre.
	 * 
	 * @return el nombre de la persona
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método que modifica el nombre.
	 * 
	 * @param nuevoNombre
	 *            el nuevo nombre para la persona
	 * @throws ParseException
	 *             si el parámetro nuevoNombre está vacío, contiene sólo espacios
	 *             o es null
	 */
	public void setNombre(String nuevoNombre) throws ParseException {
		nombre = Utils.testStringNullOrEmptyOrWhitespaceAndSet(nuevoNombre, "Campo nombre vacío");
	}

	/**
	 * Método que lee los apellidos.
	 * 
	 * @return los apellidos de la persona
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * Método que modifica los apellidos.
	 * 
	 * @param nuevosApellidos
	 *            los nuevos apellidos de la persona
	 * @throws ParseException
	 *             si el parámetro nuevosApellidos está vacío, contiene sólo espacios
	 *             o es null
	 */
	public void setApellidos(String nuevosApellidos) throws ParseException {
		apellidos = Utils.testStringNullOrEmptyOrWhitespaceAndSet(nuevosApellidos, "Campo apellidos vacío");
	}

	/**
	 * Método que devuelve la fecha de nacimiento como una cadena.
	 * 
	 * @return la fecha de nacimiento en formato dd-MM-yyyy
	 */
	public String getFechaNacimiento() {
		return new SimpleDateFormat("dd-MM-yyyy").format(fechaNacimiento);
	}

	/**
	 * Método que cambia la fecha a partir de una cadena.
	 * 
	 * @param nuevaFechaNacimiento
	 *            la nueva fecha de nacimiento de la persona
	 * @throws ParseException
	 *             si el parámetro <i>fechaNacimiento</i> no tiene el formato
	 *             dd-MM-yyyy
	 */
	public void setFechaNacimiento(String nuevaFechaNacimiento) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		if (Utils.isStringNullOrEmptyOrWhitespace(nuevaFechaNacimiento)) {
			throw new ParseException("Campo fecha vacío", 0);
		}

		this.fechaNacimiento = dateFormat.parse(nuevaFechaNacimiento);
	}

	/**
	 * Método que devuelve en una única cadena la información básica de la
	 * persona.
	 * 
	 * @return la descripción breve de la persona
	 */
	public String verDescripcionBreve() {
		return "DNI: " + dni + "\tNombre: " + nombre + "\tApellidos: " + apellidos + "\n";
	}

	/**
	 * Método que devuelve en una única cadena la información completa de la
	 * persona.
	 * 
	 * @return la descripción completa de la persona
	 */
	public String verDescripcionCompleta() {
		return "DNI: " + dni + "\t" + "Nombre: " + nombre + "\t" + "Apellidos: " + apellidos + "\t"
				+ "fecha de nacimiento: " + getFechaNacimiento() + "\n";
	}
}
