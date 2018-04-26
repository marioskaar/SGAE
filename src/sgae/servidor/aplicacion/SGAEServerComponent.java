package sgae.servidor.aplicacion;
import org.restlet.Client;
import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.Context;
import org.restlet.Server;
import org.restlet.routing.VirtualHost;

public class SGAEServerComponent extends Component{

	public static void main(String[] args) throws Exception{
		new SGAEServerComponent().start();
	}
	public SGAEServerComponent() {
		setName("SGAE server application");
		setDescription("Proyecto de la asignatura PTPD");
		setOwner("ptpdx03");
		setAuthor("Raquel Perez & Mario Calle");

		Server server = new Server(new Context(), Protocol.HTTP, 8111);
		server.getContext().getParameters().set("tracing", "true");
		getServers().add(server);

		Client client = new Client(new Context(),Protocol.CLAP);
		client.getContext().getParameters().set("tracing", "true");
		getClients().add(client);

		VirtualHost host = getDefaultHost();
		host.attachDefault(new SGAEServerApplication());
	}
}
