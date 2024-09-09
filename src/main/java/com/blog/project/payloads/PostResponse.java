package com.blog.project.payloads;

import java.util.*;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
public class PostResponse {
	//this is used for the pagination purpose
	private List<PostDto> content;
	private int pageNumber;
	private int pageSize;
	private long totalElement;
	private int totalPages;
	private boolean lastPage;
}
