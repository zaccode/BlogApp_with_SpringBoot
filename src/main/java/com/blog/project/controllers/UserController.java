package com.blog.project.controllers;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.project.payloads.ApiResponse;
import com.blog.project.payloads.UserDto;
import com.blog.project.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//POST - create user
	@PostMapping("/")
	public ResponseEntity<UserDto>createUser(@Valid @RequestBody UserDto userDto){
		UserDto createUserDto  = this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
	}
	
	//PUT - update user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto>updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer uid){
		UserDto updateUserDto = this.userService.updateUser(userDto, uid);
		return ResponseEntity.ok(updateUserDto);
	}
	
	//DELETE = delete user
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse>deleteUser(@PathVariable("userId") Integer uid){
		this.userService.deleteUser(uid);
//		return new ResponseEntity(Map.of("message","User Deleted Successfully"),HttpStatus.OK);
		return new ResponseEntity(new ApiResponse("User Deleted Successfully",true),HttpStatus.OK);

	}
	
	//GET - user get
	@GetMapping("/")
	public ResponseEntity<List<UserDto>>getAllUsers(){
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
	
	//GET - single user get
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto>getSingleUser(@PathVariable("userId") Integer uid){
		return ResponseEntity.ok(this.userService.getUserById(uid));
	}
}
