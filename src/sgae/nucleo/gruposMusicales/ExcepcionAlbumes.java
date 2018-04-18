package sgae.nucleo.gruposMusicales;

/**
 * Clase de excepción relacionada con los álbumes.
 * @author Manuel Rodríguez Cayetano. ETSIT UVa.
 * @version 1.0
 */
public class ExcepcionAlbumes extends Exception {
	/** Serial ID que debe ponerse */
	private static final long serialVersionUID = 1L;
	/** El id del álbum sobre el que se ha hecho una operación fallida */
	private String id;
	/** El tipo de operación que ha fallado */
	private String causaFallo;
	
	/**
	 * Constructor con parámetros.
	 * @param id id del álbum
	 * @param causaFallo descripción textual del fallo
	 */
	public ExcepcionAlbumes(String id, String causaFallo) {
		super("Álbum: " + id + ", error: " + causaFallo + "\n");
		this.id = id;
		this.causaFallo = causaFallo;
	}
	
	/**
	 * Método que devuelve el id del álbum.
	 * @return el id del álbum
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Método que devuelve la causa del fallo
	 * @return la causa del fallo
	 */
	public String getCausaFallo() {
		return causaFallo;
	}
}
