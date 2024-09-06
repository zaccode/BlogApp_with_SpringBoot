package com.blog.project.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.project.entities.Category;
import com.blog.project.entities.Post;
import com.blog.project.entities.User;

public interface PostRepo extends JpaRepository<Post,Integer>{
	List<Post>findByUser(User user);
	
	List<Post>findByCategory(Category category);
}
