[![Build Status](https://travis-ci.org/wenzaca/Tokyo.Olympic.Games.svg?branch=master)](https://travis-ci.org/wenzaca/Tokyo.Olympic.Games)

# Hands on - Tokyo 2020 Olympic Games
## Overview
The 'Tokyo.Olympic.Games' is a 'Spring-Boot / Spring-Cloud / Spring-Netflix' - Middle Tier Service.

* Spring MVC-REST
* Swagger UI
* Java 8
* Discovery Server registration via Eureka-Client
* Hystrix Circuit Breaking - NetflixOSS
* Hystrix Dashboard
* H2 data base
* Test Tests
	* JUnit
	* MockMVC
	* AssertJ

## How to run
### Spring Boot Plugin

#### Linux/OSX
	LOCALHOST - w/o Discovery Server Registration
		gradle bootRun

#### Windows
	LOCALHOST - w/o Discovery Server Registration
		gradle bootRun

### Swagger UI - Local
	http://localhost:8090/swagger-ui.html

## Database
The data is a Create type, so every time the Application run it resets the persisted Data. It is a in memory database. 
For more information see [H2-Website](http://www.h2database.com/html/main.html, "H2")

## Rest
The application has two endpoints one for persisting Games and the second for retrieving the games.
### Post
The method is available in the URL below, and it is responsible for saving new competitions.

```POST http://localhost:8090/games```

In the body of this requisition it is required a Game as Json.
The application will validate:
* If there any null or empty data;
* If the countries is the same for non FINAL or SEMIFINAL stages;
* If the competition duration is over 30 minutes;
* If the begin time is over the end time;
* If there is ano other competition in the same local, type and time;
* If there is more than 4 competitions happening in the same day and local;
### Get
The method is available in the URL below, and it is responsible for retrieving competitions based in a non required modality filter.

```GET http://localhost:8090/games```

In the body of this requisition it is required a Game as Json.

## Test
### Unit
Unit tests are available under src/test/java. It can be run using
	
	gradle test

## Entity information
The main entity is called game that defines the competition between 2 countries. The schema is available below:
```
{
   "beginTime": "2018-03-02T12:28:09.081Z",
   "endTime": "2018-03-02T12:28:09.081Z",
   "firstCountry": "Brazil",
   "local": "Tokyo",
   "modality": "Soccer",
   "secondCountry": "Germany",
   "stage": "FINAL"
}
```

The Stage contains the specified values
* SEMIFINAL
* EIGHTFINAL
* QUARTERFINAL
* FINAL
* ELIMINATION

