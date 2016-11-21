# product-api

- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [API](#api)
- [Development](#development)
- [Testing](#testing)
- [Performance](#performance)
- [Explanations](#explanations)
- [Further Changes](#further-changes)

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
    * [products/list](http://localhost:8088/products/list) returns 3 results
    * [metrics](http://localhost:8088/metrics) prints request metrics
    * [metrics-jvm](http://localhost:8088/metrics/jvm) prints general JVM metrics
    * [health](http://localhost:8088/health) returns healthy status

## API

* list products
GET [http://localhost:8088/products/list?page={page}](http://localhost:8088/products/list?page=0)

* add product
POST [http://localhost:8088/products/add](http://localhost:8088/products/add)

Content-Type: application/json

Body:
```
{
  "id": 6,
  "title": "X-COM 2",
  "price": 6000, 
  "currency": "USD"
}
```


## Development

* Proceed to IDE:
  * IDEA > `Open` *(not import)* > Select the root `api/build.gradle`
  * IDEA > Preferences > Search and enable `Annotation Processing`
  
By default the API uses Redis storage and tries to connect to `api.redis.uri=redis://redis:6379` what id default for docker setup.
For local development you might need:
* local redis
* docker redis, that can be run by `docker-compose up redis`. Also change `application.properties` to `api.redis.uri=redis://localhost:6379`
* use other storege i.e. `InMemoryStorage`

### Data sample

By default redis stores the data in `{project_path}/redis/data/dump.rdb` which can be a symbolic link pointing to a data file.
To switch the data just create a link i.e. `ln -s {project_path}/redis/data/big.rdb {project_path}/redis/data/dump.rdb`
The project has two sample data files:
* `init.rdb` - 5 products
* `big.rdb` - with ~129k randomly generated products
 

## Testing

Tools for load testing.

* Download stable version of [`Gatling`](http://gatling.io/#/resources/download).
* Extract it to your programs folder
* Go to the `bin` Gatling directory
  E.g.: `cd /opt/gatling-charts-highcharts-bundle-2.2.2-bundle/bin`
* Create a symbolic link for Gatling jobs in seo project
  E.g.: `ln -s ~/product-api/load-tests-gatling/user-files .`
* Run Gatling
E.g.: `. gatling.sh`
* Select simulator from list by the number, and press _Enter_
* Check out the results in `results` folder

## Performance

Load tests done on a single machine against docker containers.

### Memory

For a sample with ~129k items the memory consumption:
used_memory_human:33.18M
used_memory_rss_human:36.94M
used_memory_peak_human:33.18M

Assuming the data grows linearly, 10 milion of products would take ~3-4GB.

### Requests per second

Checkout example load test results by opening in browser:
* `{project_path}/load-tests-gattling/results/add_product/add_products.html`
* `{project_path}/load-tests-gattling/results/paginated_list_products/index.html`

Results present throughput of 200 requests per second.

## Explanations

### Storage

Choosen redis to provide quick access serve requests quickly. Hoewver to avoid any data loss we could add secondary storage i.e. database.

## Further Changes

* Extending API
  * Removing elements
  * Searching elements by title
* Dockerize Gattling
* Add smoke tests
* Implement transactional `add`
* Compare performance with asynchronous processing
