
# Minimum microservices ever! using Eureka and Zuul.

In this example, we will ask *cart service* and *items service* for data from through a gateway using Eureka naming service.


### Prerequisites

IDE and minimum knowledge of java and spring framework.


## Getting Started:

### Initializing your projects

We will import *cart, items, and namingserver* from a previous repository microservices/[02-Eureka-namingservice](https://github.com/yyagoub/microservices/tree/master/02-Eureka-namingservice)/ .
then, we are going to initializing a new project from [start.spring.io](https://start.spring.io/):
* We will add **Zuul**, **Eureka Discovery Client**, and **OpenFeign** as your dependencies, give your project a name -*gateway* for instance-, then click generate.
* extract the project and import it to your IDE

### Setup items service
We will keep the service as it is.

### Setup cart service
We will keep the service as it is.

### Setup naming server
We will keep the service as it is.

### Setup gateway server
first, let's setup server port in application.properties file:
*keep in mind this time we are using* **namingserver** *to call other services instead of localhost url.*

```
# you may choose any available port in your system
server.port=8762
# notifying the naming server - Eureka - : "this service has a name and it is:"
spring.application.name=zuul-server
# here we are referring to the naming server -Eureka- url
eureka.client.service-url.default-zone=http://localhost:8761/eureka/

# we will identify cart server name and give it a path
zuul.routes.cart.path=/cart/**
# zuul.routes.cart.url=http://localhost:8082/
zuul.routes.cart.service-id=cart-service

# we will identify items server name and give it a path
zuul.routes.items.path=/item/**
# zuul.routes.items.url=http://localhost:8081/
zuul.routes.items.service-id=items-service
```

now let's discover your service classes, you will find only a class has the annotation **@SpringBootApplication** which going to boot up your service using its only main method.
any other spring components must be one level down the package that contains your main method, unless you configure package scanning.
*you may test it now! just right click on the class file, and run as -> java application.*

- up or down **@SpringBootApplication** annotation add **@EnableZuulProxy** and **@EnableEurekaClient** annotations to add Zuul Server auto-configuration to the service, so the service will act as a gateway server and a client for eureka the namingserver.

```
@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
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
* http://localhost:8761/	will return a page with system status -active services for instance-.
* http://localhost:8762/item/item this time we will call item service through a gate way and it will return: "this is items service!"
* http://localhost:8762/cart/cartmthis time we will call cart service through a gate way and it will return: "this is your cart items: this is items service!"





**NOTICE**: *this is not the pattern neither the design that happening in production environments for sure! this is only to simplify things, by breaking down the services.*
