package com.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.Advice.This;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.repositories.CategoryRepo;
import com.blog.repositories.PostRepo;
import com.blog.repositories.UserRepo;
import com.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PostDto createPost(PostDto postDto,Integer userId, Integer categoryId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Id", categoryId));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setCreatedDate(new Date());
		post.setCategory(category);
		post.setUser(user);
		
		Post newPost = this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto pDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
		post.setContent(pDto.getContent());
		post.setImageName(pDto.getImageName());
		post.setTitle(pDto.getTitle());
		Post updatePost= this.postRepo.save(post);
		return this.modelMapper.map(updatePost, PostDto.class);
		
	}

	@Override
	public void deletePost( Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
		this.postRepo.delete(post);
	}
	
	@Override
	public void deleteAllPost() {
		this.postRepo.deleteAll();
	}

	
	@Override
	public List<PostDto> getAllPost() {
		List<Post> posts = this.postRepo.findAll();
		
		List<PostDto> postDtos = posts.stream().map(post-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		// TODO Auto-generated method stub
		return postDtos;
	}
	
	
	
	/*
	@Override
	public List<PostDto> getAllPost(Integer pageNumber,Integer pageSize) {
		Pageable page = PageRequest.of(pageNumber, pageSize);
		Page<Post> pagePosts =  this.postRepo.findAll(page);
		List<Post> allPosts = pagePosts.getContent();
		List<PostDto> postDtos = allPosts.stream().map(post-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		// TODO Auto-generated method stub
		return postDtos;
	}
	*/
	
	
	// batching response in proper format
	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		Sort sort = null;
		sort = ( sortDir.equalsIgnoreCase("asc") ) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending() ;
		
		Pageable page = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost =  this.postRepo.findAll(page);
		
		List<Post> allPosts = pagePost.getContent();
		List<PostDto> postDtos = allPosts.stream().map(post-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		postResponse.setElementsFetched(pagePost.getNumberOfElements());
		
		return postResponse;
	}
	

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		List<PostDto> postDtos = this.postRepo.findByUser(user).stream().map(post ->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
		List<PostDto> postDtos = this.postRepo.findByCategory(category).stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByUserAndCategory(Integer userId, Integer categoryId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
		List<PostDto> postDtos = this.postRepo.findByUserAndCategory(user, category).stream().map(post-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByTitleContaining(String title) {
		List<Post> posts = this.postRepo.findByTitleContaining(title);
		List<PostDto> postDtos = posts.stream().map(post-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

}
