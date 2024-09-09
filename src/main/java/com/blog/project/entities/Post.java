package com.blog.project.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="posts")
@Getter
@Setter
@NoArgsConstructor
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;
	
	@Column(name="title",length=100,nullable=false)
	private String postTitle;
	
	@Column(name="content", length=10000)
	private String postContent;
	
	@Column(name = "imageName")
	private String postImageName;
	
	@Column(name = "addedDate")
	private Date PostAddedDate;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	
	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy="post",cascade = CascadeType.ALL)
	private Set<Comment>comments = new HashSet<>();
}
