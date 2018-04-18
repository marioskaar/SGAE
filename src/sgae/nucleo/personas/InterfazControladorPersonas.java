package sgae.nucleo.personas;

import java.text.ParseException;
import java.util.List;

/**
 * Interfaz para la clase controlador para las personas.
 * @author Eduardo Gómez Sánchez y Manuel Rodríguez Cayetano. ETSIT UVa.
 * @version 1.0
 *
 */
public interface InterfazControladorPersonas {

	/**
	 * Método que crea una nueva persona y la añade a la colección.
	 * @param dni DNI de la persona
	 * @param nombre nombre de la persona
	 * @param apellidos apellidos de la persona 
	 * @param fechaNacimiento fecha de nacimiento de la persona en formato
	 * dd-MM-yyyy
	 * @throws ParseException si el parámetro <i>fechaNacimiento</i> no tiene 
	 * el formato dd-MM-yyyy
	 * @throws ExcepcionPersonas si ya existe una persona con un DNI igual 
	 * al valor del campo <i>dni</i>
	 */
	void crearPersona(String dni, String nombre, String apellidos, String fechaNacimiento)
			throws ParseException, ExcepcionPersonas;

	/**
	 * Método que permite modificar una persona, recibiendo todos los 
	 * campos (el DNI no puede cambiar).
	 * @param dni DNI de la persona
	 * @param nombre nombre de la persona
	 * @param apellidos apellidos de la persona 
	 * @param fechaNacimiento fecha de nacimiento de la persona en formato
	 * dd-MM-yyyy
	 * @throws ParseException si el parámetro <i>fechaNacimiento</i> no tiene 
	 * el formato dd-MM-yyyy
	 * @throws ExcepcionPersonas si no existe una persona con un DNI igual 
	 * al valor del campo <i>dni</i>
	 */
	void modificarPersona(String dni, String nombre, String apellidos, String fechaNacimiento)
			throws ParseException, ExcepcionPersonas;

	/**
	 * Método que devuelve una lista de personas, con su descripción breve.
	 * @return una lista donde cada elemento es una cadena de texto con la 
	 * descripción breve de una persona
	 */
	List<String> listarPersonas();

	/**
	 * Método que permite obtener una colección de objetos que 
	 * representan a todas las personas existentes en este controlador.
	 * @return una lista donde cada elemento es un objeto de la clase 
	 * Persona
	 */
	List<Persona> recuperarPersonas();

	/**
	 * Método que permite ver los detalles de una persona en una cadena
	 * de texto.
	 * @param dni DNI de la persona
	 * @return cadena de texto con la descripción completa de la persona
	 * @throws ExcepcionPersonas si no existe una persona con un valor
	 * de DNI igual al parámetro <i>dni</i>
	 */
	String verPersona(String dni) throws ExcepcionPersonas;

	/**
	 * Método que permite recuperar el objeto que representa a una persona.
	 * @param dni DNI de la persona
	 * @return un objeto de tipo Persona
	 * @throws ExcepcionPersonas si no existe una persona con un valor
	 * de DNI igual al parámetro <i>dni</i>
	 */
	Persona recuperarPersona(String dni) throws ExcepcionPersonas;

	/**
	 * Método que permite borrar una persona.
	 * @param dni DNI de la persona
	 * @throws ExcepcionPersonas si no existe una persona con un valor
	 * de DNI igual al parámetro <i>dni</i>
	 */
	void borrarPersona(String dni) throws ExcepcionPersonas;

	/**
	 * Método que devuelve el número total de personas mantenido por este 
	 * controlador.
	 * @return el número total de personas
	 */
	int verNumeroPersonas();

}