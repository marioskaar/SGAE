package sgae.servidor.personas;

import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.ext.jaxb.JaxbRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import sgae.nucleo.personas.ControladorPersonas;
import sgae.nucleo.personas.ExcepcionPersonas;
import sgae.servidor.aplicacion.SGAEServerApplication;


import sgae.util.generated.Persona;
import java.text.ParseException;

public class PersonaServerResource extends ServerResource {
	SGAEServerApplication ref = (SGAEServerApplication) getApplication();
	ControladorPersonas controladorPersonas = ref.getControladorPersonas();
	private String dni;

	//Tareas a realizar en la inicializacion estandar del recurso
	@Override
	protected void doInit() throws ResourceException {
		dni = getAttribute("dni");
		System.out.println("The persona resource was initialized");
	}

	//Tareas a realizar en la liberacion estandar del recurso
	@Override
	protected void doRelease() throws ResourceException {
		System.out.println("The persona resource was release");
	}

	//Metodo GET en texto plano
	@Get("txt")
	public String represent() {
		StringBuilder result = new StringBuilder();

		try {
			result.append(controladorPersonas.verPersona(this.dni));
		} catch (ExcepcionPersonas a) {
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		}

		return result.toString();
	}

	//Metodo GET en formato XML
	@Get("xml")
	public Representation toXml() {

		Persona personaXML = new Persona();
		try {
			sgae.nucleo.personas.Persona persona = controladorPersonas.recuperarPersona(this.dni);
			personaXML.setDni(this.dni);
			personaXML.setNombre(persona.getNombre());
			personaXML.setApellidos(persona.getApellidos());
			personaXML.setFechaNacimiento(persona.getFechaNacimiento());

		} catch (ExcepcionPersonas a) {
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		}

		JaxbRepresentation<Persona> result = new JaxbRepresentation<Persona>(personaXML);
		result.setFormattedOutput(true);
		return result;
	}

	@Put("form-data")
	public String store(Representation data) {
		StringBuilder result = new StringBuilder();
		Form form = new Form(data);

		try {
			controladorPersonas.crearPersona(this.dni, form.getFirstValue("nombre"),
					form.getFirstValue("apellidos"), form.getFirstValue("fechaNacimiento"));
			result.append("Persona Creada\n" + controladorPersonas.verPersona(this.dni));
			getResponse().setStatus(Status.SUCCESS_OK);
		} catch (ParseException a) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
		} catch (ExcepcionPersonas a) {
			try {
				controladorPersonas.modificarPersona(this.dni, form.getFirstValue("nombre"),
						form.getFirstValue("apellidos"), form.getFirstValue("fechaNacimiento"));
				result.append("Persona Modificada\n" + controladorPersonas.verPersona(this.dni));
			} catch (ParseException aa) {
				throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
			} catch (ExcepcionPersonas aa) {
				throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
			}
		}
		return result.toString();
	}

	@Delete
	public String remove(){
		StringBuilder result = new StringBuilder();
		try{
			controladorPersonas.borrarPersona(this.dni);
			result.append("Se ha borrado la persona con DNI: "+this.dni);
		}catch (ExcepcionPersonas a){
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		}
		return result.toString();
	}
}