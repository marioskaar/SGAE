package sgae.clientes;

import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;


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

        //Crear personas
        System.out.println("\n----Inicio de creacion de personas----");

        //pBart
        clientResource.getRequest().setResourceRef(uriBase+"personas/"+pBart);
        try {
            Form persona = new Form();
            persona.add("nombre","Bart");
            persona.add("apellidos","Simpson");
            persona.add("fechaNacimiento","05-05-2005");

            clientResource.put(persona);
            if(clientResource.getStatus().equals(Status.SUCCESS_CREATED)){
                System.out.println("Se ha creado Persona Bart");
            }else{
                System.out.println("No se ha creado Persona Bart. Codigo: "+clientResource.getStatus());
            }
        }catch(ResourceException a) {
            System.err.println("Error al crear Persona Bart. Codigo: "+a.getStatus().getCode() + "\nDescripcion: " + a.getStatus().getDescription());
        }

        //pLisa
        clientResource.getRequest().setResourceRef(uriBase+"personas/"+pLisa);
        try {
            Form persona = new Form();
            persona.add("nombre","Lisa");
            persona.add("apellidos","Simpson");
            persona.add("fechaNacimiento","02-02-2005");

            clientResource.put(persona);
            if(clientResource.getStatus().equals(Status.SUCCESS_CREATED)){
                System.out.println("Se ha creado Persona Lisa");
            }else{
                System.out.println("No se ha creado Persona Lisa. Codigo: "+clientResource.getStatus());
            }
        }catch(ResourceException a) {
            System.err.println("Error al crear Persona Lisa. Codigo: "+a.getStatus().getCode() + "\nDescripcion: " + a.getStatus().getDescription());
        }
        //pMaggie
        clientResource.getRequest().setResourceRef(uriBase+"personas/"+pMaggie);
        try {
            Form persona = new Form();
            persona.add("nombre","Maggie");
            persona.add("apellidos","Simpson");
            persona.add("fechaNacimiento","03-03-2014");

            clientResource.put(persona);
            if(clientResource.getStatus().equals(Status.SUCCESS_CREATED)){
                System.out.println("Se ha creado Persona Maggie");
            }else{
                System.out.println("No se ha creado Persona Maggie. Codigo: "+clientResource.getStatus());
            }
        }catch(ResourceException a) {
            System.err.println("Error al crear Persona Maggie. Codigo: "+a.getStatus().getCode() + "\nDescripcion: " + a.getStatus().getDescription());
        }
        //pHomer
        clientResource.getRequest().setResourceRef(uriBase+"personas/"+pHomer);
        try {
            Form persona = new Form();
            persona.add("nombre","Homer");
            persona.add("apellidos","Simpson");
            persona.add("fechaNacimiento","01-08-1970");

            clientResource.put(persona);
            if(clientResource.getStatus().equals(Status.SUCCESS_CREATED)){
                System.out.println("Se ha creado Persona Homer");
            }else{
                System.out.println("No se ha creado Persona Homer. Codigo: "+clientResource.getStatus());
            }
        }catch(ResourceException a) {
            System.err.println("Error al crear Persona Homer. Codigo: "+a.getStatus().getCode() + "\nDescripcion: " + a.getStatus().getDescription());
        }
        //pMarge
        clientResource.getRequest().setResourceRef(uriBase+"personas/"+pMarge);
        try {
            Form persona = new Form();
            persona.add("nombre","Margaret");
            persona.add("apellidos","Simpson");
            persona.add("fechaNacimiento","01-10-1975");

            clientResource.put(persona);
            if(clientResource.getStatus().equals(Status.SUCCESS_CREATED)){
                System.out.println("Se ha creado Persona Margaret");
            }else{
                System.out.println("No se ha creado Persona Margaret. Codigo: "+clientResource.getStatus());
            }
        }catch(ResourceException a) {
            System.err.println("Error al crear Persona Margaret. Codigo: "+a.getStatus().getCode() + "\nDescripcion: " + a.getStatus().getDescription());
        }
        //pAbe
        clientResource.getRequest().setResourceRef(uriBase+"personas/"+pAbe);
        try {
            Form persona = new Form();
            persona.add("nombre","Abe");
            persona.add("apellidos","Simpson");
            persona.add("fechaNacimiento","01-03-1940");

            clientResource.put(persona);
            if(clientResource.getStatus().equals(Status.SUCCESS_CREATED)){
                System.out.println("Se ha creado Persona Abe");
            }else{
                System.out.println("No se ha creado Persona Abe. Codigo: "+clientResource.getStatus());
            }
        }catch(ResourceException a) {
            System.err.println("Error al crear Persona Abe. Codigo: "+a.getStatus().getCode() + "\nDescripcion: " + a.getStatus().getDescription());
        }
        //pBurns
        clientResource.getRequest().setResourceRef(uriBase+"personas/"+pBurns);
        try {
            Form persona = new Form();
            persona.add("nombre","Monty");
            persona.add("apellidos","Burns");
            persona.add("fechaNacimiento","01-06-1930");

            clientResource.put(persona);
            if(clientResource.getStatus().equals(Status.SUCCESS_CREATED)){
                System.out.println("Se ha creado Persona Monty");
            }else{
                System.out.println("No se ha creado Persona Monty. Codigo: "+clientResource.getStatus());
            }
        }catch(ResourceException a) {
            System.err.println("Error al crear Persona Monty. Codigo: "+a.getStatus().getCode() + "\nDescripcion: " + a.getStatus().getDescription());
        }
        //pCarl
        clientResource.getRequest().setResourceRef(uriBase+"personas/"+pCarl);
        try {
            Form persona = new Form();
            persona.add("nombre","Carl");
            persona.add("apellidos","Carlson");
            persona.add("fechaNacimiento","01-07-1965");

            clientResource.put(persona);
            if(clientResource.getStatus().equals(Status.SUCCESS_CREATED)){
                System.out.println("Se ha creado Persona Carl");
            }else{
                System.out.println("No se ha creado Persona Carl. Codigo: "+clientResource.getStatus());
            }
        }catch(ResourceException a) {
            System.err.println("Error al crear Persona Carl. Codigo: "+a.getStatus().getCode() + "\nDescripcion: " + a.getStatus().getDescription());
        }
        //pSkinner
        clientResource.getRequest().setResourceRef(uriBase+"personas/"+pSkinner);
        try {
            Form persona = new Form();
            persona.add("nombre","Seymor");
            persona.add("apellidos","Skinner");
            persona.add("fechaNacimiento","01-08-1969");

            clientResource.put(persona);
            if(clientResource.getStatus().equals(Status.SUCCESS_CREATED)){
                System.out.println("Se ha creado Persona Seymor");
            }else{
                System.out.println("No se ha creado Persona Seymor. Codigo: "+clientResource.getStatus());
            }
        }catch(ResourceException a) {
            System.err.println("Error al crear Persona Seymor. Codigo: "+a.getStatus().getCode()
                    + "\nDescripcion: " + a.getStatus().getDescription());
        }

        System.out.println("\n----Listando personas----");
        //Listar personas (texto plano y en xml)
        clientResource.getRequest().setResourceRef(uriBase+"personas/");
        try{
            Representation response = clientResource.get(MediaType.TEXT_PLAIN);
            if(clientResource.getStatus().equals(Status.SUCCESS_OK)){
                System.out.println("Texto plano: \n"+response.getText());
            }else{
                System.out.println("No hay personas registradas");
            }
            response = clientResource.get(MediaType.TEXT_XML);
            if(clientResource.getStatus().equals(Status.SUCCESS_OK)){
                System.out.println("Texto XML: \n"+response.getText());
            }else{
                System.out.println("No hay personas registradas");
            }
        }catch (ResourceException a){
            System.err.println("Error al listar Personas. Codigo: "+a.getStatus().getCode()
                    +"\nDescripcion" +a.getStatus().getDescription());
        }

        System.out.println("\n----Inicio de creacion de grupos musicales----");
        //Creacion de grupos musicales
        //gmJamiroquai
        clientResource.getRequest().setResourceRef(uriBase+"gruposmusicales/"+gmJamiroquai+"/");
        try {
            Form grupo = new Form();
            grupo.add("nombre","Jamiroquai");
            grupo.add("fechaCreacion","02-04-1992");

            clientResource.put(grupo);
            if(clientResource.getStatus().equals(Status.SUCCESS_CREATED)){
                System.out.println("Se ha creado Grupo Jamiroquai");
            }else{
                System.out.println("No se ha creado Grupo Jamiroquai. Codigo: "+clientResource.getStatus());
            }
        }catch(ResourceException a) {
            System.err.println("Error al crear Grupo Jamiroquai. Codigo: "+a.getStatus().getCode()
                    + "\nDescripcion: " + a.getStatus().getDescription());
        }
        //gmBlur
        clientResource.getRequest().setResourceRef(uriBase+"gruposmusicales/"+gmBlur+"/");
        try {
            Form grupo = new Form();
            grupo.add("nombre","Blur");
            grupo.add("fechaCreacion","03-05-1998");

            clientResource.put(grupo);
            if(clientResource.getStatus().equals(Status.SUCCESS_CREATED)){
                System.out.println("Se ha creado Grupo Blur");
            }else{
                System.out.println("No se ha creado Grupo Blur. Codigo: "+clientResource.getStatus());
            }
        }catch(ResourceException a) {
            System.err.println("Error al crear Grupo Blur. Codigo: "+a.getStatus().getCode()
                    + "\nDescripcion: " + a.getStatus().getDescription());
        }

        System.out.println("\n----Listando grupos musicales----");
        //Listar todos los grupos musicales (plano y xml)
        clientResource.getRequest().setResourceRef(uriBase+"gruposmusicales/");
        try{
            Representation response = clientResource.get(MediaType.TEXT_PLAIN);
            if(clientResource.getStatus().equals(Status.SUCCESS_OK)){
                System.out.println("Texto plano: \n"+response.getText());
            }else{
                System.out.println("No hay grupos registrados");
            }
            response = clientResource.get(MediaType.TEXT_XML);
            if(clientResource.getStatus().equals(Status.SUCCESS_OK)){
                System.out.println("Texto XML: \n"+response.getText());
            }else{
                System.out.println("No hay grupos registrados");
            }
        }catch (ResourceException a){
            System.err.println("Error al listar Grupos. Codigo: "+a.getStatus().getCode()
                    +"\nDescripcion" +a.getStatus().getDescription());
        }


        System.out.println("\n----Inicio de aniadir miembros a un grupo----");
        //aniadir miembro al grupo Jamiroquoai
        clientResource.getRequest().setResourceRef(uriBase+"gruposmusicales/"+gmJamiroquai+"/");
        try {
            Form grupo = new Form();
            grupo.add("dni",pBart);
            grupo.add("dni",pLisa);
            grupo.add("dni",pMaggie);
            grupo.add("dni",pHomer);
            grupo.add("dni",pMarge);
            grupo.add("dni",pAbe);

            clientResource.put(grupo);
            if(clientResource.getStatus().equals(Status.SUCCESS_OK)){
                System.out.println("Se han aniadido varios miembros al Grupo Jamiroquai");
            }else{
                System.out.println("No se han aniadido miembros al Grupo Jamiroquai. Codigo: "+clientResource.getStatus());
            }
        }catch (ResourceException a){
            System.err.println("Error al aniadir miembro. Codigo: "+a.getStatus().getCode()
                        +"\nDescripcion" +a.getStatus().getDescription());
        }
        //aniadir miembro al grupo Blur
        clientResource.getRequest().setResourceRef(uriBase+"gruposmusicales/"+gmBlur+"/");
        try {
            Form grupo = new Form();
            grupo.add("dni",pCarl);
            grupo.add("dni",pBurns);
            grupo.add("dni",pSkinner);

            clientResource.put(grupo);
            if(clientResource.getStatus().equals(Status.SUCCESS_OK)){
                System.out.println("Se han aniadido varios miembros al Grupo Blur");
            }else{
                System.out.println("No se han aniadido miembros al Grupo Blur. Codigo: "+clientResource.getStatus());
            }
        }catch (ResourceException a){
            System.err.println("Error al aniadir miembro. Codigo: "+a.getStatus().getCode()
                    +"\nDescripcion" +a.getStatus().getDescription());
        }

        System.out.println("\n----Inicio de eliminar miembros a un grupo----");
        //eliminar dos miembros de Jamiroquai
        clientResource.getRequest().setResourceRef(uriBase+"gruposmusicales/"+gmJamiroquai+"/");
        try {
            Form grupo = new Form();
            grupo.add("dni",pBart);
            grupo.add("dni",pLisa);
            grupo.add("dni",pMaggie);
            grupo.add("dni",pHomer);

            clientResource.put(grupo);
            if(clientResource.getStatus().equals(Status.SUCCESS_OK)){
                System.out.println("Se han eliminado varios miembros del Grupo Jamiroquai");
            }else{
                System.out.println("No se han eliminado miembros del Grupo Jamiroquai. Codigo: "+clientResource.getStatus());
            }
        }catch (ResourceException a){
            System.err.println("Error al eliminar miembro. Codigo: "+a.getStatus().getCode()
                    +"\nDescripcion" +a.getStatus().getDescription());
        }
        //eliminar miembro al grupo Blur
        clientResource.getRequest().setResourceRef(uriBase+"gruposmusicales/"+gmBlur+"/");
        try {
            Form grupo = new Form();
            grupo.add("dni",pCarl);
            grupo.add("dni",pBurns);

            clientResource.put(grupo);
            if(clientResource.getStatus().equals(Status.SUCCESS_OK)){
                System.out.println("Se ha eliminado un miembro del Grupo Blur");
            }else{
                System.out.println("No se ha eliminado miembro del Grupo Blur. Codigo: "+clientResource.getStatus());
            }
        }catch (ResourceException a){
            System.err.println("Error al eliminar miembro. Codigo: "+a.getStatus().getCode()
                    +"\nDescripcion" +a.getStatus().getDescription());
        }

        System.out.println("\n----Listando miembros de grupos musicales (actuales y anteriores)----");
        //Listar miembros del grupo musical Jamiroquai (plano y html)
        System.out.println("\nGrupo Jamiroquai");
        clientResource.getRequest().setResourceRef(uriBase+"gruposmusicales/"+gmJamiroquai+"/miembros");
        try{
            Representation response = clientResource.get(MediaType.TEXT_PLAIN);
            if(clientResource.getStatus().equals(Status.SUCCESS_OK)){
                System.out.println("Texto plano: \n"+response.getText());
            }else{
                System.out.println("No hay miembros");
            }
            response = clientResource.get(MediaType.TEXT_HTML);
            if(clientResource.getStatus().equals(Status.SUCCESS_OK)){
                System.out.println("Texto HTML: \n"+response.getText());
            }else{
                System.out.println("No hay miembros");
            }
        }catch (ResourceException a){
            System.err.println("Error al listar miembros. Codigo: "+a.getStatus().getCode()
                    +"\nDescripcion" +a.getStatus().getDescription());
        }
        //Listar miembros del grupo musical Blur (plano y html)
        System.out.println("\nGrupo Blur");
        clientResource.getRequest().setResourceRef(uriBase+"gruposmusicales/"+gmBlur+"/miembros");
        try{
            Representation response = clientResource.get(MediaType.TEXT_PLAIN);
            if(clientResource.getStatus().equals(Status.SUCCESS_OK)){
                System.out.println("Texto plano: \n"+response.getText());
            }else{
                System.out.println("No hay miembros");
            }
            response = clientResource.get(MediaType.TEXT_HTML);
            if(clientResource.getStatus().equals(Status.SUCCESS_OK)){
                System.out.println("Texto HTML: \n"+response.getText());
            }else{
                System.out.println("No hay miembros");
            }
        }catch (ResourceException a){
            System.err.println("Error al listar miembros. Codigo: "+a.getStatus().getCode()
                    +"\nDescripcion" +a.getStatus().getDescription());
        }


        //Crear albumes
        System.out.println("\n----Inicio de creacion de albumes----");

        //a0
        clientResource.getRequest().setResourceRef(uriBase+"gruposmusicales/"+gmJamiroquai+"/albumes/");
        try {
            Form album = new Form();
            album.add("titulo","Azul");
            album.add("fechaPublicacion","20-12-2017");
            album.add("ejemplaresVendidos","300");

            clientResource.post(album);
            if(clientResource.getStatus().equals(Status.SUCCESS_CREATED)){
                System.out.println("Se ha creado Album Azul");
            }else{
                System.out.println("No se ha creado Album Azul. Codigo: "+clientResource.getStatus());
            }
        }catch(ResourceException a) {
            System.err.println("Error al crear Album Azul. Codigo: "+a.getStatus().getCode() + "\nDescripcion: " + a.getStatus().getDescription());
        }
        //a1
        clientResource.getRequest().setResourceRef(uriBase+"gruposmusicales/"+gmJamiroquai+"/albumes/");
        try {
            Form album = new Form();
            album.add("titulo","Rojo");
            album.add("fechaPublicacion","20-01-2018");
            album.add("ejemplaresVendidos","30");

            clientResource.post(album);
            if(clientResource.getStatus().equals(Status.SUCCESS_CREATED)){
                System.out.println("Se ha creado Album Rojo");
            }else{
                System.out.println("No se ha creado Album Rojo. Codigo: "+clientResource.getStatus());
            }
        }catch(ResourceException a) {
            System.err.println("Error al crear Album Rojo. Codigo: "+a.getStatus().getCode() + "\nDescripcion: " + a.getStatus().getDescription());
        }

        System.out.println("\n----Listando albumes del grupo musical Jamiroquai----");
        //Listar albumes del grupo musical Jamiroquai (plano y html)
        clientResource.getRequest().setResourceRef(uriBase+"gruposmusicales/"+gmJamiroquai+"/albumes/");
        try{
            Representation response = clientResource.get(MediaType.TEXT_PLAIN);
            if(clientResource.getStatus().equals(Status.SUCCESS_OK)){
                System.out.println("Texto plano: \n"+response.getText());
            }else{
                System.out.println("No hay albumes");
            }
            response = clientResource.get(MediaType.TEXT_HTML);
            if(clientResource.getStatus().equals(Status.SUCCESS_OK)){
                System.out.println("Texto HTML: \n"+response.getText());
            }else{
                System.out.println("No hay albumes");
            }
        }catch (ResourceException a){
            System.err.println("Error al listar albumes. Codigo: "+a.getStatus().getCode()
                    +"\nDescripcion" +a.getStatus().getDescription());
        }



        //Crear pistas del album a0
        System.out.println("\n----Inicio de creacion de pistas----");

        //Azul
        clientResource.getRequest().setResourceRef(uriBase+"gruposmusicales/"+gmJamiroquai+"/albumes/a0/pistas/");
        try {
            Form pista = new Form();
            pista.add("nombre","Flor");
            pista.add("duracion","300");

            clientResource.post(pista);
            if(clientResource.getStatus().equals(Status.SUCCESS_CREATED)){
                System.out.println("Se ha creado Pista Flor");
            }else{
                System.out.println("No se ha creado Pista FLor. Codigo: "+clientResource.getStatus());
            }
        }catch(ResourceException a) {
            System.err.println("Error al crear Pista FLor. Codigo: "+a.getStatus().getCode() + "\nDescripcion: " + a.getStatus().getDescription());
        }        //a0
        clientResource.getRequest().setResourceRef(uriBase+"gruposmusicales/"+gmJamiroquai+"/albumes/a0/pistas/");
        try {
            Form pista = new Form();
            pista.add("nombre","Tierra");
            pista.add("duracion","500");

            clientResource.post(pista);
            if(clientResource.getStatus().equals(Status.SUCCESS_CREATED)){
                System.out.println("Se ha creado Pista Tierra");
            }else{
                System.out.println("No se ha creado Pista Tierra. Codigo: "+clientResource.getStatus());
            }
        }catch(ResourceException a) {
            System.err.println("Error al crear Pista Tierra. Codigo: "+a.getStatus().getCode() + "\nDescripcion: " + a.getStatus().getDescription());
        }

        System.out.println("\n----Listando pistas del album Azul----");
        //Listar pistas del album Azul (plano y html)
        clientResource.getRequest().setResourceRef(uriBase+"gruposmusicales/"+gmJamiroquai+"/albumes/a0/pistas/");
        try{
            Representation response = clientResource.get(MediaType.TEXT_PLAIN);
            if(clientResource.getStatus().equals(Status.SUCCESS_OK)){
                System.out.println("Texto plano: \n"+response.getText());
            }else{
                System.out.println("No hay pistas");
            }
            response = clientResource.get(MediaType.TEXT_HTML);
            if(clientResource.getStatus().equals(Status.SUCCESS_OK)){
                System.out.println("Texto HTML: \n"+response.getText());
            }else{
                System.out.println("No hay pistas");
            }
        }catch (ResourceException a){
            System.err.println("Error al listar pistas. Codigo: "+a.getStatus().getCode()
                    +"\nDescripcion" +a.getStatus().getDescription());
        }


        System.out.println("\n----Eliminando una pista del album Azul----");
        //Eliminar la pista Flor del album Azul (plano y html)
        clientResource.getRequest().setResourceRef(uriBase+"gruposmusicales/"+gmJamiroquai+"/albumes/a0/pistas/p0");
        try {
            clientResource.delete();
            if(clientResource.getStatus().equals(Status.SUCCESS_NO_CONTENT)){
                System.out.println("Se ha eliminado Pista Flor");
            }else{
                System.out.println("No se ha eliminado Pista Flor. Codigo: "+clientResource.getStatus());
            }
        }catch(ResourceException a) {
            System.err.println("Error al eliminar Pista Flor. Codigo: "+a.getStatus().getCode() + "\nDescripcion: " + a.getStatus().getDescription());
        }
    }
}
