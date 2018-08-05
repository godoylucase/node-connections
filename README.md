# node-connections
### [upwork exercise](https://url.upwork.com/_01Vi9EXRg9YOji9g3YD1cO39kiowZFZdub)

The exercise has been solved using 2 different strategies:
- SEARCH: iterating over connected nodes from first parameter till reach the connection with the second one or not
- OBSERVER: using observer pattern, an event broadcasting that holds with the new connected node details, it is sent to 
all the other nodes connected to it.

The main reason I came up with this 2 possible solutions it is the fact that SEARCH could take some time till reach the connection path
during many iterations. Which is not happening with OBSERVER strategy where all the nodes knows all the direct connected nodes
as well as non-direct connections. Therefore connection matching it is quite faster than SEARCH algorithm. 

In order to switch implementations you would need to change the environment variable on the `local-dev/docker-compose.yml` file if you
have used `Docker` to run this program. Otherwise just change it on the `application.yml` file. (Default value it is OBSERVER).


```
### docker-compose.yml file
environment:
  - interview.network.networkSolutionType=SEARCH or OBSERVER
```

```
### application.yml file
interview:
  network:
    networkSolutionType: <value> # any of SEARCH / OBSERVER
 ```   

#### 1. Running application 
`mvn spring-boot:run` or run it on `Docker` by running these commands in the terminal:
```
# mvn clean install -P local-dev 
# docker-compose -f local-dev/docker-compose.yml up
```

#### 2. Connect two nodes request example
```
# curl -X POST \
  http://localhost:8080/api/network/connect \
  -H 'Accept: application/json' \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -d '{
	"origin": 0,
	"destination":1
}'
```

#### 3. Query two nodes request example
```
# curl -X GET \
  'http://localhost:8080/api/network/query?origin=1&destination=0' \
  -H 'Accept: application/json' \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' 
```
