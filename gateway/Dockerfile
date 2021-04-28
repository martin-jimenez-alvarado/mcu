FROM amazoncorretto:11.0.9-alpine
COPY build/libs/gateway-0.0.1-SNAPSHOT.jar ms-gateway.jar
ENTRYPOINT ["java", "-Dreactor.netty.http.server.accessLogEnabled=true" ,"-jar", "ms-gateway.jar"]