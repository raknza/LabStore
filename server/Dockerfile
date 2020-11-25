FROM maven:3-jdk-8 as builder

RUN mkdir /workdir

WORKDIR /workdir

COPY . .

ARG WEB_API_BASEURI=
ENV WEB_API_BASEURI=$WEB_API_BASEURI

RUN apt-get update \
    && apt-get -y install gettext-base \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*
	
RUN mvn -T 1C clean package -DskipTests

FROM tomcat:8-jdk8-adoptopenjdk-hotspot

ARG DEBUG=false
ENV DEBUG=$DEBUG
ENV LOG_LEVEL=$LOG_LEVEL
ENV JAVA_OPTS="-Xmx512m"

RUN echo $DEBUG

RUN mkdir /usr/local/tomcat/images  && \
    mkdir /usr/local/tomcat/temp_images  && \
    if [ ${DEBUG} = true ]; then \
    rm -rf /usr/local/tomcat/webapps/ROOT /usr/local/tomcat/webapps/ROOT.war \
    ;else \
    rm -rf /usr/local/tomcat/webapps/ && mkdir /usr/local/tomcat/webapps/ \
    ;fi

COPY --from=builder /workdir/target/LabStore.war /usr/local/tomcat/webapps/ROOT.war
