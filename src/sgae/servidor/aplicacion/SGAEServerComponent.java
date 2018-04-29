package sgae.servidor.aplicacion;

import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.Context;
import org.restlet.Server;
import org.restlet.routing.VirtualHost;

public class SGAEServerComponent extends Component{

	public static void main(String[] args) throws Exception{
		//Se ejecuta el servidor
		new SGAEServerComponent().start();
	}
	public SGAEServerComponent() {
		//Definir propiedades del componente
		setName("SGAE server application");
		setDescription("Proyecto de la asignatura PTPD");
		setOwner("ptpdx03");
		setAuthor("Raquel Perez & Mario Calle");

		//Crear el servidor HTTP en el puerto 8111 y habilitar el tracing
		Server server = new Server(new Context(), Protocol.HTTP, 8111);
		server.getContext().getParameters().set("tracing", "true");
		getServers().add(server);

		//Incluir el protocolo CLAP para el cliente
		getClients().add(Protocol.CLAP);

		//conectar la aplicacion al host predeterminado
		VirtualHost host = getDefaultHost();
		host.attachDefault(new SGAEServerApplication());
	}
}
