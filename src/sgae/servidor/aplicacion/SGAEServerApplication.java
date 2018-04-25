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

    //Creacion de los objetos controladores
	private ControladorPersonas controladorPersonas;
	private ControladorGruposMusicales controladorGruposMusicales;

	//Constructor de la clase SGAEServerApplication
	public SGAEServerApplication() {
		setName("SGAE server application");
		setDescription("Proyecto de la asignatura PTPD");
		setOwner("ptpdx03");
		setAuthor("Raquel Perez & Mario Calle");
		//Instanciacion de los objetos controladores
		controladorPersonas = new ControladorPersonas();
		controladorGruposMusicales = new ControladorGruposMusicales(controladorPersonas);

		//Datos para que no este vacia la lista
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
		router.attach("/gruposmusicales/",GruposMusicalesServerResource.class);
//		router.attach("/gruposmusicales/{cif}",grupoMusicalServerResource.class);
//		router.attach("/albumes/",albumesServerResource.class);
//		router.attach("/albumes/{albumId}",albumServerResource.class);
//		router.attach("/albumes/pistas/",pistasServerResource.class);
//		router.attach("/albumes/pistas/{pistaId}",pistaServerResource.class);
		return router;
	}
	public ControladorPersonas getControladorPersonas() {
		return controladorPersonas;
	}
	public ControladorGruposMusicales getControladorGruposMusicales() {
		return controladorGruposMusicales;
	}

}
