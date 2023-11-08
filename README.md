# org.news
News Release System. tech stack: Spring, SpringMVC, Mybatis, Jedis
## Overview
`org.news` is a Java web application designed to serve as a news platform. It is built using the Spring Framework and follows the typical Maven project structure for a web application. The application is packaged as a WAR file for deployment in servlet containers like Tomcat.

## Features
- Spring MVC for request handling.
- MyBatis for database interaction.
- Integration with various Java libraries for utility functions.

## Prerequisites
- JDK 1.7 or later.
- Maven for dependency management and building the project.
- Servlet container like Tomcat for deploying the WAR file.

## Configuration
The application's servlet and Spring context configurations are specified in `web.xml`. The Spring configuration files are expected to be named in the pattern `spring*.xml` and located in the `classpath:spring` directory.

## Building the Project
To build the project, run the following command in the project's root directory:

```shell
mvn clean install
