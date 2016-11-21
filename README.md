# product-api

- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Development](#development)
- [Interaction](#interaction)
- [Debugging](#debugging)
- [Further Info](#further-info)

## Prerequisites

* JDK 8 (local development)
  * Ubuntu: http://www.webupd8.org/2012/01/install-oracle-java-jdk-7-in-ubuntu-via.html
  * Mac via Homebrew: `brew cask install java`
  * Others: https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html
  * Health Check:
    * `java -version` prints `1.8+`
    * `javac -version` prints `1.8+`

* docker 1.11+ client
  * Download the docker client v1.11 or above
  * Mac: `brew install docker`
  * Other Platforms: Get the release binary from https://docs.docker.com/engine/installation/binaries/#get-the-docker-engine-binaries
  * Health Check:
    * `docker version` prints `1.11.x` or above

* docker-compose:
  * For Windows and Mac users it's installed with docker 
  * Other Platforms: https://docs.docker.com/compose/install/
  * Health Check:
    * `docker-compose --version`

## Getting Started

* First build docker image (Redis and API):
  * Run `docker-compose build`
* Secondly build the app jar
  * Run `docker-compose run api ./build.sh`
* Finally run containers
  * Run `docker-compose up`
  * Health Check:
    * http://localhost:8088/products/list returns 3 results
    * http://localhost:8088/metrics prints request metrics
    * http://localhost:8088/metrics/jvm prints general JVM metrics
    * http://localhost:8088/health returns healthy status

## Development

* Proceed to IDE:
  * IDEA > `Open` *(not import)* > Select the root `api/build.gradle`
  * IDEA > Preferences > Search and enable `Annotation Processing`
  
By default the API uses Redis storage and tries to connect to `api.redis.uri=redis://redis:6379` what id default for docker setup.
For local development you might need:
* local redis
* docker redis, that can be run by `docker-compose up redis`. Also change `application.properties` to `api.redis.uri=redis://localhost:6379`
* use other storege i.e. `InMemoryStorage`