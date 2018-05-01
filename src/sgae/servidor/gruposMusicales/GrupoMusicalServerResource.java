package sgae.servidor.gruposMusicales;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.ext.jaxb.JaxbRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import sgae.nucleo.gruposMusicales.Album;
import sgae.nucleo.gruposMusicales.ControladorGruposMusicales;
import sgae.nucleo.gruposMusicales.ExcepcionGruposMusicales;
import sgae.nucleo.personas.ExcepcionPersonas;
import sgae.nucleo.personas.Persona;
import sgae.util.generated.GrupoMusical;
import sgae.util.generated.Link;
import sgae.servidor.aplicacion.SGAEServerApplication;
/**
 * Clase que recoge las características del recurso Grupo Musical y 
 * los métodos para consultar dichas características en formato texto plano y en XML y 
 * para la creación o modificación en formulario HTML.
 * @author Mario Calle Martín y Raquel Pérez García.Máster en Ingeniería de Telecomunicaciones.
 * @version 1.0
 *
 */

public class GrupoMusicalServerResource extends ServerResource{
	//Obtenemos la referencia de la aplicacion
	private SGAEServerApplication ref = (SGAEServerApplication)getApplication();
	/**
	 * ControladorGruposMusicales referencia al objeto de la clase SGAEServerApplication.
	 */
	//Objeto de la clase ControladorGruposMusicales que hace referencia al instanciado en la clase SGAEServerApplication
	private ControladorGruposMusicales controladorGruposMusicales = ref.getControladorGruposMusicales();
	/**
	 * CIF del grupo musical.
	 */
	//cif del grupo musical
	private String cif;
	/**
	 * Método que realiza la inicialización estándar del recurso Grupo Musical.
	 * Se obtiene el cif del grupo musical introducido.
	 *
	 */

	//Tareas a realizar en la inicializacion estandar del recurso
	// obtener el cif del grupo
	@Override
	protected void doInit() throws ResourceException{
		cif = getAttribute("cif");
	}
	/**
	 * Método que realiza una operación GET sobre el recurso Grupo Musical en formato texto plano.
	 *
	 * @return una cadena de texto con la representación del recurso grupo musical en texto plano.
	 * @throws ResourceException 404 NOT FOUND si no existe un grupo musical registrado con el cif introducido.
	 */

	//Metodo GET en texto plano
	@Get("txt")
	public String represent() {
		StringBuilder result = new StringBuilder();

		try{
			//Dar la informacion del grupo musical
			result.append(controladorGruposMusicales.verGrupoMusical(this.cif));
			int eV = 0;
			for(Album a : controladorGruposMusicales.recuperarAlbumes(this.cif))
				eV=eV + a.getEjemplaresVendidos();
			result.append(eV);
			result.append("URI: albumes/\nURI: miembros");
		}catch(ExcepcionGruposMusicales a){
			//Si no existe el cif del grupo
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,a.getCausaFallo());
		}

