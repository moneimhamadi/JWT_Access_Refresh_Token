package spring.com.security;


import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import spring.com.security.entities.User;
import spring.com.security.service.IUserService;

@SpringBootApplication
public class SpringSecurtiyAccessRefreshTokenApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurtiyAccessRefreshTokenApiApplication.class, args);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	CommandLineRunner run(IUserService userService) {
		return args ->{
			ArrayList<String> roles = new ArrayList<String>();
			roles.add("ADMINISTRATOR");
			roles.add("RESPONSABLE");
			userService.addUser(new User("4", "hamma", "hamma","12345",roles));
		};
	}

}
