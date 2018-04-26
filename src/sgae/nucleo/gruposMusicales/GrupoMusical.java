package sgae.nucleo.gruposMusicales;

import java.util.Map;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import sgae.nucleo.personas.Persona;
import sgae.util.Utils;
import sgae.nucleo.personas.ExcepcionPersonas;

/**
 * Clase que almacena información sobre los grupos musicales.
 * @author Manuel Rodríguez Cayetano. ETSIT UVa.
 * @version 1.0
 */
public class GrupoMusical {
	/** Attributes */
	private String cif;
	private String nombre;
	private Date fechaCreacion;
	private boolean contratado;
	/** Associations */
	/**
	 * Mapa indexado por identificador con la lista de personas que son miembros actuales
	 * del grupo  (hacemos que esta clase mantenga la persistencia de esta lista)
	 */
	private Map<String, Persona> listaMiembrosActuales;
	
	/**
	 * Mapa indexado por identificador con la lista de personas que han sido miembros 
	 * del grupo (hacemos que esta clase mantenga la persistencia de esta lista)
	 */
	private Map<String, Persona> listaMiembrosAnteriores;
	/**
	 * Mapa indexado por identificador con la lista de álbumes (hacemos que esta
	 * clase mantenga la persistencia de esta lista)
	 */
	private Map<String, Album> listaAlbumes;
	/** Un contador para generar identificadores únicos de álbumes */
	private int ultimoAlbum;

	/**
	 * Constructor con los campos básicos.
	 * @param cif el identificador del grupo musical
	 * @param nombre el nombre del grupo
	 * @param fechaCreacion fecha de creación del grupo
	 * @throws ParseException si el parámetro <i>fechaCreacion</i> no tiene 
	 * el formato dd-MM-yyyy o si los parámetros cif o nombre están vacíos, contienen sólo espacios o son el puntero null
	 */
	public GrupoMusical(String cif, String nombre, String fechaCreacion)
		throws ParseException {
		super();
		this.cif = Utils.testStringNullOrEmptyOrWhitespaceAndSet(cif, "Campo CIF vacío");
		this.nombre = Utils.testStringNullOrEmptyOrWhitespaceAndSet(nombre, "Campo nombre vacío");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		if (Utils.isStringNullOrEmptyOrWhitespace(fechaCreacion)) {
			throw new ParseException("Campo fecha de creación vacío", 0);
		}
		this.fechaCreacion = dateFormat.parse(fechaCreacion);
		contratado = false;
		listaMiembrosActuales = new HashMap<String, Persona>();
		listaMiembrosAnteriores = new HashMap<String, Persona>();
		// Inicializa la lista de álbumes
		listaAlbumes = new HashMap<String, Album>();
		ultimoAlbum = 0;
	}

	/**
	 * Método que permite leer el CIF.
	 * NOTA: el CIF no se puede cambiar.
	 * @return el valor del CIF del grupo musical
	 */
	public String getCif() {
		return cif;
	}
	
	/**
	 * Método que permite leer el nombre.
	 * @return el nombre del grupo musical
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Método que permite cambiar el nombre.
	 * @param nombre el nuevo nombre del grupo musical
	 * @throws ParseException si el parámetro nombre está vacío, contiene sólo espacios
	 * o es null
	 */
 	public void setNombre(String nombre) throws ParseException {
		this.nombre = Utils.testStringNullOrEmptyOrWhitespaceAndSet(nombre, "Campo nombre vacío");
	}

	/**
	 * Método que permite leer la fecha de creación.
	 * @return la fecha de creación del grupo musical
	 */
	public String getFechaCreacion() {
		return new SimpleDateFormat("dd-MM-yyyy").format(fechaCreacion);
	}

