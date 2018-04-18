package sgae.nucleo.gruposMusicales;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sgae.util.Utils;

/**
 * Clase que almacena información sobre los álbumes de un grupo musical.
 * @author Manuel Rodríguez Cayetano. ETSIT UVa.
 * @version 1.0
 */
public class Album {
	/** Attributes */
	private String idAlbum;
	private String titulo;
	private Date fechaPublicacion;
	private int ejemplaresVendidos;
	
	/** Associations */
	
	/** La lista de pistas, indexada por un identificador Único */
	private Map<String,Pista> listaPistas;
	/** Un contador para generar identificadores Únicos de pista */
	private int ultimaPista;
	
	/** 
	 * Constructor con los atributos que se pueden inicializar de partida. 
	 * Ojo, la fecha de publicación se pasa como una cadena con el formato 
	 * dd-MM-yyyy .
	 *
	 * @param idAlbum el identificador del álbum
	 * @param titulo el título del álbum
	 * @param fechaPublicacion la fecha de publicación del álbum, se 
	 * pasa como una cadena dd-MM-yyyy
	 * @param ejemplaresVendidos número de ejempares vendidos del álbum
	 * @throws ParseException si el parámetro <i>fechaPublicacion</i> no tiene 
	 * el formato dd-MM-yyyy
	 */
	public Album(String idAlbum, String titulo, String fechaPublicacion, int ejemplaresVendidos) 
		throws ParseException {
		super();
		// Inicializa con valores pasados como parámetros
		this.idAlbum = Utils.testStringNullOrEmptyOrWhitespaceAndSet(idAlbum, "Campo idAlbum vacío");
		this.titulo = Utils.testStringNullOrEmptyOrWhitespaceAndSet(titulo, "Campo título vacío");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		if (Utils.isStringNullOrEmptyOrWhitespace(fechaPublicacion)) {
			throw new ParseException("Campo fecha de publicación vacío", 0);
		}
		this.fechaPublicacion = dateFormat.parse(fechaPublicacion);
		this.ejemplaresVendidos = ejemplaresVendidos;
		// Inicializa una lista de pistas vacía y el contador de pistas
		listaPistas = new HashMap<String,Pista>();
		ultimaPista = 0;
	}
	
	/**
	 * Método que lee el identificador del álbum.
	 * NOTA: El identificador sólo se puede leer, no escribir.
	 * @return el valor del identificador
	 */
	public String getId() {
		return idAlbum;
	}
	
	/**
	 * Método que devuelve el título del álbum.
	 * @return el título del álbum
	 */
	public String getTitulo() {
		return titulo;
	}
	
	/**
	 * Método que modifica el título.
	 * @param nuevoTitulo el nuevo título del nombre
	 */
	public void setTitulo(String nuevoTitulo) {
		titulo = Utils.testStringNullOrEmptyOrWhitespaceAndSet(nuevoTitulo, "Campo título vacío");
	}
	
	/** 
	 * Método que devuelve la fecha de publicación como una cadena.
	 * @return la fecha de publicación en formato dd-MM-yyyy
	 */
	public String getFechaPublicacion() {
		return new SimpleDateFormat("dd-MM-yyyy").format(fechaPublicacion);
	}
	
