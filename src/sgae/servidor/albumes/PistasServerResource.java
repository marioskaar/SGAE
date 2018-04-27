package sgae.servidor.albumes;

import org.restlet.data.Form;
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
import sgae.util.generated.PistaInfoBreve;
import sgae.util.generated.Pistas;
import sgae.util.generated.Link;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PistasServerResource extends ServerResource{

    SGAEServerApplication ref = (SGAEServerApplication)getApplication();
    ControladorGruposMusicales controladorGruposMusicales = ref.getControladorGruposMusicales();
    private String cif;
    private String albumId;

    //Tareas a realizar en la inicializacion estandar del recurso
    //con negociacion de contenidos
    @Override
    protected void doInit()throws ResourceException {
        getVariants().add(new Variant(MediaType.TEXT_PLAIN));
        getVariants().add(new Variant (MediaType.TEXT_HTML));
        getVariants().add(new Variant(MediaType.APPLICATION_WWW_FORM));
        this.cif = getAttribute("cif");
        this.albumId = getAttribute("albumId");
    }

    @Override
    protected Representation get(Variant variant)throws ResourceException{
        Representation result = null;
        StringBuilder result2 = new StringBuilder();

        if(MediaType.TEXT_PLAIN.isCompatible(variant.getMediaType())){
            try{
                for (sgae.nucleo.gruposMusicales.Pista p: controladorGruposMusicales.recuperarPistas(this.cif,this.albumId)){
                    result2.append((p == null) ? "": "Nombre: "+p.getNombre() + "\tUri relativa: " +p.getIdPista()).append('\n');
                }
                result = new StringRepresentation(result2.toString());
            }catch(ExcepcionGruposMusicales e){
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
            } catch (ExcepcionAlbumes excepcionAlbumes) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
            }
        }else if(MediaType.TEXT_HTML.isCompatible(variant.getMediaType())){

             Pistas pistasHTML = new Pistas();
            final List<PistaInfoBreve> pistasInfoBreve = pistasHTML.getPistaInfoBreve();
            try{
                for(sgae.nucleo.gruposMusicales.Pista p:controladorGruposMusicales.recuperarPistas(this.cif,this.albumId)){
                    PistaInfoBreve pistaInfo = new PistaInfoBreve();
                    pistaInfo.setNombre(p.getNombre());
                    Link link = new Link();
                    link.setHref(p.getIdPista());
                    link.setTitle("Pistas");
                    link.setType("simple");
                    pistaInfo.setUri(link);
                    pistasInfoBreve.add(pistaInfo);
                }
                //Preparamos el modelo de datos
                Map<String,Object> pistaDataModel = new HashMap<String,Object>();
                pistaDataModel.put("pistas",pistasInfoBreve);
                //Cargamos la plantilla velocity
                Representation pistaVtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())+"/pistas.vtl").get();
                result = new TemplateRepresentation(pistaVtl,pistaDataModel,MediaType.TEXT_HTML);
                return result;
            }catch(ExcepcionGruposMusicales e){
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
            } catch (IOException e) {
                throw new ResourceException(Status.SERVER_ERROR_INTERNAL);
            } catch (ExcepcionAlbumes excepcionAlbumes) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
            }
        }
        return result;
    }

    @Override
    public Representation post(Representation data, Variant variant){
        Representation result = null;

        if (MediaType.APPLICATION_WWW_FORM.isCompatible(variant.getMediaType())) {
            Form form = new Form(data);
            try {
                controladorGruposMusicales.anadirPista(this.cif,this.albumId,form.getFirstValue("nombre")
                        ,Integer.parseInt(form.getFirstValue("duracion")));
                result = new StringRepresentation("Pista creada");
            } catch(ExcepcionGruposMusicales a){
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
            } catch (ExcepcionPistas excepcionPistas) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
            } catch (ExcepcionAlbumes excepcionAlbumes) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
            }
        }
        return  result;
    }
}
