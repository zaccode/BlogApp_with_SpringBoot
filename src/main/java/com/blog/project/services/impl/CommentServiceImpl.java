package com.blog.project.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.project.entities.Comment;
import com.blog.project.entities.Post;
import com.blog.project.entities.User;
import com.blog.project.exceptions.ResourceNotFoundException;
import com.blog.project.payloads.CommentDto;
import com.blog.project.repository.PostRepo;
import com.blog.project.repository.UserRepo;
import com.blog.project.repository.CommentsRepo;
import com.blog.project.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CommentsRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId,Integer userId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("Post","postId",postId));
		User user = this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User","userId",userId));

		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setUser(user);
		comment.setPost(post);
		Comment savedComment =this.commentRepo.save(comment);
		
		return  this.modelMapper.map(savedComment, CommentDto.class); 
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId)
				.orElseThrow(()->new ResourceNotFoundException("Comment","commentId",commentId));
			this.commentRepo.delete(comment);
	}

}
