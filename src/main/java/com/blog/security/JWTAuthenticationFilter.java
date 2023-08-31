package com.blog.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTTokenHelper jwtTokenHelper;
	
	//it will exexute when any API request will be called
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
	//1 .get token
		String requestToken = request.getHeader("Authorization");
		System.out.println("requestToken..."+requestToken);   //Bearer 2353567sfyh
		
		String username=null;
		String	token=null;
		
		if (requestToken != null && requestToken.startsWith("Bearer")) {
			token = requestToken.substring(7);
			
			try {
				username = this.jwtTokenHelper.getUsernameFromToken(token);
			}catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT token");
			}catch (ExpiredJwtException e) {
				System.out.println("JWT token has expired");
			}catch (MalformedJwtException e) {
				System.out.println("Invalid JWT");
			}
			
		} else {
			System.out.println("JWT Token does not begin with Bearer !!");
		}
		
	//2. once we get the token, now vaidate token
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			boolean isValid = this.jwtTokenHelper.validateToken(token, userDetails);
			
			if(isValid) {
				//create object of Authentication to pass in setAuthentication
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				
				//authenticate now
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}else {
				System.out.println("Invalid token");
			}
			
		}else {
			System.out.println("Username is null or context is not null ");
		}
		
		filterChain.doFilter(request, response);
		
	}

}
