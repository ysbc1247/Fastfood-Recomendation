FROM openjdk:11
ARG JAR_FILE=build/libs/app.jar
COPY ${JAR_FILE} ./app.jar
ENV TZ=ASIA/SEOUL
ENTRYPOINT ["java", "-jar", "./app.jar"]