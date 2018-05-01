package sgae.servidor.personas;
import java.util.List;

import org.restlet.data.Status;
import org.restlet.ext.jaxb.JaxbRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import sgae.nucleo.personas.ControladorPersonas;
import sgae.util.generated.Link;
import sgae.servidor.aplicacion.SGAEServerApplication;
import sgae.util.generated.PersonaInfoBreve;
import sgae.util.generated.Personas;
/**
 * Clase que recoge las características del recurso Personas y 
 * los métodos para consultar dichas características en formato texto plano y en XML.
 * @author Mario Calle Martín y Raquel Pérez García.Máster en Ingeniería de Telecomunicaciones.
 * @version 1.0
 *
 */

public class PersonasServerResource extends ServerResource{
	//Obtenemos la referencia de la aplicacion
	private SGAEServerApplication ref = (SGAEServerApplication)getApplication();
	/**
	 * ControladorPersonas referencia al objeto de la clase SGAEServerApplication.
	 */
	//Objeto de la clase ControladorPersonas que hace referencia al instanciado en la clase SGAEServerApplication
	private ControladorPersonas controladorPersonas = ref.getControladorPersonas();
	/**
	 * Método que realiza una operación GET sobre el recurso Personas en formato texto plano.
	 *
	 * @return cadena de texto con la representación del recurso Personas en texto plano.
	 */
	//Metodo GET en texto plano
	@Get("txt")
	public String represent() {
		StringBuilder result = new StringBuilder();

		//Si no hay ninguna persona registrada en la aplicacion devolvemos el estado
		//No Content en la respuesta
		if(controladorPersonas.recuperarPersonas().size()==0){
			getResponse().setStatus(Status.SUCCESS_NO_CONTENT,"No hay personas registradas");
		}else {
			//generacion del documento en texto plano con el listado de las personas
			for (sgae.nucleo.personas.Persona p : controladorPersonas.recuperarPersonas()) {
				result.append("DNI: "+p.getDni()+" Nombre: "+p.getNombre()+" Apellidos: "
						+p.getApellidos()+ " URI: personas/"+p.getDni()+"\n");
			}
		}
		return result.toString();
	}
	/**
	 * Método que realiza una operación GET sobre el recurso Personas en formato XML utilizando la API JAXB.
	 *
	 * @return representación del recurso Personas en formato XML.
	 */

	//Metodo GET en formato XML
	@Get("xml")
	public Representation toXml() throws ResourceException{

		//objeto de la clase Personas sgae.util.generated
		Personas personasXML = new Personas();
		//obtener la lista de sgae.util.generated.Personas
		final List<PersonaInfoBreve> personaInfoBreve = personasXML.getPersonaInfoBreve();

		//Si no hay personas, se devuelve el estado 204
		if(controladorPersonas.recuperarPersonas().size()==0){
			getResponse().setStatus(Status.SUCCESS_NO_CONTENT,"No hay personas registradas");
		}else {
			for (sgae.nucleo.personas.Persona p : controladorPersonas.recuperarPersonas()) {
				//Para cada persona se genera un objeto de la clase auxiliar de representacion en xml
				PersonaInfoBreve personaXML = new PersonaInfoBreve();
				personaXML.setDni(p.getDni());
				personaXML.setApellidos(p.getApellidos());
				personaXML.setNombre(p.getNombre());
				//Se usa la clase Link para fijar una uri
				Link link = new Link();
				link.setHref("personas/" + p.getDni());
				link.setTitle("Persona");
				link.setType("simple");
				personaXML.setUri(link);
				//Se aniade la persona en la lista
				personaInfoBreve.add(personaXML);
			}
		}
		//Se genera el documento xml que se envia como respuesta
		JaxbRepresentation<Personas> result = new JaxbRepresentation<Personas>(personasXML);
		result.setFormattedOutput(true);
		return result;
	}
}
