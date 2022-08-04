FROM openjdk:11
EXPOSE 8088
ADD target/employee-details-service-1.0.jar employee-service.jar
ENTRYPOINT ["java","-jar","/employee-service.jar"]