	/**
	 * Método que cambia la fecha de publicación a partir de una cadena.
	 * @param nuevaFechaPublicacion la nueva fecha de publicación del álbum
	 * @throws ParseException si el parámetro <i>fechaPublicacion</i> no tiene 
	 * el formato dd-MM-yyyy
	 */
	public void setFechaPublicacion(String nuevaFechaPublicacion) 
		throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		if (Utils.isStringNullOrEmptyOrWhitespace(nuevaFechaPublicacion)) {
			throw new ParseException("Campo fecha de publicación vacío", 0);
		}
		this.fechaPublicacion = dateFormat.parse(nuevaFechaPublicacion);
	}
	
	/**
	 * Método que devuelve el número de ejemplares vendidos del álbum.
	 * @return el número de ejemplares vendidos
	 */
	public int getEjemplaresVendidos() {
		return ejemplaresVendidos;
	}
	
	/**
	 * Método que cambia el número de ejemplares vendidos del álbum.
	 * @param nuevosEjemplaresVendidos el nuevo valor de los ejemplares vendidos
	 */
	public void setEjemplaresVendidos(int nuevosEjemplaresVendidos) {
		ejemplaresVendidos = nuevosEjemplaresVendidos;
	}
	
	
	/**
	 * Método que devuelve una descripción textual breve del álbum.
	 * @return la descripción textual breve del álbum
	 */
	String verDescripcionBreve() {
		return "Título: " + titulo + "\n";
	}
	
	/**
	 * Método que devuelve en una única cadena la información completa del álbum.
	 * 
	 * @return la descripción completa del álbum
	 */
	String verDescripcionCompleta() {
		return "Id: " + idAlbum + 
			"Título: " + titulo +
			"Fecha de publicación: " + fechaPublicacion +
			"Ejemplares vendidos: " + ejemplaresVendidos + "\n";
	}
	
	/**
	 * Método que añade un pista, y devuelve su identificador único.
	 * @param nombre nombre de la pista a añadir
	 * @param duracion duración de la pista a añadir
	 * @return el identificador único de la pista añadira al álbum
	 */
	public String anadirPista(String nombre, int duracion) {
		// Crea un identificador para la pista, formado por una 'p' y un 
		// número auto-incrementado
		String idPista = "p" + ultimaPista;
		// Crea el objeto
		Pista p = new Pista(idPista, nombre, duracion);
		// Colecciona el objeto indexado por el identificador
		listaPistas.put(idPista, p);
		// Incrementa el contador
		ultimaPista++;
		// Retorna el identificador del objeto reciÃ©n creado
		return idPista;
	}

	/**
	 * Método que comprueba si existe una pista identificada por un número único
	 * @param id identificador de la pista
	 * @return objeto del tipo Pista correspondiente al identificador dado
	 * @throws ExcepcionPistas si no existe una pista con un identificador
	 * igual al valor del parámetro <i>id</i>
	 */
	private Pista comprobarPistaExiste (String id) 
		throws ExcepcionPistas {
		Pista pista = listaPistas.get(id);
		if (pista == null) {
			throw new ExcepcionPistas(id,
							   "La pista que ha especificado no existe");
		}
		return pista;
	}
	
	/**
	 * Método que devuelve en una lista de cadenas la información de las 
	 * pistas.
	 * @return la lista formada por cadenas de texto, donde cada una 
	 * contiene la descripción de una pista
	 */
	public List<String> verPistas() {
		List<String> listado = new ArrayList<String>();
		
		// Recorre la lista de pistas
		for(Pista p : listaPistas.values()) {
			// Y a cada una le pide los detalles
			listado.add(p.verDescripcionCompleta());
		}
		return listado;
	}
	
	/**
	 * Método que devuelve las pistas en una lista de objetos 
	 * de tipo Pista.
	 * @return la lista cuyos elementos son objetos del tipo Pista
	 */
	public List<Pista> recuperarPistas() {
		return new ArrayList<Pista>(listaPistas.values());
	}
	
	/**
	 * Método que permite ver la descripción textual de una pista de este álbum.
	 * @param idPista el identificador único de la pista a mostrar
	 * @return una cadena con la descripción de la pista
	 * @throws ExcepcionPistas si no existe la pista que se busca
	 */
	public String verPista(String idPista) throws ExcepcionPistas {
		// Intenta obtener el objeto
		return comprobarPistaExiste(idPista).verDescripcionCompleta();
	}
	
	/**
	 * Método que permite obtener el objeto que representa a una pista dada
	 * en este álbum
	 * @param idPista el identificador único de la pista a recuperar
	 * @return un objeto de tipo Pista
	 * @throws ExcepcionPistas si no existe la pista que se busca
	 */
	public Pista recuperarPista(String idPista) throws ExcepcionPistas {
		return comprobarPistaExiste(idPista);
	}

	/**
	 * Comprueba si el álbum tiene una pista con un nombre determinado.
	 * @param nombrePista nombre de la pista a buscar
	 * @return valor booleano <i>true</i> si existe la pista buscada
	 */
	public boolean tienePista (String nombrePista) {
		for (Pista p: listaPistas.values()) {
			if (p.getNombre().equals(nombrePista)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Método que elimina una pista.
	 * @param idPista el identificador único de la pista a eliminar
	 * @throws ExcepcionPistas si no existe una pista con el idientificador que se 
	 * ha pasado como parámetro
	 */
	public void eliminarPista(String idPista) throws ExcepcionPistas {
		// Intenta borrar el objeto
		comprobarPistaExiste(idPista);
		listaPistas.remove(idPista);
	}    
}
