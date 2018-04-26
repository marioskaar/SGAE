package sgae.nucleo.gruposMusicales;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sgae.nucleo.personas.Persona;
import sgae.nucleo.personas.ExcepcionPersonas;
import sgae.nucleo.personas.InterfazControladorPersonas;

/**
 * Controlador para los grupos musicales.
 * @author Manuel Rodríguez Cayetano. ETSIT UVa.
 * @version 1.0
 */
public class ControladorGruposMusicales {
	
	/**
	 * Mapa indexado por CIF con la lista de grupos musicales
	 * (hacemos que esta clase mantenga la persistencia de esta lista
	 */
	private Map<String,GrupoMusical> listaGruposMusicales;
	
	/** 
	 * Referencia al controlador de personas (usado para gestionar los miembros 
	 * de los grupos musicales
	 */
	private InterfazControladorPersonas cp;
	
	/**
	 * Constructor que inicializa la lista de grupos musicales y guarda la referencia 
	 * al controlador de personas.
	 * @param cp referencia al controlador de personas
	 */
	public ControladorGruposMusicales(InterfazControladorPersonas cp) {
		this.cp = cp;
		listaGruposMusicales = new HashMap<String,GrupoMusical>();
	}
	
	/**
	 * Método para crear un grupo musical.
	 * @param cif CIF del grupo musical
	 * @param nombre nombre del grupo musical
	 * @param fechaCreacion fecha de creación del grupo musical.
	 * @throws ParseException si la fecha no tiene el formato día-mes-año (dd-MM-yyyy)
	 * @throws ExcepcionGruposMusicales si el grupo muiscal con CIF igual al valor del 
	 * parámetro <i>cif</i> ya existe
	 */
	public void crearGrupoMusical(String cif, String nombre, String fechaCreacion)
		throws ParseException, ExcepcionGruposMusicales {
		// Comprueba si no existe ya
		if(listaGruposMusicales.containsKey(cif) == false) {
			// Crea la instancia
			GrupoMusical gm = new GrupoMusical(cif, nombre, fechaCreacion);
			// La colecciona, indexada por CIF
			listaGruposMusicales.put(cif, gm);
		} else {
			throw new ExcepcionGruposMusicales(cif,
							   "El grupo musical que se ha intentado crear ya existe");
		}
	}

	/**
	 * Método que comprueba si existe un grupo musical identificado por un CIF
	 * @param cif CIF del grupo musical
	 * @return objeto del tipo GrupoMusical correspondiente al CIF dado
	 * @throws ExcepcionGruposMusicales si no existe un grupo musical con un CIF
	 * igual al valor del parámetro <i>cif</i>
	 */
	private GrupoMusical comprobarGrupoMusicalExiste (String cif) 
		throws ExcepcionGruposMusicales {
		GrupoMusical grupoMusical = listaGruposMusicales.get(cif);
		if (grupoMusical == null) {
			throw new ExcepcionGruposMusicales(cif,
							   "El grupo musical que ha especificado no existe");
		}
		return grupoMusical;		
	}
	
	/**
	 * Método para modificar un grupo musical (salvo su CIF)
	 * @param cif CIF del grupo musical al modificar
	 * @param nombre nuevo nombre del grupo
	 * @param fechaCreacion nueva fecha de creación del grupo
	 * @throws ParseException si la fecha no está en formato día-mes-año (dd-MM-yyyy)
	 * @throws ExcepcionGruposMusicales si no existe un grupo musical con un CIF
	 * igual al valor del parámetro <i>cif</i>
	 */
	public void modificarGrupoMusical(String cif, String nombre, String fechaCreacion)
		throws ParseException, ExcepcionGruposMusicales {
		// Recupera la instancia
		GrupoMusical gm = comprobarGrupoMusicalExiste(cif);
		gm.setNombre(nombre);
		gm.setFechaCreacion(fechaCreacion);
	}
	
