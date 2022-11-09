package spring.com.security.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import spring.com.security.service.UserServiceImplementation;

public class CustomAuthFilter extends UsernamePasswordAuthenticationFilter {
	Logger log = LoggerFactory.getLogger(UserServiceImplementation.class);

	private final AuthenticationManager authManager;

	public CustomAuthFilter(AuthenticationManager authenticationManager) {
		this.authManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		log.info("UserName is " + username);
		log.info("password is " + password);

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

		return authManager.authenticate(authToken);

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {
		// super.successfulAuthentication(request, response, chain, authResult);
		User user = (User) authentication.getPrincipal();
		Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
		String access_Token = JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 1 * 60 * 1000))
				.withIssuer(request.getRequestURL().toString())
				.withClaim("roles",
						user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algorithm);
		String refresh_Token = JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
				.withIssuer(request.getRequestURL().toString()).sign(algorithm);
		
		List<String> roles=user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

		//		response.setHeader("access_Token",access_Token);
//		response.setHeader("refresh_Token", refresh_Token);
		Map<String, Object> access_refresh_tokens=new HashMap<>();
		access_refresh_tokens.put("access_Token", access_Token);
		access_refresh_tokens.put("refresh_Token", refresh_Token);
		access_refresh_tokens.put("username", user.getUsername());
		access_refresh_tokens.put("roles", roles);
	
		response.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue( response.getOutputStream(), access_refresh_tokens);
	}

}
