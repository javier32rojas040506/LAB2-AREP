package org.example.reto2;

import java.io.IOException;

public class SimulationSparkWebTest {
    public static void main(String[] args) {


        //end point example in spark web
        SimulationSparkWeb.get("/hello", (request, response) -> {
            //String name = request.queryParams("name");
            return "Hello Heroku :) ";
        });
        SimulationSparkWeb.get("/", (request, response) -> {
            //String name = request.queryParams("name");
            return "Hello from Server done by simutalion Spring ";
        });
        SimulationSparkWeb.get("/index.html", (request, response) -> {
            //String name = request.queryParams("name");
            return "/index.html";
        });
        SimulationSparkWeb.get("/eci.jpg", (request, response) -> {
            //String name = request.queryParams("name");
            return "/eci.jpg";
        });
        SimulationSparkWeb.get("/entrada.png", (request, response) -> {
            //String name = request.queryParams("name");
            return "/entrada.png";
        });
        SimulationSparkWeb.get("/index.css", (request, response) -> {
            //String name = request.queryParams("name");
            return "/index.css";
        });
        //Opening the Server
        try {
            SimulationSparkWeb.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        /**
        //end point to get data via GET
        get("/data", (request, response) -> {
            response.type("application/json");
            String name = request.queryParams("name");
            return api1.getStockByName(name, API_URL_1, "Time Series (5min)");
        });
        //end point to get data via POST
        post("/databypost", "application/json",
                (request, response) -> {
                    String name = request.queryParams("name");
                    return api2.getStockByName(name, API_URL_2, null);
                });
        */
    }
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
}
