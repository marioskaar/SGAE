package sgae.servidor.aplicacion;

import java.io.IOException;
//import java.io.File;


import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.restlet.ext.xml.DomRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

//import sgae.nucleo.gruposMusicales.ExcepcionAlbumes;
//import sgae.nucleo.gruposMusicales.ExcepcionGruposMusicales;
//import sgae.servidor.albumes.IOExcepcion;

/**
 * Clase que recoge las caracter�sticas del recurso Ra�z de la aplicaci�n y los m�tdos
 * para consultar sus caracter�sicas en texto plano y XML utilizando la API DOM.
 * @author Mario Calle Mart�n y Raquel P�rez Garc�a.M�ster en Ingenier�a de Telecomunicaciones.
 * @version 1.0
 *
 */
public class RootServerResource extends ServerResource{
	/**
	 * Constructor con los atributos del recurso ra�z.
	 * 
	 */
	public RootServerResource(){
		//En la api por defecto dice que esta a true...
		setNegotiated(true);
	}
	/**
	 * M�todo que realiza una operaci�n GET sobre el recurso Ra�z en formato texto plano.
	 * 
	 * @return cadena de texto con la representaci�n del recurso ra�z en texto plano.
	 */
	//Metodo Get en texto plano
	@Get ("txt")
	public String represent(){
		//Se genera el documento en texto plano
		String valor = new String();

		/* //Detecta automoticamente los directorios
		File file = new File(System.getProperty("user.dir")+"/src/sgae/nucleo");
	    String files[] = file.list();
	    for (int i = 0; i < files.length; i++) {
	    	valor = valor + "Nombre recurso: " + files[i] + "; URI relativo: " + files[i] + "/\n";
	    }
	    */

		//Fijamos a mano los nombres y uris de los recursos un nivel por debajo del root
		valor = "Nombre recurso: Grupos Musicales; URI relativo: gruposmusicales/"+"\n"
		+ "Nombre recurso: Personas; URI relativo: personas/"+"\n"
		+ "Nombre recurso: Discograficas; URI relativo: discograficas/";
		return valor;
	}
	/**
	 * M�todo que realiza una operaci�n GET sobre el recurso Ra�z en formato XML utilizando la API DOM.
	 * 
	 * @return representaci�n del recurso ra�z en formato XML.
	 * @throws ResourceException si se produce un error en la creaci�n del documento.
	 */

	@Get ("xml")
	public Representation toXml(){

		try{
			//creacion de los elementos por debajo del
			DomRepresentation result = new DomRepresentation();

			result.setIndenting(true);
			Document doc = result.getDocument();

			//root
			Node sgaeElt = doc.createElement("SGAE");
			sgaeElt.setTextContent("Jerarquia");
			doc.appendChild(sgaeElt);

			//grupos musicales
			Node gm = doc.createElement("GruposMusicales");
			Element linkGM = doc.createElement("link");
			linkGM.setAttribute("title", "Grupos Musicales");
			linkGM.setAttribute("type", "simple");
			linkGM.setAttribute("href", "gruposmusicales/");
			gm.appendChild(linkGM);
			sgaeElt.appendChild(gm);

			//personas
			Node personas = doc.createElement("Personas");
			Element linkP = doc.createElement("link");
			linkP.setAttribute("title", "Personas");
			linkP.setAttribute("type", "simple");
			linkP.setAttribute("href", "personas/");
			personas.appendChild(linkP);
			sgaeElt.appendChild(personas);

			//discograficas
			Node discograficas = doc.createElement("Discograficas");
			Element linkD = doc.createElement("link");
			linkD.setAttribute("title", "Discográficas");
			linkD.setAttribute("type", "simple");
			linkD.setAttribute("href", "discograficas/");
			discograficas.appendChild(linkD);
			sgaeElt.appendChild(discograficas);

			return result;
		}catch(IOException a){
			//Si se produce un error en la creacion del documento se lanza status 500
			throw new ResourceException(Status.SERVER_ERROR_INTERNAL,"No se ha creado el documento XML");
		}
	}
}
