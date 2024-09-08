package com.blog.project.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.project.services.FileService;

@Service
public class FileServiceImp implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		
		//File name
		String name= file.getOriginalFilename();
		//abc.png
		
		//random name generate file
		String randomId = UUID.randomUUID().toString();
		String fileName1 =randomId.concat(name.substring(name.lastIndexOf(".")));
		
		//Fullpath
		String filePath = path + File.separator +fileName1;
		
		
		//create folder if not created
		File f = new File(path);
		if(!f.exists()) {
			f.mkdirs();
		}
		
		//file copy
		Files.copy(file.getInputStream(), Paths.get(filePath));
		
		return fileName1;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPath = path + File.separator + fileName;
		InputStream is = new FileInputStream(fullPath);
		//do logic to return inputStream
		return is;
	}

}
