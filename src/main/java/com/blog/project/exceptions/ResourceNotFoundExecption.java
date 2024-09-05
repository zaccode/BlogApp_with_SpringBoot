package com.blog.project.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResourceNotFoundExecption extends RuntimeException{
	String resourceName;
	String fieldName;
	long fieldValue;
	public ResourceNotFoundExecption(String resourceName, String fieldName, long fieldValue) {
		super(String.format(resourceName+" not found with "+fieldName+" : "+fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	

}
