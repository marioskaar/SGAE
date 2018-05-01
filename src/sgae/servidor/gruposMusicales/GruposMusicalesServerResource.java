package sgae.servidor.gruposMusicales;

import java.util.List;

import org.restlet.ext.jaxb.JaxbRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.data.Status;

import sgae.nucleo.gruposMusicales.ControladorGruposMusicales;
import sgae.util.generated.GrupoMusicalInfoBreve;
import sgae.util.generated.Link;
import sgae.servidor.aplicacion.SGAEServerApplication;
import sgae.util.generated.GruposMusicales;
/**
 * Clase que recoge las características del recurso Grupos Musicales y 
 * los métodos para consultar dichas características en formato texto plano y en XML.
 * @author Mario Calle Martín y Raquel Pérez García.Máster en Ingeniería de Telecomunicaciones.
 * @version 1.0
 *
 */
public class GruposMusicalesServerResource extends ServerResource{
	//Obtenemos la referencia de la aplicacion
	private SGAEServerApplication ref = (SGAEServerApplication)getApplication();
	//Objeto de la clase ControladorGruposMusicales que hace referencia al instanciado en la clase SGAEServerApplication
	private ControladorGruposMusicales controladorGruposMusicales = ref.getControladorGruposMusicales();
	/**
	 * Método que realiza una operación GET sobre el recurso Grupos Musicales en formato texto plano.
	 *
	 * @return cadena de texto con la representación del recurso grupos musicales en texto plano.
	 */
	//Método GET en texto plano
	@Get("txt")
	//Obtener una representación de la lista de grupos musicales del sistema
	public String represent() {
		StringBuilder result = new StringBuilder();

		//Si no hay ningun grupo musical registrado en la aplicacion devolvemos el estado
		//No Content en la respuesta
		if(controladorGruposMusicales.recuperarGruposMusicales().size()==0) {
			getResponse().setStatus(Status.SUCCESS_NO_CONTENT,"No hay grupos registrados");
		}else{
			//Para cada grupo almacenado en la lista, devolvemos su infobreve
			for (sgae.nucleo.gruposMusicales.GrupoMusical gm : controladorGruposMusicales.recuperarGruposMusicales()){
				result.append("CIF: "+gm.getCif()+" Nombre: "+gm.getNombre()
						+" Fecha Creacion: "+gm.getFechaCreacion()+" URI: gruposmusicales/"+gm.getCif()+"/"+"\n");
			}
		}
		return result.toString();
	}
	/**
	 * Método que realiza una operación GET sobre el recurso Grupos Musicales en formato XML utilizando la API JAXB.
	 *
	 * @return  representación del recurso grupos musicales en formato XML.
	 */
	//Método GET en formato XML
	@Get("xml")
	public Representation toXml() {
		GruposMusicales gmsXML = new GruposMusicales();
		final List<GrupoMusicalInfoBreve> grupoMusicalInfoBreve = gmsXML.getGrupoMusicalInfoBreve();
		//Si no hay ningun grupo musical registrado en la aplicacion devolvemos el estado
		//No Content en la respuesta
		if(controladorGruposMusicales.recuperarGruposMusicales().size()==0) {
			getResponse().setStatus(Status.SUCCESS_NO_CONTENT,"No hay grupos registrados");
		}else{
			for (sgae.nucleo.gruposMusicales.GrupoMusical gm : controladorGruposMusicales.recuperarGruposMusicales()){
				//Para cada grupo se genera un objeto de la clase auxiliar de representacion en xml
				GrupoMusicalInfoBreve gmXML = new GrupoMusicalInfoBreve();
				gmXML.setCif(gm.getCif());
				gmXML.setNombre(gm.getNombre());
				gmXML.setFechaCreacion(gm.getFechaCreacion());
				//Objeto link para asignar la URI
				Link link = new Link();
				link.setHref("gruposmusicales/"+gm.getCif()+"/");
				link.setTitle("GrupoMusical");
				link.setType("simple");
				gmXML.setUri(link);
				//Se aniade el grupo a la lista
				grupoMusicalInfoBreve.add(gmXML);
			}
		}
		//Se genera el documento xml que se envia como respuesta
		JaxbRepresentation<GruposMusicales> result = new JaxbRepresentation<GruposMusicales>(gmsXML);
		result.setFormattedOutput(true);
		return result;
	}
}


