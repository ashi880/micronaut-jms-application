# micronaut-jms-mq application

This application provides a showcase for using the `micronaut-jms-mq` library to enable
simple MQ applications with https://micronaut.io/

The `mironaut-jms-mq` library provides an easy way to integrate IBM MQ into your micronaut
microservice.

## Getting Started

These instructions will get the project up and running on your local machine for development
and testing purposes.

### Prerequisites
To start the application you need:
* `docker` and `docker-compose` installed
* `GNU Make` installed
* A bash shell running (you can use git bash on windows)

To start the application:
```bash
$ make start
```

This will create 2 docker images:
* rma/mq - A custom version of the ibmcom/mq:9.1.5.0-r1 docker image with some extra queues created for testing
* rma/micronaut-jms-mq - The micronaut application

Once the 2 docker images have been created they will be started, and you should see something similar to:
```bash
application_1  | 06:29:08.302 [      main] INFO  a.c.r.micronaut.app.StartupListener - Reverse String: pre-972730.80:92:60T03-50-0202 ecivreS gnirtS esreveR-post
application_1  | 06:29:08.441 [01501D21]]] INFO  a.c.r.m.app.service.UpperCaseService - uppercaseString called with uppercase service call
application_1  | 06:29:08.458 [      main] INFO  a.c.r.micronaut.app.StartupListener - Uppercase String: UPPERCASE SERVICE CALL
application_1  | 06:29:10.659 [014F1D21]]] INFO  a.c.r.m.app.service.BookService - Received: Book{author=Author{name='David Brin', birthDate=1950-10-06}, title='The River of Time', yearPublished=1997, numberOfPages=295, coverType=PAPERBACK, isbn=null}
application_1  | 06:29:10.676 [      main] INFO  a.c.r.micronaut.app.StartupListener - *** Book Book{author=Author{name='David Brin', birthDate=1950-10-06}, title='The River of Time', yearPublished=1997, numberOfPages=295, coverType=PAPERBACK, isbn=9781857234138} ***
application_1  | 06:29:10.943 [      main] INFO  io.micronaut.runtime.Micronaut - Startup completed in 4006ms. Server Running: http://345d57eb0132:8181
```

This will expose several endpoints:
* https://localhost:9443/ibmmq/console/ - The IBM MQ console - you can login with:
  * username: admin
  * Password: passw0rd
* http://localhost:9191/health - The Micronaut application, if working you should see:
```json5
{
  name: "micronaut-jms-mq",
  status: "UP",
  details: {
    compositeDiscoveryClient(): {
      name: "micronaut-jms-mq",
      status: "UP"
    },
    micronaut-jms-mq: {
      name: "micronaut-jms-mq",
      status: "UP",
      details: {
        app: "UP",
        admin: "UP"
      }
    },
    diskSpace: {
      name: "micronaut-jms-mq",
      status: "UP",
      details: {
        total: xxxxxxx,
        free: xxxxxxx,
        threshold: xxxxxxxx
      }
    }
  }
}
```

## Quickstart
The `micronaut-jms-mq` library requires configuration telling micronaut where the MQ servers are:
```yaml
mq-server:
  ibm-mq:
    host: "${mq.server:localhost}"
    port: "${mq.port:1414}"
    queueManager: QM1
    channel: DEV.ADMIN.SVRCONN
    username: admin
    password: passw0rd
```
To send messages you simply need to create a client interface:
```java
@JmsClient("ibm-mq")
public interface MessageService {
  @JmsDestination(value = "///DEV.QUEUE.MESSAGE")
  @JmsReplyDestination(value = "///DEV.QUEUE.REPLY", timeout = 5_000)
  String sendMessage(String text);
}
```

To listen to messages you create a listener interface:
```java
@Infrastructure
@JmsListener("ibm-mq")
public class MessageServiceListener {
  private static Logger logger = LoggerFactory.getLogger(MessageServiceListener.class);

  @JmsDestination("///DEV.QUEUE.MESSAGE")
  public String handleMessage(@Body String text) {
    logger.info("message received {}", text);
    return text;
  }
}
```

## Developer Getting Started
If you run:
```bash
$ make start-mq
```
Only the mq server will be started up, you can access it through:
* https://localhost:9443/ibmmq/console/ - The IBM MQ console - you can login with:
  * username: admin
  * Password: passw0rd

You can then run the application using your favourite IDE, and the application will be available
on http://localhost:8181/health

You can also use a simple REST api to send messages:

### Uppercase Service
```bash
POST http://localhost:8181/api/upperCase

upper case text

< HTTP/1.1 200 OK
UPPER CASE TEXT
```

### Reverse Service
```bash
POST http://localhost:8181/api/reverse HTTP/1.1

Some Text to Reverse

< HTTP/1.1 200 OK
pre-esreveR ot txeT emoS-post
```

### Book Service
```bash
POST http://localhost:8181/api/book
Content-Type: application/json
Accept: application/json

{
  "author": {
    "name": "David Brin",
    "birthDate": "1950-10-06"
  },
  "title": "The River of Time",
  "yearPublished": 1997,
  "numberOfPages": 295,
  "coverType": "PAPERBACK"
}

< HTTP/1.1 200 OK
{
  "author": {
    "name": "David Brin",
    "birthDate": [
      1950,
      10,
      6
    ]
  },
  "title": "The River of Time",
  "yearPublished": 1997,
  "numberOfPages": 295,
  "coverType": "PAPERBACK",
  "isbn": "9781857234138"
}
```

## Supported Features

* Simple JMS Client annotations allow you to create an interface for sending messages
  * Includes support for topics
  * Includes support for send/receive style messaging
* Simple JMS Listener annotations allow you to create a bean that receives messages from MQ
* Uses docker-compose to start the application.

## TODO

* Add an example using Topics 
  * Simple chat client using Server Send Events (SSE)
* Add support for different message types
* Add @Transactional support

# Authors
* **Richard Allwood** - Initial Version

## Licence
This product is licensed under the MIT License

## Acknowledgements
The project was inspired from the [Micronaut RabbitMQ project](https://micronaut-projects.github.io/micronaut-rabbitmq/latest/guide/)
 