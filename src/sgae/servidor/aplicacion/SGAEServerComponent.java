package sgae.servidor.aplicacion;
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

		VirtualHost host = getDefaultHost();
		host.attachDefault(new SGAEServerApplication());
	}
}
