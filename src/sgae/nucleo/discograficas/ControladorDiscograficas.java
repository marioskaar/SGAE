package sgae.nucleo.discograficas;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sgae.nucleo.gruposMusicales.ControladorGruposMusicales;
import sgae.nucleo.gruposMusicales.ExcepcionGruposMusicales;
import sgae.nucleo.gruposMusicales.GrupoMusical;
import sgae.nucleo.personas.Persona;
/**
 * Clase controlador de discográficas.
 * 
 * @author Eduardo Gómez Sánchez y Manuel Rodríguez Cayetano. ETSIT UVa.
 * @version 1.0
 */
public class ControladorDiscograficas implements InterfazControladorDiscograficas {
	/**
	 * Mapa indexado por CIF con la lista de discográficas (hacemos que esta
	 * clase mantenga la persistencia de esta lista
	 */
	private Map<String, Discografica> listaDiscograficas;
	/** objeto controlador de grupos musicales */
	private ControladorGruposMusicales cgm;

	/**
	 * Constructor que inicializa la lista de discográficas.
	 * @param cgm referencia al controlador de grupos musicales
	 */
	public ControladorDiscograficas(ControladorGruposMusicales cgm) {
		super();
		this.cgm = cgm;
		// Inicializa la lista
		listaDiscograficas = new HashMap<String, Discografica>();
	}

	/* (non-Javadoc)
	 * @see sgae.nucleo.discograficas.InterfazControladorDiscograficas#crearDiscografica(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void crearDiscografica(String cif, String nombre, String direccion) throws ExcepcionDiscograficas, ParseException {
		// Comprueba si no existe ya
		if (listaDiscograficas.containsKey(cif) == false) {
			// Crea la instancia
			Discografica d = new Discografica(cif, nombre, direccion);
			// La colecciona, indexada por CIF
			listaDiscograficas.put(cif, d);
		} else {
			throw new ExcepcionDiscograficas(cif, "La discográfica que se ha intentado crear ya existe");
		}
	}

	/* (non-Javadoc)
	 * @see sgae.nucleo.discograficas.InterfazControladorDiscograficas#modificarDiscografica(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void modificarDiscografica(String cif, String nombre, String direccion) throws ExcepcionDiscograficas, ParseException {
		// Recupera la instancia
		Discografica d = listaDiscograficas.get(cif);
		if (d != null) {
			d.setNombre(nombre);
			d.setDireccion(direccion);
		} else {
			throw new ExcepcionDiscograficas(cif, "La discográfica que se quiere modificar no existe");
		}
	}

	/* (non-Javadoc)
	 * @see sgae.nucleo.discograficas.InterfazControladorDiscograficas#listarDiscograficas()
	 */
	@Override
	public List<String> listarDiscograficas() {
		List<String> listado = new ArrayList<String>();
		// Recorre la lista de discográficas
		for (Discografica d : listaDiscograficas.values()) {
			// A cada una le pide su descripción breve
			listado.add(d.verDescripcionBreve());
		}
		return listado;
	}

	/* (non-Javadoc)
	 * @see sgae.nucleo.discograficas.InterfazControladorDiscograficas#recuperarDiscograficas()
	 */
	@Override
	public List<Discografica> recuperarDiscograficas() {
		// Crea y devuelve una lista con los valores almacenados
		return new ArrayList<Discografica>(listaDiscograficas.values());
	}

	/* (non-Javadoc)
	 * @see sgae.nucleo.discograficas.InterfazControladorDiscograficas#verDiscografica(java.lang.String)
	 */
	@Override
	public String verDiscografica(String cif) throws ExcepcionDiscograficas {
		// Recupera la instancia
		Discografica d = listaDiscograficas.get(cif);
		if (d != null) {
			return d.verDescripcionCompleta();
		} else {
			throw new ExcepcionDiscograficas(cif, "La discográfica que se ha intentado mostrar no existe");
		}
	}

	/* (non-Javadoc)
	 * @see sgae.nucleo.discograficas.InterfazControladorDiscograficas#recuperarDiscografica(java.lang.String)
	 */
	@Override
	public Discografica recuperarDiscografica(String cif) throws ExcepcionDiscograficas {
		// Recupera la instancia
		Discografica d = listaDiscograficas.get(cif);
		if (d != null) {
			return d;
		} else {
			throw new ExcepcionDiscograficas(cif, "La discográfica que se ha intentado mostrar no existe");
		}
	}

