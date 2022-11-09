package spring.com.security.controllers;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpStatus.FORBIDDEN;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;

import spring.com.security.entities.User;
import spring.com.security.service.IUserService;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;

	Logger log = LoggerFactory.getLogger(UserController.class);

	@GetMapping("/getAllUsers")
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.ok(userService.getAllUsers());
	}

	@PostMapping("/saveUser")
	public ResponseEntity<User> addUser(@RequestBody User user) {
		return		ResponseEntity.ok(userService.addUser(user));
		 //ResponseEntity.ok().build();
	}

	@GetMapping("/token/refresh")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String authorizationHeader = request.getHeader(org.springframework.http.HttpHeaders.AUTHORIZATION);

		if (authorizationHeader != null & authorizationHeader.startsWith("Bearer")) {

			try {
				String refresh_token = authorizationHeader.substring("Bearer ".length());
				Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
				JWTVerifier verifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = verifier.verify(refresh_token);
				String username = decodedJWT.getSubject();
				User user = userService.getOneUserByUsername(username);
				String access_Token = JWT.create().withSubject(user.getUsername())
						.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
						.withIssuer(request.getRequestURL().toString())
						.withClaim("roles", user.getRoles().stream().map(role -> role).collect(Collectors.toList()))
						.sign(algorithm);

				Map<String, String> access_refresh_tokens = new HashMap<>();
				access_refresh_tokens.put("access_Token", access_Token);
				access_refresh_tokens.put("refresh_Token", refresh_token);
//				access_refresh_tokens.put("username", user.getUsername());
				response.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), access_refresh_tokens);

			} catch (Exception e) {
				//log.error("Error LOGGING IN : {}", e.getMessage());
				response.setHeader("ERROR ", e.getMessage());
				response.setStatus(FORBIDDEN.value());
				// response.sendError(FORBIDDEN.value());
				Map<String, String> error = new HashMap<>();
				error.put("ERROR_MESSAGE", e.getMessage());

				response.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), error);
			}

		} else {
			throw new RuntimeException("RFRERSH TOKEN IS MISSING");
		}

	}
	@GetMapping("/isEnabled/{username}")
	public int isEnabled(@PathVariable String username) {
		return userService.isEnabled(username);
	}
	
	
	@PutMapping("/changeUserPassword/{token}/{newPassword}")
	public int changeUserPassword (@PathVariable String token,@PathVariable String newPassword) {
		return userService.changePassword(token, newPassword);
	}


}
