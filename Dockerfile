FROM openjdk:11-jdk-slim
COPY --from=build /target//InstagramProject-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8099
ENTRYPOINT ["java","-jar","demo.jar"]