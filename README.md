# country-counter 

## API 

- GET http://localhost:8080/country/counter -> counters for each country {"us":1, "ru":2 ...}
- PATCH http://localhost:8080/country/counter/ru/increment -> increment "ru" counter by 1

## Build && tests
```bash
./gradlew build 
docker-compose build
```

## Run
```bash
docker-compose up -d
```

## Add counters
* With performance testing toolkit artillery. 1k rps for beginning 
```bash
docker run --network host --rm -it -v ${PWD}/scripts:/scripts artilleryio/artillery:latest run /scripts/increment_counter_script.yaml
```

## Check counters
```bash
curl http://localhost:8080/country/counter
```

## Stop
```bash
docker-compose down
```
