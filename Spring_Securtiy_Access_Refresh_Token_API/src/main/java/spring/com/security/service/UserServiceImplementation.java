package spring.com.security.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import spring.com.security.entities.ConfirmationToken;
import spring.com.security.entities.User;
import spring.com.security.repository.ITokenRepository;
import spring.com.security.repository.IUserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImplementation implements IUserService, UserDetailsService {

	Logger log = LoggerFactory.getLogger(UserServiceImplementation.class);

	@Autowired
	private IUserRepository userRepository;
	@Autowired
	EmailSender emailSender;

	@Autowired
	ITokenRepository tokenRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);

		if (user == null) {
			log.error("User not find in Database !!");
			throw new UsernameNotFoundException("User not find in Database !!");
		} else {
			log.info(" User Founded with username :  **  ", username + "    **");
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role));
		});
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				authorities);
	}

	@Override
	@Async
	public User addUser(User user) {

		
		
		if ((userRepository.findByUsername(user.getUsername()) != null)
				|| (userRepository.findByEmail(user.getEmail()) != null)) {
			throw new IllegalStateException("User or Email Already Exists  !! ");
		}

		else {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			log.info("Saving User in DataBase  ....");


			User registredUser = userRepository.save(user);

			String token = UUID.randomUUID().toString();
			ConfirmationToken confirmToken = new ConfirmationToken();
			confirmToken.setToken(token);
			confirmToken.setCretationDate(LocalDateTime.now());
			confirmToken.setExpirationDate(LocalDateTime.now().plusMinutes(15));
			confirmToken.setIdUser(registredUser.getId());

			tokenRepository.save(confirmToken);
			String link="http://localhost:6039/token/confirmToken/"+token;
			
			emailSender.send(user.getEmail(), emailSender.buildEmail(user.getNom(), link));
			
			return registredUser;

		}

	}

	@Override
	public User getOneUserByUsername(String username) {
		log.info("Get User from DataBase with username : *" + username + "  *");
		return userRepository.findByUsername(username);
	}

	@Override
	public List<User> getAllUsers() {
		log.info("Get All users From DataBase  ....");
		return userRepository.findAll();
	}

	@Override
	public String enableUser(String emailUser) {
		User u = userRepository.findByEmail(emailUser);
		u.setEnabled(true);
		userRepository.save(u);
		return ("User with eamil " + u.getEmail() + " is eanbled ");
	}

}
