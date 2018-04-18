package sgae.nucleo.discograficas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sgae.nucleo.gruposMusicales.GrupoMusical;
import sgae.nucleo.personas.Persona;
import sgae.util.Utils;

/**
 * Clase que recoge las características de una discográfica y los métodos para
 * consultar o modificar dichas características.
 * 
 * @author Eduardo Gómez Sánchez y Manuel Rodríguez Cayetano. ETSIT UVa.
 * @version 1.0
 */
public class Discografica {
	/** El identificador de la discográfica */
	private String cif;
	/** El nombre comercial */
	private String nombre;
	/** La dirección de la discográfica */
	private String direccion;
	/** La persona que la dirige */
	private Persona director = null;
	/** La lista de directores anteriores */
	private Map<String, Persona> listaDirectoresAnteriores;
	/** El total de gruposContratados de esta discográfica */
	private int numeroGruposContratados;
	/** El total de sueldos pagados por esta discográfica a todos sus grupos */
	private float masaSalarial;
	// Además, mantiene dos listas de contratos
	// NOTA: la separación en dos listas se debe solamente a criterios
	// de eficiencia, ya que se harán más búsquedas sobre los contratos
	// activos, que por otra parte serán los menos
	/**
	 * Una colección de los contratos activos, indexada por un identificador
	 * único
	 */
	private Map<String, Contrato> listaContratosActivos;
	/**
	 * Una colección de los contratos terminados, indexada por un identificador
	 * único
	 */
	private Map<String, Contrato> listaContratosTerminados;
	/**
	 * Un contador de los contratos realizados, utilizado para generar
	 * identificadores únicos
	 */
	private int ultimoContrato;

	/**
	 * Constructor con los campos básicos
	 * 
	 * @param cif
	 *            el identificador de la discografica
	 * @param nombre
	 *            el nombre comercial
	 * @param direccion
	 *            la dirección de la discografica
	 */
	public Discografica(String cif, String nombre, String direccion) {
		super();
		// Asigna campos básicos
		this.cif = Utils.testStringNullOrEmptyOrWhitespaceAndSet(cif, "Campo CIF vacío");
		this.nombre = Utils.testStringNullOrEmptyOrWhitespaceAndSet(nombre, "Campo nombre vacío");
		this.direccion = Utils.testStringNullOrEmptyOrWhitespaceAndSet(direccion, "Campo dirección vacío");
		// Inicializa las colecciones de contratos
		listaContratosActivos = new HashMap<String, Contrato>();
		listaContratosTerminados = new HashMap<String, Contrato>();
		// Inicializa el contador de contratos
		ultimoContrato = 0;
		listaDirectoresAnteriores = new HashMap<String,Persona>();
	}

	/**
	 * Método que permite leer el CIF. NOTA: el CIF no se puede cambiar.
	 * 
	 * @return el valor del CIF de la discografica
	 */
	public String getCif() {
		return cif;
	}

	/**
	 * Método que permite leer el nombre.
	 * 
	 * @return el nombre de la discografica
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método que permite cambiar el nombre.
	 * 
	 * @param nuevoNombre
	 *            el nuevo nombre de la discografica
	 */
	public void setNombre(String nuevoNombre) {
		this.nombre = Utils.testStringNullOrEmptyOrWhitespaceAndSet(nuevoNombre, "Campo nombre vacío");
	}

	/**
	 * Método que permite leer la dirección.
	 * 
	 * @return la dirección de la discografica
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * Método que permite cambiar la dirección.
	 * 
	 * @param nuevaDireccion
	 *            la nueva dirección de la discografica
	 */
	public void setDireccion(String nuevaDireccion) {
		this.direccion = Utils.testStringNullOrEmptyOrWhitespaceAndSet(nuevaDireccion, "Campo dirección vacío");
	}

	/**
	 * Método que permite leer el numero de grupos contratados.
	 * 
	 * @return el número de grupos contratados por la discografica
	 */
	public int getNumeroGruposContratados() {
		return numeroGruposContratados;
	}

	/**
	 * Método que permite leer la masa salarial.
	 * 
	 * @return la masa salarial de la discografica
	 */
	public float getMasaSalarial() {
		return masaSalarial;
	}

	/**
	 * Método que permite recuperar una descripción breve de la discografica.
	 * 
	 * @return descripción textual breve de la discografica
	 */
	public String verDescripcionBreve() {
		return nombre + ", con CIF " + cif;
	}

	/**
	 * Método que devuelve en una única cadena la información completa de la
	 * discografica.
	 * 
	 * @return la descripción textual completa de la discografica
	 */
	public String verDescripcionCompleta() {
		return "CIF: " + cif + "\n" + "Nombre: " + nombre + "\n" + "Dirección: " + direccion + "\nDirector: "
				+ ((director == null) ? "sin asignar" : director.verDescripcionBreve())
				+ "\nNúmero de grupos contratados: " + numeroGruposContratados + "\nMasa salarial: " + masaSalarial
				+ "\n";
	}

