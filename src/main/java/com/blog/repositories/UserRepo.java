package com.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.entities.User;


public interface UserRepo extends JpaRepository<User, Integer> {

}
