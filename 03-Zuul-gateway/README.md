# Minimum microservices ever! using Zuul as gateway.

In this example, we will ask *cart service* and *items service* for data through a gateway.


### Prerequisites

IDE and minimum knowledge of java and spring framework.


## Getting Started:

### Initializing your projects
We will import cart and items services from a previous repository microservices/[01-simplest-microservices](https://github.com/yyagoub/microservices/tree/master/01-simplest-microservices)/ .
then, we are going to initializing a new project from [start.spring.io](https://start.spring.io/):
* We will add **Zuul** as the only dependency we need, give your project a name -*gateway* for instance-, then click generate.
* extract the project and import it to your IDE

### Setup items service
We will keep the service as it is.

### Setup cart service
We will keep the service as it is.

### Setup gateway server
first, let's setup server port in application.properties file:
* here we are going to give our service a port, and identify other services paths and urls:

```
# you may choose any available port in your system
server.port=8762

# we will identify cart server url and give it a path
zuul.routes.cart.path=/cart/**
zuul.routes.cart.url=http://localhost:8082/

# we will identify items server url and give it a path
zuul.routes.items.path=/item/**
zuul.routes.items.url=http://localhost:8081/
```

now let's discover your service classes, you will find only a class has the annotation **@SpringBootApplication** which going to boot up your service using its only main method.
any other spring components must be one level down the package that contains your main method, unless you configure package scanning.
*you may test it now! just right click on the class file, and run as -> java application.*

- up or down **@SpringBootApplication** annotation add **@EnableZuulProxy** annotation to add Zuul Server auto-configuration to the service, so the service will act as gateway server.

```
@SpringBootApplication
@EnableZuulProxy
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

}
```

that's all for Zuul our gateway server.

# Test your application
now open your browser and test the services:
* http://localhost:8081/item will return: "this is items service!"
* http://localhost:8082/cart will return: "this is your cart items: this is items service!"
* http://localhost:8762/item/item this time we will call *item service* through a gate way and it will return: "this is items service!"
* http://localhost:8762/cart/cartmthis time we will call *cart service* through a gate way and it will return: "this is your cart items: this is items service!"





**NOTICE**: *this is not the pattern neither the design that happening in production environments for sure! this is only to simplify things, by break down services.*
