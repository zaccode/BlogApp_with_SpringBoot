package com.blog.project;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.blog.project.repository.UserRepo;

@SpringBootTest
class ApplicationTests {
	private UserRepo userRepo;
	@Test
	void contextLoads() {
		
	}
	public void repoTest() {
		String className = this.userRepo.getClass().getName();
		String packName = this.userRepo.getClass().getPackageName();
		System.out.println(className);
		System.out.println(packName);
	}

}
