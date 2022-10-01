package spring.com.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import spring.com.security.entities.User;
import spring.com.security.repository.IUserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImplementation implements IUserService, UserDetailsService {

	Logger log = LoggerFactory.getLogger(UserServiceImplementation.class);

	
	
	@Autowired
	private IUserRepository userRepository;
	

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
	public User addUser(User user) {
		BCryptPasswordEncoder passwordEncoder =new BCryptPasswordEncoder() ;
		log.info("Saving User in DataBase  ....");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
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

}
