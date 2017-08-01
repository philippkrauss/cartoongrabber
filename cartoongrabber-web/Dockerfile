FROM openjdk:8-jre-alpine
COPY build/docker /app
CMD java -Dserver.port=8081 -Dredis=redis://redis:6379 -jar /app/cartoongrabber-web.jar
EXPOSE 8081