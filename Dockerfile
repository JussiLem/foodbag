FROM openjdk:11
ENV LANG C.UTF-8
COPY . /hello
WORKDIR /hello
RUN chmod +x ./docker/wait-for-it.sh
EXPOSE 8080
CMD ["./docker/wait-for-it.sh", "--strict", "--timeout=60", "foodbagdb:3306", "--", "java", "-jar", "./target/hello-0.0.1-SNAPSHOT.jar"]