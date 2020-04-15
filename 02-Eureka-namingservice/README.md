
# Minimum microservices ever! using Eureka as naming server.

In this example, a *cart service* will ask for data from *items service* using Eureka naming service.


### Prerequisites

IDE and minimum knowledge of java and spring framework.


## Getting Started:

### Initializing your projects

We will import cart and items services from a previous repository microservices/[01-simplest-microservices](https://github.com/yyagoub/microservices/tree/master/01-simplest-microservices)/ .
then, we are going to initializing a new project from [start.spring.io](https://start.spring.io/):
* We will add **Eureka Server** as the only dependency we need, give your project a name -*namingserver* for instance-, then click generate.
* extract the project and import it to your IDE

### Setup naming server
first, let's setup server port in application.properties file:

```
# you may choose any available port in your system
# but usually 8761 is the default one for Eureka servers.
server.port=8761

# We will give a name to the naming server - Eureka - so other microservices can refer to it.
spring.application.name=eureka-server
# We are telling the built-in Eureka Client not to register with itself because our application should be acting as a server.
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
```

now let's discover your service classes, you will find only a class has the annotation **@SpringBootApplication** which going to boot up your service using its only main method.
any other spring components must be one level down the package that contains your main method, unless you configure package scanning.
*you may test it now! just right click on the class file, and run as -> java application.*

- up or down **@SpringBootApplication** annotation add **@EnableEurekaServer** annotation to add Eureka Server auto-configuration to the service, so the service will act as naming server.

```
@SpringBootApplication
@EnableEurekaServer    // import and enable Eureka Server with auto-configuration
public class NamingserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NamingserviceApplication.class, args);
	}

}
```

believe it or not that's all for the naming server, and it's ready to work!

### Setup items service
- Go to pom.xml file and add the dependency **Eureka Client**:

```
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

- also, in your pom.xml file do not forget to add *spring cloud dependency management*:

```
	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>${spring-cloud.version}</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>
```

Now we need to tell the service to act like *Eureka Client*. go to your application class -the one with the main method-.
- up or down **@SpringBootApplication** annotation add **@@EnableEurekaClient** annotation to add Eureka Client auto-configuration to the service, so the service will act as namingserver client.

```
@SpringBootApplication
@EnableEurekaClient    // import and enable Eureka Client with auto-configuration
public class ItemsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItemsApplication.class, args);
	}

}
```

- then go to application.properties file, and give your service a name -so other services call you by your name-:

```
# remember you may choose different available port
server.port=8081

# notifying the naming server - Eureka - : "this service has a name and it is:"
spring.application.name=items-service
# here we are referring to the naming server -Eureka- url
eureka.client.service-url.default-zone=http://localhost:8761/eureka
```

that's all for any Eureka Client!

### Setup cart service
For *cart service* we will apply the same things as we did for *items service*
- Go to pom.xml file and add **Eureka Client**:

```
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

- also, in your pom.xml file do not forget to add *spring cloud dependency management*:

```
	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>${spring-cloud.version}</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>
```

- up or down **@SpringBootApplication** annotation add **@@EnableEurekaClient** annotation to add Eureka Client auto-configuration to the service, so the service will act as namingserver client.

```
@SpringBootApplication
@EnableEurekaClient    // import and enable Eureka Client with auto-configuration
public class CartApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItemsApplication.class, args);
	}

}
```

- add service name in application.properties, and add a reference to the namingserver :

```
# you have to choose a different port than items service
server.port=8082

# notifying the naming server - Eureka - : "this service has a name and it is:"
spring.application.name=cart-service
# here we are referring to the naming server -Eureka- url
eureka.client.service-url.default-zone=http://localhost:8761/eureka
```

- now we need to add **@LoadBalanced** annotation to RestTemplate bean, so instead of calling other services with uri, we will use services names!

```
@Configuration
public class RestTemplateConfig {

	@Bean
	@LoadBalanced // adding load balance annotation
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
```

finally, inside your *CartController* we want to replace the fixed call to *items service* with a dynamic name provided by the namingserver:

```
@RestController
public class CartController {

	@Autowired
	private RestTemplate restTemplate;

	//private final String ItemService = "http://localhost:8081/item";
	private final String ItemService = "http://items-service/item/"; // we are replacing fixed call by using service name.

	@GetMapping("/cart")
	public String getCartItems() {
		String itemServiceResponse = restTemplate.getForObject(ItemService, String.class);
		return "this is your cart items: "+itemServiceResponse;
	}
}
```

# Test your application
now open your browser and test the services:
* http://localhost:8081/item will return: "this is items service!"
* http://localhost:8082/cart will return: "this is your cart items: this is items service!"
* http://localhost:8761/	will return a page with system status -active services for instance-.





**NOTICE**: *this is not the pattern neither the design that happening in production environments for sure! this is only to simplify things, by breaking down the services.*
