package com.blog.payloads;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class CommentDto {
	
	private int commentId;
	
	private String content;
	
	private Date createdDate;
	
	//private UserDto user;
	
	//private PostDto post;
	

}
