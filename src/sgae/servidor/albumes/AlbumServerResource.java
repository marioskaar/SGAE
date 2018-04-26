package sgae.servidor.albumes;
import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.velocity.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.representation.Variant;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import sgae.nucleo.gruposMusicales.ControladorGruposMusicales;
import sgae.nucleo.gruposMusicales.ExcepcionAlbumes;
import sgae.nucleo.gruposMusicales.ExcepcionGruposMusicales;
import sgae.servidor.aplicacion.SGAEServerApplication;
import sgae.util.generated.Album;
import sgae.util.generated.Link;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AlbumServerResource extends ServerResource{
    SGAEServerApplication ref = (SGAEServerApplication)getApplication();
    ControladorGruposMusicales controladorGruposMusicales = ref.getControladorGruposMusicales();
    private String cif;
    private String idAlbum;


    //Tareas a realizar en la inicializacion estandar del recurso
    //con negociacion de contenidos
    @Override
    protected void doInit()throws ResourceException {
        getVariants().add(new Variant(MediaType.TEXT_PLAIN));
        getVariants().add(new Variant (MediaType.TEXT_HTML));
        this.cif = getAttribute("cif");
        this.idAlbum = getAttribute("albumId");
    }

    //Tareas a realizar en la liberacion estandar del recurso
    @Override
    protected void doRelease() throws ResourceException{
        System.out.println("The grupos musicales resource was release");
    }

    @Override
    protected Representation get(Variant variant)throws ResourceException{
        Representation result = null;
        if(MediaType.TEXT_PLAIN.isCompatible(variant.getMediaType())){
            try{
                StringBuilder result1 = new StringBuilder();
                result1.append(controladorGruposMusicales.verAlbum(cif,idAlbum));
                result = new StringRepresentation(result1.toString());
                return result;
            }catch(ExcepcionAlbumes a){
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
            }
            catch(ExcepcionGruposMusicales e){
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
            }
        }else if(MediaType.TEXT_HTML.isCompatible(variant.getMediaType())){
            Album albumHTML = new Album();
            try{
                sgae.nucleo.gruposMusicales.Album a = controladorGruposMusicales.recuperarAlbum(this.cif,this.idAlbum);
                albumHTML.setIdAlbum(a.getId());
                albumHTML.setTitulo(a.getTitulo());
                albumHTML.setFechaPublicacion(a.getFechaPublicacion());
                albumHTML.setEjemplaresVendidos(a.getEjemplaresVendidos());
                //URI
                Link link = new Link();
                link.setHref(this.idAlbum+"/pistas/");
                link.setTitle("Pistas");
                link.setType("simple");
                albumHTML.setUri(link);
                Map<String, Object> dataModel = new HashMap<String,Object>();
                dataModel.put("album",albumHTML);
                Representation albumVtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())
                        + "/album.vtl").get();
                Representation result2 = new TemplateRepresentation(albumVtl,dataModel,MediaType.TEXT_HTML);
                return result2;
            }catch(ExcepcionAlbumes a){
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
            }
            catch(ExcepcionGruposMusicales e){
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
            }catch (IOException io){
                throw  new ResourceException(Status.SERVER_ERROR_INTERNAL);
            }
        }
        return result;
    }

}
