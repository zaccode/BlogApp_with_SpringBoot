package com.blog.project.services;

import com.blog.project.entities.User;
import com.blog.project.payloads.UserDto;
import java.util.*;

public interface UserService {
	UserDto createUser(UserDto userDto);
	UserDto updateUser(UserDto user,Integer userId);
	UserDto getUserById(Integer userId);
	List<UserDto>getAllUsers();
	void deleteUser(Integer userId);
}
