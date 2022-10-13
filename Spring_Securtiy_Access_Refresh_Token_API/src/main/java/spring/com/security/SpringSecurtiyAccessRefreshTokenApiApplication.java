package spring.com.security;


import java.util.ArrayList;
import java.util.Properties;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import spring.com.security.entities.User;
import spring.com.security.service.IUserService;

//@EnableEurekaClient
//@EnableZuulProxy
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
    public JavaMailSender javaMailSender() { 
    	JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    	mailSender.setHost("smtp.gmail.com");
    	mailSender.setPort(587);
    
    	Properties props = mailSender.getJavaMailProperties();
    	props.put("mail.transport.protocol", "smtp");
    	props.put("mail.smtp.auth", "true");
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.debug", "true");
    	return mailSender;
    }
//	@Bean
//	CommandLineRunner run(IUserService userService) {
//		return args ->{
//			ArrayList<String> roles = new ArrayList<String>();
//			roles.add("ADMINISTRATOR");
//			roles.add("RESPONSABLE");
//			userService.addUser(new User("4", "hamma", "hamma","12345",roles));
//		};
//	}

}