	/* (non-Javadoc)
	 * @see sgae.nucleo.discograficas.InterfazControladorDiscograficas#borrarDiscografica(java.lang.String)
	 */
	@Override
	public void borrarDiscografica(String cif) throws ExcepcionDiscograficas {
		// Borra la instancia
		Discografica d = listaDiscograficas.remove(cif);
		// Si d tiene un valor, es que existía y la ha borrado
		if (d == null) {
			// Si p es null es que no se ha borrado porque no existía
			throw new ExcepcionDiscograficas(cif, "La discográfica que se ha intentado borrar no existe");
		}
	}

	public void nuevoDirector(String cif, Persona director) throws ExcepcionDiscograficas {
		Discografica d = listaDiscograficas.get(cif);
		if (d == null) {
			throw new ExcepcionDiscograficas(cif, "La discográfica que se ha intentado usar no existe");
		}
		d.nuevoDirector(director);
	}

	public Persona recuperarDirector(String cif) throws ExcepcionDiscograficas {
		Discografica d = listaDiscograficas.get(cif);
		if (d == null) {
			throw new ExcepcionDiscograficas(cif, "La discográfica que se ha intentado usar no existe");
		}
		return d.recuperarDirector();
	}

	public void borrarDirector(String cif)  throws ExcepcionDiscograficas{
		Discografica d = listaDiscograficas.get(cif);
		if (d == null) {
			throw new ExcepcionDiscograficas(cif, "La discográfica que se ha intentado usar no existe");
		}
		d.borrarDirector();
	}

	public List<Persona> recuperarDirectoresAnteriores(String cif) throws ExcepcionDiscograficas {
		Discografica d = listaDiscograficas.get(cif);
		if (d == null) {
			throw new ExcepcionDiscograficas(cif, "La discográfica que se ha intentado usar no existe");
		}
		return d.recuperarDirectoresAnteriores();
	}
		
	/* (non-Javadoc)
	 * @see sgae.nucleo.discograficas.InterfazControladorDiscograficas#verNumeroGruposDeDiscografica(java.lang.String)
	 */
	@Override
	public int verNumeroGruposDeDiscografica(String cif) throws ExcepcionDiscograficas {
		// Recupera la instancia
		Discografica d = listaDiscograficas.get(cif);
		if (d != null) {
			// Delega en la discográfica que nos da ese dato
			return d.getNumeroGruposContratados();
		} else {
			throw new ExcepcionDiscograficas(cif, "La discográfica que se ha intentado mostrar no existe");
		}
	}

	/* (non-Javadoc)
	 * @see sgae.nucleo.discograficas.InterfazControladorDiscograficas#verMasaSalarialDeDiscografica(java.lang.String)
	 */
	@Override
	public float verMasaSalarialDeDiscografica(String cif) throws ExcepcionDiscograficas {
		// Recupera la instancia
		Discografica d = listaDiscograficas.get(cif);
		if (d != null) {
			// Delega en la discográfica que nos dé ese dato
			return d.getMasaSalarial();
		} else {
			throw new ExcepcionDiscograficas(cif, "La discográfica que se ha intentado mostrar no existe");
		}
	}

	/* (non-Javadoc)
	 * @see sgae.nucleo.discograficas.InterfazControladorDiscograficas#nuevoContrato(java.lang.String, java.lang.String, java.lang.String, java.lang.String, float)
	 */
	@Override
	public String nuevoContrato(String cifDiscografica, String cifGrupoMusical, String fechaInicio, String fechaFin,
			float sueldo) throws ExcepcionContratos, ParseException {
		try {
			// Recupera la instancia de grupo musical
			GrupoMusical grupoMusical = cgm.recuperarGrupoMusical(cifGrupoMusical);
			// Recupera la instancia de discográfica
			Discografica discografica = recuperarDiscografica(cifDiscografica);
			// Si el grupo está actualmente sin contratar
			if (grupoMusical.estaContratado() == false) {
				return discografica.nuevoContrato(grupoMusical, fechaInicio, fechaFin, sueldo);
			} else {
				// Si el grupo musical estaba ya contratado
				throw new ExcepcionContratos("<no asignado>",
						"No se ha podido crear un contrato porque el grupo musical con CIF " + cifGrupoMusical
								+ " ya está contratada.");
			}
		} catch (ExcepcionGruposMusicales e) {
			// Si no hemos podido recuperar el grupo musical
			throw new ExcepcionContratos("<no asignado>",
					"No se ha podido crear un contrato porque el grupo musical con CIF " + cifGrupoMusical
							+ " no existe.");
		} catch (ExcepcionDiscograficas e) {
			// Si no hemos podido recuperar la discográfica
			throw new ExcepcionContratos("<no asignado>",
					"No se ha podido crear un contrato porque la discográfica con CIF " + cifDiscografica
							+ " no existe.");
		}
	}

