package sgae.nucleo.gruposMusicales;

/**
 * Clase de excepción relacionada con los grupos musicales.
 * @author Manuel Rodríguez Cayetano. ETSIT UVa.
 * @version 1.0
 */
public class ExcepcionGruposMusicales extends Exception {
	/** Serial ID que debe ponerse */
	private static final long serialVersionUID = 1L;
	/** El CIF de la Discografica sobre el que se ha hecho una operación fallida */
	private String cif;
	/** El tipo de operación que ha fallado */
	private String causaFallo;
	
	/**
	 * Constructor con parámetros.
	 * @param cif cif del grupo musical
	 * @param causaFallo descripción textual del fallo
	 */
	public ExcepcionGruposMusicales(String cif, String causaFallo) {
		super("Grupo musical: " + cif + ", error: " + causaFallo + "\n");
		this.cif = cif;
		this.causaFallo = causaFallo;
	}
	
	/**
	 * Método que devuelve el CIF del grupo musical.
	 * @return el CIF del grupo musical
	 */
	public String getCif() {
		return cif;
	}
	
	/**
	 * Método que devuelve la causa del fallo
	 * @return la causa del fallo
	 */
	public String getCausaFallo() {
		return causaFallo;
	}
}
