FROM adoptopenjdk/openjdk16

WORKDIR /app

COPY ./build/libs/ecommerve-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 8090

ENV HOSTNAME=localhost

ENTRYPOINT ["java", "-jar", "./app.jar"]