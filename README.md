# Mercadolibre xmen challenge

Source code in this repository is to solve the mercadolibre xmen challenge.

# Prerequisites
 - MYSQL Database Version 14.14 Distrib 5.7.30
 - Java version 11
 - Maven


# Default Port Mappings
| Service Name | Port | 
| --------| -----|
| Xmen Service | 8080 |

Local set up steps 

* Download or clone the project 
* Move to the project base folder ex: "cd mercadolibre"
* Execute the following commads: 
    - Import the script import.sql into MYSQL DB
    - mvn clean package
    - mvn install
    - mvn spring-boot:run -Dspring-boot.run.profiles=
    
After these steps the project will be running and ready to be tested with POSTMAN



 