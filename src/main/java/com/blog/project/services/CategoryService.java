package com.blog.project.services;

import com.blog.project.payloads.CategoryDto;
import java.util.*;

public interface CategoryService {
	//Bydefault in interface everyfunctions are public
	
	//create
	CategoryDto createCategory(CategoryDto categoryDto);
	
	//update
	CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	
	//delete
	void deleteCategory(Integer categoryId);
	
	//get
	CategoryDto get(Integer categoryId);
	
	//get All
	List<CategoryDto>getCategories();
	
}
