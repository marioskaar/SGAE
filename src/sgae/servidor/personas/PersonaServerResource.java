package sgae.servidor.personas;

import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.ext.jaxb.JaxbRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.Delete;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import sgae.nucleo.personas.ControladorPersonas;
import sgae.nucleo.personas.ExcepcionPersonas;
import sgae.servidor.aplicacion.SGAEServerApplication;


import sgae.util.generated.Persona;
import java.text.ParseException;

public class PersonaServerResource extends ServerResource {
    //Obtenemos la referencia de la aplicacion
	private SGAEServerApplication ref = (SGAEServerApplication) getApplication();
    //Objeto de la clase ControladorPersonas que hace referencia al instanciado en la clase SGAEServerApplication
	private ControladorPersonas controladorPersonas = ref.getControladorPersonas();
	//Dni de la persona
	private String dni;

	//Tareas a realizar en la inicializacion estandar del recurso
    // se obtiene el dni introducido
	@Override
	protected void doInit() throws ResourceException {
		dni = getAttribute("dni");
	}

	//Metodo GET en texto plano
	@Get("txt")
	public String represent() {
		StringBuilder result = new StringBuilder();

		try {
		    //Se adjunta toda la informacion de una personas
			result.append(controladorPersonas.verPersona(this.dni));
		} catch (ExcepcionPersonas a) {
		    //Si no hay una persona registrada con el dni introducido se lanza la excepcion y se devuelve
            // el codigo de error 404
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,a.getCausaFallo());
		}
		return result.toString();
	}

	//Metodo GET en formato XML
	@Get("xml")
	public Representation toXml() {

	    //Objeto de la clase sgae.util.generated.Persona
		Persona personaXML = new Persona();
		try {
			sgae.nucleo.personas.Persona persona = controladorPersonas.recuperarPersona(this.dni);
			personaXML.setDni(this.dni);
			personaXML.setNombre(persona.getNombre());
			personaXML.setApellidos(persona.getApellidos());
			personaXML.setFechaNacimiento(persona.getFechaNacimiento());

		} catch (ExcepcionPersonas a) {
		    //Si no existe ninguna persona con el dni introducido salta la excepcion
            // y se devuelve el codigo de error 404
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,a.getCausaFallo());
		}

		//Generacion del documento xml usando la clase auxiliar
		JaxbRepresentation<Persona> result = new JaxbRepresentation<Persona>(personaXML);
		result.setFormattedOutput(true);
		return result;
	}

	//Metodo put para registrar personas o modificarlas
	@Put("form-data")
	public String store(Representation data) {
		StringBuilder result = new StringBuilder();
		//Obtener los datos de entrada
		Form form = new Form(data);

		//Si no se ha introducido el nombre o apellidos o fecha se envia status BAD_REQUEST
        if(form.getFirstValue("nombre") == null || form.getFirstValue("apellidos") == null
        || form.getFirstValue("fechaNacimiento") == null){
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,"Datos incorrectos o no suficientes");
        }
		try {
			controladorPersonas.crearPersona(this.dni, form.getFirstValue("nombre"),
					form.getFirstValue("apellidos"), form.getFirstValue("fechaNacimiento"));
			result.append("Persona Creada\n" + controladorPersonas.verPersona(this.dni));
			//Si se ha creado correctamente la persona, se devuelve el status 201
			getResponse().setStatus(Status.SUCCESS_CREATED,"Se ha creado la persona con DNI "+this.dni);
		} catch (ParseException a) {
            //Se introduce la fecha en formato incorrecto
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,"Fecha introducida no valida. dd-mm-aaaa");
		} catch (ExcepcionPersonas a) {
            //Si la persona existe al intentar crearla se modifica
			try {
				controladorPersonas.modificarPersona(this.dni, form.getFirstValue("nombre"),
						form.getFirstValue("apellidos"), form.getFirstValue("fechaNacimiento"));
				result.append("Persona Modificada\n" + controladorPersonas.verPersona(this.dni));
                //Si se ha modificado correctamente la persona, se devuelve el status 200
                getResponse().setStatus(Status.SUCCESS_OK,"Se ha modificado la persona con DNI "+this.dni);
			} catch (ParseException aa) {
			    //Se introduce la fecha en formato incorrecto
				throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,"Fecha introducida no valida. dd-mm-aaaa");
			} catch (ExcepcionPersonas aa) {
			    //En teoria nunca entraria en este bloque, ya que si existe dni entra en la excepcion anterior similar
                // a esta y no podria entrar en esta
				throw new ResourceException(Status.SERVER_ERROR_INTERNAL,"Error interno");
			}
		}
		return result.toString();
	}

	//Metodo delete para eliminar una persona
	@Delete
	public String remove(){
		StringBuilder result = new StringBuilder();
		try{
		    //Se elimina la persona con el dni
			controladorPersonas.borrarPersona(this.dni);
			result.append("Se ha borrado la persona con DNI: "+this.dni);
            //Si se ha eliminado correctamente se devuleve el status 204
            getResponse().setStatus(Status.SUCCESS_NO_CONTENT,"Se ha eliminado la persona con DNI "+this.dni);
		}catch (ExcepcionPersonas a){
		    //Si el dni no coincide con ninguna persona se envia 404
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,a.getCausaFallo());
		}
		return result.toString();
	}
}