		return result.toString();
	}
	/**
	 * Método que realiza una operación GET sobre el recurso Grupo Musical en formato XML utilizando la API JAXB..
	 *
	 * @return representación del recurso grupo musical en XML.
	 * @throws ResourceException 404 NOT FOUND si no existe un grupo musical registrado con el cif introducido.
	 */

	//Metodo GET en formato XML
	@Get("xml")
	public Representation toXml() {

		//Clase auxiliar de sgae.util.generated
		GrupoMusical grupoMusicalXML = new GrupoMusical();
		try{
			//Rellenar la informacion para crear el XML
			sgae.nucleo.gruposMusicales.GrupoMusical grupoMusical = controladorGruposMusicales.recuperarGrupoMusical(this.cif);
			grupoMusicalXML.setCif(this.cif);
			grupoMusicalXML.setNombre(grupoMusical.getNombre());
			grupoMusicalXML.setFechaCreacion(grupoMusical.getFechaCreacion());
			int eV = 0;
			for(Album a : controladorGruposMusicales.recuperarAlbumes(this.cif))
				eV=eV+a.getEjemplaresVendidos();
			grupoMusicalXML.setEjemplaresVendidos(Integer.toString(eV));
			Link link1 = new Link();
			Link link2 = new Link();
			link1.setHref("miembros");
			link1.setTitle("Miembros");
			link1.setType("simple");
			link2.setHref("albumes/");
			link2.setTitle("Albumes");
			link2.setType("simple");

			grupoMusicalXML.setUri1(link1);
			grupoMusicalXML.setUri2(link2);

		}catch(ExcepcionGruposMusicales a){
			//Si no existe el cif del grupo
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,a.getCausaFallo());
		}
		//Creacion del documento XML
		JaxbRepresentation<GrupoMusical> result = new JaxbRepresentation<GrupoMusical>(grupoMusicalXML);
		result.setFormattedOutput(true);
		return result;
	}
	/**
	 * Método que realiza una operación PUT sobre el recurso Grupo Musical en formato formulario HTML.
	 *
	 * @param data datos de entrada de un grupo musical.
	 * @return una cadena de texto con el recurso grupo musical que ha sido creado o modificado.
	 * @throws ResourceException 201 CREATED si el grupo musical se ha creado con éxito,
	 * 200 OK si el grupo musical se ha modificado con éxito,
	 * 400 BAD REQUEST si la fecha de creación introducida no se encuentra en el formato correcto,
	 * 404 NOT FOUND si el grupo musical no existe al intentar modificarlo
	 * o al añadir un miembro a un grupo musical y el dni introducido no
	 * esta registrado en el sistema.
	 */
	//Metodo put para la creacion de un grupo musical, su modificacion y la adiccion de miembros
	@Put("form-data")
	public String store(Representation data) {
		StringBuilder result = new StringBuilder();
		//Obtener los datos de entrada
		Form form = new Form(data);
		//Obtencion de los dnis si los hubiera
		String dnis[] = form.getValuesArray("dni");

		try{
			controladorGruposMusicales.crearGrupoMusical(this.cif,form.getFirstValue("nombre"),
					form.getFirstValue("fechaCreacion"));
			result.append("Grupo Musical Creado\n"+ controladorGruposMusicales.verGrupoMusical(this.cif));
			//Si se ha introducido algun dni al crear el grupo, se aniade esa persana como miembro
			if(dnis.length > 0){
				try {
					for (String dni : dnis) {
						controladorGruposMusicales.anadirMiembro(this.cif, dni);
						result.append("Miembro incluido: "+dni+"\n");
					}
				}catch (ExcepcionPersonas a) {
					//No se encuentra la persona con ese dni
					throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,a.getCausaFallo());
				}
			}
			//Si se ha creado el grupo se devuelve status 201
			getResponse().setStatus(Status.SUCCESS_CREATED,"Se ha creado el grupo con CIF "+this.cif);
		}catch(ParseException a){
			//Fecha mal introducida
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,"Fecha introducida no valida. dd-mm-aaaa");
		}catch(ExcepcionGruposMusicales a){
			//Si ya existe el grupo se modifica
			try{
				//Si el grupo esta creado y hay dnis como parametros, se comparan los dnis con los miembros actuales
				//los que coincidan no se modifican y los que no aparezcan se paran a la lista de miembros anteriores

				//Solamente se modifica el grupo musical los campos de nombre y fechaCreacion existen
				if(form.getFirstValue("nombre")!=null && form.getFirstValue("fechaCreacion") != null) {
					controladorGruposMusicales.modificarGrupoMusical(this.cif, form.getFirstValue("nombre"),
							form.getFirstValue("fechaCreacion"));
					result.append("Grupo Musical Modificado\n" + controladorGruposMusicales.verGrupoMusical(this.cif));
				}
				//Si se han introducido dnis
				if(dnis.length > 0) {
					try {
						List<Persona> miembros = controladorGruposMusicales.recuperarMiembros(this.cif);
						List<String> miembrosStringList = new ArrayList<>();
						List<String> dnisList = new ArrayList<>(Arrays.asList(dnis));
						for (Persona p : miembros){
							miembrosStringList.add(p.getDni());
							//En el caso de que hay un miembro del grupo que no aparezca en los parametros, se elimina del grupo
							if (dnisList.contains(p.getDni())==false){
								controladorGruposMusicales.eliminarMiembro(this.cif,p.getDni());
								result.append("Miembro eliminado: "+p.getDni()+"\n");
							}
						}
						for (String dni : dnis) {
							//Si un dni de parametro aparece en la lista no se modifica
							if (miembrosStringList.contains(dni)) {
								result.append("Miembro no modificado: "+dni+"\n");
							}
							//pero si no aparece en la lista es que es nuevo
							else{
								controladorGruposMusicales.anadirMiembro(this.cif, dni);
								result.append("Miembro incluido: "+dni+"\n");
							}
						}
					}catch (ExcepcionPersonas aa) {
						//No existe la persona con el dni
						throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,aa.getCausaFallo());
					}
				}
				//Si se ha modificado correctamente el grupo, se devuelve el status 200
				getResponse().setStatus(Status.SUCCESS_OK,"Se ha modificado el grupo con CIF "+this.cif);
			}catch (ParseException aa){
				//Fecha de creacion mal introducida al modificar grupo
				throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,"Fecha introducida no valida. dd-mm-aaaa");
			}catch (ExcepcionGruposMusicales aa){
				//En teoria nunca entraria en este bloque
				throw new ResourceException(Status.SERVER_ERROR_INTERNAL,"Error interno");
			}
		}
		return result.toString();
	}
	/**
	 * Método que realiza una operación DELETE sobre el recurso Grupo Musical.
	 *
	 * @return cadena de texto que nos indica el grupo musical que ha sido eliminado del sistema.
	 * @throws ResourceException 404 NOT FOUND si el cif no coincide con ningún grupo musical del sistema.
	 */

	//Metodo para borrar un grupo musical
	@Delete
	public String remove(){
		String result;
		try{
			//Se elimina el grupo
			controladorGruposMusicales.borrarGrupoMusical(this.cif);
			result = "Se ha borrado el grupo musical con CIF: "+this.cif;
			//Si se ha eliminado correctamente se devuleve el status 204
			getResponse().setStatus(Status.SUCCESS_NO_CONTENT,"Se ha eliminado el grupo con CIF "+this.cif);
		}catch (ExcepcionGruposMusicales a){
			//Si no existe
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,a.getCausaFallo());
		}
		return result;
	}
}