	/* (non-Javadoc)
	 * @see sgae.nucleo.discograficas.InterfazControladorDiscograficas#terminarContrato(java.lang.String, java.lang.String)
	 */
	@Override
	public void terminarContrato(String cifDiscografica, String idContrato)
			throws ExcepcionContratos {
		Discografica d = listaDiscograficas.get(cifDiscografica);
		if (d == null) {
			throw new ExcepcionContratos(cifDiscografica, 
					"La discográfica de la que se quiere terminar el conrato no existe");
		}
		d.terminarContrato(idContrato);
	}
	
	/* (non-Javadoc)
	 * @see sgae.nucleo.discograficas.InterfazControladorDiscograficas#recuperarContrato(java.lang.String, java.lang.String)
	 */
	@Override
	public Contrato recuperarContrato(String cifDiscografica, String idContrato)
			throws ExcepcionContratos {
		Discografica d = listaDiscograficas.get(cifDiscografica);
		if (d == null) {
			throw new ExcepcionContratos(cifDiscografica, 
					"La discográfica de la que se quiere recuperar el conrato no existe");
		}
		return d.recuperarContrato(idContrato);
	}
	
	/* (non-Javadoc)
	 * @see sgae.nucleo.discograficas.InterfazControladorDiscograficas#verContrato(java.lang.String, java.lang.String)
	 */
	@Override
	public String verContrato(String cifDiscografica, String idContrato)
			throws ExcepcionContratos {
		Discografica d = listaDiscograficas.get(cifDiscografica);
		if (d == null) {
			throw new ExcepcionContratos(cifDiscografica, 
					"La discográfica de la que se quiere mostrar el conrato no existe");
		}
		return d.verContrato(idContrato);
	}
	
	/* (non-Javadoc)
	 * @see sgae.nucleo.discograficas.InterfazControladorDiscograficas#verContratoActualDeGrupoMusical(java.lang.String)
	 */
	@Override
	public String verContratoActualDeGrupoMusical(String cifGrupoMusical) throws ExcepcionContratos {
		// Recorre la lista de contratos activos
		for (Discografica d : listaDiscograficas.values()) {
			for (Contrato c : d.recuperarContratosVigentes()) {
				// Si el contrato tiene a este grupo musical
				if (c.tieneGrupoMusical(cifGrupoMusical)) {
					// Devuelve los detalles del contrato
					return c.verDetalles();
				}
			}
		}
		// Si se llega hasta aquí es que no tenemos ningún contrato
		// activo para este grupo musical
		throw new ExcepcionContratos("<no asignado>",
				"No se ha podido encontrar contrato activo para el grupo musical con CIF " + cifGrupoMusical + ".");
	}

	/* (non-Javadoc)
	 * @see sgae.nucleo.discograficas.InterfazControladorDiscograficas#recuperarContratoActualDeGrupoMusical(java.lang.String)
	 */
	@Override
	public Contrato recuperarContratoActualDeGrupoMusical(String cifGrupoMusical) 
			throws ExcepcionContratos {
		// Recorre la lista de contratos activos
		for (Discografica d : listaDiscograficas.values()) {
			for (Contrato c : d.recuperarContratosVigentes()) {
				// Si el contrato tiene a este grupo musical
				if (c.tieneGrupoMusical(cifGrupoMusical)) {
					// Devuelve los detalles del contrato
					return c;
				}
			}
		}
		// Si se llega hasta aquí es que no tenemos ningún contrato
		// activo para este grupo musical
		throw new ExcepcionContratos("<no asignado>",
				"No se ha podido encontrar contrato activo para el grupo musical con CIF " + cifGrupoMusical + ".");
	}

	/* (non-Javadoc)
	 * @see sgae.nucleo.discograficas.InterfazControladorDiscograficas#verContratosAnterioresDeGrupoMusical(java.lang.String)
	 */
	@Override
	public List<String> verContratosAnterioresDeGrupoMusical(String cifGrupoMusical) {
		// Se inicializa una lista
		List<String> listado = new ArrayList<String>();

		// Recorre la lista de contratos terminados
		for (Discografica d : listaDiscograficas.values()) {
			for (Contrato c : d.recuperarContratosTerminados()) {
				// Si el contrato tiene a este grupo musical
				if (c.tieneGrupoMusical(cifGrupoMusical)) {
					// Devuelve los detalles del contrato
					listado.add(c.verDetalles());
				}
			}
		}

		// Al terminar se devuelve el listado (que podría estar vacío)
		return listado;
	}

