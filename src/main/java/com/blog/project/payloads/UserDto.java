package com.blog.project.payloads;

import java.util.HashSet;
import java.util.Set;

import com.blog.project.entities.Comment;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

//import org.hibernate.validator.constraints.Email;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//Basically this is the class of easily get the data to set and get methods according to the user schema
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
//	@NotNull: Only checks if the value is null. It doesn't consider empty strings as invalid.
//	@NotEmpty: Ensures that the value is neither null nor an empty string ("").
//	@NotBlank: Ensures that the value is neither null, an empty string (""), nor a string with only whitespace (e.g., " ").
 private int id;
	@NotNull
	@NotBlank(message = "Name cannot be Blank")
	@Size(min=4, message="Size must be grater than 4 letters")
 private String name;
	
	@Email(message = "Message cannot be Blank")
 private String email;
	@NotNull
	@NotBlank(message="Password cannot be blank")
	@Size(min=3,max=10, message="Size must be grater than 3 letters and lesser than 10 letter")
 private String password;
	@NotNull
	@NotBlank(message="About Cannot be blank")
	@Size(min=10, message="Size must be grater than 10 letters")
 private String about;
 
//	private Set<CommentDto>comments = new HashSet<>();
 
}