	/**
	 * Método que devuelve una lista de cadenas que representan información breve de 
	 * los grupos musicales.
	 * @return una lista de cadenas de texto, cada una contiene la información breve de 
	 * un grupo musical
	 */
	public List<String> listarGruposMusicales() {
		List<String> listado = new ArrayList<String>();
		// Recorre la lista de grupos musicales
		for(GrupoMusical gm : listaGruposMusicales.values()) {
			// A cada una le pide su descripción breve
			listado.add(gm.verDescripcionBreve());
		}
		return listado;
	}

	/**
	 * Método que devuelve una lista de objetos de tipo GrupoMusical (cada uno contiene  
	 * la información completa de un grupo musical).
	 * @return una lista de objetos de la clase GrupoMusical
	 */
	public List<GrupoMusical> recuperarGruposMusicales() {
		// Crea y devuelve una lista con los valores almacenados
		return new ArrayList<GrupoMusical>(listaGruposMusicales.values());
	}

	/**
	 * Método que devuelve una cadena de texto que describe la información completa de un 
	 * grupo musical dado. 
	 * @param cif CIF del grupo musical buscado
	 * @return cadena de texto con la información completa del grupo musical
	 * @throws ExcepcionGruposMusicales si no existe un grupo musical con un CIF igual al 
	 * valor del parámetro <i>cif</i>
	 */
	public String verGrupoMusical(String cif) throws ExcepcionGruposMusicales {
		// Recupera la instancia
		return comprobarGrupoMusicalExiste(cif).verDescripcionCompleta();
	}
	
	/**
	 * Método que devuelve un objeto de tipo GrupoMusical (contiene la información completa de un 
	 * grupo musical dado). 
	 * @param cif CIF del grupo musical buscado
	 * @return objeto que contiene la información completa del grupo musical
	 * @throws ExcepcionGruposMusicales si no existe un grupo musical con un CIF igual al 
	 * valor del parámetro <i>cif</i>
	 */
	public GrupoMusical recuperarGrupoMusical(String cif) throws ExcepcionGruposMusicales {
		return comprobarGrupoMusicalExiste(cif);
	}

	/**
	 * Borra un grupo musical dado.
	 * @param cif CIF del grupo musical a borrar
	 * @throws ExcepcionGruposMusicales si no existe un grupo musical con un CIF igual al 
	 * valor del parámetro <i>cif</i>
	 */
	public void borrarGrupoMusical(String cif) throws ExcepcionGruposMusicales {
		// Borra la instancia
		comprobarGrupoMusicalExiste(cif);
		listaGruposMusicales.remove(cif);
	}
	
	/**
	 * Comprueba si un grupo musical está contratado por una compañía discográfica.
	 * @param cif CIF del grupo musical a comprobar	
	 * @return valor booleano <i>true</i> si el grupo está contratado
	 * @throws ExcepcionGruposMusicales si no existe un grupo musical con un CIF
	 * igual al valor del parámetro <i>cif</i>
	 */
	public boolean estaContratado(String cif) throws ExcepcionGruposMusicales{	
		return comprobarGrupoMusicalExiste(cif).estaContratado();
	}

	/**
	 * Cambia el estado del grupo musical a contratado por una compañía discográfica.
	 * @param cif CIF del grupo musical a comprobar	
	 * @throws ExcepcionGruposMusicales si no existe un grupo musical con un CIF
	 * igual al valor del parámetro <i>cif</i>
	 */
	public void contrata(String cif) throws ExcepcionGruposMusicales {
		comprobarGrupoMusicalExiste(cif).contrata();
	}

	/**
	 * Cambia el estado del grupo musical a no contratado por una compañía discográfica.
	 * @param cif CIF del grupo musical a comprobar	
	 * @throws ExcepcionGruposMusicales si no existe un grupo musical con un CIF
	 * igual al valor del parámetro <i>cif</i>
	 */
	public void despide(String cif) throws ExcepcionGruposMusicales {
		comprobarGrupoMusicalExiste(cif).despide();
	}
	
