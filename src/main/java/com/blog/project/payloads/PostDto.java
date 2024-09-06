package com.blog.project.payloads;

import java.util.Date;

import com.blog.project.entities.Category;
import com.blog.project.entities.User;

import jakarta.validation.constraints.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class PostDto {
	@NotBlank
	@Size(min=4,message="Title cannot be blank")
	private String postTitle;
	
	@NotBlank
	@Size(min=10,message= "Content of the post cannot be blank")
	private String postContent;
	
	private String postImageName;
	
	private Date PostAddedDate;
	
	private CategoryDto category;
	
	private UserDto user;

}
