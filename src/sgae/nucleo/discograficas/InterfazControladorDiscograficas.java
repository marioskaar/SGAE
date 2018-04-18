package sgae.nucleo.discograficas;

import java.util.List;
import sgae.nucleo.personas.Persona;

/**
 * Interfaz para la clase controlador de discográficas.
 * 
 * @author Eduardo Gómez Sánchez y Manuel Rodríguez Cayetano. ETSIT UVa.
 * @version 1.0
 */
public interface InterfazControladorDiscograficas {

	/**
	 * Método que crea una nueva discográfica y la añade a la colección
	 * 
	 * @param cif
	 *            cif de la discográfica
	 * @param nombre
	 *            nombre de la discográfica
	 * @param direccion
	 *            dirección de la discográfica
	 * @throws ExcepcionDiscograficas
	 *             si ya existe una discográfica con un CIF igual al valor del
	 *             parámetro <i>cif</i>
	 */
	void crearDiscografica(String cif, String nombre, String direccion) throws ExcepcionDiscograficas;

	/**
	 * Método que permite modificar una discográfica, recibiendo todos los
	 * campos (el CIF no puede cambiar).
	 * 
	 * @param cif
	 *            cif de la discográfica
	 * @param nombre
	 *            nombre de la discográfica
	 * @param direccion
	 *            dirección de la discográfica
	 * @throws ExcepcionDiscograficas
	 *             si no existe una discográfica con un CIF igual al valor del
	 *             parámetro <i>cif</i>
	 */
	void modificarDiscografica(String cif, String nombre, String direccion) throws ExcepcionDiscograficas;

	/**
	 * Método que devuelve una lista de discográficas, con su descripción breve
	 * 
	 * @return lista de cadenas de texto, donde cada una contiene la descripción
	 *         breve de una discográfica
	 */
	List<String> listarDiscograficas();

	/**
	 * Método que devuelve una lista de objetos que representan a las
	 * discográficas
	 * 
	 * @return lista de objetos de tipo Discografica
	 */
	List<Discografica> recuperarDiscograficas();

	/**
	 * Método que permite ver los detalles de una discográfica.
	 * 
	 * @param cif
	 *            cif de la discográfica que se desea consultar
	 * @return cadena de texto con la descripción completa de la discográfica
	 * @throws ExcepcionDiscograficas
	 *             si no existe una discográfica con un CIF igual al valor del
	 *             parámetro <i>cif</i>
	 */
	String verDiscografica(String cif) throws ExcepcionDiscograficas;

	/**
	 * Método que permite recuperar el objeto que representa a una discográfica.
	 * 
	 * @param cif
	 *            cif de la discográfica que se desea consultar
	 * @return objeto de tipo Discografica
	 * @throws ExcepcionDiscograficas
	 *             si no existe una discográfica con un CIF igual al valor del
	 *             parámetro <i>cif</i>
	 */
	Discografica recuperarDiscografica(String cif) throws ExcepcionDiscograficas;

	/**
	 * Método que permite borrar una discográfica.
	 * 
	 * @param cif
	 *            de la discográfica que se desea borrar
	 * @throws ExcepcionDiscograficas
	 *             si no existe una discográfica con un CIF igual al valor del
	 *             parámetro <i>cif</i>
	 */
	void borrarDiscografica(String cif) throws ExcepcionDiscograficas;

	/**
	 * Método que permite ver el número de grupos contratados por una
	 * discográfica.
	 * 
	 * @param cif
	 *            cif de la discográfica
	 * @return el número de grupos contratados de la discográfica
	 * @throws ExcepcionDiscograficas
	 *             si no existe una discográfica con un CIF igual al valor del
	 *             parámetro <i>cif</i>
	 */
	int verNumeroGruposDeDiscografica(String cif) throws ExcepcionDiscograficas;

	/**
	 * Método que permite ver la masa salarial de una discográfica.
	 * 
	 * @param cif
	 *            cif de la discográfica
	 * @return la masa salarial de la discográfica
	 * @throws ExcepcionDiscograficas
	 *             si no existe una discográfica con un CIF igual al valor del
	 *             parámetro <i>cif</i>
	 */
	float verMasaSalarialDeDiscografica(String cif) throws ExcepcionDiscograficas;

