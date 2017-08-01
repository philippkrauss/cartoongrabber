FROM openjdk:8
#RUN apt-get update && apt-get install -yq curl && apt-get clean
WORKDIR /app
ADD clitest.sh /app/test.sh
ADD build/docker/cartoongrabber-cli-0.1.0.tar /app
CMD ["bash", "test.sh"]