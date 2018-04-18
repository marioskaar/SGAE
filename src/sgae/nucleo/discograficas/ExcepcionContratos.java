package sgae.nucleo.discograficas;

/**
 * Clase de excepción relacionada con los contratos.
 * @author Eduardo Gómez Sánchez y Manuel Rodríguez Cayetano. ETSIT UVa.
 * @version 1.0
 */
public class ExcepcionContratos extends Exception {
    /** Serial ID que debe ponerse */
    private static final long serialVersionUID = 1L;
    /** El id del contrato sobre el que se ha hecho una operación fallida */
    private String idContrato;
    /** El tipo de operación que ha fallado */
    private String causaFallo;

    /**
     * Constructor con parámetros.
     * @param idContrato identificador del contrato
     * @param causaFallo descripción textual del fallo
     */
    public ExcepcionContratos(String idContrato, String causaFallo) {
        super();
        this.idContrato = idContrato;
        this.causaFallo = causaFallo;
    }

    /**
     * Método que devuelve el identificador del contrato.
     * @return el identificador del contrato
     */
    public String getIdContrato() {
        return idContrato;
    }
    
    /**
     * Método que devuelve la causa del fallo
     * @return la causa del fallo
     */
    public String getCausaFallo() {
        return causaFallo;
    }
}
