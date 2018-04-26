package sgae.servidor.albumes;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.velocity.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.representation.Variant;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import sgae.nucleo.gruposMusicales.ControladorGruposMusicales;
import sgae.nucleo.gruposMusicales.ExcepcionGruposMusicales;
import sgae.servidor.aplicacion.SGAEServerApplication;
import sgae.util.generated.AlbumInfoBreve;
import sgae.util.generated.Albumes;
import sgae.util.generated.Link;

public class AlbumesServerResource extends ServerResource {

	SGAEServerApplication ref = (SGAEServerApplication)getApplication();
	ControladorGruposMusicales controladorGruposMusicales = ref.getControladorGruposMusicales();
	private String cif;


	//Tareas a realizar en la inicializacion estandar del recurso
	//con negociacion de contenidos
	@Override
	protected void doInit()throws ResourceException{
		getVariants().add(new Variant (MediaType.TEXT_PLAIN));
		getVariants().add(new Variant (MediaType.TEXT_HTML));
		this.cif = getAttribute("cif");
	}

	//Tareas a realizar en la liberacion estandar del recurso
	@Override
	protected void doRelease() throws ResourceException{
		System.out.println("The grupos musicales resource was release");
	}

	@Override
	protected Representation get(Variant variant)throws ResourceException{
		Representation result = null;
		StringBuilder result2 = new StringBuilder();

		if(MediaType.TEXT_PLAIN.isCompatible(variant.getMediaType())){
			try{
				for (sgae.nucleo.gruposMusicales.Album album: controladorGruposMusicales.recuperarAlbumes(this.cif)){
					result2.append((album == null) ? "": "Titulo: "+album.getTitulo() + "\tUri relativa: " +album.getId()+"/").append('\n');
				}
			}catch(ExcepcionGruposMusicales e){
				throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
			}
			result = new StringRepresentation(result2.toString());
		}else if(MediaType.TEXT_HTML.isCompatible(variant.getMediaType())){

			Albumes albumesHTML = new Albumes();
			final List<AlbumInfoBreve> albumesInfoBreve = albumesHTML.getAlbumInfoBreve();
			//Preparamos el modelo de datos
			Map<String,Object> albumDataModel = new HashMap<String,Object>();
			try{
				for(sgae.nucleo.gruposMusicales.Album a:controladorGruposMusicales.recuperarAlbumes(this.cif)){
					AlbumInfoBreve albumInfo = new AlbumInfoBreve();
					albumInfo.setTitulo(a.getTitulo());
					Link link = new Link();
					link.setHref(a.getId()+"/");
					link.setTitle("Álbumes");
					link.setType("simple");
					albumInfo.setUri(link);
					albumDataModel.put("albumes",albumesHTML);
					//Cargamos la plantilla velocity
					Representation albumVtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())+"/album.vtl").get();
					result = new TemplateRepresentation(albumVtl,albumDataModel,MediaType.TEXT_HTML);
				}
			}catch(ExcepcionGruposMusicales e){
				throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
			} catch (IOException e) {
				throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
			}
		}
		return result;
	}


	/*
	@Post("form-data")
	public String store(){

	}*/
}