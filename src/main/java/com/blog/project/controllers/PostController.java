package com.blog.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.blog.project.services.PostService;

import jakarta.validation.Valid;

import com.blog.project.payloads.ApiResponse;
import com.blog.project.payloads.PostDto;
import com.blog.project.entities.Post;

@RestController
@RequestMapping("/api")
public class PostController {
	@Autowired
	private PostService postService;
	
	//create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto>createPost(
			@Valid
			@RequestBody PostDto postDto,
			@PathVariable Integer userId,
			@PathVariable Integer categoryId
			){
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
	}
	
	//get by User
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>>getPostByUser(@PathVariable Integer userId){
		List<PostDto>posts = this.postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	//get by Category
	@GetMapping("/post/{postId}/posts")
	public ResponseEntity<PostDto>getPostById(@PathVariable Integer postId){
		PostDto post = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(post,HttpStatus.OK);
	}
	
	//get post by Id
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>>getPostByCategory(@PathVariable Integer categoryId){
		List<PostDto>posts = this.postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}

	//get all Posts
	@GetMapping("/posts")
	public ResponseEntity<List<PostDto>>getAllPost(){
		List<PostDto>posts = this.postService.getAllPost();
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	//delete post
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ApiResponse("Post is Deleted Successfully",true);
	}
	
	//update post
	@PutMapping("/post/{postId}/post")
	public ResponseEntity<PostDto>updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Integer postId){
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
}
