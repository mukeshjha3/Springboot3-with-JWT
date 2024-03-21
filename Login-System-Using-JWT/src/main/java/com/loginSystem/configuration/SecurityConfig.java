package com.loginSystem.configuration;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.loginSystem.Filter.JWTTokenGenerator;
import com.loginSystem.Filter.JWTTokenValidator;

@Configuration
public class SecurityConfig {

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();

	}

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf-> csrf.disable())
				.authorizeHttpRequests((requests) -> requests.requestMatchers("/adminlogin", "/hello","/goodmorning").authenticated() 
						.requestMatchers("/register/admin").permitAll()) 																
				  .addFilterAfter(new JWTTokenGenerator(), BasicAuthenticationFilter.class)
				  .addFilterBefore(new JWTTokenValidator(), BasicAuthenticationFilter.class);
		http.httpBasic(withDefaults());
				
		return http.build(); 
	}

}

/*
 * http.csrf().disable() .authorizeHttpRequests((requests) -> requests.
 * requestMatchers("/login", "/hello").authenticated()
 * .requestMatchers("/register/admin").permitAll()) .sessionManagement(session
 * -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
 * .addFilterAfter(new JWTTokenGenerator(), BasicAuthenticationFilter.class);
 * 
 * http.formLogin(withDefaults()); // Configure form login
 * http.httpBasic(withDefaults()); // Configure HTTP Basic authentication
 * 
 * return http.build(); // Build and return the security filter chain
 */