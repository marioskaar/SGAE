package sgae.servidor.gruposMusicales;
import java.text.ParseException;

import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.ext.jaxb.JaxbRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;
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

    //Metodo GET en texto plano
  	@Get("txt")
  	public String represent() {
  		StringBuilder result = new StringBuilder();
  		
  		try{
  			result.append(controladorGruposMusicales.verGrupoMusical(this.cif));
  			result.append("URI: albumes/\nURI: miembros/");
  		}catch(ExcepcionGruposMusicales a){
  			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
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
  			Link link1 = new Link();
  			Link link2 = new Link();
			link1.setHref("miembros/");
			link1.setTitle("Miembros");
			link1.setType("simple");
			link2.setHref("albumes/");
			link2.setTitle("Albumes");
			link2.setType("simple");

  			grupoMusicalXML.setUri1(link1);
  			grupoMusicalXML.setUri2(link2);
  		
  		}catch(ExcepcionGruposMusicales a){
  			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
  		}
  		
  		JaxbRepresentation<GrupoMusical> result = new JaxbRepresentation<GrupoMusical>(grupoMusicalXML);
  		result.setFormattedOutput(true);
  		return result;
  	}
    
    @Put("form-data")
    public String store(Representation data) {
  		StringBuilder result = new StringBuilder();
    	Form form = new Form(data);

    	try{
			controladorGruposMusicales.crearGrupoMusical(this.cif,form.getFirstValue("nombre"),
					form.getFirstValue("fechaCreacion"));
    		result.append("Grupo Musical Creado\n"+ controladorGruposMusicales.verGrupoMusical(this.cif));
    		getResponse().setStatus(Status.SUCCESS_OK);
    	}catch(ParseException a){
    		throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
    	}catch(ExcepcionGruposMusicales a){
    		try{
				controladorGruposMusicales.modificarGrupoMusical(this.cif,form.getFirstValue("nombre"),
						form.getFirstValue("fechaCreacion"));
				result.append("Grupo Musical Modificado\n"+ controladorGruposMusicales.verGrupoMusical(this.cif));
			}catch (ParseException aa){
				throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
			}catch (ExcepcionGruposMusicales aa){
				throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
			}
    	}
    	return result.toString();
    }

    @Delete
	public String remove(){
  		String result;
  		try{
  			controladorGruposMusicales.borrarGrupoMusical(this.cif);
  			result = "Se ha borrado el grupo musical con CIF: "+this.cif;
		}catch (ExcepcionGruposMusicales a){
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
		}
  		return result;
	}
}
