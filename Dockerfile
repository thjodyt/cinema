FROM openjdk:23-oracle

COPY target/cinema-0.0.1-SNAPSHOT.jar cinema-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/cinema-0.0.1-SNAPSHOT.jar"]

