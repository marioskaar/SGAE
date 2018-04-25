package sgae.servidor.gruposMusicales;
import java.text.ParseException;

import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.ext.jaxb.JaxbRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.restlet.ext.jaxb.JaxbRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.restlet.data.Status;

import sgae.nucleo.gruposMusicales.ControladorGruposMusicales;
import sgae.nucleo.gruposMusicales.ExcepcionGruposMusicales;
import sgae.util.generated.GrupoMusical;
import sgae.util.generated.GrupoMusicalInfoBreve;
import sgae.util.generated.Link;
import sgae.servidor.aplicacion.SGAEServerApplication;
import sgae.util.generated.GruposMusicales;


public class GrupoMusicalServerResource extends ServerResource{
	SGAEServerApplication ref = (SGAEServerApplication)getApplication();
    ControladorGruposMusicales controladorGruposMusicales = ref.getControladorGruposMusicales();
    private String cif;
    
    //Tareas a realizar en la inicializacion estandar del recurso
  	@Override
  	protected void doInit() throws ResourceException{
  		cif = getAttribute("cif");
  		System.out.println("The grupo musical resource was initialized");
  	}

    //Tareas a realizar en la liberacion estandar del recurso
  	@Override
  	protected void doRelease() throws ResourceException{
  		System.out.println("The grupo musical resource was release");
  	}

    //Metodo GET en texto plano
  	@Get("txt")
  	public String represent() {
  		StringBuilder result = new StringBuilder();
  		
  		try{
  			result.append(controladorGruposMusicales.verGrupoMusical(this.cif));  			
  		}catch(ExcepcionGruposMusicales a){
  			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
  		}
  		
  		return result.toString();
  	}

    //Metodo GET en formato XML
  	@Get("xml")
  	public Representation toXml() {
  		
  		GrupoMusical grupoMusicalXML = new GrupoMusical();
  		try{
  			sgae.nucleo.gruposMusicales.GrupoMusical grupoMusical = controladorGruposMusicales.recuperarGrupoMusical(this.cif);
  			grupoMusicalXML.setCif(this.cif);
  			grupoMusicalXML.setNombre(grupoMusical.getNombre());
  			grupoMusicalXML.setFechaCreacion(grupoMusical.getFechaCreacion());
  			
  		
  		}catch(ExcepcionGruposMusicales a){
  			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
  		}
  		
  		JaxbRepresentation<	GrupoMusical> result = new JaxbRepresentation<GrupoMusical>(grupoMusicalXML);
  		result.setFormattedOutput(true);
  		return result;
  	}
    
//    @Put("form-data")
//    public String store(Representation data) {
//  		StringBuilder result = new StringBuilder();
//    	Form form = new Form(data);
//    	
//    	try{
//    		controladorPersonas.crearPersona(this.dni, form.getFirstValue("nombre"),
//    				form.getFirstValue("apellidos"), form.getFirstValue("fechaNacimiento"));
//    		result.append("Persona Creada\n"+ controladorPersonas.verPersona(this.dni));
//    		getResponse().setStatus(Status.SUCCESS_OK);
//    		return result.toString();
//    	}catch(ParseException a){
//    		throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
//    	}catch(ExcepcionPersonas a){
//    		throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
//    	}
//    }
}
