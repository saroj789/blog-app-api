package com.blog.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGenrator implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void run(String... args) throws Exception {
		String pass = this.passwordEncoder.encode("xyz");
		System.out.println("pass :  "+pass);
	}

}
