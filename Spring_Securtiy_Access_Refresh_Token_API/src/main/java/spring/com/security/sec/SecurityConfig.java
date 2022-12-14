package spring.com.security.sec;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import lombok.RequiredArgsConstructor;
import spring.com.security.filter.CustomAuthFilter;
import spring.com.security.filter.CustomAuthorizationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;

	private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		CustomAuthFilter cunstomFilter = new CustomAuthFilter(authenticationManagerBean());
		cunstomFilter.setFilterProcessesUrl("/api/login");

		http.csrf().disable();

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//		http.authorizeRequests().antMatchers("/api/login/**", "/user/token/refresh/**", "/token/confirmToken/**",
//				"/user/saveUser", "/user/isEnabled/**").permitAll();

		http.authorizeRequests().antMatchers("/user/getAllUsers").hasAnyAuthority("ADMINISTRATOR");
		// http.authorizeRequests().antMatchers("/user/saveUser").permitAll();
		http.authorizeRequests().anyRequest().permitAll();
		http.addFilter(cunstomFilter).cors();
		http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowCredentials(true);
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		configuration.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
		source.registerCorsConfiguration("/**", configuration);
		return new CorsFilter(source);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/user/saveUser", "/user/token/refresh/**", "/token/confirmToken/{token}",
				"/user/saveUser", "/user/isEnabled/**", "/email/sendEmailForgetPassword/{username}",
				"/user/changeUserPassword/{token}/{newPassword}");
	}

}