	/**
	 * Método que permite cambiar la fecha de creación.
	 * @param fechaCreacion la nueva fecha de creación del grupo musical
	 * @throws ParseException si el parámetro <i>fechaCreacion</i> no tiene 
	 * el formato dd-MM-yyyy
	 */
 	public void setFechaCreacion(String fechaCreacion) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		if (Utils.isStringNullOrEmptyOrWhitespace(fechaCreacion)) {
			throw new ParseException("Campo fecha de creación vacío", 0);
		}
		this.fechaCreacion = dateFormat.parse(fechaCreacion);
	}

	/** 
	 * Método que indica si el grupo está contratado por una compañía discográfica.
	 * @return valor booleano <i>true</i> si el grupo está contratado
	 */
	public boolean estaContratado() {
		return contratado;
	}

	/**
	 * Método que cambia el estado del grupo musical a contratado por una 
	 * compañía discográfica.
	 */
	public void contrata() {
		contratado = true;
	}

	/**
	 * Método que cambia el estado del grupo musical a no contratado 
	 * por una compañía discográfica.
	 */
	public void despide() {
		contratado = false;
	}
	
	/**
	 * Método que devuelve una descripción textual breve del grupo musical.
	 * @return la descripción textual breve del grupo musical
	 */
	public String verDescripcionBreve() {
		return "CIF: " + cif + "\tNombre: " + nombre + "\n";
	}

	/**
	 * Método que devuelve en una única cadena la información completa del grupo
	 * musical.
	 * 
	 * @return la descripción completa del grupo musical
	 */
	public String verDescripcionCompleta() {
		return "CIF: " + cif + "\tNombre: " + nombre +
			"\tFecha de creación: " + fechaCreacion + "\n";
	}

	/**
	 * Método que devuelve el número total de álbumes de este grupo musical.
	 * 
	 * @return el número total de álbumes
	 */
	public int verNumeroAlbumes() {
		return listaAlbumes.size();
	}

	/**
	 * Método que añade un nuevo miembro al grupo musical.
	 * @param persona objeto de la clase Persona que va a añadirse como miembro
	 * @throws ExcepcionPersonas si la persona ya aparece como miembro del grupo
	 */
	public void anadirMiembro(Persona persona) throws ExcepcionPersonas {
		if (listaMiembrosActuales.containsKey(persona.getDni()) == false) {
			listaMiembrosActuales.put(persona.getDni(), persona);
		} else {
			throw new ExcepcionPersonas(persona.getDni(),
							   "La persona que se ha intentado añadir ya es miembro del grupo");
		}
	}
	

	/**
	 * Método que permite obtener una colección de objetos que representan a
	 * todas las personas que son miembros del grupo.
	 * 
	 * @return una lista donde cada elemento es un objeto de la clase Persona
	 */
	public List<Persona> recuperarMiembros() {
		// Devuelve un objeto lista con los valores que había en el mapa
		return new ArrayList<Persona>(listaMiembrosActuales.values());
	}

	/**
	 * Método que permite obtener una colección de objetos que representan a
	 * todas las personas que han sido miembros del grupo.
	 * 
	 * @return una lista donde cada elemento es un objeto de la clase Persona
	 */
	public List<Persona> recuperarMiembrosAnteriores() {
		// Devuelve un objeto lista con los valores que había en el mapa
		return new ArrayList<Persona>(listaMiembrosAnteriores.values());
	}

	/**
	 * Método que elimina un miembro del grupo musical y lo añade a la lista de miembros
	 * anteriores.
	 * @param dniPersona DNI de la persona que se va a eliminar como miembro
	 * @throws ExcepcionPersonas si la persona que se quiere eliminar no 
	 * forma parte del grupo
	 */
	public void eliminarMiembro(String dniPersona) throws ExcepcionPersonas {
		Persona p = listaMiembrosActuales.remove(dniPersona);
		if (p == null) {
			// la persona no era miembro del grupo
			throw new ExcepcionPersonas(dniPersona,
						   "La persona no era miembro del grupo");
		}
		if (listaMiembrosAnteriores.containsKey(p.getDni()) == false) {
			listaMiembrosAnteriores.put(p.getDni(), p);
		}
	}

	/**
	 * Método que crea un nuevo álbum y lo añade a la colección.
	 * 
	 * @param titulo
	 *            título del álbum
	 * @param fechaPublicacion
	 *            fecha de publicación del álbum en formato dd-MM-yyyy
	 * @param ejemplaresVendidos
	 *            número de ejemplares vendidos del álbum
	 * @return el identificador del álbum creado
	 * @throws ParseException si el parámetro <i>fechaPublicacion</i> no tiene 
	 * el formato dd-MM-yyyy
	 */	
	public String crearAlbum(String titulo, String fechaPublicacion, int ejemplaresVendidos)
			throws ParseException {
		// Crea un identificador para el álbum, formado por una 'a' y un
		// número auto-incrementado
		String idAlbum = "a" + ultimoAlbum;
		// Crea el objeto
		Album a = new Album(idAlbum, titulo, fechaPublicacion, ejemplaresVendidos);
		// La colecciona, indexada por identificador
		listaAlbumes.put(idAlbum, a);
		// Incrementa el contador
		ultimoAlbum++;
		return idAlbum;
	}

	/**
	 * Método que comprueba si existe un álbum identificado por un número único
	 * @param id identificador del grupo musical
	 * @return objeto del tipo Album correspondiente al identificador dado
	 * @throws ExcepcionAlbumes si no existe un álbum con un identificador
	 * igual al valor del parámetro <i>id</i>
	 */
	private Album comprobarAlbumExiste (String id) 
		throws ExcepcionAlbumes {
		Album album = listaAlbumes.get(id);
		if (album == null) {
			throw new ExcepcionAlbumes(id,
							   "El álbum que ha especificado no existe");
		}
		return album;
	}
	
	/**
	 * Método que permite modificar un álbum, recibiendo todos los campos (el
	 * identificador de álbum no puede cambiar).
	 * 
	 * @param idAlbum
	 *            identificador del álbum
	 * @param titulo
	 *            título del álbum
	 * @param fechaPublicacion
	 *            fecha de publicación del álbum en formato dd-MM-yyyy
	 * @param ejemplaresVendidos
	 *            número de ejemplares vendidos del álbum
	 * @throws ParseException si el parámetro <i>fechaPublicacion</i> no tiene 
	 * el formato dd-MM-yyyy
	 * @throws ExcepcionAlbumes
	 *             si no existe un álbum con un identificador igual al valor
	 *             del campo <i>idAlbum</i>
	 */
	public void modificarAlbum(String idAlbum, String titulo,
				   String fechaPublicacion, int ejemplaresVendidos)
			throws ParseException, ExcepcionAlbumes {
		// Recupera la instancia
		Album a = comprobarAlbumExiste(idAlbum);
		a.setTitulo(titulo);
		a.setFechaPublicacion(fechaPublicacion);
		a.setEjemplaresVendidos(ejemplaresVendidos);
	}

	/**
	 * Método que devuelve una lista de álbumes existentes en este 
	 * grupo musical, con su descripción breve.
	 * 
	 * @return una lista donde cada elemento es una cadena de texto con la
	 *         descripción breve de un álbum
	 */
	public List<String> listarAlbumes() {
		List<String> listado = new ArrayList<String>();
		// Recorre la lista de álbumes
		for (Album a : listaAlbumes.values()) {
			// A cada una le pide su descripción breve
			listado.add(a.verDescripcionBreve());
		}
		return listado;
	}

	/**
	 * Método que permite obtener una colección de objetos que representan a
	 * todos los álbumes existentes en este grupo musical.
	 * 
	 * @return una lista donde cada elemento es un objeto de la clase Album
	 */
	public List<Album> recuperarAlbumes() {
		// Devuelve un objeto lista con los valores que había en el mapa
		return new ArrayList<Album>(listaAlbumes.values());
	}

	/**
	 * Método que permite ver los detalles de un álbum en una cadena de texto.
	 * 
	 * @param idAlbum
	 *            identificador del álbum
	 * @return cadena de texto con la descripción completa del álbum
	 * @throws ExcepcionAlbumes
	 *             si no existe un álbum con un valor de identificador igual al
	 *             parámetro <i>idAlbum</i>
	 */
	public String verAlbum(String idAlbum) throws ExcepcionAlbumes {
		// Recupera la instancia
		Album a = comprobarAlbumExiste(idAlbum);
		// Le pide una descripción
		return a.verDescripcionCompleta();
	}

	/**
	 * Método que permite recuperar el objeto que representa a un Álbum.
	 * 
	 * @param idAlbum
	 *            identificador del álbum
	 * @return un objeto de tipo Album
	 * @throws ExcepcionAlbumes
	 *             si no existe un álbum con un valor de identificador igual al
	 *             parámetro <i>idAlbum</i>
	 */
	public Album recuperarAlbum(String idAlbum) throws ExcepcionAlbumes {
		// Recupera la instancia
		Album a = comprobarAlbumExiste(idAlbum);
		return a;
	}

	/**
	 * Método que permite borrar un álbum.
	 * 
	 * @param idAlbum
	 *            identificador del álbum
	 * @throws ExcepcionAlbumes
	 *             si no existe un álbum con un valor de identificador igual al
	 *             parámetro <i>idAlbum</i>
	 */
	public void borrarAlbum(String idAlbum) throws ExcepcionAlbumes {
		// Borra la instancia
		comprobarAlbumExiste(idAlbum);
		listaAlbumes.remove(idAlbum);
	}

	/**
	 * Método que permite añadir un pista a un álbum.
	 * 
	 * @param idAlbum
	 *            identificador del álbum
	 * @param nombre
	 *            nombre de la pita
	 * @param duracion
	 *            duración de la pista
	 * @return identificador único de la pista recién creada
	 * @throws ExcepcionAlbumes
	 *             si no existe un álbum con un valor de idAlbum igual al
	 *             parámetro <i>idAlbum</i>
	 * @throws ExcepcionPistas si los parámetros de creación de la pista no son correctos
	 * @see sgae.nucleo.gruposMusicales.Pista
	 */
	public String anadirPista(String idAlbum, String nombre, int duracion)
		throws ExcepcionAlbumes, ExcepcionPistas {
		// Recupera la instancia de Album
		Album a = comprobarAlbumExiste(idAlbum);
		return a.anadirPista(nombre, duracion);
	}

	/**
	 * Método que permite ver las pistas de un álbum.
	 * 
	 * @param idAlbum
	 *            identificador del álbum
	 * @return lista de cadenas de texto que contienen la información de un
	 *         pista
	 * @throws ExcepcionAlbumes
	 *             si no existe un álbum con un valor de identificador del álbum
	 *             igual al parámetro <i>idAlbum</i>
	 */
	public List<String> listarPistas(String idAlbum) throws ExcepcionAlbumes {
		// Recupera la instancia
		Album a = comprobarAlbumExiste(idAlbum);
		return a.verPistas();
	}

	/**
	 * Método que permite recuperar la lista de objetos que representan las
	 * pistas de un álbum.
	 * 
	 * @param idAlbum
	 *            identificador del álbum
	 * @return lista de objetos de tipo Pista
	 * @throws ExcepcionAlbumes
	 *             si no existe un álbum con un valor de identificador igual al
	 *             parámetro <i>idAlbum</i>
	 */
	public List<Pista> recuperarPistas(String idAlbum) throws ExcepcionAlbumes {
		// Recupera la instancia
		Album a = comprobarAlbumExiste(idAlbum);
		return a.recuperarPistas();
	}

	/**
	 * Método que permite ver una pista concreta de un álbum dado.
	 * 
	 * @param idAlbum
	 *            identificador del álbum
	 * @param idPista
	 *            identificador único de la pista buscado
	 * @return texto con información de la pista
	 * @throws ExcepcionAlbumes
	 *             si no existe un álbum con un valor de identificador igual al
	 *             parámetro <i>idAlbum</i>
	 * @throws ExcepcionPistas
	 *             si no existe una pista con un valor de identificador igual al
	 *             valor del parámetro <i>idPista</i>
	 */
	public String verPista(String idAlbum, String idPista)
		throws ExcepcionAlbumes, ExcepcionPistas {
		// Recupera la instancia
		Album a = comprobarAlbumExiste(idAlbum);
		return a.verPista(idPista);
	}

	/**
	 * Método que permite recuperar el objeto que representa un pista concreto
	 * de un álbum dada.
	 * 
	 * @param idAlbum
	 *            identificador del álbum
	 * @param idPista
	 *            identificador único de la pista buscada
	 * @return objeto de tipo Pista cuyo identificador es igual al parámetro
	 *         <i>idPista</i>
	 * @throws ExcepcionAlbumes
	 *             si no existe un álbum con un valor de identificador igual al
	 *             parámetro <i>idAlbum</i>
	 * @throws ExcepcionPistas
	 *             si no existe una pista con un valor de identificador igual al
	 *             valor del parámetro <i>idPista</i>
	 */
	public Pista recuperarPista(String idAlbum, String idPista)
		throws ExcepcionAlbumes, ExcepcionPistas {
		// Recupera la instancia
		Album a = comprobarAlbumExiste(idAlbum);
		return a.recuperarPista(idPista);
	}

	/**
	 * Método que busca los álbumes que entre sus pistas poseen una pista
	 * determinada, y para cada una consigue su descripción breve.
	 * 
	 * @param nombre
	 *            nombre de la pista buscada
	 * @return lista de cadenas de texto, donde cada una contiene la descripción
	 *         breve de un álbum
	 */
	public List<String> buscarAlbumesConPista(String nombre) {
		List<String> listado = new ArrayList<String>();
		// Recorre la lista de álbumes
		for (Album a : listaAlbumes.values()) {
			// Primero preguntamos si tiene la pista
			if (a.tienePista(nombre) == true) {
				// Si la tiene, entonces pedimos
				// la descripción breve
				listado.add(a.verDescripcionBreve());
			}
		}
		return listado;
	}

	/**
	 * Método que busca los álbumes que entre sus pistas poseen una pista
	 * determinada, y devuelve los objetos que cumplen la condición.
	 * 
	 * @param nombre
	 *            nombre de la pista buscada
	 * @return lista de objetos de tipo Album
	 */
	public List<Album> recuperarAlbumesConPista(String nombre) {
		List<Album> listado = new ArrayList<Album>();
		// Recorre la lista de álbumes
		for (Album a : listaAlbumes.values()) {
			// Primero preguntamos si tiene la pista
			if (a.tienePista(nombre) == true) {
				// Si la tiene, entonces añadimos el objeto de la clase Album
				listado.add(a);
			}
		}
		return listado;
	}

	/**
	 * Método que permite borrar un pista de un álbum.
	 * 
	 * @param idAlbum
	 *            identificador del álbum
	 * @param idPista
	 *            identificador único de la pista
	 * @throws ExcepcionAlbumes
	 *             si no existe un álbum con un valor de identificador igual al
	 *             parámetro <i>idAlbum</i>
	 * @throws ExcepcionPistas
	 *             si no existe un pista con un identificador igual al valor del
	 *             parámetro <i>idPista</i>
	 */
	public void eliminarPista(String idAlbum, String idPista)
		throws ExcepcionAlbumes, ExcepcionPistas {
		// Recupera la instancia
		Album a = comprobarAlbumExiste(idAlbum);
		a.eliminarPista(idPista);
	}
}
