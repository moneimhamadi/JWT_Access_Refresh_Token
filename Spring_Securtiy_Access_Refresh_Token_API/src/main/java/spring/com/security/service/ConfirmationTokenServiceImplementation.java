package spring.com.security.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.com.security.entities.ConfirmationToken;
import spring.com.security.entities.User;
import spring.com.security.repository.ITokenRepository;
import spring.com.security.repository.IUserRepository;

@Service
public class ConfirmationTokenServiceImplementation implements IConfirmationTokenService {

	@Autowired
	ITokenRepository tokenRepository;
	@Autowired
	IUserRepository userRepository;
	@Autowired
	IUserService userService;
	
	

	@Override
	public void saveConfirmationToken(ConfirmationToken confirmToken, String idUser) {

		String token = UUID.randomUUID().toString();
		confirmToken.setToken(token);
		confirmToken.setCretationDate(LocalDateTime.now());
		confirmToken.setExpirationDate(LocalDateTime.now().plusMinutes(15));
		confirmToken.setIdUser(idUser);

		tokenRepository.save(confirmToken);

	}

	@Override
	public String confirmToken(String token) {
		ConfirmationToken confirmationToken = tokenRepository.findByToken(token)
				.orElseThrow(() -> new IllegalStateException("Token not found !!"));

		if (confirmationToken.getConfirmationDate() != null) {
			throw new IllegalStateException("Email already confirmed");
		}

		LocalDateTime expiredAt = confirmationToken.getExpirationDate();

		if (expiredAt.isBefore(LocalDateTime.now())) {
			throw new IllegalStateException("Token is expired  !!");
		}

		confirmationToken.setConfirmationDate(LocalDateTime.now());
		tokenRepository.save(confirmationToken);
		User userToEnable=userRepository.findById((confirmationToken.getIdUser())).get();
		userService.enableUser(userToEnable.getEmail() );
		return "Confirmed";
	}

}
