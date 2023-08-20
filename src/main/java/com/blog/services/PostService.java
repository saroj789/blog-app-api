package com.blog.services;

import java.util.List;

import com.blog.entities.Category;
import com.blog.entities.User;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;

public interface PostService {
	
	PostDto createPost(PostDto postDto,Integer userId, Integer CategoryId);
	
	PostDto updatePost(PostDto postDto, Integer postId);
	
	void deletePost(Integer postId);
	
	void deleteAllPost();
	
	List<PostDto> getAllPost();
	
	//List<PostDto> getAllPost(Integer pageNumber,Integer PageSize);
	
	PostResponse getAllPost(Integer pageNumber,Integer PageSize,String sortBy, String sortDir);
	
	
	PostDto getPostById(Integer postId);
	
	List<PostDto> getPostByUser(Integer userId);
	
	List<PostDto> getPostByCategory(Integer categoryId);
	
	List<PostDto> getPostByUserAndCategory(Integer userId,Integer categoryId);
	
	
	List<PostDto> getPostByTitleContaining(String title);
	
	
	
	
	

}
