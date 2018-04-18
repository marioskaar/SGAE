package sgae.servidor.gruposMusicales;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.restlet.ext.jaxb.JaxbRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import org.restlet.data.Status;
import sgae.nucleo.gruposMusicales.ControladorGruposMusicales;
import sgae.util.generated.GrupoMusicalInfoBreve;
import sgae.util.generated.Link;
import sgae.util.generated.GrupoMusical;
import sgae.servidor.aplicacion.SGAEServerApplication;
import sgae.util.generated.GruposMusicales;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.util.JAXBResult;

public class GruposMusicalesServerResource extends ServerResource{
	
	SGAEServerApplication ref = (SGAEServerApplication)getApplication();
	ControladorGruposMusicales controladorGruposMusicales= ref.getControladorGruposMusicales();

	//Tareas a realizar en la inicialización estándar del recurso
	@Override
	protected void doInit() throws ResourceException{
		System.out.println("The grupos musicales resource was initialized");
	}
	//Tareas en la gestión estándar del recurso
	@Override
	protected void doCatch(Throwable throwable){
		System.out.println("An exception was thrown in the grupos musicales resource");
	}
	//Tareas a realizar en la liberación estándar del recurso
	@Override
	protected void doRelease() throws ResourceException{
		System.out.println("The grupos musicales resource was release");
	}
	//Método GET en texto plano
	@Get("txt")
	//Obtener una representación de la lista de personas del sistema
	public String represent() {
		StringBuilder result = new StringBuilder();

		if(controladorGruposMusicales.listarGruposMusicales().size()==0) {
			getResponse().setStatus(Status.SUCCESS_NO_CONTENT);
		}
		for (String grupoMusical: controladorGruposMusicales.listarGruposMusicales()){
			result.append((grupoMusical == null) ? "": grupoMusical).append('\n');
		}
		return result.toString();
	}
	//Método GET en formato XML
	@Get("xml")
	public JaxbRepresentation toXml() {

		GruposMusicales gmsXML = new GruposMusicales();
        final List<GrupoMusicalInfoBreve> grupoMusicalInfoBreve = gmsXML.getGrupoMusicalInfoBreve();

        for (sgae.nucleo.gruposMusicales.GrupoMusical gm : controladorGruposMusicales.recuperarGruposMusicales()){
        	GrupoMusicalInfoBreve gmXML = new GrupoMusicalInfoBreve();
            gmXML.setCif(gm.getCif());
			gmXML.setNombre(gm.getNombre());
			gmXML.setFechaCreacion(gm.getFechaCreacion());
            Link link = new Link();
            link.setHref("/grupoMusical/"+gm.getCif());
			link.setTitle("GrupoMusical");
			link.setType("simple");
			gmXML.setUri(link);
			grupoMusicalInfoBreve.add(gmXML);
		}
        JaxbRepresentation<GruposMusicales> result = new JaxbRepresentation<GruposMusicales>(gmsXML);
		result.setFormattedOutput(true);
		return result;
	}
	
}

