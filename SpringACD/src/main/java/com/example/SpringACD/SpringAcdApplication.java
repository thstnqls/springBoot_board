package com.example.SpringACD;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.SpringACD")    // Spring Boot 애플리케이션의 메인 클래스에 사용되는 편리한 어노테이션
@EnableAutoConfiguration      //Spring Boot의 자동 구성(Auto-configuration)을 활성화하는 역할
public class SpringAcdApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAcdApplication.class, args);
	}    //서버동작

}

