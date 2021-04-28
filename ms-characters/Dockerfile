FROM amazoncorretto:11.0.9-alpine
COPY build/libs/ms-characters-0.0.1-SNAPSHOT.jar ms-characters.jar
ENTRYPOINT ["java", "-Dreactor.netty.http.server.accessLogEnabled=true" ,"-jar", "ms-characters.jar"]