package com.blog.project.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blog.project.entities.Category;
import com.blog.project.entities.Post;
import com.blog.project.entities.User;

public interface PostRepo extends JpaRepository<Post,Integer>{
	List<Post>findByUser(User user);
	
	List<Post>findByCategory(Category category);
	
	//Manual Query uses then use this type of method
//	@Query("select p from Post p where p.title like :key")
//	List<Post>searchByTitle(@Param("key") String title)
	List<Post>findBypostTitleContaining(String keyword);
}
