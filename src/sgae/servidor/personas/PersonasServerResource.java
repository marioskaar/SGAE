package sgae.servidor.personas;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.restlet.ext.jaxb.JaxbRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import sgae.nucleo.personas.ControladorPersonas;
import sgae.util.generated.Link;
import sgae.util.generated.Persona;
import sgae.servidor.aplicacion.SGAEServerApplication;
import sgae.util.generated.PersonaInfoBreve;
import sgae.util.generated.Personas;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.util.JAXBResult;

public class PersonasServerResource extends ServerResource{

	SGAEServerApplication ref = (SGAEServerApplication)getApplication();
	ControladorPersonas controladorPersonas = ref.getControladorPersonas();

	@Override
	protected void doInit() throws ResourceException{
		System.out.println("The personas resource was initialized");
	}
	@Override
	protected void doCatch(Throwable throwable){
		System.out.println("An exception was thrown in the personas resource");
	}
	@Override
	protected void doRelease() throws ResourceException{
		System.out.println("The personas resource was release");
	}
	
	@Get("txt")
	public String represent() {
		StringBuilder result = new StringBuilder();
		for (String persona: controladorPersonas.listarPersonas()){
			result.append((persona == null) ? "": persona).append('\n');
		}
		return result.toString();
	}
	@Get("xml")
	public JaxbRepresentation toXml() {

		Personas personasXML = new Personas();
        final List<PersonaInfoBreve> personaInfoBreve = personasXML.getPersonaInfoBreve();

        for (sgae.nucleo.personas.Persona p : controladorPersonas.recuperarPersonas()){
            PersonaInfoBreve personaXML = new PersonaInfoBreve();
			personaXML.setDni(p.getDni());
			personaXML.setApellidos(p.getApellidos());
			personaXML.setNombre(p.getNombre());
            Link link = new Link();
            link.setHref("/personas/"+p.getDni());
			link.setTitle("Persona");
			link.setType("simple");
			personaXML.setUri(link);
			personaInfoBreve.add(personaXML);
		}
        JaxbRepresentation<Personas> result = new JaxbRepresentation<Personas>(personasXML);
		result.setFormattedOutput(true);
		return result;
	}
}