	public String nuevoContrato(GrupoMusical grupoMusical, String fechaInicicio, String fechaFin, float sueldo)
			throws ExcepcionContratos {
		// Genera un identificador único de contrato
		String idContrato = "c" + ultimoContrato;
		// Crea la instancia
		Contrato c = new Contrato(idContrato, grupoMusical, fechaInicicio, fechaFin, sueldo);
		// Y la colecciona
		listaContratosActivos.put(idContrato, c);
		// Incrementa el identificador de contrato
		ultimoContrato++;
		// Incrementa el número de empleados
		numeroGruposContratados++;
		// Incrementa la masa salarial
		masaSalarial += sueldo;
		// Devuelve el identificador del contrato recién creado
		return idContrato;
	}

    /**
     * Método que devuelve una descripción de los contratos vigentes de 
     * una discográfica.
     * @return lista  de cadenas de texto, donde cada una contiene la 
     * descripción de un contrato de la discográfica
     */
    public List<String> verContratosVigentes() {
        // Se inicializa una lista
        List<String> listado = new ArrayList<String>();

	    // Recorre la lista de contratos terminados
	    for(Contrato c : listaContratosActivos.values()) {
		    // Añade al listado los detalles del contrato
		    listado.add(c.verDetalles());
	    }
        
        // Al terminar se devuelve el listado (que podría estar vacío)
        return listado;
    }
    
    /**
     * Método que devuelve los objetos de tipo Contrato que representan
     * los contratos vigentes de la discográfica.
     * @return lista objetos de tipo Contrato
     */
    public List<Contrato> recuperarContratosVigentes() {
        // Se inicializa una lista
        List<Contrato> listado = new ArrayList<Contrato>();
	
	    // Recorre la lista de contratos terminados
	    for(Contrato c : listaContratosActivos.values()) {
		    // Añade al listado el objeto
		    listado.add(c);
		}
	    
	    // Al terminar se devuelve el listado (que podría estar vacío)
	    return listado;
    }
    
	/**
	 * Método que devuelve una cadena con toda la información de un contrato 
	 * vigente a partir de su identificador
	 * 
	 * @param idContrato
	 *            identificador del contrato
	 * @return cadena de texto con la descripción textual del contrato
	 * @throws ExcepcionContratos
	 *             si el contrato con identificador igual al valor del parámetro
	 *             <i>idContrato</i> no existe
	 */
	public String verContrato(String idContrato) throws ExcepcionContratos {
		// Recupera la instancia de contrato
		Contrato c = listaContratosActivos.get(idContrato);
		// Si existe ese contrato
		if (c != null) {
			// Delega en él para que genere una cadena con sus detalles
			return c.verDetalles();
		} else {
			// Si el contrato no existía, lanzamos una excepción
			throw new ExcepcionContratos(idContrato, "No se ha podido encontrar el contrato que se desea ver.");
		}
	}

	/**
	 * Método que devuelve el objeto que representa a un contrato vigente a partir de su
	 * identificador.
	 * 
	 * @param idContrato
	 *            identificador del contrato
	 * @return un objeto de tipo contrato
	 * @throws ExcepcionContratos
	 *             si el contrato con identificador igual al valor del parámetro
	 *             <i>idContrato</i> no existe
	 */
	public Contrato recuperarContrato(String idContrato) throws ExcepcionContratos {
		// Recupera la instancia de contrato
		Contrato c = listaContratosActivos.get(idContrato);
		// Si existe ese contrato
		if (c != null) {
			// Devuelve el objeto
			return c;
		} else {
			// Si el contrato no existía, lanzamos una excepción
			throw new ExcepcionContratos(idContrato, "No se ha podido encontrar el contrato que se desea ver.");
		}
	}

    /**
     * Método que devuelve una descripción de los contratos terminados de 
     * una discográfica.
     * @return lista  de cadenas de texto, donde cada una contiene la 
     * descripción de un contrato de la discográfica
     */
    public List<String> verContratosTerminados() {
        // Se inicializa una lista
        List<String> listado = new ArrayList<String>();

	    // Recorre la lista de contratos terminados
	    for(Contrato c : listaContratosTerminados.values()) {
		    // Añade al listado los detalles del contrato
		    listado.add(c.verDetalles());
	    }
        
        // Al terminar se devuelve el listado (que podría estar vacío)
        return listado;
    }
    
    /**
     * Método que devuelve los objetos de tipo Contrato que representan
     * los contratos terminados de la discográfica.
     * @return lista objetos de tipo Contrato
     */
    public List<Contrato> recuperarContratosTerminados() {
        // Se inicializa una lista
        List<Contrato> listado = new ArrayList<Contrato>();
	
	    // Recorre la lista de contratos terminados
	    for(Contrato c : listaContratosTerminados.values()) {
		    // Añade al listado el objeto
		    listado.add(c);
		}
	    
	    // Al terminar se devuelve el listado (que podría estar vacío)
	    return listado;
    }
    
