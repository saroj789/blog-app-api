package com.blog.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Comment {
	
	@Column(name = "comment_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int commentId;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "createddate")
	private Date createdDate;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;
	

}
