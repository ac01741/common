FROM adoptopenjdk/openjdk11:alpine-jre
ADD build/libs/common.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]