package com.blog.project.payloads;

import lombok.*;

@Setter
@Getter
public class CommentDto {
	private int id;
	
	private String content;
	
	private UserDto user;
	
}
