package com.blog.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.UserDto;
import com.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//GET-get user
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getUsers() {
		List<UserDto> allUserDto =this.userService.getAllUsers();
		return new ResponseEntity<>(allUserDto,HttpStatus.OK);
		
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUser(@PathVariable("userId") Integer userId) {
		UserDto userDto =this.userService.getUserById(userId);
		return ResponseEntity.ok(userDto);
	
	}
	
	//POST-create user
	@PostMapping("/")
	@PreAuthorize("hasRole('ADMIN_USER')")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto createdUserDto =this.userService.createUser(userDto);
		return new ResponseEntity<>(createdUserDto,HttpStatus.CREATED);
		
	}
	
	//PUT-update user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer userid) {
		UserDto updatedUserDto =this.userService.updateUser(userDto, userid);
		return ResponseEntity.ok(updatedUserDto);
		
	}
	
	//DELETE-delete user
	@DeleteMapping("/{userId}")
	//@PreAuthorize("hasRole('ADMIN_USER')")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userid) {
		this.userService.deleteUser(userid);
		//return new ResponseEntity(Map.of("message","user deleted successfuly"),HttpStatus.OK);
		return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfuly",true),HttpStatus.OK);
	}

}
