package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.UserDto;
import com.blog.services.UserService;
import com.blog.repositories.UserRepo;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userDto) {
		// TODO Auto-generated method stub
		User user =  dtoToUser(userDto);
		User saveUser = this.userRepo.save(user);
		return this.UserToDto(saveUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto,Integer id) {
		User user = this.userRepo.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("User","id",id));
		
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		User updatedUser = this.userRepo.save(user);
		return this.UserToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User","id",userId));
		return UserToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user -> this.UserToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User","ID", userId));
		
		this.userRepo.delete(user);
		
	}
	
	
	
	
	
	
	
	
	public User dtoToUser(UserDto userDto) {
		/*
		user.setId(userDto.getId());
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		 */
		
		User user = this.modelMapper.map(userDto, User.class);
		return user;
	}
	public UserDto UserToDto(User user) {
		
		/*
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setEmail(user.getEmail());
		userDto.setName(user.getName());
		userDto.setPassword(user.getPassword());
		userDto.setAbout(user.getAbout());
		 */
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	}

}