	/**
	 * Método que devuelve el número total de álbumes creados por un grupo musical.
	 * @param cif cif del grupo musical
	 * @return el número total de álbumes
	 * @throws ExcepcionGruposMusicales si no existe un grupo musical con un CIF
	 * igual al valor del parámetro <i>cif</i>
	 */
	public int verNumeroAlbumes(String cif) throws ExcepcionGruposMusicales {
		return comprobarGrupoMusicalExiste(cif).verNumeroAlbumes();
	}

	/**
	 * Añade un miembro a un grupo musical.
	 * @param cif CIF del grupo musical
	 * @param dni DNI de la persona a añadir como miembro
	 * @throws ExcepcionGruposMusicales si no existe un grupo musical con un CIF
	 * igual al valor del parámetro <i>cif</i>
	 * @throws ExcepcionPersonas si ya existe una persona con un DNI
	 * igual al valor del parámetro <i>dni</i> como miembro del grupo o si no existe dicha persona
	 */
	public void anadirMiembro(String cif, String dni) 
		throws ExcepcionGruposMusicales, ExcepcionPersonas {
		Persona persona = cp.recuperarPersona(dni);
		comprobarGrupoMusicalExiste(cif).anadirMiembro(persona);
	}
	
	/**
	 * Recupera la lista de miembros de un grupo musical como una lista de objetos de tipo Persona.
	 * @param cif CIF del grupo musical cuyos miembros se desea listar
	 * @return lista de objetos de la clase Persona
	 * @throws ExcepcionGruposMusicales si no existe un grupo musical con un CIF igual al valor del 
	 * parámetro <i>cif</i>
	 */
	public List<Persona> recuperarMiembros(String cif) throws ExcepcionGruposMusicales {
		return comprobarGrupoMusicalExiste(cif).recuperarMiembros();
	}

	/**
	 * Recupera la lista de miembros anteriores de un grupo musical como una lista de objetos de tipo Persona.
	 * @param cif CIF del grupo musical cuyos miembros se desea listar
	 * @return lista de objetos de la clase Persona
	 * @throws ExcepcionGruposMusicales si no existe un grupo musical con un CIF igual al valor del 
	 * parámetro <i>cif</i>
	 */
	public List<Persona> recuperarMiembrosAnteriores(String cif) throws ExcepcionGruposMusicales {
		return comprobarGrupoMusicalExiste(cif).recuperarMiembrosAnteriores();
	}

	/**
	 * Elimina un miembro de  un grupo musical.
	 * @param cif CIF del grupo musical
	 * @param dni DNI de la persona que se quiere eliminar como miembro del grupo musical
	 * @throws ExcepcionGruposMusicales si no existe un grupo musical con un CIF igual al valor del 
	 * parámetro <i>cif</i>
	 * @throws ExcepcionPersonas si la persona con DNI igual al valor del parámetro <i>dni</i> no es 
	 * miembro del grupo musical.
	 */
	public void eliminarMiembro(String cif, String dni)
		throws ExcepcionGruposMusicales, ExcepcionPersonas {
		comprobarGrupoMusicalExiste(cif).eliminarMiembro(dni);
	}

	/**
	 * Método que crea un nuevo álbum asociado a un grupo musical 
	 * y lo añade a la colección.
	 * 
	 * @param cif cif del grupo musical
	 * @param titulo título del álbum
	 * @param fechaPublicacion fecha de publicación del álbum en formato dd-MM-yyyy
	 * @param ejemplaresVendidos número de ejemplares vendidos del álbum
	 * @return identificador del nuevo álbum
	 * @throws ParseException si el parámetro <i>fechaPublicacion</i> no tiene 
	 * el formato dd-MM-yyyy
	 * @throws ExcepcionGruposMusicales si no existe un grupo musical con un CIF
	 * igual al valor del parámetro <i>cif</i>
	 */
	public String crearAlbum(String cif, String titulo, String fechaPublicacion,
			       int ejemplaresVendidos)
		throws ExcepcionGruposMusicales, ParseException {
		return comprobarGrupoMusicalExiste(cif).crearAlbum(titulo,
									       fechaPublicacion, ejemplaresVendidos);
	}

