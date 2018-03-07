Worldpay test
=============================

## Running the application

* Make sure JDK8+ is installed
* Make sure Scala 2.11+ is installed
* Make sure sbt is being installed

Go to the project root directory and run 

```
$ rm -rf target
$ sbt -mem 2048 clean run
```

Once the server is ready you should be able tro connect to 
the following [http://localhost:8080/index]()

## Using the API

### Creating an offer

```
curl -v -H "Content-Type: application/json" -X POST -d '{"price":"250","descriptions":["xyz"], "duration":3600}' 'http://localhost:8080/offer'
```

This is a synchronous operation return a representation of the `offer` entity created holding the id of the entity 


### updating an offer

```
curl -v -H "Content-Type: application/json" -X PUT -d '{"price":"275","descriptions":["xyz", "MNO"]}' 'http://localhost:8080/offer/1'
```


### getting an offer description

In this case we are getting offer with id `1`

```
curl -v 'http://localhost:8080/offer/1'
```

### getting all offers' descriptions

```
curl -v 'http://localhost:8080/offer'
```

### cancelling an offer

In the case of cancelling offer with id `2`

```
curl -X PUT -v 'http://localhost:8080/offer/2/cancellation'
```

### Deleteting an offer

In the case of cancelling offer with id `2`

```
curl -X DELETE -v 'http://localhost:8080/offer/2'
```

## Cleaning storage

1. Stop the server using `Ctrl + C` 
2. delete  from the root the directory `event_store`
3. restart the server
 