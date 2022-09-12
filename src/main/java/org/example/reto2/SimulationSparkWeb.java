package org.example.reto2;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;

public class SimulationSparkWeb {
    public static String URItoAttend;
    public static SparkMethodGet actionToAttend;

    //getter and setters
    public static SparkMethodGet getActionToAttend() {
        return actionToAttend;
    }

    public static void setActionToAttend(SparkMethodGet actionToAttend) {
        SimulationSparkWeb.actionToAttend = actionToAttend;
    }

    public static String getURItoAttend() {
        return URItoAttend;
    }
    public static void setURItoAttend(String URItoAttend) {
        SimulationSparkWeb.URItoAttend = URItoAttend;
    }

    public static void start() throws IOException {
        ServerSocket serverSocket = null;
        //Integer puerto = new Integer(System.getenv("PORT"));
        Integer puerto =  35000;
        try {
            serverSocket = new ServerSocket(puerto);
            System.out.println("server sock"+ serverSocket);

        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        boolean running = true;
        Socket clientSocket = null;
        while (running) {
            System.out.println("====================NEW REQ==========================");
            try {
                System.out.println("Listo para recibir");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            // Buffer Inputs and Outputs
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            //variables for read data
            String inputLine, outputLine;
            URI path=null;
            outputLine = "";
            boolean firstLine = true;

            //read data
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                if(firstLine){
                    try {
                        path = new URI(inputLine.split(" ")[1]);
                    } catch (URISyntaxException e){
                        //logger.getLogger(HttpServer.class.getName().log(Level.SEVERE,null, e));
                    }
                    System.out.println("parsed path: " + path);
                    System.out.println("Uri to attend: " + URItoAttend);
                    System.out.println("Query: " + path.getQuery());
                    firstLine = false;
                    String responseBody = "";

                    //end points to attend
                    if(path.getPath().equals(URItoAttend)){
                        SparkWebRequest request = new SparkWebRequest();
                        SparkWebResponse response = new SparkWebResponse();
                        responseBody = getActionToAttend().getSparkMethod(request, response);
                        System.out.println("ResponseBody: " + responseBody);
                        outputLine = builtOutputLine(responseBody);
                    }
                }
                if (!in.ready()) {
                    break;
                }

            }
            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }



    /**
     * Method that give the base html
     * @return String with base html
     */
    public static String  getIndexHtml()    {
        return  "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>Form Example</title>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <h1>Form with GET</h1>\n" +
                "        <form action=\"/hello\">\n" +
                "            <label for=\"name\">Name:</label><br>\n" +
                "            <input type=\"text\" id=\"name\" name=\"name\" value=\"John\"><br><br>\n" +
                "            <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n" +
                "        </form> \n" +
                "        <div id=\"getrespmsg\"></div>\n" +
                "\n" +
                "        <script>\n" +
                "            function loadGetMsg() {\n" +
                "                let nameVar = document.getElementById(\"name\").value;\n" +
                "                const xhttp = new XMLHttpRequest();\n" +
                "                xhttp.onload = function() {\n" +
                "                    document.getElementById(\"getrespmsg\").innerHTML =\n" +
                "                    this.responseText;\n" +
                "                }\n" +
                "                xhttp.open(\"GET\", \"/hello?name=\"+nameVar);\n" +
                "                xhttp.send();\n" +
                "            }\n" +
                "        </script>\n" +
                "\n" +
                "        <h1>Form with POST</h1>\n" +
                "        <form action=\"/hellopost\">\n" +
                "            <label for=\"postname\">Name:</label><br>\n" +
                "            <input type=\"text\" id=\"postname\" name=\"name\" value=\"John\"><br><br>\n" +
                "            <input type=\"button\" value=\"Submit\" onclick=\"loadPostMsg(postname)\">\n" +
                "        </form>\n" +
                "        \n" +
                "        <div id=\"postrespmsg\"></div>\n" +
                "        \n" +
                "        <script>\n" +
                "            function loadPostMsg(name){\n" +
                "                let url = \"/hellopost?name=\" + name.value;\n" +
                "\n" +
                "                fetch (url, {method: 'POST'})\n" +
                "                    .then(x => x.text())\n" +
                "                    .then(y => document.getElementById(\"postrespmsg\").innerHTML = y);\n" +
                "            }\n" +
                "        </script>\n" +
                "    </body>\n" +
                "</html>";
    }

    /**
     * Method to get a static file instring format
     * @param route String from the path for find file
     * @return the data of file in a String
     */
    public static String getFile(String route) {
        Path file = FileSystems.getDefault().getPath("src/main/resources/public", route);
        Charset charset = Charset.forName("US-ASCII");
        String web = new String();
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                web+=line+"\n";
            }
        } catch (IOException x) {
            web = "Not Found";
        }
        return web;
    }

    public static String builtOutputLine(String responseBody){
        return  "HTTP/1.1 200 OK \r\n"
                + "Content-Type: text/html \r\n"
                + "\r\n"
                + "\n"
                + responseBody;
    }
    private static String builtOutputLineImg(String responseBody) {
        System.out.println("response Body"+ responseBody);
        return  "HTTP/1.1 200 OK \r\n"
                + "Content-Type: image/jpg \r\n"
                + "\r\n";
    }

    public static void get(String uri, SparkMethodGet sparkMethodGet){
        setURItoAttend(uri);
        setActionToAttend(sparkMethodGet);
    }
}
