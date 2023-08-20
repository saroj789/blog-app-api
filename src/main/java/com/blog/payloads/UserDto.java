package com.blog.payloads;


import java.util.List;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private long id;
	
	@NotEmpty
	@Size(min = 4, message = "username must be min of 4 char !!")
	private String name;
	@Email
	@NotEmpty
	private String email;
	@NotEmpty
	private String about;
	@NotEmpty
	@Size(min = 4, max = 8, message = "password must be min of 4 char and max of 4 char !!")
	private String password;
	
	//private List<PostDto> posts;
	//private List<CommentDto> comments;
}
