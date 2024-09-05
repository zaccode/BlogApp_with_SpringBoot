package com.blog.project.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.blog.project.entities.User;

public interface UserRepo extends JpaRepository<User,Integer>{
	

}
