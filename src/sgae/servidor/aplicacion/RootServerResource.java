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

public class RootServerResource extends ServerResource{

	public RootServerResource(){
		//En la api por defecto dice que esta a true...
		setNegotiated(true);
	}

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
