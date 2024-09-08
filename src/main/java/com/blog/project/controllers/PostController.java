package com.blog.project.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.blog.project.services.FileService;
import com.blog.project.services.PostService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;


import com.blog.project.payloads.ApiResponse;
import com.blog.project.payloads.PostDto;
import com.blog.project.payloads.PostResponse;
import com.blog.project.config.AppConstants;
import com.blog.project.entities.Post;

@RestController
@RequestMapping("/api")
public class PostController {
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileservice;
	
	@Value("${project.image}")
	private String path;
	
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
	public ResponseEntity<PostResponse>getAllPost(
			@RequestParam(value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER,required=false)Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue=AppConstants.PAGE_SIZE,required=false)Integer pageSize,
			@RequestParam(value="sortBy",defaultValue=AppConstants.SORT_BY,required=false)String sortBy,
			@RequestParam(value="sortDir",defaultValue=AppConstants.SORT_DIR,required=false)String sortDir
			){
		PostResponse postResponse = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
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
	
	//search field
	@GetMapping("/post/search/{keywords}")
	public ResponseEntity<List<PostDto>>searchPosts(@PathVariable("keywords") String keywords){
		List<PostDto>postDtos = this.postService.searchPosts(keywords);
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
	}
	
	//post image upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto>uploadPostImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId
			) throws IOException{
		PostDto postDto = this.postService.getPostById(postId);
		String fileName = this.fileservice.uploadImage(path, image);
		postDto.setPostImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
	//method to serve files
	@GetMapping(value="/post/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName,
			HttpServletResponse response
			) throws IOException {
		InputStream resource = this.fileservice.getResource(path, imageName);
		
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource,response.getOutputStream());
	}
	
}
