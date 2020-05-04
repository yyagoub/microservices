package com.service.oauth2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	/*
	 * AuthorizationServerConfig	>>>>	authorizatione service
	 * extending AuthorizationServerConfigurerAdapter will create a filter chain
	 * 
	 * SecurityConfig				>>>>	authentication service	* not used in this project
	 * extending WebSecurityConfigurerAdapter will create another filter chain
	 * 
	 * adding @EnableResourceServer with -> @SpringBootApplication
	 * will add a third filter chain			* not used in this project
	 * 
	 * total of filter chain is: 3
	 */
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// we need to register web application as a client
		// of the authorization server
	
		/*
		 * you can also store your client metadata in the database 
		 * as Spring has a jdbc builder.
		 * 
		 * or you could create your own implementation 
		 * by creating a class that 
		 * 1 - implements ClientDetailsService class
		 * 2 - implementing loadClientByClientId() method that return ClientDetails object
		 */
		
		// for demo we will use in momery configurations
		clients.inMemory().withClient("first-client")
				.secret("{noop}noonewilleverguess")
				.authorizedGrantTypes("client_credentials")
				.scopes("any");
	}
}