	/**
	 * Método que devuelve una cadena con toda la información de un contrato 
	 * terminado a partir de su identificador
	 * 
	 * @param idContrato
	 *            identificador del contrato
	 * @return cadena de texto con la descripción textual del contrato
	 * @throws ExcepcionContratos
	 *             si el contrato con identificador igual al valor del parámetro
	 *             <i>idContrato</i> no existe
	 */
	public String verContratoTerminado(String idContrato) throws ExcepcionContratos {
		// Recupera la instancia de contrato
		Contrato c = listaContratosTerminados.get(idContrato);
		// Si existe ese contrato
		if (c != null) {
			// Delega en él para que genere una cadena con sus detalles
			return c.verDetalles();
		} else {
			// Si el contrato no existía, lanzamos una excepción
			throw new ExcepcionContratos(idContrato, "No se ha podido encontrar el contrato que se desea ver.");
		}
	}

	/**
	 * Método que devuelve el objeto que representa a un contrato terminado a partir de su
	 * identificador.
	 * 
	 * @param idContrato
	 *            identificador del contrato
	 * @return un objeto de tipo contrato
	 * @throws ExcepcionContratos
	 *             si el contrato con identificador igual al valor del parámetro
	 *             <i>idContrato</i> no existe
	 */
	public Contrato recuperarContratoTerminado(String idContrato) throws ExcepcionContratos {
		// Recupera la instancia de contrato
		Contrato c = listaContratosTerminados.get(idContrato);
		// Si existe ese contrato
		if (c != null) {
			// Devuelve el objeto
			return c;
		} else {
			// Si el contrato no existía, lanzamos una excepción
			throw new ExcepcionContratos(idContrato, "No se ha podido encontrar el contrato que se desea ver.");
		}
	}

	/**
	 * Método que permite terminar un contrato cuyo identificador se pasa como
	 * parámetro.
	 * 
	 * @param idContrato
	 *            identificador del contrado
	 * @throws ExcepcionContratos
	 *             si el contrato no existe
	 */
	public void terminarContrato(String idContrato) throws ExcepcionContratos {
		// Recupera la instancia de contrato
		Contrato c = listaContratosActivos.get(idContrato);
		// Si existe ese contrato
		if (c != null) {
			// Delega en él las tareas de que se termine
			c.terminar();
			// Lo saca del listado de contratos activos
			listaContratosActivos.remove(idContrato);
			// Y lo añade a la de los terminados
			listaContratosTerminados.put(idContrato, c);
			// Decrementa el número de empleados
			numeroGruposContratados--;
			// Decrementa la masa salarial
			masaSalarial -= c.getSueldo();
		} else {
			// Si el contrato no existía, lanzamos una excepción
			throw new ExcepcionContratos(idContrato, "No se ha podido encontrar el contrato que se desea terminar.");
		}
	}

	/**
	 * Método que establece el director de una discográfica.
	 * @param director objeto de la clase Persona que será el nuevo director
	 */	
	public void nuevoDirector(Persona director) {
		if (this.director != null) {
			// Si el último director ya está en la lista de directores anteriores, no se añade a ella
			if (!listaDirectoresAnteriores.containsKey(this.director.getDni())) {
				if (director != null) {
					// Sólo se añade un director a la lista de directores anteriores si 
					// el nuevo director es distinto del director actual
					if (director.getDni().compareTo(this.director.getDni()) != 0) {
						listaDirectoresAnteriores.put(this.director.getDni(), this.director)
;
					}
				} else {
					// Nuevo director vacío
					listaDirectoresAnteriores.put(this.director.getDni(), this.director);
				}
			}
		}
		// Se fija el director: si el nuevo y el actual es el mismo esta asignación 
		// no tiene efecto; si el nuevo director es null, el efecto es dejar a la 
		// discográfica sin director
		this.director = director;
	}

	/**
	 * Método que permite recuperar la información del director de la discográfica.
	 * @return un objeto de la clase Persona con los datos del director
	 */
	public Persona recuperarDirector() {
		return director;
	}

	/**
	 * Método para borrar el director de una discográfica.
	 */
	public void borrarDirector() {
		listaDirectoresAnteriores.put(director.getDni(), director);
		this.director = null;
	}

	/** 
	 * Método para listar todos los directores anteriores de una discográfica.
	 * @return lista de objetos de la clase @see Persona
	 */
	public List<Persona> recuperarDirectoresAnteriores() {
		// Se inicializa una lista
	    List<Persona> listado = new ArrayList<Persona>();
		
	    // Recorre la lista de contratos terminados
	    for(Persona p : listaDirectoresAnteriores.values()) {
		    // Añade al listado el objeto
		    listado.add(p);
		}
	    // Al terminar se devuelve el listado (que podría estar vacío)
	    return listado;
	}
}
