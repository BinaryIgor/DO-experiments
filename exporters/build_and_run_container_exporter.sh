 docker rm container-exporter
 
 docker run -p 9104:9104 \
    -v /sys/fs/cgroup:/cgroup \
    -v /var/run/docker.sock:/var/run/docker.sock \
    --name container-exporter prom/container-exporter 