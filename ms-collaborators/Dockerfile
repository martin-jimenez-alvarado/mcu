FROM amazoncorretto:11.0.9-alpine
COPY build/libs/ms-collaborators-0.0.1-SNAPSHOT.jar ms-collaborators.jar
ENTRYPOINT ["java", "-Dreactor.netty.http.server.accessLogEnabled=true" ,"-jar", "ms-collaborators.jar"]