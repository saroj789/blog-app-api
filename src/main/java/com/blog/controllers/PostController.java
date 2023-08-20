package com.blog.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.config.AppConstants;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.services.FileService;
import com.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("user/{userId}/category/{catId}/")
	private ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable Integer userId,@PathVariable Integer catId){
		PostDto createdPostDto  = this.postService.createPost(postDto, userId, catId);
		return new ResponseEntity<PostDto>(createdPostDto,HttpStatus.CREATED);
		
	}
	
	
	@PutMapping("/{postId}/")
	private ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId){
		PostDto updatedPostDto  = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPostDto,HttpStatus.OK);
		
	}
	
	/*
	@GetMapping("/")
	private ResponseEntity<List<PostDto>> getAllPosts(){
		List<PostDto> postDtos  = this.postService.getAllPost();
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
		
	}
	*/
	
	
	//for pagination or batching
	/*
	@GetMapping("/")
	private ResponseEntity<List<PostDto>> getAllPosts(
			@RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize
			){
		List<PostDto> postDtos  = this.postService.getAllPost(pageNumber,pageSize);
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
		
	}
	*/
	
	//batching response in proper format
	@GetMapping("")
	private ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER,required=false) Integer pageNumber,
			@RequestParam(value="pageSize",  defaultValue=AppConstants.PAGE_SIZE,  required=false) Integer pageSize,
			@RequestParam(value="sortBy",    defaultValue=AppConstants.SORT_BY,    required=false) String  sortBy,
			@RequestParam(value="sortDir",   defaultValue=AppConstants.SORT_DIR,   required=false) String  sortDir
			){
		PostResponse postResponse  = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<>(postResponse,HttpStatus.OK);	
	}
	
	
	
	@GetMapping("/{postId}/")
	private ResponseEntity<PostDto> getePost(@PathVariable Integer postId){
		PostDto postDto  = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
	}
	
	
	@GetMapping("user/{userId}/")
	private ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){
		List<PostDto> postDtos  = this.postService.getPostByUser(userId);
		return new ResponseEntity<>(postDtos,HttpStatus.OK);
		
	}
	
	@GetMapping("/category/{catId}/")
	private ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer catId){
		List<PostDto> postDtos  = this.postService.getPostByCategory(catId);
		return new ResponseEntity<>(postDtos,HttpStatus.OK);
		
	}
	
	@GetMapping("user/{userId}/category/{catId}/")
	private ResponseEntity<List<PostDto>> getPostByUserAndCategory(@PathVariable Integer userId,@PathVariable Integer catId){
		List<PostDto> postDtos  = this.postService.getPostByUserAndCategory( userId, catId);
		return new ResponseEntity<>(postDtos,HttpStatus.OK);
		
	}
	
	
	//we can return ApriResponse object also
	@DeleteMapping("/{postId}/")
	private ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
		this.postService.deletePost(postId);
		return new ResponseEntity<>(new ApiResponse("Post deleted successfuly",true),HttpStatus.OK);
	}
	
	@DeleteMapping("/")
	private ResponseEntity<ApiResponse> deleteAllPost(){
		this.postService.deleteAllPost();
		return new ResponseEntity<>(new ApiResponse("All Post deleted successfuly",true),HttpStatus.OK);
	}
	
	@GetMapping("/search")
	private ResponseEntity<List<PostDto>> getPostByTitleContains(@RequestParam(value="titleLike",required = false) String title){
		List<PostDto> postDtos  = this.postService.getPostByTitleContaining(title);
		return new ResponseEntity<>(postDtos,HttpStatus.OK);	
	}
	
	@PostMapping("/uploadimage/{postId}/")
	private ResponseEntity<PostDto> uploadImage(
			@RequestParam(name = "image", required = true) MultipartFile image,
			@PathVariable("postId") Integer postId
			){
		boolean isUploaded = false;
		
		PostDto postDto = this.postService.getPostById(postId);
		
		try {
			isUploaded = this.fileService.uploadFile(path, image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(isUploaded) {
			postDto.setImageName(image.getOriginalFilename());
			PostDto updaPostDto= this.postService.updatePost(postDto, postId);
			return new ResponseEntity<>(updaPostDto,HttpStatus.OK);
			
		}
		return null;
	}
	
	
	@GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	private void downloadImage(
			@PathVariable String imageName,
			HttpServletResponse response
			){
		InputStream resource=null;
		try {
			resource = this.fileService.getResource(path, imageName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		try {
			StreamUtils.copy(resource, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
