#!/bin/bash

docker rm prometheus-am-exp

rm -r target
mkdir -p target

envsubst '${slack_api_url} ${slack_channel}' < alertmanager.yml > target/alertmanager.yml

docker build . -t prometheus-am-exp

#9093 port we have
docker run --network host --name prometheus-am-exp prometheus-am-exp