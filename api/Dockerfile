from ubuntu:16.04

RUN apt-get update && apt-get install -y \
    openjdk-8-jdk \
    gradle \
 && rm -rf /var/lib/apt/lists/*

ADD . /code
WORKDIR /code
CMD /bin/bash service.sh block tests/docker/example
