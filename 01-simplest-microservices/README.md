# minimum microservices ever!

In this example, a *cart service* will ask for data from *items service*.


### Prerequisites

IDE and minimum knowledge of java and spring framework.


## Getting Started:

### Initializing your projects

From [start.spring.io](https://start.spring.io/) we are going to initializing two projects:
* We will add **Spring Web** and **Spring Boot DevTools** as Dependencies, give your project a name -*items* for instance-, then click generate.
* after generating *items* service, in the same page, change the name -*cart* for instance-, then click generate again.
* extract the projects and import it to your IDE

### Setup items service
first, let's setup server port in application.properties file,:

```
# you can choose any available port in your system
server.port=8081
```

now let's discover your service classes, you will find one class has the annotation **@SpringBootApplication** which going to boot up your service using its only main method.
any other spring components must be one level down the package that contains your main method, unless you configure package scanning.
*you may test it now! just right click on the class file, and run as -> java application.*

then create new controller class, that will response to http get request

```
@RestController
public class ItemsController {

	@GetMapping("/item")
	public String getItem() {
		return "this is items service!";
	}
}
```

and this is the first service!

### Setup cart service
Go to application.properties file, and setup server port:

```
# you have to choose a different port than items service
server.port=8082
```

now let's configure RestTemplate for the service, create new class and add the following

```
@Configuration
public class RestTemplateConfig {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
```

finally, create a controller which contains a method that calling *items service*!

```
@RestController
public class CartController {

	@Autowired
	private RestTemplate restTemplate;

	private final String ItemService = "http://localhost:8081/item";

	@GetMapping("/cart")
	public String getCartItems() {
		String itemServiceResponse = restTemplate.getForObject(ItemService, String.class);
		return "this is your cart items: "+itemServiceResponse;
	}
}
```

now open your browser and test the services:
* http://localhost:8081/item will return: this is items service!
* http://localhost:8082/cart will return: this is your cart items: this is items service!





**NOTICE**: *this is not the patter neither the design that happening in production for sure! this is only to simplify things, by break down services.*
