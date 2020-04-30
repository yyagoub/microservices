package com.service.securityjwt.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.service.securityjwt.util.JwtUtil;

@Component
public class JwtTokenAuthorizationOncePerRequestFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private final String tokenHeader = "Authorization";

    @Autowired
    private UserDetailsService usersService;
    
    @Autowired
    private JwtUtil jwtTokenUtil;
    

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        logger.debug("Authentication Request For '{}'", request.getRequestURL());

        final String requestTokenHeader = request.getHeader(tokenHeader);
        String jwtToken = requestTokenHeader.substring(7);
        String username = jwtTokenUtil.extractUsername(jwtToken);
        
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

        	UserDetails user = usersService.loadUserByUsername(username);
            
            if (jwtTokenUtil.validateToken(jwtToken, user)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
                		new UsernamePasswordAuthenticationToken(user, null, null);
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            
        }

        // As this authentication is in HTTP header, after success we need to continue the request normally
        // and return the response as if the resource was not secured at all
        chain.doFilter(request, response);
    }
}