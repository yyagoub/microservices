# Minimum secured service ever!

In this example, accessing resources required a username and a password.


### Prerequisites

IDE and minimum knowledge of java and spring framework.


## Getting Started:

### Initializing your projects

From [start.spring.io](https://start.spring.io/) we are going to initializing two projects:
* We will add **Spring Web** and **Spring Security** as dependencies, give your project a name -*security* for instance-, then click generate.
* extract the project and import it to your IDE.

### Setup items service
first let's discover your service classes, you will find one class has the annotation **@SpringBootApplication** which going to boot up your service using its only main method.
any other spring components must be one level down the package that contains your main method, unless you configure package scanning.
*you may test it now! just right click on the class file, and run as -> java application.*

then create new controller class, that will response to http get request

```
@RestController
public class Controller {

	@GetMapping("/")
	public String defaultString() {
		return "this is a default string";
	}
}
```

first, let's setup a username and a password in application.properties file,:

```
# give spring security a default username and password
spring.security.user.name=user
spring.security.user.password=user
```

and this is your secured service!


# Test your application
now open your browser and test the services:
* http://localhost:8080/
will ask you for username and password, enter the assigned username and password in the application.properties file.
then spring security will validate your identity and give you a response.
if identity is not valid you will receive an error.
if identity is valid then you will receive: *"this is a default string"*





**NOTICE**: *this is not the pattern neither the design that happening in production environments for sure! this is only to simplify things, by break down services.*
