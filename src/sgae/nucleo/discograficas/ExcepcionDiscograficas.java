package sgae.nucleo.discograficas;

public class ExcepcionDiscograficas extends Exception {
    /** Serial ID que debe ponerse */
    private static final long serialVersionUID = 1L;
    /** El cif de la discográfica sobre el que se ha hecho una operación fallida */
    private String cif;
    /** El tipo de operación que ha fallado */
    private String causaFallo;

    /**
     * Constructor con parámetros.
     * @param cif identificador de la discográfica
     * @param causaFallo descripción textual del fallo
     */
    public ExcepcionDiscograficas(String cif, String causaFallo) {
        super();
        this.cif = cif;
        this.causaFallo = causaFallo;
    }

    /**
     * Método que devuelve el identificador del contrato.
     * @return el identificador del contrato
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
