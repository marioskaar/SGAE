package sgae.servidor.albumes;


import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import sgae.nucleo.gruposMusicales.ExcepcionGruposMusicales;
import sgae.servidor.aplicacion.SGAEServerApplication;
import sgae.util.generated.AlbumInfoBreve;
import sgae.util.generated.Albumes;
import sgae.util.generated.Link;
/**
 * Clase que recoge las características del recurso Álbumes y 
 * los métodos para consultar dichas características en formato texto plano y en HTML y
 *  para la creación en formulario HTML (con negociación de contenidos).
 * @author Mario Calle Martín y Raquel Pérez García.Máster en Ingeniería de Telecomunicaciones.
 * @version 1.0
 *
 */

 public class AlbumesServerResource extends ServerResource {

	//Obtenemos la referencia de la aplicacion
	private SGAEServerApplication ref = (SGAEServerApplication)getApplication();
	//Objeto de la clase ControladorGruposMusicales que hace referencia al instanciado en la clase SGAEServerApplication
	private ControladorGruposMusicales controladorGruposMusicales = ref.getControladorGruposMusicales();
	//Cif del grupo
	private String cif;
	
	/**
	 * Método que realiza la inicialización estándar del recurso Álbumes.
	 * Se obtiene el cif del grupo musical introducido.
	 * @throws ResourceException si no se puede realizar la inicialización.
	 * 
	 */

	//Tareas a realizar en la inicializacion estandar del recurso
	//con negociacion de contenidos y obtencion del cif
	@Override
	protected void doInit()throws ResourceException{
		getVariants().add(new Variant (MediaType.TEXT_PLAIN));
		getVariants().add(new Variant (MediaType.TEXT_HTML));
		getVariants().add(new Variant(MediaType.APPLICATION_WWW_FORM));
		this.cif = getAttribute("cif");
	}
	/**
	 * Método que realiza una operación GET sobre el recurso Álbumes en formato texto plano y HTML.
	 * 
	 * @param variant nos indica si la petición es en formato texto plano o HTML.
	 * @return representación del recurso álbumes en texto plano o HTML.
	 * @throws ExcepcionGruposMusicales si no existe un grupo musical registrado con el cif introducido.
	 * @throws IOExcepcion si se produce algún error en la generación del documento HTML.
	 */
	//Get con negociacion de contenido, txt y html
	@Override
	protected Representation get(Variant variant)throws ResourceException{
		Representation result = null;
		StringBuilder result2 = new StringBuilder();

		if(MediaType.TEXT_PLAIN.isCompatible(variant.getMediaType())){
			//texto plano
			try{
				//Informacion breve de cada album
				for (sgae.nucleo.gruposMusicales.Album album: controladorGruposMusicales.recuperarAlbumes(this.cif)){
					result2.append((album == null) ? "": "Titulo: "+album.getTitulo() + "\tUri relativa: " +album.getId()).append('\n');
				}
				result = new StringRepresentation(result2.toString());
			}catch(ExcepcionGruposMusicales e){
				//Si no existe el cif del grupo se devuelve 404
				throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,e.getCausaFallo());
			}
		}else if(MediaType.TEXT_HTML.isCompatible(variant.getMediaType())){

			//HTML
			//Objetos de las clases auxiliares contenidas en sgae.util.generated
			Albumes albumesHTML = new Albumes();
			//Lista de albumes
			final List<AlbumInfoBreve> albumesInfoBreve = albumesHTML.getAlbumInfoBreve();
			try{
				for(sgae.nucleo.gruposMusicales.Album a:controladorGruposMusicales.recuperarAlbumes(this.cif)){
					//incluir la informacion breve de cada album
					AlbumInfoBreve albumInfo = new AlbumInfoBreve();
					albumInfo.setTitulo(a.getTitulo());
					//URI del album
					Link link = new Link();
					link.setHref(a.getId());
					link.setTitle("Álbumes");
					link.setType("simple");
					albumInfo.setUri(link);
					//aniadir a la lista
					albumesInfoBreve.add(albumInfo);
				}
				//Preparamos el modelo de datos
				Map<String,Object> albumDataModel = new HashMap<String,Object>();
				albumDataModel.put("albumes",albumesHTML);
				//Cargamos la plantilla velocity
				Representation albumVtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())+"/albumes.vtl").get();
				//Generacion del documento HTML
				result = new TemplateRepresentation(albumVtl,albumDataModel,MediaType.TEXT_HTML);
				return result;
			}catch(ExcepcionGruposMusicales e){
				//Si no existe el cid del grupo se devuelve 404
				throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,e.getCausaFallo());
			} catch (IOException e) {
				//Si se produce algun error en la generacion del documento HTML
				throw new ResourceException(Status.SERVER_ERROR_INTERNAL,"No se ha creado el documento HTML");
			}
		}else{
			throw new ResourceException(Status.CLIENT_ERROR_NOT_ACCEPTABLE,"MediaType no soportado. text/plain text/html");
		}
		return result;
	}
	/**
	 * Método que realiza una operación POST sobre el recurso Álbumes con negociación de contenidos.
	 * 
	 * @param data datos que se introcen en el formulario HTML.
	 * @param variant nos indica el formato de la petición.
	 * @return representación del recurso álbumes creado en formato HTML.
	 * @throws ParseException si la fecha de publicación se ha introducido en un formato incorrecto.
	 * @throws ExcepcionGruposMusicales si no existe un grupo musical registrado con el cif introducido.
	 */

	//Metodo Post para la creacion de albumes, con negociacion de contenido
	@Override
	public Representation post(Representation data, Variant variant){
		Representation result = null;

		if (MediaType.APPLICATION_WWW_FORM.isCompatible(variant.getMediaType())) {
			//Se obtienen los datos
			Form form = new Form(data);
			try {
				controladorGruposMusicales.crearAlbum(this.cif,form.getFirstValue("titulo"),form.getFirstValue("fechaPublicacion")
						,Integer.parseInt(form.getFirstValue("ejemplaresVendidos")));
				result = new StringRepresentation("Album creado");
				//Si se ha creado el album se devuelve status 201
				getResponse().setStatus(Status.SUCCESS_CREATED,"Se ha creado el album");
			}catch (ParseException a){
				//Si la fecha se ha introducido mal
				throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,"Fecha introducida no valida. dd-mm-aaaa");
			} catch(ExcepcionGruposMusicales a){
				//Si no existe el cif del grupo
				throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,a.getCausaFallo());
			}
		}
		return  result;
	}
}