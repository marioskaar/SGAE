package sgae.clientes;
import java.text.ParseException;
import java.util.List;

import sgae.nucleo.gruposMusicales.ControladorGruposMusicales;
import sgae.nucleo.gruposMusicales.ExcepcionGruposMusicales;
import sgae.nucleo.discograficas.ControladorDiscograficas;
import sgae.nucleo.discograficas.ExcepcionContratos;
import sgae.nucleo.discograficas.ExcepcionDiscograficas;
import sgae.nucleo.discograficas.InterfazControladorDiscograficas;
import sgae.nucleo.personas.ControladorPersonas;
import sgae.nucleo.personas.ExcepcionPersonas;
import sgae.nucleo.personas.InterfazControladorPersonas;
import sgae.nucleo.personas.ControladorPersonas;
/**
 * Clase de prueba de la aplicación.
 * @author Eduardo Gómez Sánchez y Manuel Rodríguez Cayetano. ETSIT UVa.
 * @version 1.0
 *
 */
public class PruebasSGAE {
	/**
	 * Método principal para arrancar la aplicación.
	 * @param args lista de argumentos de ejecución del programa
	 */
	/**
	 * @param args argumentos de ejecución del programa
	 */
	public static void main(String[] args) {
		// Lo primero es crear los controladores que sirven de punto de acceso a la capa de negocio
		InterfazControladorPersonas cp = new ControladorPersonas();
		ControladorGruposMusicales cgm = 
				new ControladorGruposMusicales(cp);
		InterfazControladorDiscograficas cd = new ControladorDiscograficas(cgm);
	
		
		//Variable auxiliar para almacenar listados
		List<String> listado;
		//Variable auxiliar para almacenar la ficha de un ítem
		String fichaCompleta;
		//Identificadores de persona, para asignarlos en la creación y hacer referencias después
		String pBart = "00000000A",
			   pLisa = "11111111B",
			   pMaggie = "22222222C",
			   pHomer = "33333333D",
			   pMarge = "44444444E",
			   pAbe = "55555555F",
			   pBurns = "66666666G",
			   pCarl = "77777777H",
			   pSkinner = "88888888I";

		///////////////////////////////////////////////////////////////////////
		// CASOS DE USO DE PERSONAS ///////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////
		try {
			// Creación de personas
			System.out.println("Comenzando creación de personas...");
			cp.crearPersona(pBart, "Bart", "Simpson", 
					null);
			cp.crearPersona(pLisa, "Lisa", "Simpson", 
					"02-02-2005");
			cp.crearPersona(pMaggie, "Maggie", "Simpson", 
					"03-03-2014");
			cp.crearPersona(pHomer, "Homer", "Simpson", 
					"01-08-1970");
			cp.crearPersona(pMarge, "Margaret", "Simpson", 
					"01-10-1975");			
			cp.crearPersona(pAbe, "Abe", "Simpson", 
					"01-03-1940");			
			cp.crearPersona(pBurns, "Monty", "Burns", 
					"01-06-1930");			
			cp.crearPersona(pCarl, "Carl", "Carlson", 
					"01-07-1965");			
			cp.crearPersona(pSkinner, "Seymor", "Skinner", 
					"01-08-1969");				
			System.out.println("Creación de personas completada\n");

			// Listado de personas
			System.out.println("Comenzando listado de personas...");
			listado = cp.listarPersonas();
			for(String s : listado) {
				System.out.println(s);
			}
			System.out.println("Listado de personas completado\n");

			// Visión detallada de personas
			System.out.println("Comenzando visión detallada de personas...");
			fichaCompleta = cp.verPersona(pMarge);
			System.out.println("Datos de Marge Simpson:\n" + fichaCompleta);
			fichaCompleta = cp.verPersona(pAbe);
			System.out.println("Datos de Abe Simpson:\n" + fichaCompleta);
			System.out.println("Visión detallada de personas completada\n");
			
			// Modificación de personas
			System.out.println("Comenzando modificación de personas...");
			cp.modificarPersona(pMarge, "Marge", "Simpson", 
					"01-10-1975");			
			cp.modificarPersona(pAbe, "Abe", "Simpson", 
					"01-03-1940");
			System.out.println("Modificación de personas completada\n");

			// Visión detallada de personas
			System.out.println("Comenzando visión detallada de personas...");
			fichaCompleta = cp.verPersona(pMarge);
			System.out.println("Datos de Marge Simpson:\n" + fichaCompleta);
			fichaCompleta = cp.verPersona(pAbe);
			System.out.println("Datos de Abe Simpson:\n" + fichaCompleta);
			System.out.println("Visión detallada de personas completada\n");
			
			// Borrado de personas
			System.out.println("Comenzando borrado de personas...");
			cp.borrarPersona(pAbe); // Borramos a Abe Simpson
			System.out.println("Borrado completado\n");
			
			// Listado de personas
			System.out.println("Comenzando listado de personas...");
			listado = cp.listarPersonas();
			for(String s : listado) {
				System.out.println(s);
			}
			System.out.println("Listado de personas completado\n");
			
		} catch (ParseException e) {
			System.err.println("Alguna de las fechas proporcionadas no es válida.");
		} catch (ExcepcionPersonas e) {
			System.err.println("Ha fallado una operación para la persona con DNI " + 
					e.getDniPersona() + " por la siguiente razón: " + 
					e.getCausaFallo());
		}
		
		
		///////////////////////////////////////////////////////////////////////
		/// CASOS DE USO DE GRUPOS MUSICALES //////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////
		
		try {
			// Creación de grupos musicales
			System.out.println("Comenzando creación de grupos musicales...");
			cgm.crearGrupoMusical("D0123456D", "Jamiroquai", "02-04-1992");
			cgm.crearGrupoMusical("E0123456E", "Blur", "03-05-1988");
			System.out.println("Creación de grupos musicales completada\n");

			// Listado de grupos musicales
			System.out.println("Comenzando listado de grupos musicales...");
			listado = cgm.listarGruposMusicales();
			for(String s : listado) {
				System.out.println(s);
			}
			System.out.println("Listado de grupos musicales completado\n");

			// Visión detallada de grupos musicales
			System.out.println("Comenzando visión detallada de grupos musicales...");
			String fichaDetallada = cgm.verGrupoMusical("D0123456D");
			System.out.println("Datos del grupo musical:\n" + fichaDetallada);
			fichaDetallada = cgm.verGrupoMusical("E0123456E");
			System.out.println("Datos del grupo musical:\n" + fichaDetallada);
			System.out.println("Visión detallada de grupos musicales completada\n");
			
			// Modificación de grupos musicales
			System.out.println("Comenzando modificación de compañías discográficas...");
			cgm.modificarGrupoMusical("D0123456D", "Jamiroquai", "01-06-1992");
			cgm.modificarGrupoMusical("E0123456E", "Blur", "01-05-1988");
			System.out.println("Modificación de grupos musicales completada\n");

			// Visión detallada de grupos musicales
			System.out.println("Comenzando visión detallada de grupos musicales...");
			fichaDetallada = cgm.verGrupoMusical("D0123456D");
			System.out.println("Datos del grupo musical:\n" + fichaDetallada);
			fichaDetallada = cgm.verGrupoMusical("E0123456E");
			System.out.println("Datos del grupo musical:\n" + fichaDetallada);
			System.out.println("Visión detallada de grupos musicales completada\n");
			
			// Borrado de grupos musicales
			System.out.println("Comenzando borrado de grupos musicales...");
			cgm.borrarGrupoMusical("E0123456E");
			System.out.println("Borrado de grupos musicales completada\n");
			
			// Listado de grupos musicales
			System.out.println("Comenzando listado de grupos musicales...");
			listado = cgm.listarGruposMusicales();
			for(String s : listado) {
				System.out.println(s);
			}
			System.out.println("Listado de grupos musicales completado\n");

			} catch (ExcepcionGruposMusicales e) {
				System.err.println("Ha fallado una operación para la discográfica con CIF " + 
						e.getCif() + " por la siguiente razón: " + 
						e.getCausaFallo());
			} catch (ParseException e) {
				System.err.println("Alguna de las fechas proporcionadas no es válida.");
		}
		
		///////////////////////////////////////////////////////////////////////
		/// CASOS DE USO DE DISCOGRÁFICAS //////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////
		
		try {
			// Creación de compañías discográficas
			System.out.println("Comenzando creación de compañías discográficas...");
			try {
				cd.crearDiscografica("A0123456A", "CBS", "La Casa Blanca");
				cd.crearDiscografica("B0123456B", "Virgin Records", "Las Vegas");
				cd.crearDiscografica("C0123456C", "Dro", "Madrid");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			System.out.println("Creación de compañías discográficas completada\n");

			// Listado de compañías discográficas
			System.out.println("Comenzando listado de compañías discográficas...");
			listado = cd.listarDiscograficas();
			for(String s : listado) {
				System.out.println(s);
			}
			System.out.println("Listado de compañías discográficas completado\n");

			// Visión detallada de compañías discográficas
			System.out.println("Comenzando visión detallada de compañías discográficas...");
			String fichaDetallada = cd.verDiscografica("A0123456A");
			System.out.println("Datos de la compañía:\n" + fichaDetallada);
			fichaDetallada = cd.verDiscografica("B0123456B");
			System.out.println("Datos de la compañía:\n" + fichaDetallada);
			System.out.println("Visión detallada de compañías discográficas completada\n");
			
			// Modificación de compañías discográficas
			System.out.println("Comenzando modificación de compañías discográficas...");
			try {
				cd.modificarDiscografica("B0123456B", "Virgin Records", "Londres");
				cd.modificarDiscografica("A0123456A", "CBS", "Springfield");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			System.out.println("Modificación de compañías discográficas completada\n");

			// Visión detallada de compañías discográficas
			System.out.println("Comenzando visión detallada de compañías discográficas...");
			fichaDetallada = cd.verDiscografica("A0123456A");
			System.out.println("Datos de la compañía:\n" + fichaDetallada);
			fichaDetallada = cd.verDiscografica("B0123456B");
			System.out.println("Datos de la compañía:\n" + fichaDetallada);
			System.out.println("Visión detallada de compañías discográficas completada\n");
			
			// Borrado de compañías discográficas
			System.out.println("Comenzando borrado de compañías discográficas...");
			cd.borrarDiscografica("B0123456B");
			System.out.println("Borrado de compañías discográficas completada\n");
			
			// Listado de compañías discográficas
			System.out.println("Comenzando listado de compañías discográficas...");
			listado = cd.listarDiscograficas();
			for(String s : listado) {
				System.out.println(s);
			}
			System.out.println("Listado de compañías discográficas completado\n");

			} catch (ExcepcionDiscograficas e) {
				System.err.println("Ha fallado una operación para la discográfica con CIF " + 
						e.getCif() + " por la siguiente razón: " + 
					e	.getCausaFallo());
		}

	
		///////////////////////////////////////////////////////////////////////
		// CASOS DE USO DE CONTRATOS //////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////
		try {
			// Creación de contratos 
			System.out.println("Comenzando creación de contratos sin partir "+ 
					"de una oferta...");
			try {
				cd.nuevoContrato("A0123456A", "D0123456D",
                        "01-01-2017", "01-01-2018", 3000); // A Jamiroquai en la CBS
				cd.nuevoContrato("C0123456C", "E0123456E",
						"01-01-2016", "01-01-2018", 1000);	// A Blur en Dro
			} catch (ParseException e) {
				e.printStackTrace();
			}
			System.out.println("Creación de contratos completada\n");
			
			// Listado de contratos por compañías discográficas
			System.out.println("Comenzando listado de contratos por compañías discográficas...");
			listado = cd.verContratosVigentesDeDiscografica("A0123456A");
			System.out.println("Contratos de la compañía discográfica");
			for(String s : listado) {
				System.out.println(s);
			}
			listado = cd.verContratosAnterioresDeDiscografica("C0123456C");
			System.out.println("Contratos del grupo musical");
			for(String s : listado) {
				System.out.println(s);
			}
			System.out.println("Listado de contratos por compañías discográficas completado\n");
			
			// Finalización de contratos
			System.out.println("Comenzando finalización de contratos...");
			cd.terminarContrato("A0123456A", "00000000A"); 
			cd.terminarContrato("C0123456C", "11111111B"); 
			System.out.println("Finalización de contratos completada\n");

			// Listado de contratos por compañías discográficas
			System.out.println("Comenzando listado de contratos por compañías discográficas...");
			listado = cd.verContratosVigentesDeDiscografica("A0123456A");
			System.out.println("Contratos de la compañia discográfica");
			for(String s : listado) {
				System.out.println(s);
			}
			listado = cd.verContratosVigentesDeDiscografica("C0123456C");
			System.out.println("Contratos de la compañia discográfica");
			for(String s : listado) {
				System.out.println(s);
			}
			System.out.println("Listado de contratos por compañías discográficas completado\n");
	
			
		} catch (ExcepcionContratos e) {
			System.err.println("Ha fallado una operación para el contrato con id " + 
					e.getIdContrato() + " por la siguiente razón: " + 
					e.getCausaFallo());
		} catch (ExcepcionDiscograficas e) {
			System.err.println("Ha fallado una operación para la compañía discográfica con CIF " + 
				e.getCif() + " por la siguiente razón: " + 
				e.getCausaFallo());
		}

		// // /////////////////////////////////////////////////////////////////
		// CASOS DE USO QUE OBTIENEN ESTADÍSTICAS /////////////////////////////
		///////////////////////////////////////////////////////////////////////

		//Población activa
		System.out.println("Comenzando recuperación de estadísticas...");
		int numeroPersonas = cp.verNumeroPersonas();
		System.out.println("Total personas: " + numeroPersonas);
		System.out.println("Recuperación de estadísticas completada\n");
	}
}
