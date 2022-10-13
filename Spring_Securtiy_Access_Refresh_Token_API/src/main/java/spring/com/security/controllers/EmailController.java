package spring.com.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.com.security.service.EmailSender;

@RestController
@RequestMapping("/email")
public class EmailController {
	@Autowired
	EmailSender emailsender;

	@GetMapping("/sendEmailTo/{to}/{email}")
	public void sendemailTo(@PathVariable String to,@PathVariable String email) {
		 emailsender.send(to, email);
	}
}
