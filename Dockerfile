FROM openjdk:11
ADD target/insideJWTControlEx2022jan12-0.0.1-SNAPSHOT.jar backend.jar
ENTRYPOINT ["java", "-jar", "backend.jar"]