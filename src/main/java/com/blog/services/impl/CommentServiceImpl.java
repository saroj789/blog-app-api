package com.blog.services.impl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CommentDto;
import com.blog.repositories.CommentRepo;
import com.blog.repositories.PostRepo;
import com.blog.repositories.UserRepo;
import com.blog.services.CommentService;


@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto addComment(CommentDto commentDto, Integer postId, Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
		
		Comment comment= this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		comment.setUser(user);
		comment.setCreatedDate(new Date());
		
		Comment savedComment = this.commentRepo.save(comment);
		return this.modelMapper.map(savedComment, CommentDto.class);
	}
	

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));
		this.commentRepo.delete(comment);
		
	}

	@Override
	public List<CommentDto> getAllComments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommentDto getComment(Integer commentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CommentDto> getCommentsByPost(Integer posId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CommentDto> getCommentsByUser(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