	/**
	 * Método que permite modificar un álbum existente, recibiendo todos 
	 * los campos (el identificador de álbum no puede cambiar).
	 * 
	 * @param cif cif del grupo musical
	 * @param idAlbum identificador del álbum
	 * @param titulo título del álbum
	 * @param fechaPublicacion fecha de publicación del álbum en formato dd-MM-yyyy
	 * @param ejemplaresVendidos número de ejemplares vendidos del álbum
	 * @throws ParseException si el parámetro <i>fechaPublicacion</i> no tiene 
	 * el formato dd-MM-yyyy
	 * @throws ExcepcionAlbumes si no existe una álbum con un identificador igual al valor
	 * del campo <i>idAlbum</i>
	 * @throws ExcepcionGruposMusicales si no existe un grupo musical con un CIF
	 * igual al valor del parámetro <i>cif</i>
	 */
	public void modificarAlbum(String cif, String idAlbum, String titulo,
			       String fechaPublicacion, int ejemplaresVendidos)
		throws ParseException, ExcepcionAlbumes, ExcepcionGruposMusicales {
		comprobarGrupoMusicalExiste(cif).modificarAlbum(idAlbum, titulo, 
									fechaPublicacion, ejemplaresVendidos);
	}

	/**
	 * Método que devuelve una lista de álbumes de un grupo musical, 
	 * con su descripción breve.
	 * 
	 * @param cif cif del grupo musical
	 * @return una lista donde cada elemento es una cadena de texto con la
	 *  descripción breve de un álbum
	 * @throws ExcepcionGruposMusicales si no existe un grupo musical con un CIF
	 * igual al valor del parámetro <i>cif</i>
	 */
	public List<String> listarAlbumes(String cif) throws ExcepcionGruposMusicales {
		return comprobarGrupoMusicalExiste(cif).listarAlbumes();
	}

	/**
	 * Método que permite obtener una colección de objetos que representan a
	 * todos los álbumes de un grupo musical.
	 * 
	 * @param cif cif del grupo musical
	 * @return una lista donde cada elemento es un objeto de la clase Album
	 * @throws ExcepcionGruposMusicales si no existe un grupo musical con un CIF
	 * igual al valor del parámetro <i>cif</i>
	 */
	public List<Album> recuperarAlbumes(String cif) throws ExcepcionGruposMusicales {
		return comprobarGrupoMusicalExiste(cif).recuperarAlbumes();
	}

	/**
	 * Método que permite ver los detalles de un álbum en una cadena de texto.
	 * 
	 * @param cif cif del grupo musical
	 * @param idAlbum identificador del álbum
	 * @return cadena de texto con la descripción completa del álbum
	 * @throws ExcepcionAlbumes si no existe una álbum con un valor de 
	 * identificador igual al parámetro <i>idAlbum</i>
	 * @throws ExcepcionGruposMusicales si no existe un grupo musical con un CIF
	 * igual al valor del parámetro <i>cif</i>
	 */
	public String verAlbum(String cif, String idAlbum)
		throws ExcepcionAlbumes, ExcepcionGruposMusicales {
		return comprobarGrupoMusicalExiste(cif).verAlbum(idAlbum);
	}

	/**
	 * Método que permite recuperar el objeto que representa a un  Álbum.
	 * 
	 * @param cif cif del grupo musical
	 * @param idAlbum identificador del álbum
	 * @return un objeto de tipo Album
	 * @throws ExcepcionAlbumes si no existe un álbum con un valor de 
	 * identificador igual al parámetro <i>idAlbum</i>
	 * @throws ExcepcionGruposMusicales si no existe un grupo musical con un CIF
	 * igual al valor del parámetro <i>cif</i>
	 */
	public Album recuperarAlbum(String cif, String idAlbum)
		throws ExcepcionAlbumes, ExcepcionGruposMusicales {
		return comprobarGrupoMusicalExiste(cif).recuperarAlbum(idAlbum);
	}

