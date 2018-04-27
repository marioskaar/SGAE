package sgae.servidor.albumes;

import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.velocity.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.representation.Variant;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import sgae.nucleo.gruposMusicales.ControladorGruposMusicales;
import sgae.nucleo.gruposMusicales.ExcepcionAlbumes;
import sgae.nucleo.gruposMusicales.ExcepcionGruposMusicales;
import sgae.nucleo.gruposMusicales.ExcepcionPistas;
import sgae.servidor.aplicacion.SGAEServerApplication;
import sgae.util.generated.Pista;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PistaServerResource extends ServerResource{
    SGAEServerApplication ref = (SGAEServerApplication)getApplication();
    ControladorGruposMusicales controladorGruposMusicales = ref.getControladorGruposMusicales();
    private String cif;
    private String idAlbum;
    private String idPista;


    //Tareas a realizar en la inicializacion estandar del recurso
    //con negociacion de contenidos
    @Override
    protected void doInit()throws ResourceException {
        getVariants().add(new Variant(MediaType.TEXT_PLAIN));
        getVariants().add(new Variant(MediaType.TEXT_HTML));
        getVariants().add(new Variant(MediaType.APPLICATION_WWW_FORM));
        this.cif = getAttribute("cif");
        this.idAlbum = getAttribute("albumId");
        this.idPista = getAttribute("pistaId");
    }

    @Override
    protected Representation get(Variant variant)throws ResourceException{
        Representation result = null;
        if(MediaType.TEXT_PLAIN.isCompatible(variant.getMediaType())){
            try{
                StringBuilder result1 = new StringBuilder();
                result1.append(controladorGruposMusicales.verPista(this.cif,this.idAlbum,this.idPista));
                result = new StringRepresentation(result1.toString());
            } catch(ExcepcionAlbumes a){
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
            } catch(ExcepcionGruposMusicales e){
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
            } catch (ExcepcionPistas excepcionPistas) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
            }
        }else if(MediaType.TEXT_HTML.isCompatible(variant.getMediaType())){
            Pista pistaHTML = new Pista();
            try{
                sgae.nucleo.gruposMusicales.Pista p = controladorGruposMusicales.recuperarPista(this.cif,this.idAlbum,this.idPista);
                pistaHTML.setIdPista(p.getIdPista());
                pistaHTML.setNombre(p.getNombre());
                pistaHTML.setDuracion(p.getDuracion());

                Map<String, Object> dataModel = new HashMap<String,Object>();
                dataModel.put("pista",pistaHTML);
                Representation pistaVtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())
                        + "/pista.vtl").get();
                Representation result2 = new TemplateRepresentation(pistaVtl,dataModel,MediaType.TEXT_HTML);
                return result2;
            } catch(ExcepcionAlbumes a){
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
            } catch(ExcepcionGruposMusicales e){
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
            } catch (IOException io){
                throw new ResourceException(Status.SERVER_ERROR_INTERNAL);
            } catch (ExcepcionPistas excepcionPistas) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
            }
        }
        return result;
    }

    @Override
    public Representation delete(Variant variant){
        Representation result = null;
        try{
            controladorGruposMusicales.eliminarPista(this.cif,this.idAlbum,this.idPista);
            result = new StringRepresentation("Se ha borrado la pista con ID: "+this.idPista);
        } catch(ExcepcionAlbumes a){
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
        } catch(ExcepcionGruposMusicales a){
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
        } catch (ExcepcionPistas excepcionPistas) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
        }
        return result;
    }
}
