FROM openjdk:11
ENV LANG C.UTF-8
VOLUME /tmp
ARG DEPENDENCY=target/dependency
COPY . /hello
WORKDIR /hello
EXPOSE 8080
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "./target/hello-0.0.3.jar"]