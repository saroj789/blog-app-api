package com.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.config.AppConstants;
import com.blog.entities.Role;
import com.blog.repositories.RoleRepo;

@SpringBootApplication
public class BlogAppApiApplication implements CommandLineRunner {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApiApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	
	
	@Override
	public void run(String... args) throws Exception {
		
		try {
			Role r1 = new Role();
			r1.setId(AppConstants.ADMIN_USER);
			r1.setName("ADMIN_USER");
			
			Role r2 = new Role();
			r2.setId(AppConstants.NORMAL_USER);
			r2.setName("NORMAL_USER");
			
			List<Role> roles = List.of(r1,r2);
			
			List<Role> result = this.roleRepo.saveAll(roles);
			
		} catch (Exception e) {
			System.out.println("Exception while creating role");
			e.printStackTrace();
		}
		
		
		
		
		
		
		//if you want to check encoded password
		String pass = this.passwordEncoder.encode("normal");
		System.out.println("pass :  "+pass);
	}
}