	/**
	 * Método que permite contratar a un grupo musical
	 * 
	 * @param cifDiscografica
	 *            CIF de la discográfica
	 * @param cifGrupoMusical
	 *            CIF del grupo musical
	 * @param fechaInicio
	 *            fecha de inicio del contrato
	 * @param fechaFin
	 *            fecha de finalización del contrato
	 * @param sueldo
	 *            sueldo asociado al contrato
	 * @return identificador único del nuevo contrato
	 * @throws ExcepcionContratos
	 *             si el grupo musical cuyo CIF toma el valor del parámetro
	 *             <i>cifGrupoMusical</i> ya está contratado o no existe, o si
	 *             la discográfica con CIF igual al valor del parámetro
	 *             <i>cifDiscografica</i> no existe
	 */
	String nuevoContrato(String cifDiscografica, String cifGrupoMusical, String fechaInicio, String fechaFin,
			float sueldo) throws ExcepcionContratos;

	/**
	 * Método que permite terminar un contrato de una discográfica
	 *  
	 * @param cifDiscografica CIF de la discográfica
	 * @param idContrato identificador  
	 * @throws ExcepcionContratos
	 *             si la discográfica con CIF igual al valor del parámetro
	 *             <i>cifDiscografica</i> no existe o no tiene asociado el contrato
	 *             con identificadro <i>idContrato</i>
	 */
	void terminarContrato(String cifDiscografica, String idContrato) throws ExcepcionContratos;

	/**
	 * Método que permite recuperar un contrato de una discográfica
	 *  
	 * @param cifDiscografica CIF de la discográfica
	 * @param idContrato identificador  del contrato
	 * @return un objeto de la clase Contrato
	 * @throws ExcepcionContratos
	 *             si la discográfica con CIF igual al valor del parámetro
	 *             <i>cifDiscografica</i> no existe o si el contrato
	 *             con identificador <i>idContrato</i> no existe
	 */
	Contrato recuperarContrato(String cifDiscografica, String idContrato) throws ExcepcionContratos;

	/**
	 * Método que permite recuperar una descripción textual de un contrato de una 
	 * discográfica.
	 *  
	 * @param cifDiscografica CIF de la discográfica
	 * @param idContrato identificador del contrato  
	 * @return una representación textual de un Contrato
	 * @throws ExcepcionContratos
	 *             si la discográfica con CIF igual al valor del parámetro
	 *             <i>cifDiscografica</i> no existe o si el contrato
	 *             con identificador <i>idContrato</i> no existe
	 */
	String verContrato(String cifDiscografica, String idContrato) throws ExcepcionContratos;

	/**
	 * Método que devuelve una descripción del contrato actual de un grupo
	 * musical.
	 * 
	 * @param cifGrupoMusical
	 *            CIF del grupo musical
	 * @return descripción textual del contrato
	 * @throws ExcepcionContratos
	 *             si no hay un contrato activo para el grupo musical con CIF
	 *             igual al valor del parámetro <i>cifGrupoMusical</i> o dicho
	 *             grupo no existe
	 */
	String verContratoActualDeGrupoMusical(String cifGrupoMusical) throws ExcepcionContratos;

	/**
	 * Método que devuelve el contrato actual de un grupo musical.
	 * 
	 * @param cifGrupoMusical
	 *            CIF del grupo musical
	 * @return el contrato
	 * @throws ExcepcionContratos
	 *             si no hay un contrato activo para el grupo musical con CIF
	 *             igual al valor del parámetro <i>cifGrupoMusical</i> o dicho
	 *             grupo no existe
	 */
	Contrato recuperarContratoActualDeGrupoMusical(String cifGrupoMusical) throws ExcepcionContratos;

	/**
	 * Método que devuelve una descripción de los contratos anteriores de un
	 * grupo musical
	 * 
	 * @param cifGrupoMusical
	 *            CIF del grupo musical
	 * @return lista de cadenas de texto, donde cada una contiene la descripción
	 *         de un contrato del grupo musical (puede estar vacía)
	 */
	List<String> verContratosAnterioresDeGrupoMusical(String cifGrupoMusical);

