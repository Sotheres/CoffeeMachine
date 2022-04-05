## DESCRIPTION
Spring Boot project managing Coffee Machine through Swagger API.

##Run application
Docker Desktop is required to be installed and run before running the app.
After running Docker execute following command from project root folder 
to launch PostgreSQL instance in a container:

docker-compose up -d

Run the app from CoffeeMachineApplication class.

While running CoffeeControllerIT test class, Docker also should be running,
as it automatically launches docker container with test PostgreSQL DB.