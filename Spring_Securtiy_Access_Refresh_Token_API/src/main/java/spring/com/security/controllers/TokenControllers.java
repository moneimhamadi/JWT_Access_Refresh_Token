package spring.com.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.com.security.entities.ConfirmationToken;
import spring.com.security.service.IConfirmationTokenService;

@RestController
@RequestMapping("/token")
public class TokenControllers {

	@Autowired
	IConfirmationTokenService confirmTokenService;
	
	@PostMapping("/generateUserToken/{idUser}")
	public void generateUSerToken(@RequestBody ConfirmationToken confirmToken,@PathVariable String idUser) {
		confirmTokenService.saveConfirmationToken(confirmToken, idUser);
	}
	
	@GetMapping("/confirmToken/{token}")
	public String confirmToken(@PathVariable String token) {
		return confirmTokenService.confirmToken(token);
	}
}
