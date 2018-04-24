package sgae.servidor.personas;
import java.util.List;

import org.restlet.data.Status;
import org.restlet.ext.jaxb.JaxbRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import sgae.nucleo.personas.ControladorPersonas;
import sgae.util.generated.Link;
import sgae.servidor.aplicacion.SGAEServerApplication;
import sgae.util.generated.PersonaInfoBreve;
import sgae.util.generated.Personas;

public class PersonasServerResource extends ServerResource{
    //Obtenemos la referencia de la aplicacion
	SGAEServerApplication ref = (SGAEServerApplication)getApplication();
    //Objeto de la clase ControladorPersonas que hace referencia al instanciado en la clase SGAEServerApplication
	ControladorPersonas controladorPersonas = ref.getControladorPersonas();

    //Tareas a realizar en la inicialización estándar del recurso
	@Override
	protected void doInit() throws ResourceException{
		System.out.println("The personas resource was initialized");
	}

    //Tareas en la gestión estándar del recurso
	@Override
	protected void doCatch(Throwable throwable){
		System.out.println("An exception was thrown in the personas resource");
	}

    //Tareas a realizar en la liberación estándar del recurso
	@Override
	protected void doRelease() throws ResourceException{
		System.out.println("The personas resource was release");
	}

    //Método GET en texto plano
	@Get("txt")
	public String represent() {
		StringBuilder result = new StringBuilder();

        //Si no hay ninguna persona registrada en la aplicacion devolvemos el estado
        //No Content en la respuesta
        if(controladorPersonas.listarPersonas().size()==0) {
            getResponse().setStatus(Status.SUCCESS_NO_CONTENT);
        }else{
            //Para cada persona de la lista, devolvemos su infobreve
		    for (String persona: controladorPersonas.listarPersonas()){
		        result.append((persona == null) ? "": persona).append('\n');
		    }
        }
		return result.toString();
	}

    //Método GET en formato XML
	@Get("xml")
	public JaxbRepresentation toXml() {

	    //objeto de la clase Personas sgae.util.generated
		Personas personasXML = new Personas();
		//obtener la lista de sgae.util.generated
        final List<PersonaInfoBreve> personaInfoBreve = personasXML.getPersonaInfoBreve();

        //Si no hay personas, se devuelve el estado 204
        if(controladorPersonas.recuperarPersonas().size()==0){
            getResponse().setStatus(Status.SUCCESS_NO_CONTENT);
        }else {
            for (sgae.nucleo.personas.Persona p : controladorPersonas.recuperarPersonas()) {
                PersonaInfoBreve personaXML = new PersonaInfoBreve();
                personaXML.setDni(p.getDni());
                personaXML.setApellidos(p.getApellidos());
                personaXML.setNombre(p.getNombre());
                Link link = new Link();
                link.setHref("personas/" + p.getDni());
                link.setTitle("Persona");
                link.setType("simple");
                personaXML.setUri(link);
                personaInfoBreve.add(personaXML);
            }
        }
        JaxbRepresentation<Personas> result = new JaxbRepresentation<Personas>(personasXML);
		result.setFormattedOutput(true);
		return result;
	}
}
