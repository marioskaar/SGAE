package sgae.servidor.aplicacion;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import sgae.nucleo.gruposMusicales.ControladorGruposMusicales;
import sgae.nucleo.gruposMusicales.ExcepcionGruposMusicales;
import sgae.nucleo.personas.ControladorPersonas;
import sgae.nucleo.personas.ExcepcionPersonas;
import sgae.servidor.gruposMusicales.GruposMusicalesServerResource;
import sgae.servidor.personas.*;

import java.text.ParseException;

public class SGAEServerApplication extends Application{

	private ControladorPersonas controladorPersonas;
	private ControladorGruposMusicales controladorGruposMusicales;
	public SGAEServerApplication() {
		setName("SGAE");
		setDescription("Servidor");
		setOwner("YO");
		setAuthor("RACHEl");
		controladorPersonas = new ControladorPersonas();
		controladorGruposMusicales = new ControladorGruposMusicales(controladorPersonas);
		try {
			controladorPersonas.crearPersona("00000000A", "Bart", "Simpson",
                    "01-04-2003");
			controladorPersonas.crearPersona("11111111B", "Lisa", "Simpson",
					"02-02-2005");
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (ExcepcionPersonas excepcionPersonas) {
			excepcionPersonas.printStackTrace();
		}
	}
	
	@Override
	public Restlet createInboundRoot(){
		Router router = new Router(getContext());
		router.attach("/",RootServerResource.class);
		router.attach("/personas/",PersonasServerResource.class);
		router.attach("/personas/{dni}",PersonaServerResource.class);
		router.attach("/gruposMusicales/",GruposMusicalesServerResource.class);
//		router.attach("/gruposMusicales/{cif}",gruposMusicalesServerResource.class);
//		router.attach("/albumes/",albumesServerResource.class);
//		router.attach("/albumes/{albumId}",albumesServerResource.class);
//		router.attach("/albumes/pistas/",pistasServerResource.class);
//		router.attach("/albumes/pistas/{pistaId}",pistasServerResource.class);
		return router;
	}
	public ControladorPersonas getControladorPersonas() {
		return controladorPersonas;
	}
	public ControladorGruposMusicales getControladorGruposMusicales() {
		return controladorGruposMusicales;
	}

}
