package spring.com.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.com.security.service.EmailSender;
import spring.com.security.service.IUserService;

@RestController
@RequestMapping("/email")
public class EmailController {
	@Autowired
	EmailSender emailsender;
	@Autowired
	IUserService us;

	@GetMapping("/sendEmailTo/{to}/{email}")
	public void sendemailTo(@PathVariable String to,@PathVariable String email) {
		 emailsender.send(to, email);
	}
	
	@GetMapping("/sendEmailForgetPassword/{username}")
		public int sendEmailForgetPassword (@PathVariable String username) {
	return us.sendEmailForgetPassword(username);
	}
	
}
