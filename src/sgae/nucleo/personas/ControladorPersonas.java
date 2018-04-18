package sgae.nucleo.personas;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase controlador para las personas.
 * @author Eduardo Gómez Sánchez y Manuel Rodríguez Cayetano. ETSIT UVa.
 * @version 1.0
 *
 */
public class ControladorPersonas implements InterfazControladorPersonas {
	
	/**
	 * Mapa indexado por DNI con la lista de personas 
	 * (hacemos que esta clase mantenga la persistencia de esta lista) 
	 */
	private Map<String,Persona> listaPersonas;
	
	/**
	 * Constructor que inicializa la lista de personas.
	 */
	public ControladorPersonas() {
		super();
		// Inicializa la lista
		listaPersonas = new HashMap<String,Persona>();
	}
	
	/* (non-Javadoc)
	 * @see sgae.nucleo.personas.InterfazControladorPersonas#crearPersona(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void crearPersona(String dni, String nombre, 
				 String apellidos, 
				 String fechaNacimiento) 
		throws ParseException, ExcepcionPersonas {
		// Comprueba si no existe ya
		if(listaPersonas.containsKey(dni) == false) {
			// Crea la instancia
			Persona p = new Persona(dni, nombre, apellidos, 
						fechaNacimiento);
			// La colecciona, indexada por DNI
			listaPersonas.put(dni, p);
		} else {
			throw new ExcepcionPersonas(dni, 
						    "La persona que se ha intentado crear ya existe");
		}
	}
	
	/**
	 * Método que comprueba si existe una persona identificada por un DNI
	 * @param dni DNI de la persona
	 * @return objeto del tipo Persona correspondiente al DNI dado
	 * @throws ExcepcionPersonas si no existe una persona con un DNI
	 * igual al valor del parámetro <i>dni</i>
	 */
	private Persona comprobarPersonaExiste (String dni) 
		throws ExcepcionPersonas {
		Persona persona = listaPersonas.get(dni);
		if (persona == null) {
			throw new ExcepcionPersonas(dni,
							   "La persona que ha especificado no existe");
		}
		return persona;		
	}
	
	/* (non-Javadoc)
	 * @see sgae.nucleo.personas.InterfazControladorPersonas#modificarPersona(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void modificarPersona(String dni, String nombre, 
				     String apellidos, 
				     String fechaNacimiento) 
		throws ParseException, ExcepcionPersonas {
		// Recupera la instancia
		Persona p = comprobarPersonaExiste(dni);
		p.setNombre(nombre);
		p.setApellidos(apellidos);
		p.setFechaNacimiento(fechaNacimiento);
	}
	
	/* (non-Javadoc)
	 * @see sgae.nucleo.personas.InterfazControladorPersonas#listarPersonas()
	 */
	@Override
	public List<String> listarPersonas() {
		List<String> listado = new ArrayList<String>();
		// Recorre la lista de personas
		for(Persona p : listaPersonas.values()) {
			// A cada una le pide su descripción breve
			listado.add(p.verDescripcionBreve());
		}
		return listado;
	}
	
	/* (non-Javadoc)
	 * @see sgae.nucleo.personas.InterfazControladorPersonas#recuperarPersonas()
	 */
	@Override
	public List<Persona> recuperarPersonas() {
		// Devuelve un objeto lista con los valores que había en el mapa
		return new ArrayList<Persona>(listaPersonas.values());
	}
	
	/* (non-Javadoc)
	 * @see sgae.nucleo.personas.InterfazControladorPersonas#verPersona(java.lang.String)
	 */
	@Override
	public String verPersona(String dni) throws ExcepcionPersonas {
		// Recupera la instancia
		return comprobarPersonaExiste(dni).verDescripcionCompleta();
	}
	
	/* (non-Javadoc)
	 * @see sgae.nucleo.personas.InterfazControladorPersonas#recuperarPersona(java.lang.String)
	 */
	@Override
	public Persona recuperarPersona(String dni) throws ExcepcionPersonas {
		// Recupera la instancia
		return comprobarPersonaExiste(dni);
	}
	
	/* (non-Javadoc)
	 * @see sgae.nucleo.personas.InterfazControladorPersonas#borrarPersona(java.lang.String)
	 */
	@Override
	public void borrarPersona(String dni) throws ExcepcionPersonas {
		// Borra la instancia
		comprobarPersonaExiste(dni);
		listaPersonas.remove(dni);
	}
	
	/* (non-Javadoc)
	 * @see sgae.nucleo.personas.InterfazControladorPersonas#verNumeroPersonas()
	 */
	@Override
	public int verNumeroPersonas() {
		return listaPersonas.size();
	}
}
