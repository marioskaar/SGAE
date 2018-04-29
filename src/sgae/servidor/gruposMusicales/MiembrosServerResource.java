package sgae.servidor.gruposMusicales;

import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.velocity.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.representation.Variant;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import sgae.nucleo.gruposMusicales.ControladorGruposMusicales;
import sgae.nucleo.gruposMusicales.ExcepcionGruposMusicales;
import sgae.servidor.aplicacion.SGAEServerApplication;
import sgae.util.generated.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Clase que recoge las características del recurso Miembros y 
 * los métodos para consultar dichas características en formato texto plano y en HTML
 * (negociación de contenidos).
 * @author Mario Calle Martín y Raquel Pérez García.Máster en Ingeniería de Telecomunicaciones.
 * @version 1.0
 *
 */
public class MiembrosServerResource extends ServerResource {
    //Obtenemos la referencia de la aplicacion
    private SGAEServerApplication ref = (SGAEServerApplication)getApplication();
    //Objeto de la clase ControladorGruposMusicales que hace referencia al instanciado en la clase SGAEServerApplication
    private ControladorGruposMusicales controladorGruposMusicales = ref.getControladorGruposMusicales();
    //Cif del grupo musical
    private String cif;

    /**
	 * Método que realiza la inicialización estándar del recurso Miembros.
	 * Se obtiene el cif del grupo musical introducido.
	 * 
	 */

    //Tareas a realizar en la inicializacion estandar del recurso
    //con negociacion de contenidos y obtencion del cif
    @Override
    protected void doInit()throws ResourceException {
        getVariants().add(new Variant(MediaType.TEXT_PLAIN));
        getVariants().add(new Variant (MediaType.TEXT_HTML));
        this.cif = getAttribute("cif");
    }
    /**
	 * Método que realiza una operación GET sobre el recurso Miembros con negociación de contenidos.
	 * 
	 * @param variant nos indica si la petición es en formato texto plano o HTML.
	 * @return la lista de miembros actuales y miembros anteriores en texto plano y HTML.
	 * @throws ResourceException si no existe un grupo musical registrado con el cif introducido.
	 * O si se produce un error al generar el documento HTML.
	 */
    //Get con negociacion de contenido, txt y html
    @Override
    protected Representation get(Variant variant)throws ResourceException{
        Representation result = null;
        StringBuilder result2 = new StringBuilder();

        if(MediaType.TEXT_PLAIN.isCompatible(variant.getMediaType())){
            //Texto plano
            try{
                //Se da informacion de las dos listas de miembros
                result2.append("Lista de miembros actuales\n");
                for (sgae.nucleo.personas.Persona p: controladorGruposMusicales.recuperarMiembros(this.cif)){
                    result2.append((p == null) ? "": "DNI: "+p.getDni() + " Nombre: "+p.getNombre()+" Apellidos: "+p.getApellidos()+" Uri relativa: ../../../personas/"+p.getDni()).append('\n');
                }
                result2.append("\nLista de miembros anteriores\n");
                for (sgae.nucleo.personas.Persona p: controladorGruposMusicales.recuperarMiembrosAnteriores(this.cif)){
                    result2.append((p == null) ? "": "DNI: "+p.getDni() + " Nombre: "+p.getNombre()+" Apellidos: "+p.getApellidos()+" Uri relativa: ../../../personas/"+p.getDni()).append('\n');
                }
                result = new StringRepresentation(result2.toString());
            }catch(ExcepcionGruposMusicales e){
                //Si no existe el cif del grupo se devuelve 404
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,e.getCausaFallo());
            }
        }else if(MediaType.TEXT_HTML.isCompatible(variant.getMediaType())){

            //HTML
            //Objetos de las clases auxiliares contenidas en sgae.util.generated
            Miembros miembrosHTML = new Miembros();
            Miembros miembrosAnterioresHTML = new Miembros();
            //listas de los miembros
            final List<MiembroInfoBreve> miembrosInfoBreve = miembrosHTML.getMiembroInfoBreve();
            final List<MiembroInfoBreve> miembrosAnterioresInfoBreve = miembrosAnterioresHTML.getMiembroInfoBreve();

            try{
                for(sgae.nucleo.personas.Persona p:controladorGruposMusicales.recuperarMiembros(this.cif)){
                    //incluir la informacion en los objetos auxiliares para cada miembro actual
                    MiembroInfoBreve miembroInfo = new MiembroInfoBreve();
                    miembroInfo.setDni(p.getDni());
                    miembroInfo.setNombre(p.getNombre());
                    miembroInfo.setApellidos(p.getApellidos());
                    Link link = new Link();
                    link.setHref("../../../personas/"+p.getDni());
                    link.setTitle("Persona");
                    link.setType("simple");
                    miembroInfo.setUri(link);
                    miembrosInfoBreve.add(miembroInfo);
                }
                for(sgae.nucleo.personas.Persona p:controladorGruposMusicales.recuperarMiembrosAnteriores(this.cif)){
                    //incluir la informacion en los objetos auxiliares para cada miembro anterior
                    MiembroInfoBreve miembroInfoAnterior = new MiembroInfoBreve();
                    miembroInfoAnterior.setDni(p.getDni());
                    miembroInfoAnterior.setNombre(p.getNombre());
                    miembroInfoAnterior.setApellidos(p.getApellidos());
                    Link link = new Link();
                    link.setHref("../../../personas/"+p.getDni());
                    link.setTitle("Persona");
                    link.setType("simple");
                    miembroInfoAnterior.setUri(link);
                    miembrosAnterioresInfoBreve.add(miembroInfoAnterior);
                }
                //Preparamos el modelo de datos
                Map<String,Object> miembroDataModel = new HashMap<String,Object>();
                miembroDataModel.put("miembros",miembrosHTML);
                miembroDataModel.put("miembrosanteriores",miembrosAnterioresHTML);
                //Cargamos la plantilla velocity
                Representation miembroVtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())+"/miembros.vtl").get();
                //Generar el documento html
                result = new TemplateRepresentation(miembroVtl,miembroDataModel,MediaType.TEXT_HTML);
                return result;
            }catch(ExcepcionGruposMusicales e){
                //Si no encuentra el cif del grupo
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,e.getCausaFallo());
            } catch (IOException e) {
                //Error al generar el documento HTML
                throw new ResourceException(Status.SERVER_ERROR_INTERNAL,"No se ha creado el documento HTML");
            }
        }else{
            throw new ResourceException(Status.CLIENT_ERROR_NOT_ACCEPTABLE,"MediaType no soportado. text/plain text/html");
        }
        return result;
    }
}
