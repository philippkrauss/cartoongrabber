[![Build Status](https://travis-ci.org/philippkrauss/cartoongrabber.svg?branch=master)](https://travis-ci.org/philippkrauss/cartoongrabber)

# cartoongrabber
The main purpose of this pet project is to learn new technologies and have some fun 
in the process. Therefore we came up with the idea of creating a cartoon grabber. The
idea is to grab cartoons from a number of configurable sources (like e.g. dilbert.com)
and process them in a number of backends.

It started with the need to learn about [spring-integration](https://projects.spring.io/spring-integration)
and continued with different other frameworks and tools.

Currently in use are
* [Gradle](https://gradle.org/)
* [spring-integration](https://projects.spring.io/spring-integration)
* [spring](https://projects.spring.io/spring-framework/) for IOC
* [Travis Ci](https://travis-ci.org/)
* [spring-boot](https://projects.spring.io/spring-boot/)
* [Docker](https://www.docker.com/)

See the wiki for additional information on how the project works internally.

There are different flavors of the project available. The most basic one is the 
cartoongrabber-cli. To run it, execute `gradlew :cartoongrabber-cli:run` from the 
project root. You will find the output in a newly created `output` directory below
the cartoongrabber-cli project directory.

You can also create a distribution zip file by executing 
`gradlew :cartoongrabber-cli:distZip` or `gradlew :cartoongrabber-cli:distTar`. You
will find the created distribution archive in the path 
`cartoongrabber-cli/build/distributions`.

The second flavor is a web application that gives access to the grabbed cartoons. You
can run the web application by executing `gradlew :cartoongrabber-web:bootRun`. The 
launched application will listen on http://localhost:8080. 