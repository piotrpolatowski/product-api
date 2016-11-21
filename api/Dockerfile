from ubuntu:16.04

RUN apt-get update && apt-get install -y \
    openjdk-8-jdk \
    gradle \
 && rm -rf /var/lib/apt/lists/*

COPY . /code
WORKDIR /code
RUN bash build.sh
CMD bash service.sh block
