package com.blog.project.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.blog.project.entities.Category;
import com.blog.project.entities.Post;
import com.blog.project.entities.User;
import com.blog.project.exceptions.ResourceNotFoundException;
import com.blog.project.payloads.PostDto;
import com.blog.project.payloads.PostResponse;
import com.blog.project.repository.CategoryRepo;
import com.blog.project.repository.PostRepo;
import com.blog.project.repository.UserRepo;
import com.blog.project.services.PostService;

@Service
public class PostServiceImp implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper; 
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User","UserID",userId));
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));
		
		Post post = this.modelMapper.map(postDto,Post.class);
		post.setPostImageName("default.png");
		post.setPostAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);	
	
		Post newPost = this.postRepo.save(post);
		
		return this.modelMapper.map(newPost, PostDto.class);
				
	}

	@Override
	public PostDto updatePost(PostDto postDto,Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("Posts","postId",postId));	
		post.setPostTitle(postDto.getPostTitle());
		post.setPostContent(postDto.getPostContent());
		post.setPostImageName(postDto.getPostImageName());
		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("Posts","postId",postId));	
		this.postRepo.delete(post);

	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		Pageable p = (sortDir.equalsIgnoreCase("desc"))?PageRequest.of(pageNumber, pageSize,Sort.by(sortBy).descending()):PageRequest.of(pageNumber, pageSize,Sort.by(sortBy).ascending());
		Page<Post> pagePost = this.postRepo.findAll(p);
		List<Post>posts = pagePost.getContent();
		List<PostDto>postDtos = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElement(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("Posts","postId",postId));	
		return this.modelMapper.map(post,PostDto.class);

	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).
				orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryId));
		
		List<Post>posts = this.postRepo.findByCategory(category);
		
		List<PostDto>postDtos = posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User","UserID",userId));
		List<Post>posts = this.postRepo.findByUser(user);
		List<PostDto>postDtos = posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keywords) {
		//manual query method called then
		//List<Post>posts = this.postRepo.searchByTitle("%"+keyword+"%");
		List<Post>posts = this.postRepo.findBypostTitleContaining(keywords);
		List<PostDto>postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

}