	/* (non-Javadoc)
	 * @see sgae.nucleo.discograficas.InterfazControladorDiscograficas#recuperarContratosAnterioresDeGrupoMusical(java.lang.String)
	 */
	@Override
	public List<Contrato> recuperarContratosAnterioresDeGrupoMusical(String cifGrupoMusical) {
		// Se inicializa una lista
		List<Contrato> listado = new ArrayList<Contrato>();

		// Recorre la lista de contratos terminados
		for (Discografica d : listaDiscograficas.values()) {
			for (Contrato c : d.recuperarContratosTerminados()) {
				// Si el contrato tiene a este grupo musical
				if (c.tieneGrupoMusical(cifGrupoMusical)) {
					// Devuelve los detalles del contrato
					listado.add(c);
				}
			}
		}

		// Al terminar se devuelve el listado (que podría estar vacío)
		return listado;
	}

	/* (non-Javadoc)
	 * @see sgae.nucleo.discograficas.InterfazControladorDiscograficas#verContratosVigentesDeDiscografica(java.lang.String)
	 */
	@Override
	public List<String> verContratosVigentesDeDiscografica(String cifDiscografica) 
			throws ExcepcionDiscograficas {
		// Se inicializa una lista
		List<String> listado = new ArrayList<String>();

		// Recorre la lista de contratos terminados
		Discografica d = listaDiscograficas.get(cifDiscografica);
		if (d == null) {
			throw new ExcepcionDiscograficas(cifDiscografica, "La discográfica indicada no existe");
		} else {
			for (Contrato c : d.recuperarContratosVigentes()) {
				listado.add(c.verDetalles());
			}
		}
		// Al terminar se devuelve el listado (que podría estar vacío)
		return listado;
	}
	
	/* (non-Javadoc)
	 * @see sgae.nucleo.discograficas.InterfazControladorDiscograficas#recuperarContratosVigentesDeDiscografica(java.lang.String)
	 */
	@Override
	public List<Contrato> recuperarContratosVigentesDeDiscografica(String cifDiscografica) 
			throws ExcepcionDiscograficas {
		// Se inicializa una lista
		List<Contrato> listado = new ArrayList<Contrato>();

		// Recorre la lista de contratos terminados
		Discografica d = listaDiscograficas.get(cifDiscografica);
		if (d == null) {
			throw new ExcepcionDiscograficas(cifDiscografica, "La discográfica indicada no existe");
		} else {
			for (Contrato c : d.recuperarContratosVigentes()) {	
				listado.add(c);
			}
		}
		// Al terminar se devuelve el listado (que podría estar vacío)
		return listado;
	}

	/* (non-Javadoc)
	 * @see sgae.nucleo.discograficas.InterfazControladorDiscograficas#verContratosAnterioresDeDiscografica(java.lang.String)
	 */
	@Override
	public List<String> verContratosAnterioresDeDiscografica(String cifDiscografica) 
			throws ExcepcionDiscograficas {
		// Se inicializa una lista
		List<String> listado = new ArrayList<String>();

		// Recorre la lista de contratos terminados
		Discografica d = listaDiscograficas.get(cifDiscografica);
		if (d == null) {
			throw new ExcepcionDiscograficas(cifDiscografica, "La discográfica indicada no existe");
		} else {
			for (Contrato c : d.recuperarContratosTerminados()) {
				listado.add(c.verDetalles());
			}
		}
		// Al terminar se devuelve el listado (que podría estar vacío)
		return listado;
	}
	
	/* (non-Javadoc)
	 * @see sgae.nucleo.discograficas.InterfazControladorDiscograficas#recuperarContratosAnterioresDeDiscografica(java.lang.String)
	 */
	@Override
	public List<Contrato> recuperarContratosAnterioresDeDiscografica(String cifDiscografica) 
			throws ExcepcionDiscograficas {
		// Se inicializa una lista
		List<Contrato> listado = new ArrayList<Contrato>();

		// Recorre la lista de contratos terminados
		Discografica d = listaDiscograficas.get(cifDiscografica);
		if (d == null) {
			throw new ExcepcionDiscograficas(cifDiscografica, "La discográfica indicada no existe");
		} else {
			for (Contrato c : d.recuperarContratosTerminados()) {	
				listado.add(c);
			}
		}
		// Al terminar se devuelve el listado (que podría estar vacío)
		return listado;
	}

}
