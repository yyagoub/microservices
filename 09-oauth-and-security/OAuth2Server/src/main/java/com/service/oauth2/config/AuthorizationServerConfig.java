package com.service.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	/*
	 * AuthorizationServerConfig >>>> authorizatione service extending
	 * AuthorizationServerConfigurerAdapter will create a filter chain
	 * 
	 * SecurityConfig >>>> authentication service extending
	 * WebSecurityConfigurerAdapter will create another filter chain
	 * 
	 * adding @EnableResourceServer with -> @SpringBootApplication will add a third
	 * filter chain
	 * 
	 * total of filter chain is: 3
	 */

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authManager;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// we need to register web application as a client
		// of the authorization server

		/*
		 * you can also store your client metadata in the database as Spring has a jdbc
		 * builder.
		 * 
		 * or you could create your own implementation by creating a class that 1 -
		 * implements ClientDetailsService class 2 - implementing loadClientByClientId()
		 * method that return ClientDetails object
		 */

		// for demo we will use in momery configurations
		clients.inMemory().withClient("first-client").secret(passwordEncoder.encode("noonewilleverguess"))
				.authorizedGrantTypes("client_credentials", "password").scopes("any")
		// search for redirect usage
		// .redirectUris("http://localhost:8081/oauth/login/client-app")
		;
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints.authenticationManager(authManager);
	}

}
