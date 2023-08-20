package com.blog.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Getter
@Setter
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "email", nullable = false)
	private String email;
	private String about;
	@Column(name = "password", nullable = false)
	private String password;
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<Post> posts = new ArrayList<>();
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<Comment> comments = new ArrayList<>();

}
