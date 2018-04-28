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

public class MiembrosServerResource extends ServerResource {

    private SGAEServerApplication ref = (SGAEServerApplication)getApplication();
    private ControladorGruposMusicales controladorGruposMusicales = ref.getControladorGruposMusicales();
    private String cif;


    //Tareas a realizar en la inicializacion estandar del recurso
    //con negociacion de contenidos
    @Override
    protected void doInit()throws ResourceException {
        getVariants().add(new Variant(MediaType.TEXT_PLAIN));
        getVariants().add(new Variant (MediaType.TEXT_HTML));
        this.cif = getAttribute("cif");
    }

    @Override
    protected Representation get(Variant variant)throws ResourceException{
        Representation result = null;
        StringBuilder result2 = new StringBuilder();

        if(MediaType.TEXT_PLAIN.isCompatible(variant.getMediaType())){
            try{
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
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
            }
        }else if(MediaType.TEXT_HTML.isCompatible(variant.getMediaType())){

            Miembros miembrosHTML = new Miembros();
            Miembros miembrosAnterioresHTML = new Miembros();
            final List<MiembroInfoBreve> miembrosInfoBreve = miembrosHTML.getMiembroInfoBreve();
            final List<MiembroInfoBreve> miembrosAnterioresInfoBreve = miembrosAnterioresHTML.getMiembroInfoBreve();

            try{
                for(sgae.nucleo.personas.Persona p:controladorGruposMusicales.recuperarMiembros(this.cif)){
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
                Map<String,Object> miembroDataModel = new HashMap<>();
                miembroDataModel.put("miembros",miembrosHTML);
                miembroDataModel.put("miembrosanteriores",miembrosAnterioresHTML);
                //Cargamos la plantilla velocity
                Representation miembroVtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())+"/miembros.vtl").get();
                result = new TemplateRepresentation(miembroVtl,miembroDataModel,MediaType.TEXT_HTML);
                return result;
            }catch(ExcepcionGruposMusicales e){
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
            } catch (IOException e) {
                throw new ResourceException(Status.SERVER_ERROR_INTERNAL);
            }
        }
        return result;
    }
}
