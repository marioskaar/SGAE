package sgae.servidor.aplicacion;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import sgae.nucleo.gruposMusicales.ControladorGruposMusicales;
import sgae.nucleo.personas.ControladorPersonas;
import sgae.servidor.gruposMusicales.*;
import sgae.servidor.personas.*;
import sgae.servidor.albumes.*;

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
	}
	
	@Override
	public Restlet createInboundRoot(){
		//Asignacion de URIs a los recursos
		Router router = new Router(getContext());
		router.attach("/",RootServerResource.class);
		router.attach("/personas/",PersonasServerResource.class);
		router.attach("/personas/{dni}",PersonaServerResource.class);
		router.attach("/gruposmusicales/",GruposMusicalesServerResource.class);
		router.attach("/gruposmusicales/{cif}",GrupoMusicalServerResource.class);
		router.attach("/gruposmusicales/{cif}/miembros/",MiembrosServerResource.class);
		router.attach("/gruposmusicales/{cif}/albumes/",AlbumesServerResource.class);
		router.attach("/gruposmusicales/{cif}/albumes/{albumId}",AlbumServerResource.class);
		router.attach("/gruposmusicales/{cif}/albumes/{albumId}/pistas/",PistasServerResource.class);
		router.attach("/gruposmusicales/{cif}/albumes/{albumId}/pistas/{pistaId}",PistaServerResource.class);
		return router;
	}
	public ControladorPersonas getControladorPersonas() {
		return controladorPersonas;
	}
	public ControladorGruposMusicales getControladorGruposMusicales() {
		return controladorGruposMusicales;
	}
}
