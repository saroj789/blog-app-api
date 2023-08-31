package com.blog.config;


import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.blog.security.JWTAuthenticationEntryPoint;
import com.blog.security.JWTAuthenticationFilter;



@Configuration
public class SecurityConfig {
	
	@Autowired
	private JWTAuthenticationEntryPoint point;
	
	@Autowired
	private JWTAuthenticationFilter filter;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	 @Bean
	 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
        	.cors(cors -> cors.disable())
            .authorizeHttpRequests(
            	auth ->
            		auth
            		.requestMatchers("/api/v1/auth/login").permitAll()
            		.anyRequest().authenticated()  
            )
            //.authenticationProvider(daoAuthenticationProvider())
            .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
            
            http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
	 }
	 
	 
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
	
	
	/*
	 @Bean
	 public DaoAuthenticationProvider daoAuthenticationProvider() {
		 
		 DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		 authenticationProvider.setUserDetailsService(userDetailsService);
		 authenticationProvider.setPasswordEncoder(passwordEncoder());
		 
		 return authenticationProvider;	 
	 }
	 */
	
	
	
}
