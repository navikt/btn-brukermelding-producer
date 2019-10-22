# btn-brukermelding-producer
Sender en melding fra bruker til Kafka

## Lokal kjøring
* Kjør Kafka lokalt
```
$ docker run --rm -p 2181:2181 -p 3030:3030 -p 8081-8083:8081-8083 \
       -p 9581-9585:9581-9585 -p 9092:9092 -e ADV_HOST=localhost \
       landoop/fast-data-dev:latest
```
* Lag topic for å sende melding
```
$ docker run --rm -it --net=host landoop/fast-data-dev kafka-topics --zookeeper localhost:2181 \
       --create --topic btn-brukermelding --replication-factor 1 --partitions 1
```
* Start opp ktor ved å kjøre `Application.kt` fra din favoritt-IDE
* Send en testmelding
```
$ curl --header "Content-Type: application/json" --request POST \
  --data '{"message": "testmessage", "timestamp": 74293847}' \
  http://localhost:7070
```
* Sjekk http://localhost:3030 for å se hvordan det gikk