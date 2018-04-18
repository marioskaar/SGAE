package sgae.servidor.albumes;

import java.util.HashMap;
import java.util.Map;

import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.restlet.data.MediaType;

import sgae.nucleo.gruposMusicales.ControladorGruposMusicales;
import sgae.servidor.aplicacion.SGAEServerApplication;
import sgae.util.generated.Albumes;


import sgae.nucleo.gruposMusicales.ControladorGruposMusicales;
import sgae.servidor.aplicacion.SGAEServerApplication;
import sgae.util.generated.Albumes;
//Para actualizar en local: 
public class AlbumesServerResource extends ServerResource {
//	
//	SGAEServerApplication ref = (SGAEServerApplication)getApplication();
//	ControladorGruposMusicales controladorGruposMusicales = ref.getControladorGruposMusicales();
//	
////	//Tareas a realizar en la inicializaci�n est�ndar del recurso
////	//con negociaci�n de contenidos
//	@Override
//	protected void doInit()throws ResourceException{
//	getVariants().add(new Variant (MediaType.TEXT_PLAIN));
//	getVariants().add(new Variant (MediaType.TEXT_HTML));
//		
//	}
//	//Tareas en la gesti�n est�ndar del recurso
//	@Override
//	protected void doCatch(Throwable throwable){
//		System.out.println("An exception was thrown in the grupos musicales resource");
//     }
//	//Tareas a realizar en la liberaci�n est�ndar del recurso
//	@Override
////	protected void doRelease() throws ResourceException{
////		System.out.println("The grupos musicales resource was release");
////	}
////	
////	@Override
//	protected Representation get(Variant variant)throws ResourceException{
////		Representation result = null;
////		Albumes album = new Albumes();
////		
////		if(MediaType.TEXT_PLAIN.isCompatible(variant.getMediaType())){
////			for (String albumes: controladorGruposMusicales.listarAlbumes()){
////				result.append((album == null) ? "": album).append('\n');
////			}
////			result = result.toString();
////		}
////		else if(MediaType.TEXT_HTML.isCompatible(variant.getMediaType())){
////			
////			Map<String,Object>dataModel=new HashMap<String,Object>();
////			dataModel.put("album",album);
////			Representation albumVtl=new ClientResource(LocalReference.createClapReference(getClass().getPackage())+"/album.vtl").get();
////			result= new TemplateRepresentation(albumVtl,dataModel,MediaType.TEXT_HTML);
////			return result;
////		}
////	}
////	
//
}
