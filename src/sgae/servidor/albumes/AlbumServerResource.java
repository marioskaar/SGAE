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

/**
 * Clase que recoge las caracteristicas del recurso album y
 * los metodos para consultar dichas caracteristicas en formato texto plano y en HTML y
 *  para la modificacion en formulario HTML (con negociacion de contenidos).
 * @author Mario Calle Martin y Raquel Pérez García.Máster en Ingeniería de Telecomunicaciones.
 * @version 1.0
 *
 */
public class AlbumServerResource extends ServerResource{
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
     * Método que realiza la inicialización estándar del recurso Álbum.
     * cif Se obtiene el cif del grupo musical introducido.
     * idAlbum Se obtiene el identificador del álbum introducido.
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
    }

    /**
     * Método que realiza una operación GET sobre el recurso Álbum en formato texto plano y HTML.
     *
     * @param variant nos indica si la petición es en formato texto plano o HTML.
     * @return representacion del recurso Álbum en texto plano o HTML.
     * @throws ResourceException 404 NOT FOUND si no existe un grupo musical registrado con el cif introducido,
     * o si no existe el álbum con el idAlbum introducido,
     * 500 INTERNAL SERVER ERROR si se produce algun error en la generacion del documento HTML
     * o 406 NOT ACCEPTABLE si el formato MediaType no está soportado por el recurso.
     */
    @Override
    protected Representation get(Variant variant)throws ResourceException{
        Representation result = null;
        if(MediaType.TEXT_PLAIN.isCompatible(variant.getMediaType())){
            //texto plano
            try{
                StringBuilder result1 = new StringBuilder();
                //se devuelve la informacion del album
                result1.append(controladorGruposMusicales.verAlbum(cif,idAlbum)+"URI: pistas/");
                result = new StringRepresentation(result1.toString());
            }catch(ExcepcionAlbumes a){
                //Si no existe el album con ese identificador
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,a.getCausaFallo());
            }
            catch(ExcepcionGruposMusicales e){
                //Si no existe el cif del grupo
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,e.getCausaFallo());
            }
        }else if(MediaType.TEXT_HTML.isCompatible(variant.getMediaType())){
            //HTML
            //clase auxiliar
            Album albumHTML = new Album();
            try{
                //Introduccion de los datos en el objeto auxiliar
                sgae.nucleo.gruposMusicales.Album a = controladorGruposMusicales.recuperarAlbum(this.cif,this.idAlbum);
                albumHTML.setIdAlbum(a.getId());
                albumHTML.setTitulo(a.getTitulo());
                albumHTML.setFechaPublicacion(a.getFechaPublicacion());
                albumHTML.setEjemplaresVendidos(a.getEjemplaresVendidos());
                //URI
                Link link = new Link();
                link.setHref("./pistas/");
                link.setTitle("Pistas");
                link.setType("simple");
                albumHTML.setUri(link);
                //Modelo de datos
                Map<String, Object> dataModel = new HashMap<String,Object>();
                dataModel.put("album",albumHTML);
                //Plantilla velocity
                Representation albumVtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())
                        + "/album.vtl").get();
                //Generacion del documento HTML
                Representation result2 = new TemplateRepresentation(albumVtl,dataModel,MediaType.TEXT_HTML);
                return result2;
            }catch(ExcepcionAlbumes a){
                //Si no existe el id del album
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,a.getCausaFallo());
            }
            catch(ExcepcionGruposMusicales e){
                //Si no existe el cif del grupo
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,e.getCausaFallo());
            }catch (IOException io){
                //Si se produce algun error en la generacion del documento HTML
                throw  new ResourceException(Status.SERVER_ERROR_INTERNAL,"No se ha creado el documento HTML");
            }
        }else{
            throw new ResourceException(Status.CLIENT_ERROR_NOT_ACCEPTABLE,"MediaType no soportado. text/plain text/html");
        }
        return result;
    }

    /**
     * Método que realiza una operación DELETE sobre el recurso Álbum.
     *
     * @param variant nos indica el formato de la petición.
     * @return cadena de texto que nos indica el Álbum que ha sido eliminado del sistema..
     * @throws ResourceException 404 NOT FOUND si no existe un grupo musical registrado con el cif introducido.
     * o si no existe un álbum con el idAlbum introducido.
     */
    @Override
    public Representation delete(Variant variant){
        Representation result = null;
        try{
            //Se elimina el album
            controladorGruposMusicales.borrarAlbum(this.cif,this.idAlbum);
            result = new StringRepresentation("Se ha borrado el album con ID:"+this.idAlbum);
            //Si se ha eliminado correctamente se devuleve el status 204
            getResponse().setStatus(Status.SUCCESS_NO_CONTENT,"Se ha eliminado el album con ID "+this.idAlbum);
        }catch(ExcepcionAlbumes a){
            //Si el id no existe
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,a.getCausaFallo());
        }
        catch(ExcepcionGruposMusicales a){
            //Si el cif no existe
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,a.getCausaFallo());
        }
        return result;
    }

    /**
     * Método que realiza una operación PUT sobre el recurso Álbum en formato HTML.
     *
     * @param data datos que se introducen en el formulario.
     * @param variant nos indica el formato de la petición.
     * @return representación del recurso Álbum modificado en formato HTML.
     * @throws ResourceException 400 BAD REQUEST si el formato de la fecha de publicación es incorrecto,
     * 404 NOT FOUND si no existe un grupo musical registrado con el cif introducido
     * o si no existe un álbum con el idAlbum introducido.
     */
    @Override
    public Representation put(Representation data, Variant variant){
        Representation result = null;

        if (MediaType.APPLICATION_WWW_FORM.isCompatible(variant.getMediaType())) {
            //Obtenccion de los datos
            Form form = new Form(data);
            try {
                //Se modifica el album
                controladorGruposMusicales.modificarAlbum(this.cif,this.idAlbum,form.getFirstValue("titulo"),
                        form.getFirstValue("fechaPublicacion"),Integer.parseInt(form.getFirstValue("ejemplaresVendidos")));
                result = new StringRepresentation("Album modificado: " + controladorGruposMusicales.verAlbum(this.cif,this.idAlbum));
                //Si se ha modificado correctamente el album, se devuelve el status 200
                getResponse().setStatus(Status.SUCCESS_OK,"Se ha modificado el album con ID "+this.idAlbum);
            }catch (ParseException a){
                //Error en la fecha
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,"Fecha introducida no valida. dd-mm-aaaa");
            }catch(ExcepcionAlbumes a){
                //El id del album no existe
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,a.getCausaFallo());
            } catch(ExcepcionGruposMusicales a){
                //El cif no existe
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,a.getCausaFallo());
            }
        }
        return  result;
    }
}