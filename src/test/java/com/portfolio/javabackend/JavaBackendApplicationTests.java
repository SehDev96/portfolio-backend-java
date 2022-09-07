package com.portfolio.javabackend;

import com.portfolio.javabackend.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class JavaBackendApplicationTests {

	@Test
	void contextLoads() {
		UserController userController = new UserController();
		ResponseEntity<?> response = userController.testing();
		System.out.println(response);
	}


}
