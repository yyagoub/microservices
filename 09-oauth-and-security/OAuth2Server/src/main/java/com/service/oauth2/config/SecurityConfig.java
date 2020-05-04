
package com.service.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	/*
	 * to test user login use:
	 * curl first-client:noonewilleverguess@localhost:8080/oauth/token -dgrant_type=password -dscope=any -dusername='user' -dpassword='user'
	 * or
	 * curl first-client:noonewilleverguess@localhost:8080/oauth/token -dgrant_type=password -dscope=any -dusername='admin' -dpassword='admin'
	 */
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
	       return super.authenticationManagerBean();
	}
	
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
    	
    	UserDetails user=User.builder()
    			.username("user")
    			.password(passwordEncoder().encode("user"))
    			.roles("USER")
    			.authorities("USER")
    			.build();
    	
    	UserDetails userAdmin=User.builder()
    			.username("admin")
    			.password(passwordEncoder().encode("admin"))
    			.roles("ADMIN")
    			.authorities("ADMIN")
    			.build();
    	
        return new InMemoryUserDetailsManager(user,userAdmin);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new  BCryptPasswordEncoder();
    }
   

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
        	.csrf().disable()
            .authorizeRequests()
            	.antMatchers("/","/index","/webpublico").permitAll()
            	//.antMatchers("/webprivado").authenticated()
            	//.antMatchers("/webadmin").hasRole("ADMIN")
            	.and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout() // Metodo get pues he desabilitado CSRF
                .permitAll();
    }
}
