# Cupuama Services Implementation

The project is based on a small web service which uses the following technologies:
   
   + Java 8
   + Spring Boot 2.1
   + Spring Data (JPA/Hibernate)
   + PostgreSQL Database
   + Docker / Docker Compose
   + Swagger
   + Spring Security

The application starts a webserver on port 8080 (http://<server_ip_address>:8080) and serves SwaggerUI where can inspect and 
try existing endpoints. To view the SwaggerUI use the endpoint `http://<server_ip_address>:8080/v2/api-docs`

To build and run the application follow the instructions bellow

    * build the docker image with the spring boot acme test  application run: 
        `mvn clean package dockerfile:build`

    * run docker image
        `docker run --name it cupuama-app`

    * the image will run on a local machine specific Ip Address. To check wich Ip is being
    used open a second terminal window and run the following command:
        `docker inspect it`

The application is also implemented under a simple memory authentication the uses JWT tokens on the request header

	* All endpoints except "http://<server_ip_address>:8080/v2/api-docs" are required to have the Authorization token on the header.
	
	* The endpoint for authentication is "http://<server_ip_address>:8080/login". 
	Please use the json bellow using a POST method to create a new token:

	{
	   "username": "admin",
	   "password": "p4ssw0rd"
	}
	
	* On header from the response of this POST, copy the Authorization into the header of all other endpoint access.


