package sgae.servidor.aplicacion;
import java.io.IOException;

import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.restlet.ext.xml.DomRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class RootServerResource extends ServerResource{
	public RootServerResource(){
	setNegotiated(true);
	}
	@Override
	protected void doInit() throws ResourceException{
		System.out.println("The root resource was initialized");
	}
	@Override
	protected void doCatch(Throwable throwable){
		System.out.println("An exception was thrown in the root resource");
	}
	@Override
	protected void doRelease() throws ResourceException{
		System.out.println("The root resource was release");
	}
	@Get ("txt")
	public String represent(){
		System.out.println("The GET method of root resource is invoked");
		File file = new File(System.getProperty("user.dir")+"/src/sgae/nucleo");
	    String files[] = file.list();
	    String valor = new String();
	    for (int i = 0; i < files.length; i++) {
	    	valor = valor + "Nombre recurso: " + files[i] + "; URI relativo: /" + files[i] + "/\n";
	    }
		return valor;
	}
	@Get ("xml")
	public Representation toXml() throws IOException{
	DomRepresentation result = new DomRepresentation();
	result.setIndenting(true);
	Document doc = result.getDocument();
	
	Node sgaeElt = doc.createElement("SGAE");
	sgaeElt.setTextContent("Jerarqu�a");
	doc.appendChild(sgaeElt);
	Node gm = doc.createElement("GruposMusicales");
	
	Element linkGM = doc.createElement("link");
	linkGM.setAttribute("title", "Grupos Musicales");
	linkGM.setAttribute("type", "simple");
	linkGM.setAttribute("href", "/gruposmusicales/");
	gm.appendChild(linkGM);
	sgaeElt.appendChild(gm);
	
	Node personas = doc.createElement("Personas");
	Element linkP = doc.createElement("link");
	linkP.setAttribute("title", "Personas");
	linkP.setAttribute("type", "simple");
	linkP.setAttribute("href", "/personas/");
	personas.appendChild(linkP);
	sgaeElt.appendChild(personas);

	Node discograficas = doc.createElement("Discograficas");
	Element linkD = doc.createElement("link");
	linkD.setAttribute("title", "Discográficas");
	linkD.setAttribute("type", "simple");
	linkD.setAttribute("href", "/discograficas/");
	discograficas.appendChild(linkD);
	sgaeElt.appendChild(discograficas);
	return result;
	}

}
