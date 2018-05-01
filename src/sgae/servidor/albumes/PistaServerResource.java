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

/**
 * Clase que recoge las características del recurso Pista y 
 * los métodos para consultar dichas características en formato texto plano y en HTML y
 * para la modificación en formulario HTML (con negociación de contenidos).
 * @author Mario Calle Martín y Raquel Pérez García.Máster en Ingeniería de Telecomunicaciones.
 * @version 1.0
 *
 */
public class PistaServerResource extends ServerResource{
    //Obtenemos la referencia de la aplicacion
    private SGAEServerApplication ref = (SGAEServerApplication)getApplication();

    /**
     * ControladorGruposMusicales referencia al objeto de la clase SGAEServerApplication.
     */
    private ControladorGruposMusicales controladorGruposMusicales = ref.getControladorGruposMusicales();

    /**
     * CIF el identificador del grupo musical.
     */
    private String cif;

    /**
     * idAlbum el identificador del álbum.
     */
    private String idAlbum;

    /**
     * idPista el identificador de la pista.
     */
    private String idPista;

    /**
     * Método que realiza la inicialización estándar del recurso Pista.
     * cif Se obtiene el cif del grupo musical introducido.
     * idAlbum Se obtiene el identificador del álbum introducido.
     * idPista Se obtiene el identificador de la pista introducido.
     * @throws ResourceException si no se puede realizar la inicialización.
     *
     */
    @Override
    protected void doInit()throws ResourceException {
        getVariants().add(new Variant(MediaType.TEXT_PLAIN));
        getVariants().add(new Variant(MediaType.TEXT_HTML));
        getVariants().add(new Variant(MediaType.APPLICATION_WWW_FORM));
        this.cif = getAttribute("cif");
        this.idAlbum = getAttribute("albumId");
        this.idPista = getAttribute("pistaId");
    }

    /**
     * Método que realiza una operación GET sobre el recurso Pista en formato texto plano y HTML.
     *
     * @param variant nos indica si la petición es en formato texto plano o HTML.
     * @return representación del recurso Pista en texto plano o HTML.
     * @throws ResourceException 404 NOT FOUND si no existe un grupo musical registrado con el cif introducido,
     * si no existe un álbum con el idAlbum introducido o si no existe una pista con el idPista introducido,
     * 500 INTERNAL SERVER ERROR si se produce un error al generar el documento HTML o
     * 406 NOT ACCEPTABLE si el formato MediaType no está soportado por el recurso.
     */
    @Override
    protected Representation get(Variant variant)throws ResourceException{
        Representation result = null;
        if(MediaType.TEXT_PLAIN.isCompatible(variant.getMediaType())){
            //texto plano
            try{
                //Devolver la informacion de la pista
                StringBuilder result1 = new StringBuilder();
                result1.append(controladorGruposMusicales.verPista(this.cif,this.idAlbum,this.idPista));
                result = new StringRepresentation(result1.toString());
            } catch(ExcepcionAlbumes a){
                //id del album no existe
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,a.getCausaFallo());
            } catch(ExcepcionGruposMusicales e){
                //cif del grupo no existe
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,e.getCausaFallo());
            } catch (ExcepcionPistas e) {
                //id de la pista no existe
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,e.getCausaFallo());
            }
        }else if(MediaType.TEXT_HTML.isCompatible(variant.getMediaType())){
            //HTML
            //Clase auxiliar
            Pista pistaHTML = new Pista();
            try{
                //Introducir los datos de la pista
                sgae.nucleo.gruposMusicales.Pista p = controladorGruposMusicales.recuperarPista(this.cif,this.idAlbum,this.idPista);
                pistaHTML.setIdPista(p.getIdPista());
                pistaHTML.setNombre(p.getNombre());
                pistaHTML.setDuracion(p.getDuracion());

                //Modelo de datos
                Map<String, Object> dataModel = new HashMap<String,Object>();
                dataModel.put("pista",pistaHTML);
                //Plantilla velocity
                Representation pistaVtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())
                        + "/pista.vtl").get();
                //Generacion del documento HTML
                Representation result2 = new TemplateRepresentation(pistaVtl,dataModel,MediaType.TEXT_HTML);
                return result2;
            } catch(ExcepcionAlbumes a){
                //id album no existe
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,a.getCausaFallo());
            } catch(ExcepcionGruposMusicales e){
                //cif no existe
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,e.getCausaFallo());
            } catch (IOException io){
                //Error al generar documento HTML
                throw new ResourceException(Status.SERVER_ERROR_INTERNAL,"No se ha creado el documento HTML");
            } catch (ExcepcionPistas e) {
                //no existe id pista
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,e.getCausaFallo());
            }
        }else{
            throw new ResourceException(Status.CLIENT_ERROR_NOT_ACCEPTABLE,"MediaType no soportado. text/plain text/html");
        }
        return result;
    }

    /**
     * Método que realiza una operación DELETE sobre el recurso Pista.
     *
     * @param variant nos indica el formato de la petición.
     * @return cadena de texto que nos indica la Pista que ha sido eliminada del sistema..
     * @throws ResourceException 404 NOT FOUND si no existe un grupo musical registrado con el cif introducido,
     * si no existe un álbum con el idAlbum introducido o si no existe una pista con el idPista introducido.
     */
    @Override
    public Representation delete(Variant variant){
        Representation result = null;
        try{
            //Se elimina la pista
            controladorGruposMusicales.eliminarPista(this.cif,this.idAlbum,this.idPista);
            result = new StringRepresentation("Se ha borrado la pista con ID: "+this.idPista);
            //Si se ha eliminado correctamente se devuleve el status 204
            getResponse().setStatus(Status.SUCCESS_NO_CONTENT,"Se ha eliminado la pista con ID "+this.idPista);
        } catch(ExcepcionAlbumes a){
            //id album no existe
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,a.getCausaFallo());
        } catch(ExcepcionGruposMusicales a){
            //cif no existe
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,a.getCausaFallo());
        } catch (ExcepcionPistas e) {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,e.getCausaFallo());
        }
        return result;
    }
}
