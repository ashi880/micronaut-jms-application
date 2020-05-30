FROM gradle:6.4.1-jdk14 as build
COPY . /app
WORKDIR /app
RUN gradle assemble --no-daemon

FROM openjdk:14-alpine
COPY --from=build /app/build/libs/micronaut-jms-mq-*-all.jar micronaut-jms-mq.jar
EXPOSE 8181
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "micronaut-jms-mq.jar"]