
# Proyecto para consultar acciones

### Autor: Francisco Javier Rojas M
### Escuela Colombiana de Ingeniería Julio Garavito
### AREP G1
### Fecha: 9 de Septiembre de 2022


[Heroku](https://lab02arep.herokuapp.com/)

[GitHub](https://github.com/javier32rojas040506/LAB2-AREP.git)

## PROYECTO:
Esta aplicacion consta de una funcionalidad en la cual se pueda visualizar las acciones del mercado valores esto soportado en
una arquitectura que es el enfoque de la aplicacion de este proyecto, por esto se  debe dar solucion al problema.

#### PROBLEMA:
Usted debe construir una aplicación para consultar el mercado de valores de las acciones negociadas en Bolsa.
La aplicación recibirá el identificador de una acción, por ejemplo “MSFT” para Microsoft y deberá mostrar el histórico
de la valoración intra-día, diaria, semanal y mensual.

---

## Iniciando

puede clonar y alistar el repo con el siguiente comando

```
  git clone https://github.com/javier32rojas040506/LAB1-AREP.git
```
```
  cd LAB1-AREP
```
### Prerequisites

es necesario tener java 8 y maven pruebello con el siguiente comando
![](.README_images/f8ca9a5b.png)

```
mvn --version
```

### Installing

puede habilitar su IDE para correr el programa con maven en caso de InteliJ
![](.README_images/6385faf8.png)
y darler run a la clase main. Por otro lado tambien puede corre el proyecto con los siguientes comandos

![](.README_images/6d2eb383.png)
```
mvn package
```

Y luego

![](.README_images/b97d145b.png)
```
java -cp "target/classes;target/dependency/*" edu.escuelaing.arep.SparkWebApp
```

## Built With

* [Spark](https://sparkjava.com/documentation#views-and-templates) - Framework API
* [Maven](https://maven.apache.org/) - Manejo de dependencias
* [JavaScript](https://developer.mozilla.org/es/docs/Web/JavaScript) - Statics files (frontend)


## Authors

* **Francisco Javier Rojas** - *lab01 AERP* - [javier32rojas040506](https://github.com/javier32rojas040506)


---

### DESCRIPCIÓN  DE LA ARQUITECTURA:

![](.README_images/18e55e62.png)

##### Requerimientos:
1) El cliente Web debe ser un cliente asíncrono que use servicios REST desplegados en Heroku y use Json como formato 
para los mensajes.
2) El servidor de Heroku servirá como un gateway para encapsular llamadas a otros servicios Web externos.
3) La aplicación debe ser multiusuario.
4) Todos los protocolos de comunicación serán sobre HTTP.
5) Los formatos de los mensajes de intercambio serán siempre JSON.
6) La interfaz gráfica del cliente debe ser los más limpia y agradable posible solo HTML y JS 
(Evite usar librerías complejas). Para invocar métodos REST desde el cliente usted puede utilizar la tecnología que 
desee.
7) Debe construir un cliente Java que permita probar las funciones del servidor fachada.
El cliente utiliza simples conexiones http para conectarse a los servicios. 
Este cliente debe hacer pruebas de concurrencia en su servidor de backend.
8) La fachada de servicios tendrá un caché que permitirá que llamados que ya se han realizado a las implementaciones
concretas con parámetros específicos no se realicen nuevamente. Puede almacenar el llamado como un String con su
respectiva respuesta, y comparar el string respectivo. Recuerde que el caché es una simple estructura de datos.
9) Se debe poder extender fácilmente, por ejemplo, es fácil agregar nuevas funcionalidades, o es fácil cambiar el
proveedor de una funcionalidad.
10) Debe utilizar maven para gestionar el ciclo de vida, git y github para almacenar al código fuente y heroku como 
plataforma de producción.

#### EXPONIENDO LA ARQUITECTURA
- ##### Spark web REST API Facade in Heroku || SparkWebApp
  * ~~~
    primero tenemos la estructura: 
    ~~~
    ![](.README_images/76ab0d69.png)

  * ~~~
    en la siguinete imagen se muestra como el contentType es JSON
    ~~~
    ![](.README_images/c44df07e.png)

  * ~~~
    es la clase que atiende y los endpoints
    ~~~
    ![](.README_images/d53adfa8.png)
  
  * ~~~
    podemos agregar todas las APIs en este  lugar,
    es importante agregar el query param name en 
    donde va el name stock de la api a consultar
    ~~~
    ![](.README_images/09f39f8d.png)
  * ~~~
    aqui se definen los metodos
    ~~~
    ![](.README_images/bfc2acf2.png)
  
  ##### JS WEB client
  * ~~~
    El servidor usa fetch para traer la informacion via Json
    ~~~
    ![](.README_images/23d94ecc.png)
  ##### EXTERNAL APIs
  * ~~~
       External APIs are
     ~~~
     ![](.README_images/04768b7c.png)
     ![](.README_images/0b96cfca.png)

#### EXTENSIBILIDAD:
La aplicación fue diseñada de manera que se pueda agregar nuevos componentes sin afectar los anteriores, principio Open
Close.

##### Ejemplo de nuevo componente
* ~~~
    podemos agregar todas las APIs en este  lugar,
    es importante agregar el query param name en donde
    va el name stock de la api a consultar:
  
    private static final String API_URL_ORIGINAL = "https://api.stock/IBM";
    POR
    private static final String API_URL_ORIGINAL = "https://api.stock/name";
  ~~~
    ![](.README_images/09f39f8d.png)

* ~~~
    definir metodo con el verbo correspondiente y el endpoint
    HTTP-VERB("/EndpoitName", "application/json",
                (request, response) -> {
                    String name = request.queryParams("name");
                    return ActionsStock.getStockByName(name, API_URL_2, JSON_KEY);
        });
    
   -> name name of the Stock to search
   -> GET_URL address of the API to search
   -> JSON_KEY json key to identify data an extract it
    
    
  EXAMPLE OF JSON_KEY
    DATA:{
        DATA2:{
          NAME:NOMBRE
          age:21
         }
    }
  JSON_KEY FROM THE PREVIUS JSON IS DATA
    
  ~~~
  ![](.README_images/bfc2acf2.png)
  
  ~~~
    probar en postman
  ~~~
  ![](.README_images/91e04351.png)
  ![](.README_images/7a894d98.png)

  ~~~
  Tambien es posible extender la aplicacion para parsear y manejar las peticiones a la api de vairas maneras, ya que 
  estamos usando es principio OpenClose y Inversion de Dependencias mediante la interface ActionStock. Puede agrgar una
  clase que implenete a esta interface 
  ~~~
  ![](.README_images/39608547.png)
  ![](.README_images/318725ff.png)
  ~~~
    ahora puede mapearlo al gusto en el html de la aplicacion o incluso incorporrarlo a
  su propia API
  ~~~