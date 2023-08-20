package com.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CommentDto;
import com.blog.services.CommentService;

@RestController
@RequestMapping("/api/comments/")
public class CommnetController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/add/")
	private ResponseEntity<CommentDto> createComment(
			@RequestBody CommentDto commentDto,
			@RequestParam(name = "postId",  required = true, defaultValue = "20") Integer postId,
			@RequestParam(name = "userId", required = true, defaultValue = "2") Integer userId
			)  {
		CommentDto addedComment = this.commentService.addComment(commentDto, postId, userId);
		return new ResponseEntity<>(addedComment,HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("delete/{commentId}/")
	private ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<>(new ApiResponse("Comment deleted successfully",true),HttpStatus.OK);
	}

}
