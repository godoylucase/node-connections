# node-connections
### [upwork exercise](https://url.upwork.com/_01Vi9EXRg9YOji9g3YD1cO39kiowZFZdub)

#### 1. Running application 
`mvn spring-boot:run`

#### 2. Connect two nodes request example
```
curl -X POST \
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
curl -X GET \
  'http://localhost:8080/api/network/query?origin=1&destination=0' \
  -H 'Accept: application/json' \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' 
```
