package sgae.servidor.albumes;
import org.restlet.data.Form;
import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.velocity.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.representation.Variant;
import org.restlet.resource.*;
import sgae.nucleo.gruposMusicales.ControladorGruposMusicales;
import sgae.nucleo.gruposMusicales.ExcepcionAlbumes;
import sgae.nucleo.gruposMusicales.ExcepcionGruposMusicales;
import sgae.servidor.aplicacion.SGAEServerApplication;
import sgae.util.generated.Album;
import sgae.util.generated.Link;

import java.io.IOException;
import java.text.ParseException;
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
        getVariants().add(new Variant(MediaType.TEXT_HTML));
        getVariants().add(new Variant(MediaType.APPLICATION_WWW_FORM));
        this.cif = getAttribute("cif");
        this.idAlbum = getAttribute("albumId");
    }

    @Override
    protected Representation get(Variant variant)throws ResourceException{
        Representation result = null;
        if(MediaType.TEXT_PLAIN.isCompatible(variant.getMediaType())){
            try{
                StringBuilder result1 = new StringBuilder();
                result1.append(controladorGruposMusicales.verAlbum(cif,idAlbum)+"URI: pistas/");
                result = new StringRepresentation(result1.toString());
            }catch(ExcepcionAlbumes a){
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
            }
            catch(ExcepcionGruposMusicales e){
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
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
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
            }
            catch(ExcepcionGruposMusicales e){
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
            }catch (IOException io){
                throw  new ResourceException(Status.SERVER_ERROR_INTERNAL);
            }
        }
        return result;
    }
    @Override
    public Representation delete(Variant variant){
        Representation result = null;
        try{
            controladorGruposMusicales.borrarAlbum(this.cif,this.idAlbum);
            result = new StringRepresentation("Se ha borrado el album con ID:"+this.idAlbum);
        }catch(ExcepcionAlbumes a){
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
        }
        catch(ExcepcionGruposMusicales a){
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
        }
        return result;
    }

    @Override
    public Representation put(Representation data, Variant variant){
        Representation result = null;

        if (MediaType.APPLICATION_WWW_FORM.isCompatible(variant.getMediaType())) {
            Form form = new Form(data);
            try {
                controladorGruposMusicales.modificarAlbum(this.cif,this.idAlbum,form.getFirstValue("titulo"),
                        form.getFirstValue("fechaPublicacion"),Integer.parseInt(form.getFirstValue("ejemplaresVendidos")));
                result = new StringRepresentation("Album modificado: " + controladorGruposMusicales.verAlbum(this.cif,this.idAlbum));
            }catch (ParseException a){
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
            }catch(ExcepcionAlbumes a){
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
            } catch(ExcepcionGruposMusicales a){
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
            }
        }
        return  result;
    }
}