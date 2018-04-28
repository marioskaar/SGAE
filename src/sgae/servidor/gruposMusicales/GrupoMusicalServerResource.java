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

import sgae.nucleo.gruposMusicales.ControladorGruposMusicales;
import sgae.nucleo.gruposMusicales.ExcepcionGruposMusicales;
import sgae.nucleo.personas.ExcepcionPersonas;
import sgae.nucleo.personas.Persona;
import sgae.util.generated.GrupoMusical;
import sgae.util.generated.Link;
import sgae.servidor.aplicacion.SGAEServerApplication;


public class GrupoMusicalServerResource extends ServerResource{
	private SGAEServerApplication ref = (SGAEServerApplication)getApplication();
	private ControladorGruposMusicales controladorGruposMusicales = ref.getControladorGruposMusicales();
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
				}catch (ExcepcionPersonas excepcionPersonas) {
					throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
				}
			}
    		getResponse().setStatus(Status.SUCCESS_OK);
    	}catch(ParseException a){
    		throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
    	}catch(ExcepcionGruposMusicales a){
    		try{
				//Si el grupo esta creado y hay dnis como parametros, se comparan los dnis con los miembros actuales
				//los que coincidan no se modifican y los que no aparezcan se paran a la lista de miembros anteriores

				//Solamente se modifica el grupo musical los campos de nombre y fechaCreacion existen
				if(form.getFirstValue("nombre")!=null && form.getFirstValue("fechaCreacion") != null) {
					controladorGruposMusicales.modificarGrupoMusical(this.cif, form.getFirstValue("nombre"),
							form.getFirstValue("fechaCreacion"));
					result.append("Grupo Musical Modificado\n" + controladorGruposMusicales.verGrupoMusical(this.cif));
				}
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
					}catch (ExcepcionPersonas excepcionPersonas) {
					throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
					}
				}
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
