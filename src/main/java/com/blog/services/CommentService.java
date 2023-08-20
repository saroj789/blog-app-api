package com.blog.services;

import java.util.List;

import com.blog.payloads.CommentDto;

public interface CommentService {
	
	CommentDto addComment(CommentDto commentDto,Integer postId, Integer userId);
	
	void deleteComment(Integer commentId);
	
	List<CommentDto> getAllComments();
	
	CommentDto getComment(Integer commentId);
	
	List<CommentDto> getCommentsByPost(Integer posId);
	
	List<CommentDto> getCommentsByUser(Integer userId);
	
	
	
	

}
