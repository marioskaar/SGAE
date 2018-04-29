package sgae.clientes;

import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import java.io.IOException;

public class ClienteSGAE  {
    public static void main(String args[]) throws Exception{

        //URI del servidor
        String uriBase = "http://localhost:8111/";

        //DNI de las personas a incluir
        String pBart = "00000000A",
                pLisa = "11111111B",
                pMaggie = "22222222C",
                pHomer = "33333333D",
                pMarge = "44444444E",
                pAbe = "55555555F",
                pBurns = "66666666G",
                pCarl = "77777777H",
                pSkinner = "88888888I";

        //CIF de los grupos a incluir
        String gmJamiroquai = "D0123456D",
                gmBlur = "E0123456E";

        //Conexion al servidor
        ClientResource clientResource = new ClientResource(uriBase);
        clientResource.setRetryOnError(false);
        clientResource.setRetryAttempts(0);

        clientResource.getRequest().setResourceRef(uriBase+"personas/");
        clientResource.get(MediaType.TEXT_PLAIN);

        //Crear personas
        System.out.println("Creacion de personas");

        //pBart
        clientResource.getRequest().setResourceRef(uriBase+"personas/"+pBart);
        try {
            Form persona = new Form();
            persona.add("dni",pBart);
            persona.add("nombre","Bart");
            persona.add("apellidos","Simpson");
            persona.add("fechaNacimiento","05-05-2005");

            clientResource.put(persona);
            System.out.println(clientResource.getStatus().getCode()+"\n"+clientResource.getStatus().getDescription());
        }catch(ResourceException a) {
            System.out.println(a.getStatus().getCode() + "\n" + a.getStatus().getDescription());
        }
    }
}
