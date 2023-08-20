package com.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	
	//custom finder method
		List<Post> findByUser(User user);
		
		List<Post> findByCategory(Category category);
		
		List<Post> findByUserAndCategory(User user,Category category);
		
		List<Post> findByTitleContaining(String title) ;

}
