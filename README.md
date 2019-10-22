# btn-brukermelding-producer
Sender en melding fra bruker til Kafka

## Lokal kjøring
Kjør Kafka lokalt
```
$ docker run --rm -p 2181:2181 -p 3030:3030 -p 8081-8083:8081-8083 \
       -p 9581-9585:9581-9585 -p 9092:9092 -e ADV_HOST=localhost \
       landoop/fast-data-dev:latest
```
Lag topic for å sende melding
```
$ docker run --rm -it --net=host landoop/fast-data-dev kafka-topics --zookeeper localhost:2181 \
       --create --topic dice-rolls --replication-factor 1 --partitions 1
```