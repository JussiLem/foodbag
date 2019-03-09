FROM openjdk:11
ENV LANG C.UTF-8
VOLUME /tmp
ARG DEPENDENCY=target/dependency
COPY . /hello
WORKDIR /hello
EXPOSE 8080 5005
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=0.0.0.0:5005", "-jar", "./target/hello-0.0.3.jar"]