	/**
	 * Método que devuelve una lista de los contratos anteriores de un
	 * grupo musical
	 * 
	 * @param cifGrupoMusical
	 *            CIF del grupo musical
	 * @return lista de contratos anteriores del grupo musical (puede estar vacía)
	 */
	List<Contrato> recuperarContratosAnterioresDeGrupoMusical(String cifGrupoMusical);

	/**
	 * Método que devuelve una descripción de los contratos vigentes de una
	 * compañía discográfica
	 * 
	 * @param cifDiscografica CIF de la compañía discográfica
	 * @return lista de cadenas de texto, donde cada una contiene la descripción
	 *         de un contrato
	 * @throws ExcepcionDiscograficas
	 *             si la compañía discográfica con CIF igual al valor del parámetro
	 *             <i>cifDiscografica</i> no existe
	 */
	List<String> verContratosVigentesDeDiscografica(String cifDiscografica) throws ExcepcionDiscograficas;

	/**
	 * Método que devuelve una lista de los contratos vigentes de una
	 * cimpañía discográfica
	 * 
	 * @param cifDiscografica CIF de la compañía discográfica
	 * @return lista de contratos vigentes de la compañia discográfica
	 * @throws ExcepcionDiscograficas
	 *             si la compañia discográfica con CIF igual al valor del parámetro
	 *             <i>cifDiscografica</i> no existe
	 */
	List<Contrato> recuperarContratosVigentesDeDiscografica(String cifDiscografica) throws ExcepcionDiscograficas;

	/**
	 * Método que devuelve una descripción de los contratos anteriores de una
	 * compañía discográfica
	 * 
	 * @param cifDiscografica CIF de la compañía discográfica
	 * @return lista de cadenas de texto, donde cada una contiene la descripción
	 *         de un contrato
	 * @throws ExcepcionDiscograficas
	 *             si la compañía discográfica con CIF igual al valor del parámetro
	 *             <i>cifDiscografica</i> no existe
	 */
	List<String> verContratosAnterioresDeDiscografica(String cifDiscografica) throws ExcepcionDiscograficas;

	/**
	 * Método que devuelve una lista de los contratos anteriores de una
	 * compañía discográfica
	 * 
	 * @param cifDiscografica CIF de la compañía discográfica
	 * @return lista de contratos anteriores de la compañia discográfica
	 * @throws ExcepcionDiscograficas
	 *             si la compañia discográfica con CIF igual al valor del parámetro
	 *             <i>cifDiscografica</i> no existe
	 */
	List<Contrato> recuperarContratosAnterioresDeDiscografica(String cifDiscografica) throws ExcepcionDiscograficas;

	/**
	 * Método que establece el director de una discográfica.
	 * @param cif cif de la discográfica
	 * @param director objeto de la clase Persona que será el nuevo director
	 * @throws ExcepcionDiscograficas si no existe una discográfica con un CIF igual al valor del parámetro <i>cif</i>
	 */	
	public void nuevoDirector(String cif, Persona director) throws ExcepcionDiscograficas;
	
	/**
	 * Método que permite recuperar la información del director de la discográfica.
	 * @param cif cif de la discográfica
	 * @return un objeto de la clase Persona con los datos del director
	 * @throws ExcepcionDiscograficas si no existe una discográfica con un CIF igual al valor del parámetro <i>cif</i>
	 */
	public Persona recuperarDirector(String cif) throws ExcepcionDiscograficas;
	
	/**
	 * Método para borrar el director de una discográfica.
	 * @param cif cif de la discográfica
	 * @throws ExcepcionDiscograficas si no existe una discográfica con un CIF igual al valor del parámetro <i>cif</i>
	 */
	public void borrarDirector(String cif) throws ExcepcionDiscograficas;
	
	/** 
	 * Método para listar todos los directores anteriores de una discográfica.
	 * @param cif cif de la discográfica
	 * @return lista de objetos de la clase @see Persona
	 * @throws ExcepcionDiscograficas si no existe una discográfica con un CIF igual al valor del parámetro <i>cif</i>
	 */
	public List<Persona> recuperarDirectoresAnteriores(String cif) throws ExcepcionDiscograficas;
}
