FROM maven:3.8.6 AS build
WORKDIR /app
COPY pom.xml /app
RUN mvn dependency:resolve
COPY . /app
RUN mvn clean
RUN mvn package


FROM eclipse-temurin:17.0.4.1_1-jre
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8030
ENV USERNAME=${USERNAME}
ENV PASSWORD=${PASSWORD}
CMD ["java", "-jar", "app.jar"]