	/**
	 * Método que permite borrar un álbum.
	 * 
	 * @param cif cif del grupo musical
	 * @param idAlbum identificador del álbum
	 * @throws ExcepcionAlbumes si no existe un álbum con un valor de 
	 * identificador igual al parámetro <i>idAlbum</i>
	 * @throws ExcepcionGruposMusicales si no existe un grupo musical con un CIF
	 * igual al valor del parámetro <i>cif</i>
	 */
	public void borrarAlbum(String cif, String idAlbum)
		throws ExcepcionAlbumes, ExcepcionGruposMusicales {
		comprobarGrupoMusicalExiste(cif).borrarAlbum(idAlbum);
	}

	/**
	 * Método que permite añadir un pista a un álbum.
	 * 
	 * @param cif cif del grupo musical
	 * @param idAlbum identificador del álbum
	 * @param nombre nombre de la pista
	 * @param duracion duración de la pista
	 * @return identificador único de la pista recién creada
	 * @throws ExcepcionAlbumes si no existe una álbum con un valor de 
	 * identificador igual al parámetro <i>idAlbum</i>
	 * @throws ExcepcionGruposMusicales si no existe un grupo musical con un CIF
	 * igual al valor del parámetro <i>cif</i>
	 * @throws ExcepcionPistas si los parámetros para la creación de la pista no son correctos
	 * @see sgae.nucleo.gruposMusicales.Pista
	 */
	public String anadirPista(String cif, String idAlbum, String nombre, int duracion) 
		throws ExcepcionAlbumes, ExcepcionGruposMusicales, ExcepcionPistas {
		return comprobarGrupoMusicalExiste(cif).anadirPista(idAlbum,
									  nombre, duracion);
	}

	/**
	 * Método que permite ver las pistas de un álbum.
	 * 
	 * @param cif cif del grupo musical
	 * @param idAlbum identificador del álbum
	 * @return lista de cadenas de texto que contienen la información de las pistas
	 * @throws ExcepcionAlbumes si no existe un álbum con un valor de 
	 * identificador del álbum igual al parámetro <i>idAlbum</i>
	 * @throws ExcepcionGruposMusicales si no existe un grupo musical con un CIF
	 * igual al valor del parámetro <i>cif</i>
	 */
	public List<String> listarPistas(String cif, String idAlbum) 
			throws ExcepcionAlbumes, ExcepcionGruposMusicales {
		return comprobarGrupoMusicalExiste(cif).listarPistas(idAlbum);
	}

	/**
	 * Método que permite recuperar la lista de objetos que representan las
	 * pistas de un álbum.
	 * 
	 * @param cif cif del grupo musical
	 * @param idAlbum identificador del álbum
	 * @return lista de objetos de tipo Pista
	 * @throws ExcepcionAlbumes si no existe un álbum con un valor de 
	 * identificador igual al parámetro <i>idAlbum</i>
	 * @throws ExcepcionGruposMusicales si no existe un grupo musical con un CIF
	 * igual al valor del parámetro <i>cif</i>
	 */
	public List<Pista> recuperarPistas(String cif, String idAlbum)
		throws ExcepcionAlbumes, ExcepcionGruposMusicales {
		return comprobarGrupoMusicalExiste(cif).recuperarPistas(idAlbum);
	}

