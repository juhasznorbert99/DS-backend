FROM maven:3.6.3-jdk-11 AS builder

COPY ./src/ /root/src
COPY ./pom.xml /root/
COPY ./checkstyle.xml /root/
WORKDIR /root
RUN mvn package
RUN java -Djarmode=layertools -jar /root/target/ds-2020-0.0.1-SNAPSHOT.jar list
RUN java -Djarmode=layertools -jar /root/target/ds-2020-0.0.1-SNAPSHOT.jar extract
RUN ls -l /root

FROM openjdk:11.0.6-jre

ENV TZ=UTC
ENV DB_IP=ec2-54-220-243-77.eu-west-1.compute.amazonaws.com
ENV DB_PORT=5432
ENV DB_USER=tpmbokcnhictev
ENV DB_PASSWORD=c6c5a77446efbb3c796c644a89b3a22d1bce2ba99c38067764debfe6c4b49506
ENV DB_DBNAME=d849df66ih77s4

COPY --from=builder /root/dependencies/ ./
COPY --from=builder /root/snapshot-dependencies/ ./

RUN sleep 10
COPY --from=builder /root/spring-boot-loader/ ./
COPY --from=builder /root/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher","-XX:+UseContainerSupport -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -Xms512m -Xmx512m -XX:+UseG1GC -XX:+UseSerialGC -Xss512k -XX:MaxRAM=72m"]
