package sgae.util;

/**
 * Clase con utilidades para el procesamiento de URLs
 * 
 * @author Eduardo Gómez Sánchez y Manuel Rodríguez Cayetano. ETSIT UVa.
 * @version 1.0
 *
 */
public class URLUtils {
    /**
     * Método para extraer el último segmento del URL
     * (extraído de <a href="http://stackoverflow.com/questions/4050087/how-to-obtain-the-last-path-segment-of-an-uri">
     * http://stackoverflow.com/questions/4050087/how-to-obtain-the-last-path-segment-of-an-uri</a>)
     * 
     * <br>
     * 
     * Explanation of 
     * <code>return url.replaceFirst(".&lowast;/([^/?]+).&lowast;", "$1");
     * </code> :
     * <ul>
     * <li><code>.&lowast;/</code> find anything up to the last / character</li>
     * 
     * <li><code>([^/?]+) </code> find (and capture) all following characters 
     * up to the next / or ? the + makes sure that at least 1 character is 
     * matched</li>
     * <li><code>.&lowast;</code> find all following characters</li>
     * <li><code>$1</code> this variable references the saved second group 
     * from above
     *    I.e. the entire string is replaces with just the portion
     *    captured by the parentheses above</li>
     * </ul>
     * @param url URL que se desea procesar
     * @return el último segmento del URL (sin consultas)
     */
    public static String getLastPathFromUrl(final String url){
        if (url == null)
            return null;
        // return url.replaceFirst("[^?]*/(.*?)(?:\\?.*)","$1);" <-- incorrect
        return url.replaceFirst(".*/([^/?]+).*", "$1");
    }
}
