package sgae.nucleo.gruposMusicales;

/**
 * Clase de excepción relacionada con las pistas.
 * @author Manuel Rodríguez Cayetano. ETSIT UVa.
 * @version 1.0
 */
public class ExcepcionPistas extends Exception {
	/** Serial ID que debe ponerse */
	private static final long serialVersionUID = 1L;
	/** El id de la pista sobre el que se ha hecho una operación fallida */
	private String id;
	/** El tipo de operación que ha fallado */
	private String causaFallo;
	
	/**
	 * Constructor con parámetros.
	 * @param id id de la pista
	 * @param causaFallo descripción textual del fallo
	 */
	public ExcepcionPistas(String id, String causaFallo) {
		super("Pista: " + id + ", error: " + causaFallo + "\n");
		this.id = id;
		this.causaFallo = causaFallo;
	}
	
	/**
	 * Método que devuelve el id de la pista.
	 * @return el id de la pista
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
