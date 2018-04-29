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
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Clase que recoge las características del recurso Pistas y 
 *  los métodos para consultar dichas características en formato texto plano y en HTML y
 *  para la creación en formulario HTML (con negociación de contenidos).
 * @author Mario Calle Martín y Raquel Pérez García.Máster en Ingeniería de Telecomunicaciones.
 * @version 1.0
 *
 */
public class PistasServerResource extends ServerResource{
    //Obtenemos la referencia de la aplicacion
    private SGAEServerApplication ref = (SGAEServerApplication)getApplication();
    //Objeto de la clase ControladorGruposMusicales que hace referencia al instanciado en la clase SGAEServerApplication
    private ControladorGruposMusicales controladorGruposMusicales = ref.getControladorGruposMusicales();
    //cif e id album
    private String cif;
    private String albumId;
    /**
	 * Método que realiza la inicialización estándar del recurso Pistass.
	 * cif Se obtiene el cif del grupo musical introducido.
	 * idAlbum Se obtiene el idAlbum del álbum introducido.
	 * @throws ResourceException si no se puede realizar la inicialización.
	 * 
	 */
    //Tareas a realizar en la inicializacion estandar del recurso
    //con negociacion de contenidos y obtencion del cif e id
    @Override
    protected void doInit()throws ResourceException {
        getVariants().add(new Variant(MediaType.TEXT_PLAIN));
        getVariants().add(new Variant (MediaType.TEXT_HTML));
        getVariants().add(new Variant(MediaType.APPLICATION_WWW_FORM));
        this.cif = getAttribute("cif");
        this.albumId = getAttribute("albumId");
    }
    /**
	 * Método que realiza una operación GET sobre el recurso Pistas en formato texto plano y HTML.
	 * 
	 * @param variant nos indica si la petición es en formato texto plano o HTML.
	 * @return representación del recurso pistas en texto plano o HTML.
	 * @throws ExcepcionGruposMusicales si no existe un grupo musical registrado con el cif introducido.
	 * @throws ExcepcionAlbumes si no existe un álbum con el idAlbum introducido.
	 * @throws IOExcepcion si se produce algún error en la generación del documento HTML.
	 */
    //Metodo Get con negociacion de contenido
    @Override
    protected Representation get(Variant variant)throws ResourceException{
        Representation result = null;
        StringBuilder result2 = new StringBuilder();

        if(MediaType.TEXT_PLAIN.isCompatible(variant.getMediaType())){
            //texto plano
            try{
                //Se devuelve la info breve de cada pista
                for (sgae.nucleo.gruposMusicales.Pista p: controladorGruposMusicales.recuperarPistas(this.cif,this.albumId)){
                    result2.append((p == null) ? "": "Nombre: "+p.getNombre() + "\tUri relativa: " +p.getIdPista()).append('\n');
                }
                result = new StringRepresentation(result2.toString());
            }catch(ExcepcionGruposMusicales e){
                //Si no existe el cif del grupo
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,e.getCausaFallo());
            } catch (ExcepcionAlbumes e) {
                //Si no existe el id del album
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,e.getCausaFallo());
            }
        }else if(MediaType.TEXT_HTML.isCompatible(variant.getMediaType())){

            //HTML
            //Clase auxiliar
            Pistas pistasHTML = new Pistas();
            //Lista de pistas
            final List<PistaInfoBreve> pistasInfoBreve = pistasHTML.getPistaInfoBreve();
            try{
                for(sgae.nucleo.gruposMusicales.Pista p:controladorGruposMusicales.recuperarPistas(this.cif,this.albumId)){
                    //Incluir la info breve de cada pista
                    PistaInfoBreve pistaInfo = new PistaInfoBreve();
                    pistaInfo.setNombre(p.getNombre());
                    //URI de la pista
                    Link link = new Link();
                    link.setHref(p.getIdPista());
                    link.setTitle("Pistas");
                    link.setType("simple");
                    pistaInfo.setUri(link);
                    //Aniadir a la lista
                    pistasInfoBreve.add(pistaInfo);
                }
                //Preparamos el modelo de datos
                Map<String,Object> pistaDataModel = new HashMap<String,Object>();
                pistaDataModel.put("pistas",pistasHTML);
                //Cargamos la plantilla velocity
                Representation pistaVtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())+"/pistas.vtl").get();
                //Generacion del documento HTML
                result = new TemplateRepresentation(pistaVtl,pistaDataModel,MediaType.TEXT_HTML);
                return result;
            }catch(ExcepcionGruposMusicales e){
                //El cif no existe
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,e.getCausaFallo());
            } catch (IOException e) {
                //Error al generar el documento HTML
                throw new ResourceException(Status.SERVER_ERROR_INTERNAL,"No se ha creado el documento HTML");
            } catch (ExcepcionAlbumes e) {
                //Si el id no existe
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,e.getCausaFallo());
            }
        }else{
            throw new ResourceException(Status.CLIENT_ERROR_NOT_ACCEPTABLE,"MediaType no soportado. text/plain text/html");
        }
        return result;
    }
    /**
	 * Método que realiza una operación POST sobre el recurso Pistas con negociación de contenidos.
	 * 
	 * @param data datos que se introcen en el formulario HTML.
	 * @param variant nos indica el formato de la petición.
	 * @return representación del recurso pistas creado en formato HTML.
	 * @throws ExcepcionGruposMusicales si no existe un grupo musical registrado con el cif introducido.
	 * @throws ExcepcionAlbumes si no existe un álbum con el idAlbum introducido.
	 * @throws ExcepcionPistas si los parámetros de la pista introducidos no son correctos.
	 */

    //Metodo Post para la creacion de pistas
    @Override
    public Representation post(Representation data, Variant variant){
        Representation result = null;

        if (MediaType.APPLICATION_WWW_FORM.isCompatible(variant.getMediaType())) {
            //Obtenccion de los datos
            Form form = new Form(data);
            try {
                //Crear la pista
                controladorGruposMusicales.anadirPista(this.cif,this.albumId,form.getFirstValue("nombre")
                        ,Integer.parseInt(form.getFirstValue("duracion")));
                result = new StringRepresentation("Pista creada");
                //Si se ha creado la pista se devuelve status 201
                getResponse().setStatus(Status.SUCCESS_CREATED,"Se ha creado la pista");
            } catch(ExcepcionGruposMusicales a){
                //no existe el cif
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,a.getCausaFallo());
            } catch (ExcepcionPistas e) {
                //parametros introducidos no son correctos
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,e.getCausaFallo());
            } catch (ExcepcionAlbumes e) {
                //No existe el id del album
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,e.getCausaFallo());
            }
        }
        return  result;
    }
}
