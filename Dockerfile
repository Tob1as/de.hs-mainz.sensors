# Pull base image
FROM resin/raspberrypi2-debian:latest
MAINTAINER Tobias Hargesheimer tobias.hargesheimer@hs-mainz.de

# Install dependencies
RUN apt-get update && apt-get install -y \
    openjdk-8-jre cron bluez bluez-tools libbluetooth-dev \
    --no-install-recommends && \
    rm -rf /var/lib/apt/lists/*

# Define commonly used JAVA_HOME variable
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-armhf

ADD Sensors-jar-with-dependencies.jar /Sensors.jar
ADD sensorsdata /sensorsdata
VOLUME ["/sensorsdata"]

# Define default command
#CMD ["java","-jar","/Sensors.jar"]

# Crontab
ADD crontab.txt /etc/cron.d/sensors-cron
RUN chmod 0644 /etc/cron.d/sensors-cron
CMD cron
