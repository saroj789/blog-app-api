package com.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.entities.User;
import com.blog.exceptions.ApiException;
import com.blog.payloads.JWTAuthRequest;
import com.blog.payloads.JWTAuthResponse;
import com.blog.payloads.UserDto;
import com.blog.security.JWTTokenHelper;
import com.blog.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	private JWTTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/login")
	public ResponseEntity<JWTAuthResponse> createToken(
			@RequestBody JWTAuthRequest request) {
		
		System.out.println("user : "+request.getUsername()+"   "+request.getPassword());
		this.doAuthenticate(request.getUsername(),request.getPassword()); 
		
		
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetails);
		
		JWTAuthResponse response = new JWTAuthResponse(token);
		return new ResponseEntity<JWTAuthResponse>(response,HttpStatus.OK);
		
	}

	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) 
	{
		UserDto createdUserDto = this.userService.registerDto(userDto);	
		return new ResponseEntity<>(createdUserDto,HttpStatus.CREATED);
	}
	
	
	
	 private void doAuthenticate(String email, String password) {

	        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
	        try {
	            manager.authenticate(authentication);


	        } catch (BadCredentialsException e) {
	            throw new ApiException(" Invalid Username or Password  !!");
	        }

	    }

}
