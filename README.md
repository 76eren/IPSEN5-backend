# IPSEN5-backend
Dit project is een backend applicatie voor een werkplek reserveringsapp, ontwikkeld met Java en Spring Boot. Het maakt gebruik van Maven voor dependency management en heeft PostgreSQL als databasebeheersysteem.

## Installatie
Volg deze stappen uit om de applicatie te installeren en te draaien:
1. Clone de repository:
```bash
  git clone https://github.com/jouw-gebruikersnaam/werkplek-reserveringsapp.git
  cd werkplek-reserveringsapp
```
2. Installeer de dependencies
  ```bash
  mvn clean install
```
3. Configureer de database
4. Configureer de applicatie
   Kopieer example-application.properties naar application.properties:
```bash   
   cp src/main/resources/example-application.properties src/main/resources/application.properties
```
   Pas src/main/resources/application.properties aan met de juiste database configuratie.


 ## Gebruik
Start de applicatie met het volgende Maven commando:
```bash
mvn spring-boot:run
```
De applicatie draait nu op http://localhost:8080.