	/**
	 * Método que permite ver un pista concreto de un álbum dada.
	 * 
	 * @param cif cif del grupo musical
	 * @param idAlbum identificador del álbum
	 * @param idPista identificador único de la pista buscada
	 * @return lista de cadenas de texto, donde cada una contiene la 
	 * descripción breve de una pista
	 * @throws ExcepcionPistas si no existe una pista con un valor de 
	 * identificador igual al valor del parámetro <i>idPista</i>
	 * @throws ExcepcionAlbumes si no existe un álbum con un valor de 
	 * identificador igual al parámetro <i>idAlbum</i>
	 * @throws ExcepcionGruposMusicales si no existe un grupo musical con un CIF
	 * igual al valor del parámetro <i>cif</i>
	 */
	public String verPista(String cif, String idAlbum, String idPista)
		throws ExcepcionAlbumes, ExcepcionPistas, ExcepcionGruposMusicales {
		return comprobarGrupoMusicalExiste(cif).verPista(idAlbum, idPista);
	}

	/**
	 * Método que permite recuperar el objeto que representa un pista concreta
	 * de un álbum dado.
	 * 
	 * @param cif cif del grupo musical
	 * @param idAlbum identificador del álbum
	 * @param idPista identificador único de la pista buscada
	 * @return objeto de tipo Pista cuyo identificador es igual al parámetro 
	 * <i>idPista</i>
	 * @throws ExcepcionGruposMusicales si no existe un grupo musical con un CIF
	 * igual al valor del parámetro <i>cif</i>
	 * @throws ExcepcionAlbumes si no existe un álbum con un valor de 
	 * identificador igual al parámetro <i>idAlbum</i>
	 * @throws ExcepcionPistas si no existe una pista con un valor de 
	 * identificador igual al valor del parámetro <i>idPista</i>
	 */
	public Pista recuperarPista(String cif, String idAlbum, String idPista) 
			throws ExcepcionGruposMusicales, ExcepcionAlbumes, ExcepcionPistas {
		return comprobarGrupoMusicalExiste(cif).recuperarPista(idAlbum, idPista);
	}

	/**
	 * Método que busca los álbumes que entre sus pistas poseen una pista
	 * determinada, y para cada una consigue su descripción breve.
	 * 
	 * @param cif cif del grupo musical
	 * @param nombre nombre de la pista buscada
	 * @return lista de cadenas de texto, donde cada una contiene la descripción
	 *  breve de un álbum
	 * @throws ExcepcionGruposMusicales si no existe un grupo musical con un CIF
	 * igual al valor del parámetro <i>cif</i>
	 */
	public List<String> buscarAlbumesConPista(String cif, String nombre)
		throws ExcepcionGruposMusicales {
		return comprobarGrupoMusicalExiste(cif).buscarAlbumesConPista(nombre);
	}

	/**
	 * Método que busca los álbumes que entre sus pistas poseen una pista
	 * determinada, y devuelve los objetos que cumplen la condición.
	 * 
	 * @param cif cif del grupo musical
	 * @param nombre nombre de la pista buscada
	 * @return lista de objetos de tipo Album
	 * @throws ExcepcionGruposMusicales si no existe un grupo musical con un CIF
	 * igual al valor del parámetro <i>cif</i>
	 */
	public List<Album> recuperarAlbumesConPista(String cif, String nombre)
		throws ExcepcionGruposMusicales {
		return comprobarGrupoMusicalExiste(cif).recuperarAlbumesConPista(nombre);
	}

	/**
	 * Método que permite borrar un pista de un álbum.
	 * 
	 * @param cif cif del grupo musical
	 * @param idAlbum identificador del álbum
	 * @param idPista identificador único de la pista
	 * @throws ExcepcionGruposMusicales si no existe un grupo musical con un CIF
	 * igual al valor del parámetro <i>cif</i>
	 * @throws ExcepcionAlbumes si no existe una álbum con un valor de 
	 * identificador igual al parámetro <i>idAlbum</i>
	 * @throws ExcepcionPistas si no existe un pista con un identificador 
	 * igual al valor del parámetro <i>idPista</i>
	 */
	public void eliminarPista(String cif, String idAlbum, String idPista) 
			throws ExcepcionGruposMusicales, ExcepcionAlbumes, ExcepcionPistas {
		comprobarGrupoMusicalExiste(cif).eliminarPista(idAlbum, idPista);
	}
}
