FROM openjdk:latest
VOLUME /tmp
COPY target/*.jar kameleoontrialtask.jar
ENTRYPOINT ["java","-jar","/kameleoontrialtask.jar"]