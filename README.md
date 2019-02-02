# FoodBag Spring app

Project for school course service side programming.
Goal is to create an app which keeps track of a weekly diet.

#### Run the project by typing:<br>
/mvnw spring-boot:run


#### With docker:
/mvnw clean package
docker build -t foodbag .
docker run -p 8080:8080 foodbag