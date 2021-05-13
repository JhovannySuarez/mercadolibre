# Mercadolibre xmen challenge


El código fuente en este repositorio es para dar solución al reto técnico X-Men de Mercado libre

# Prerequisitos
 - MYSQL Database Version 14.14 Distrib 5.7.30
 - Java version 11
 - Maven


Para ejecutar localmente siga estos pasos:

* Descargue o clone este proyecto 
* Importar el script import.sql en MYSQL DB
* Ir a la carpeta base del proyecto ej: "cd mercadolibre"
* Ejecutar los siguientes comandos: 
    - mvn clean package
    - mvn install
    - mvn spring-boot:run -Dspring-boot.run.profiles=

# Default Port Mappings
| Service Name | Port | 
| --------| -----|
| Xmen Service | 8080 |

Después de realizar estos pasos, el proyecto estará listo para ser probado con POSTMAN


# Ejemplo de las peticiones para análisi de ADN
    
    Petición ambiente productivo
    POST https://xmen-challenge-ml.herokuapp.com/mutant/        
    {
      "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
    }

    Petición ambiente local
    POST http://localhost:8080/mutant/        
    {
      "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
    }


# Ejemplo de las peticiones obteter estadisticas

    Petición ambiente productivo
    GET https://xmen-challenge-ml.herokuapp.com/stats/        

    Petición ambiente local
    POST http://localhost:8080/stats/     


Los links de la documentación 
* [Coverage](https://xmen-challenge-ml.herokuapp.com/coverage/index.html)
* [Swagger](https://xmen-challenge-ml.herokuapp.com/swagger-ui/)




 
