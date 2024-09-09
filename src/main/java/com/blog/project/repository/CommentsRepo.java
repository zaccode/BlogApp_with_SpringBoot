package com.blog.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.project.entities.Comment;

public interface CommentsRepo extends JpaRepository<Comment, Integer> {

}
