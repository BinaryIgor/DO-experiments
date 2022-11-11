#!/bin/bash

docker volume create elastic-volume

docker rm elasticsearch-exp

docker run -p "9200:9200" -p "9300:9300" \
    -e "ES_JAVA_OPTS=-Xms1g -Xmx1g" \
    -e "discovery.type=single-node" \
    -e "cluster.routing.allocation.disk.threshold_enabled=false" \
    -e "xpack.security.enabled=false" \
    -v "elastic-volume:/usr/share/elasticsearch/data" \
    --name elasticsearch-exp docker.elastic.co/elasticsearch/elasticsearch:8.5.0