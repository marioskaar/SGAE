package sgae.servidor.personas;

import org.restlet.Request;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import sgae.nucleo.personas.ControladorPersonas;
import sgae.nucleo.personas.ExcepcionPersonas;
import sgae.servidor.aplicacion.SGAEServerApplication;

import java.io.IOException;
import java.text.ParseException;

public class PersonaServerResource extends ServerResource
{
    SGAEServerApplication ref = (SGAEServerApplication)getApplication();
    ControladorPersonas controladorPersonas = ref.getControladorPersonas();

    @Put("form-data")
    public void store(Representation data) throws IOException {

        System.out.println(data.getText());
        String dni = getRequest().getResourceRef().getLastSegment();
        try {
            controladorPersonas.crearPersona(dni,"a","b","01-12-12");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ExcepcionPersonas excepcionPersonas) {
            excepcionPersonas.printStackTrace();
        }
    }
}
