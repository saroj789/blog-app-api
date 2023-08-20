package com.blog.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class PostResponse {
	
	private int pageNumber;
	private int pageSize;
	private int elementsFetched;
	private long totalElements;
	private int totalPages;
	private boolean lastPage;
	
	private List<PostDto> content;
	

}
