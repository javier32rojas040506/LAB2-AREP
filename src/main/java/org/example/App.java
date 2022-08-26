package org.example;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Hello world!
 *
 */
public class App 
{
    static URL personalSite;
    public static void main( String[] args ) throws IOException {
        try {
            personalSite = new URL("http://ldbn.is.escuelaing.edu.co/index.html");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println( personalSite.getProtocol());
        System.out.println(personalSite.getAuthority());
        System.out.println(personalSite.getHost());
        System.out.println(personalSite.getPort());
        System.out.println(personalSite.getPath());
        System.out.println(personalSite.getQuery());
        System.out.println(personalSite.getFile());
        System.out.println(personalSite.getRef());
        System.out.println( "END" );
        // Crea el objeto que representa una URL
        URL siteURL = new URL("http://ldbn.is.escuelaing.edu.co/index.html");
        // Crea el objeto que URLConnection
        URLConnection urlConnection = siteURL.openConnection();
        // Obtiene los campos del encabezado y los almacena en un estructura Map
        Map<String, List<String>> headers = urlConnection.getHeaderFields();
        // Obtiene una vista del mapa como conjunto de pares <K,V>
        // para poder navegarlo
         Set<Map.Entry<String, List<String>>> entrySet = headers.entrySet();
         // Recorre la lista de campos e imprime los valores
        for (Map.Entry<String, List<String>> entry : entrySet) {
             String headerName = entry.getKey();
        //Si el nombre es nulo, significa que es la linea de estado
             if(headerName !=null){System.out.print(headerName + ":");}
            List<String> headerValues = entry.getValue();
            for (String value : headerValues) {
                System.out.print(value);
                }
            System.out.println("");
            //System.out.println("");
        }
        System.out.println("-------message-body------");
        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
    }

}
