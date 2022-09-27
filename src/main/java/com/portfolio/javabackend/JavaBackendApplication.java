package com.portfolio.javabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableAutoConfiguration
public class JavaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaBackendApplication.class, args);
	}


	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				//registry.addMapping("/*").allowedOrigins("*");
				registry.addMapping("/app/login").allowedOriginPatterns(
						"http://localhost:3000",
						"http://localhost:3000/login");
//						"http://localhost:8080",
//						"http://localhost:8080/login");
				WebMvcConfigurer.super.addCorsMappings(registry);
			}
		};
	}

}
