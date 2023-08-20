package com.blog.payloads;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class PostDto {

	private Integer postId;
	private String title;
	private String content;
	
	private String imageName;
	private Date createdDate;
	private CategoryDto category;
	private UserDto user;
	
	private List<CommentDto> comments= new ArrayList<>();
	//private Set<CommentDto> comments= new HashSet();
}
